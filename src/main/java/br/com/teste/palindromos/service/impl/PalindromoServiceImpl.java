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

	private List<Palindromo> palindromosEncontrados = new ArrayList<Palindromo>();

	public List<Palindromo> encontraPalindromos(String jsonMatrix) {
		JSONObject jsonObject = new JSONObject(jsonMatrix);
		JSONArray matrizArray = jsonObject.getJSONArray("nome_da_matriz");
		int numLinhas = matrizArray.length();
		int numCols = numLinhas;

		if (numLinhas > 10 || numCols > 10) {
			System.out.println("A matriz não pode ter mais de 10 linhas ou 10 colunas.");
			return null;
		}

		char[][] matriz = new char[numLinhas][numCols];
		Palindromo palindromo = new Palindromo();
		for (int i = 0; i < numLinhas; i++) {
			for (int j = 0; j < numCols; j++) {
				matriz[i][j] = matrizArray.getJSONArray(i).getString(j).charAt(0);
			}
		}
		for (int linha = 0; linha < numLinhas; linha++) {
			for (int coluna = 0; coluna < numCols; coluna++) {
				// Horizontal
				for (int len = 1; coluna + len <= numCols; len++) {
					String palavraHorizontal = new String(matriz[linha], coluna, len);
					if (ehPalindromo(palavraHorizontal)) {
						palindromo.setPalavra(palavraHorizontal);
						palindromosEncontrados.add(palindromo);
					}
				}

				// Vertical
				for (int len = 1; linha + len <= numLinhas; len++) {
					StringBuilder palavraVertical = new StringBuilder();
					for (int i = 0; i < len; i++) {
						palavraVertical.append(matriz[linha + i][coluna]);
					}
					if (ehPalindromo(palavraVertical.toString())) {
						palindromo.setPalavra(palavraVertical.toString());
						palindromosEncontrados.add(palindromo);
					}
				}

				// Diagonal (cima-esquerda para baixo-direita)
				for (int len = 1; linha + len <= numLinhas && coluna + len <= numCols; len++) {
					StringBuilder palavraDiagonal = new StringBuilder();
					for (int i = 0; i < len; i++) {
						palavraDiagonal.append(matriz[linha + i][coluna + i]);
					}
					if (ehPalindromo(palavraDiagonal.toString())) {
						palindromo.setPalavra(palavraDiagonal.toString());
						palindromosEncontrados.add(palindromo);
					}
				}

				// Diagonal (cima-direita para baixo-esquerda)
				for (int len = 1; linha + len <= numLinhas && coluna - len + 1 >= 0; len++) {
					StringBuilder palavraDiagonal = new StringBuilder();
					for (int i = 0; i < len; i++) {
						palavraDiagonal.append(matriz[linha + i][coluna - i]);
					}
					if (ehPalindromo(palavraDiagonal.toString())) {
						palindromo.setPalavra(palavraDiagonal.toString());
						palindromosEncontrados.add(palindromo);
					}
				}
			}
		}
		return persistePalindromos(palindromosEncontrados);
	}

	private static boolean ehPalindromo(String palavra) {
		int comprimento = palavra.length();
		for (int i = 0; i < comprimento / 2; i++) {
			if (palavra.charAt(i) != palavra.charAt(comprimento - i - 1)) {
				return false;
			}
		}
		return true;
	}

	private List<Palindromo> persistePalindromos(List<Palindromo> palindromos) {
		palindromos.forEach(palindromo -> {
			palindromeRepository.save(palindromo);
		});
		return palindromos;

	}

	public List<Palindromo> listaTodosPalindromos() {
		return palindromeRepository.findAll();
	}

	public Optional<Palindromo> buscarPorIdPalindromo(Long id) {
		return palindromeRepository.findById(id);
	}
}
