package br.com.teste.palindromos.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.teste.palindromos.entity.Palindromo;

public interface PalindromoRepository extends JpaRepository<Palindromo, Long> {
}
