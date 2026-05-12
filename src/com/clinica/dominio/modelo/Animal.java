package com.clinica.dominio.modelo;

import java.time.LocalDate;

public class Animal {
    private Long id;
    private final String nome;
    private final String especie;
    private final String raca;
    private final LocalDate dataNascimento;
    private final String tutor;

    public Animal(Long id, String nome, String especie, String raca, LocalDate dataNascimento, String tutor) {
        // TODO: validar nome, especie e tutor: não podem ser nulos nem vazios.
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.tutor = tutor;
    }

    public int calcularIdadeEmAnos() {
        // TODO: calcular idade usando dataNascimento e LocalDate.now().
        return 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTutor() { return tutor; }
}
