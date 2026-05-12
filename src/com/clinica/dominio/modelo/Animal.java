package com.clinica.dominio.modelo;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;

public class Animal {
    private Long id;
    private final String nome;
    private final String especie;
    private final String raca;
    private final LocalDate dataNascimento;
    private final String tutor;

    public Animal(Long id, String nome, String especie, String raca, LocalDate dataNascimento, String tutor) {
        validarTexto(raca, "raca");
        validarTexto(nome, "nome");
        validarTexto(tutor, "tutor");
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.tutor = tutor;
    }

    public int calcularIdadeEmAnos() {
        if (dataNascimento == null)
        {
            throw new IllegalArgumentException("Data de nascimento não pode ser nula !");
        }
        try{
            LocalDate today = LocalDate.now();

            return Period.between(dataNascimento, today).getYears();
        } catch(DateTimeException e){
            throw new DateTimeException("Erro ao calcular idade: " + e.getMessage());
        }
    }

    private void validarTexto(String valor, String campo)
    {
        if(valor == null || valor.trim().isEmpty()){
            throw new IllegalArgumentException("O campo " + campo + " é obrigatório !");
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTutor() { return tutor; }
}
