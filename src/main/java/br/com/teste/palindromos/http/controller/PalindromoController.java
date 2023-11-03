package br.com.teste.palindromos.http.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.palindromos.entity.Palindromo;
import br.com.teste.palindromos.service.impl.PalindromoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/palindromos")
public class PalindromoController {

    @Autowired
    private PalindromoServiceImpl palindromoService;

    @PostMapping
    @Operation(description = "Caça Palindromos presentes na matiz e persiste na base")
    @ApiResponse(responseCode = "200", description = "Retorna os Palindromos encontrados e persistidos")
    @ApiResponse(responseCode = "400", description = "Não foi posivel presistir os Palindromos")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Palindromo> encontraPalindromos(@RequestBody String matriz) {
        return palindromoService.encontrarPalindromos(matriz);
    }

    @GetMapping
    @Operation(description = "Busca lista de Palindromos")
    @ApiResponse(responseCode = "200", description = "Retorna os Palindromos")
    @ApiResponse(responseCode = "400", description = "Não foi posivel recuperar a lista de Palindromos")
    @ResponseStatus(HttpStatus.OK)
    public List<Palindromo> listaTodosPalindromos() {
        return palindromoService.listarTodosPalindromos();
    }
}
