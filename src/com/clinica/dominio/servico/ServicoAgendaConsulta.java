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
        Animal animal = animalRepo.buscarPorId(animalId)
            .orElseThrow(() -> new AnimalNaoEncontradoException("Animal não encontrado com ID: " + animalId));

        Veterinario veterinario = vetRepo.buscarPorId(veterinarioId)
            .orElseThrow(() -> new VeterinarioIndisponivelException("Veterinario não encontrado com ID: " + veterinarioId));

        if (!veterinario.estaDisponivel()) {
            throw new VeterinarioIndisponivelException("Veterinario indisponivel: " + veterinario.getNome());
        }

        veterinario.ocupar();
        vetRepo.salvar(veterinario);

        Consulta consulta = new Consulta(null, animal, veterinario, data, hora, tipo, SituacaoConsulta.AGENDADA, null);
        consultaRepo.salvar(consulta);
        notificacao.notificarAgendamento(animal.getTutor(), animal, consulta);

        return consulta;
    }

    @Override
    public Consulta realizarConsulta(Long consultaId, String observacoes) {
        Consulta consulta = consultaRepo.buscarPorId(consultaId)
            .orElseThrow(() -> new RuntimeException("Consulta nao encontrada com ID: " + consultaId));

        consulta.realizar(observacoes);

        Veterinario veterinario = consulta.getVeterinario();
        if (veterinario != null) {
            veterinario.liberar();
            vetRepo.salvar(veterinario);
        }

        consultaRepo.salvar(consulta);
        return consulta;
    }

    @Override
    public void cancelarConsulta(Long consultaId) {
        Consulta consulta = consultaRepo.buscarPorId(consultaId)
            .orElseThrow(() -> new RuntimeException("Consulta nao encontrada com ID: " + consultaId));

        consulta.cancelar();

        Veterinario veterinario = consulta.getVeterinario();
        if (veterinario != null) {
            veterinario.liberar();
            vetRepo.salvar(veterinario);
        }

        Animal animal = consulta.getAnimal();
        notificacao.notificarCancelamento(animal.getTutor(), animal, "Consulta cancelada pelo sistema.");
        consultaRepo.salvar(consulta);
    }

    @Override
    public List<Consulta> obterHistoricoAnimal(Long animalId) {
        return consultaRepo.buscarPorAnimal(animalId);
    }

    @Override
    public List<Consulta> obterAgendaVeterinario(Long vetId) {
        return consultaRepo.buscarPorVeterinario(vetId);
    }
}
