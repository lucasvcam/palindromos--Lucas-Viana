package br.com.teste.palindromos.http.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.teste.palindromos.entity.Palindromo;
import br.com.teste.palindromos.service.impl.PalindromoServiceImpl;

@RestController
@RequestMapping("/api/palindromos")
public class PalindromoController {

    @Autowired
    private PalindromoServiceImpl palindromoService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Palindromo> encontraPalindromos(@RequestBody String matrix) {
        return palindromoService.encontraPalindromos(matrix);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Palindromo> listaTodosPalindromos() {
        return palindromoService.listaTodosPalindromos();
    }
     
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Palindromo listaPorIdPalindromos(@PathVariable("id") Long id) {
        return palindromoService.buscarPorIdPalindromo(id)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Palindromo não encontrado"));
    }
}
