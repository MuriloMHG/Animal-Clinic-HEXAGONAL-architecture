package com.clinica.dominio.servico;

import com.clinica.dominio.excecao.AnimalNaoEncontradoException;
import com.clinica.dominio.excecao.VeterinarioIndisponivelException;
import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.SituacaoConsulta;
import com.clinica.dominio.modelo.TipoConsulta;
import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ServicoAgendaConsulta implements PortaAgendaConsulta {
    private final PortaAnimalRepositorio animalRepo;
    private final PortaVeterinarioRepositorio vetRepo;
    private final PortaConsultaRepositorio consultaRepo;
    private final PortaNotificacaoTutor notificacao;

    public ServicoAgendaConsulta(
            PortaAnimalRepositorio animalRepo,
            PortaVeterinarioRepositorio vetRepo,
            PortaConsultaRepositorio consultaRepo,
            PortaNotificacaoTutor notificacao) {
        this.animalRepo = animalRepo;
        this.vetRepo = vetRepo;
        this.consultaRepo = consultaRepo;
        this.notificacao = notificacao;
    }

    @Override
    public Consulta agendarConsulta(Long animalId, Long veterinarioId, LocalDate data, LocalTime hora, TipoConsulta tipo) {
        // TODO: buscar animal pelo ID. Se não existir, lançar AnimalNaoEncontradoException.
        // TODO: buscar veterinário pelo ID. Se não existir ou estiver ocupado, lançar VeterinarioIndisponivelException.
        // TODO: ocupar veterinário.
        // TODO: criar Consulta com situação AGENDADA.
        // TODO: salvar consulta no consultaRepo.
        // TODO: notificar tutor.
        // TODO: retornar consulta criada.
        return null;
    }

    @Override
    public Consulta realizarConsulta(Long consultaId, String observacoes) {
        // TODO: buscar consulta. Se não existir, lançar RuntimeException.
        // TODO: chamar consulta.realizar(observacoes).
        // TODO: liberar veterinário associado.
        // TODO: salvar alterações.
        // TODO: retornar consulta atualizada.
        return null;
    }

    @Override
    public void cancelarConsulta(Long consultaId) {
        // TODO: buscar consulta. Se não existir, lançar RuntimeException.
        // TODO: chamar consulta.cancelar().
        // TODO: liberar veterinário associado.
        // TODO: notificar tutor sobre cancelamento.
        // TODO: salvar alterações.
    }

    @Override
    public List<Consulta> obterHistoricoAnimal(Long animalId) {
        // TODO: delegar para consultaRepo.buscarPorAnimal(animalId).
        return null;
    }

    @Override
    public List<Consulta> obterAgendaVeterinario(Long vetId) {
        // TODO: delegar para consultaRepo.buscarPorVeterinario(vetId).
        return null;
    }
}
