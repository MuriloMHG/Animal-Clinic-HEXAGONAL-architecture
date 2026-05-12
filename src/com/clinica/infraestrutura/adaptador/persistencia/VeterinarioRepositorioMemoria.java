package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VeterinarioRepositorioMemoria implements PortaVeterinarioRepositorio {
    private final Map<Long, Veterinario> store = new HashMap<>();
    private long proximoId = 1L;

    @Override
    public void salvar(Veterinario vet) {
        // TODO: se vet.getId() for null, atribuir ID automático.
        // TODO: salvar no HashMap.
    }

    @Override
    public Optional<Veterinario> buscarPorId(Long id) {
        // TODO: retornar veterinário pelo id.
        return Optional.empty();
    }

    @Override
    public List<Veterinario> buscarDisponiveis() {
        // TODO: retornar apenas veterinários disponíveis.
        return new ArrayList<>();
    }

    @Override
    public List<Veterinario> buscarPorEspecialidade(String especialidade) {
        // TODO: filtrar por especialidade ignorando maiúsculas/minúsculas.
        return new ArrayList<>();
    }
}
