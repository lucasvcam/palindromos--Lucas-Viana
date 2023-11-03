package br.com.teste.palindromos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.palindromos.entity.Palindromo;
import br.com.teste.palindromos.repository.PalindromoRepository;
import br.com.teste.palindromos.service.PalindromoService;

@Service
public class PalindromoServiceImpl implements PalindromoService {

	@Autowired
	private PalindromoRepository palindromeRepository;

	private static List<Palindromo> palindromosEncontrados = new ArrayList<>();

	public List<Palindromo> encontrarPalindromos(String jsonMatriz) {

		try {
			JSONObject jsonObj = new JSONObject(jsonMatriz);

			JSONArray matrizJson = jsonObj.getJSONArray("matriz");
			int numRows = matrizJson.length();

			if (numRows > 10) {
				System.out.println("O número de linhas excede o máximo de 10.");
				return null;
			}

			char[][] matriz = new char[numRows][];
			int numCols = 0;

			for (int i = 0; i < numRows; i++) {
				JSONArray row = matrizJson.getJSONArray(i);
				int currentCols = row.length();
				if (currentCols > 10) {
					System.out.println("O número de colunas na linha " + i + " excede o máximo de 10.");
					return null;
				}
				if (numCols == 0) {
					numCols = currentCols;
				} else if (numCols != currentCols) {
					System.out.println("A matriz não é quadrada. O número de colunas na linha " + i
							+ " é diferente das linhas anteriores.");
					return null;
				}
				matriz[i] = new char[currentCols];
				for (int j = 0; j < currentCols; j++) {
					matriz[i][j] = row.getString(j).charAt(0);
				}
			}
			cacaPalindromosNaMatriz(matriz);

			return persistirPalindromos(palindromosEncontrados);
		} catch (Exception e) {
			System.out.println("Erro ao analisar o JSON: " + e.getMessage());
			return null;
		}
	}

	public List<Palindromo> cacaPalindromosNaMatriz(char[][] matriz) {
		int numLinhas = matriz.length;
		int numCols = matriz[0].length;
		for (int linha = 0; linha < numLinhas; linha++) {
			for (int coluna = 0; coluna < numCols; coluna++) {
				// Horizontal (esquerda para direita)
				for (int len = 1; coluna + len <= numCols; len++) {
					String palavraHorizontal = new String(matriz[linha], coluna, len);
					if (ehPalindromo(palavraHorizontal)) {
						Palindromo palindromoEncontrado = new Palindromo();
						palindromoEncontrado.setPalavra(palavraHorizontal);
						inserirPalindromoNaLista(palavraHorizontal);
					}
				}

				// Vertical (cima para baixo)
				for (int len = 1; linha + len <= numLinhas; len++) {
					StringBuilder palavraVertical = new StringBuilder();
					for (int i = 0; i < len; i++) {
						palavraVertical.append(matriz[linha + i][coluna]);
					}
					if (ehPalindromo(palavraVertical.toString())) {
						inserirPalindromoNaLista(palavraVertical.toString());
					}
				}


				// Diagonal (cima-esquerda para baixo-direita)
				for (int len = 1; linha + len <= numLinhas && coluna + len <= numCols; len++) {
					StringBuilder palavraDiagonal = new StringBuilder();
					for (int i = 0; i < len; i++) {
						palavraDiagonal.append(matriz[linha + i][coluna + i]);
					}
					if (ehPalindromo(palavraDiagonal.toString())) {
						inserirPalindromoNaLista(palavraDiagonal.toString());
					}
				}

				// Diagonal (cima-direita para baixo-esquerda)
				for (int len = 1; linha + len <= numLinhas && coluna - len >= 0; len++) {
					StringBuilder palavraDiagonal = new StringBuilder();
					for (int i = 0; i < len; i++) {
						palavraDiagonal.append(matriz[linha + i][coluna - i]);
					}
					if (ehPalindromo(palavraDiagonal.toString())) {
						inserirPalindromoNaLista(palavraDiagonal.toString());
					}
				}
			}
		}
		return palindromosEncontrados;
	}

	public static void inserirPalindromoNaLista(String palavra) {
		Palindromo palindromoEncontrado = new Palindromo();
		palindromoEncontrado.setPalavra(palavra);
		palindromosEncontrados.add(palindromoEncontrado);
	}

	public static boolean ehPalindromo(String palavra) {
		palavra = palavra.replaceAll("\\s", "").toLowerCase();
		if (palavra.length() < 4) {
			return false;
		}
		int comprimento = palavra.length();
		for (int i = 0; i < comprimento / 2; i++) {
			if (palavra.charAt(i) != palavra.charAt(comprimento - i - 1)) {
				return false;
			}
		}
		return true;
	}

	private List<Palindromo> persistirPalindromos(List<Palindromo> palindromosEncontrados) {
		return palindromeRepository.saveAll(palindromosEncontrados);

	}

	public List<Palindromo> listarTodosPalindromos() {
		return palindromeRepository.findAll();
	}

	public Optional<Palindromo> buscarPorIdPalindromo(Long id) {
		return palindromeRepository.findById(id);
	}
}
