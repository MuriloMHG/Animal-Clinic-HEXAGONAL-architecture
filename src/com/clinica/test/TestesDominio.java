package com.clinica.test;

import com.clinica.dominio.excecao.AnimalNaoEncontradoException;
import com.clinica.dominio.excecao.VeterinarioIndisponivelException;
import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.SituacaoConsulta;
import com.clinica.dominio.modelo.SituacaoVeterinario;
import com.clinica.dominio.modelo.TipoConsulta;
import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;
import com.clinica.dominio.servico.ServicoAgendaConsulta;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestesDominio {
    public static void main(String[] args) {
        agendamentoBemSucedido();
        veterinarioIndisponivel();
        animalNaoEncontrado();
        transicaoInvalida();
        cancelamentoLiberaVeterinario();
        System.out.println("Todos os testes passaram.");
    }

    private static void agendamentoBemSucedido() {
        FakeAnimalRepositorio animalRepo = new FakeAnimalRepositorio();
        FakeVeterinarioRepositorio vetRepo = new FakeVeterinarioRepositorio();
        FakeConsultaRepositorio consultaRepo = new FakeConsultaRepositorio();

        Animal animal = new Animal(1L, "Thor", "Cachorro", "Labrador", LocalDate.of(2020, 1, 1), "João");
        Veterinario vet = new Veterinario(1L, "Dra. Ana", "CRMV 1", "Geral", SituacaoVeterinario.DISPONIVEL);
        animalRepo.salvar(animal);
        vetRepo.salvar(vet);

        PortaAgendaConsulta agenda = new ServicoAgendaConsulta(animalRepo, vetRepo, consultaRepo, new FakeNotificacao());
        Consulta consulta = agenda.agendarConsulta(1L, 1L, LocalDate.now(), LocalTime.of(10, 0), TipoConsulta.ROTINA);

        assert consulta.getSituacao() == SituacaoConsulta.AGENDADA : "Consulta deveria estar AGENDADA.";
        assert !vet.estaDisponivel() : "Veterinário deveria estar OCUPADO.";
    }

    private static void veterinarioIndisponivel() {
        FakeAnimalRepositorio animalRepo = new FakeAnimalRepositorio();
        FakeVeterinarioRepositorio vetRepo = new FakeVeterinarioRepositorio();
        FakeConsultaRepositorio consultaRepo = new FakeConsultaRepositorio();

        animalRepo.salvar(new Animal(1L, "Thor", "Cachorro", "Labrador", LocalDate.of(2020, 1, 1), "João"));
        vetRepo.salvar(new Veterinario(1L, "Dra. Ana", "CRMV 1", "Geral", SituacaoVeterinario.OCUPADO));

        PortaAgendaConsulta agenda = new ServicoAgendaConsulta(animalRepo, vetRepo, consultaRepo, new FakeNotificacao());

        try {
            agenda.agendarConsulta(1L, 1L, LocalDate.now(), LocalTime.of(10, 0), TipoConsulta.ROTINA);
            assert false : "Deveria lançar VeterinarioIndisponivelException.";
        } catch (VeterinarioIndisponivelException e) {
            assert e.getMessage() != null;
        }
    }

    private static void animalNaoEncontrado() {
        PortaAgendaConsulta agenda = new ServicoAgendaConsulta(
            new FakeAnimalRepositorio(),
            new FakeVeterinarioRepositorio(),
            new FakeConsultaRepositorio(),
            new FakeNotificacao()
        );

        try {
            agenda.agendarConsulta(999L, 1L, LocalDate.now(), LocalTime.of(10, 0), TipoConsulta.ROTINA);
            assert false : "Deveria lançar AnimalNaoEncontradoException.";
        } catch (AnimalNaoEncontradoException e) {
            assert e.getMessage() != null;
        }
    }

    private static void transicaoInvalida() {
        Animal animal = new Animal(1L, "Thor", "Cachorro", "Labrador", LocalDate.of(2020, 1, 1), "João");
        Veterinario vet = new Veterinario(1L, "Dra. Ana", "CRMV 1", "Geral", SituacaoVeterinario.DISPONIVEL);
        Consulta consulta = new Consulta(1L, animal, vet, LocalDate.now(), LocalTime.of(10, 0), TipoConsulta.ROTINA, SituacaoConsulta.AGENDADA, null);

        consulta.cancelar();

        try {
            consulta.realizar("Tentativa inválida.");
            assert false : "Deveria lançar IllegalStateException.";
        } catch (IllegalStateException e) {
            assert e.getMessage() != null;
        }
    }

    private static void cancelamentoLiberaVeterinario() {
        FakeAnimalRepositorio animalRepo = new FakeAnimalRepositorio();
        FakeVeterinarioRepositorio vetRepo = new FakeVeterinarioRepositorio();
        FakeConsultaRepositorio consultaRepo = new FakeConsultaRepositorio();

        Animal animal = new Animal(1L, "Thor", "Cachorro", "Labrador", LocalDate.of(2020, 1, 1), "João");
        Veterinario vet = new Veterinario(1L, "Dra. Ana", "CRMV 1", "Geral", SituacaoVeterinario.DISPONIVEL);
        animalRepo.salvar(animal);
        vetRepo.salvar(vet);

        PortaAgendaConsulta agenda = new ServicoAgendaConsulta(animalRepo, vetRepo, consultaRepo, new FakeNotificacao());
        Consulta consulta = agenda.agendarConsulta(1L, 1L, LocalDate.now(), LocalTime.of(10, 0), TipoConsulta.ROTINA);
        agenda.cancelarConsulta(consulta.getId());

        assert vet.estaDisponivel() : "Veterinário deveria estar DISPONIVEL após cancelamento.";
    }

    static class FakeAnimalRepositorio implements PortaAnimalRepositorio {
        private final Map<Long, Animal> store = new HashMap<>();
        public void salvar(Animal animal) { store.put(animal.getId(), animal); }
        public Optional<Animal> buscarPorId(Long id) { return Optional.ofNullable(store.get(id)); }
        public List<Animal> listarPorTutor(String tutor) { return new ArrayList<>(); }
        public List<Animal> listarTodos() { return new ArrayList<>(store.values()); }
        public void remover(Long id) { store.remove(id); }
    }

    static class FakeVeterinarioRepositorio implements PortaVeterinarioRepositorio {
        private final Map<Long, Veterinario> store = new HashMap<>();
        public void salvar(Veterinario vet) { store.put(vet.getId(), vet); }
        public Optional<Veterinario> buscarPorId(Long id) { return Optional.ofNullable(store.get(id)); }
        public List<Veterinario> buscarDisponiveis() { return store.values().stream().filter(Veterinario::estaDisponivel).collect(Collectors.toList()); }
        public List<Veterinario> buscarPorEspecialidade(String especialidade) { return new ArrayList<>(); }
    }

    static class FakeConsultaRepositorio implements PortaConsultaRepositorio {
        private final Map<Long, Consulta> store = new HashMap<>();
        private long proximoId = 1L;
        public void salvar(Consulta consulta) {
            if (consulta.getId() == null) {
                consulta.setId(proximoId++);
            }
            store.put(consulta.getId(), consulta);
        }
        public Optional<Consulta> buscarPorId(Long id) { return Optional.ofNullable(store.get(id)); }
        public List<Consulta> buscarPorAnimal(Long animalId) { return store.values().stream().filter(c -> c.getAnimal().getId().equals(animalId)).collect(Collectors.toList()); }
        public List<Consulta> buscarPorVeterinario(Long vetId) { return store.values().stream().filter(c -> c.getVeterinario().getId().equals(vetId)).collect(Collectors.toList()); }
        public List<Consulta> listarAgendadas() { return store.values().stream().filter(c -> c.getSituacao() == SituacaoConsulta.AGENDADA).collect(Collectors.toList()); }
    }

    static class FakeNotificacao implements PortaNotificacaoTutor {
        public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {}
        public void notificarCancelamento(String tutor, Animal animal, String motivo) {}
    }
}
