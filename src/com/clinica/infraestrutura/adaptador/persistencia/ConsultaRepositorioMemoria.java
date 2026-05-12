package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.SituacaoConsulta;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ConsultaRepositorioMemoria implements PortaConsultaRepositorio {
    private final Map<Long, Consulta> store = new HashMap<>();
    private long proximoId = 1L;

    @Override
    public void salvar(Consulta consulta) {
        // TODO: se consulta.getId() for null, atribuir ID automático.
        // TODO: salvar no HashMap.
    }

    @Override
    public Optional<Consulta> buscarPorId(Long id) {
        // TODO: retornar consulta pelo id.
        return Optional.empty();
    }

    @Override
    public List<Consulta> buscarPorAnimal(Long animalId) {
        // TODO: filtrar pelo id do animal associado à consulta.
        return new ArrayList<>();
    }

    @Override
    public List<Consulta> buscarPorVeterinario(Long vetId) {
        // TODO: filtrar pelo id do veterinário associado à consulta.
        return new ArrayList<>();
    }

    @Override
    public List<Consulta> listarAgendadas() {
        // TODO: retornar consultas cuja situação seja AGENDADA.
        return new ArrayList<>();
    }
}
