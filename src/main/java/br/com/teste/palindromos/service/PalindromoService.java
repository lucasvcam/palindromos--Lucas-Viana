package br.com.teste.palindromos.service;

import java.util.Optional;

import br.com.teste.palindromos.entity.Palindromo;

public interface PalindromoService {
	
	public Iterable<Palindromo> encontrarPalindromos(String jsonMatriz);

    public Iterable<Palindromo> listarTodosPalindromos();
    
    public Optional<Palindromo> buscarPorIdPalindromo(Long id);
    
    
}
