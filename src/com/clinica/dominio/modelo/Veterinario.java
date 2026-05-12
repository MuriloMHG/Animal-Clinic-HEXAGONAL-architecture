package com.clinica.dominio.modelo;

import com.clinica.dominio.excecao.VeterinarioIndisponivelException;

public class Veterinario {
    private Long id;
    private final String nome;
    private final String crmv;
    private final String especialidade;
    private SituacaoVeterinario situacao;

    public Veterinario(Long id, String nome, String crmv, String especialidade, SituacaoVeterinario situacao) {
        // TODO: opcionalmente validar campos obrigatórios.
        this.id = id;
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.situacao = situacao;
    }

    public boolean estaDisponivel() {
        // TODO: retornar true quando situacao == SituacaoVeterinario.DISPONIVEL.
        return false;
    }

    public void ocupar() {
        // TODO: se estiver DISPONIVEL, mudar para OCUPADO.
        // TODO: se já estiver OCUPADO, lançar VeterinarioIndisponivelException.
    }

    public void liberar() {
        // TODO: mudar situacao para DISPONIVEL.
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public String getCrmv() { return crmv; }
    public String getEspecialidade() { return especialidade; }
    public SituacaoVeterinario getSituacao() { return situacao; }
}
