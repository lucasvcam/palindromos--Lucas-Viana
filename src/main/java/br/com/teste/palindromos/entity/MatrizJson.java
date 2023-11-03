package br.com.teste.palindromos.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatrizJson {

	@JsonProperty("matriz")
	private char[][] matriz;

	public MatrizJson() {
	}

	public MatrizJson(char[][] matriz) {
		this.matriz = matriz;
	}

	public char[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(char[][] matriz) {
		this.matriz = matriz;
	}
}
