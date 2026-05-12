package com.clinica.dominio.modelo;

import com.clinica.dominio.excecao.VeterinarioIndisponivelException;

public class Veterinario {
    private Long id;
    private final String nome;
    private final String crmv;
    private final String especialidade;
    private SituacaoVeterinario situacao;

    public Veterinario(Long id, String nome, String crmv, String especialidade, SituacaoVeterinario situacao) {
        if (nome == null){
            throw new IllegalArgumentException("Nome é obrigatório !");
        }
        if (crmv == null){
            throw new IllegalArgumentException("CRMV é obrigatório !");
        }
        if (especialidade == null){
            throw new IllegalArgumentException("Especialidade é obrigatório !");
        }
        
        this.id = id;
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.situacao = situacao;
    }

    public boolean estaDisponivel() {
        return this.situacao == SituacaoVeterinario.DISPONIVEL;
    }

    public void ocupar() {
        if(!estaDisponivel())
        {
            throw new VeterinarioIndisponivelException("Esse veterinario nao esta disponivel no momento !");
        }
        this.situacao = SituacaoVeterinario.OCUPADO;
    }

    public void liberar() {
        this.situacao = SituacaoVeterinario.DISPONIVEL;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public String getCrmv() { return crmv; }
    public String getEspecialidade() { return especialidade; }
    public SituacaoVeterinario getSituacao() { return situacao; }
}
