package br.com.teste.palindromos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder 
@Entity
public class Palindromo{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	 
	@Column(name= "palavra", nullable = false)
	public String palavra;
	
	public Long getId() {
        return id;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
}
