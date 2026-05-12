package com.clinica.dominio.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Consulta {
    private Long id;
    private final Animal animal;
    private final Veterinario veterinario;
    private final LocalDate data;
    private final LocalTime hora;
    private final TipoConsulta tipo;
    private SituacaoConsulta situacao;
    private String observacoes;

    public Consulta(Long id, Animal animal, Veterinario veterinario, LocalDate data, LocalTime hora,
                    TipoConsulta tipo, SituacaoConsulta situacao, String observacoes) {
        // TODO: opcionalmente validar animal, veterinario, data, hora e tipo.
        this.id = id;
        this.animal = animal;
        this.veterinario = veterinario;
        this.data = data;
        this.hora = hora;
        this.tipo = tipo;
        this.situacao = situacao;
        this.observacoes = observacoes;
    }

    public void realizar(String observacoes) {
        // TODO: permitir apenas se situacao == AGENDADA.
        // TODO: mudar situacao para REALIZADA e gravar observacoes.
        // TODO: em transição inválida, lançar IllegalStateException com mensagem descritiva.
    }

    public void cancelar() {
        // TODO: permitir cancelamento se situacao == AGENDADA ou REALIZADA.
        // TODO: mudar situacao para CANCELADA.
        // TODO: em transição inválida, lançar IllegalStateException com mensagem descritiva.
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Animal getAnimal() { return animal; }
    public Veterinario getVeterinario() { return veterinario; }
    public LocalDate getData() { return data; }
    public LocalTime getHora() { return hora; }
    public TipoConsulta getTipo() { return tipo; }
    public SituacaoConsulta getSituacao() { return situacao; }
    public String getObservacoes() { return observacoes; }
}
