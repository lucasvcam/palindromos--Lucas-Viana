package br.com.teste.palindromos.service;

import java.util.Optional;

import br.com.teste.palindromos.entity.Palindromo;

public interface PalindromoService {
	
	public Iterable<Palindromo> encontraPalindromos(String jsonMatrix);

    public Iterable<Palindromo> listaTodosPalindromos();
    
    public Optional<Palindromo> buscarPorIdPalindromo(Long id);
    
    
}
