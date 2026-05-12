package com.clinica.apresentacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.modelo.SituacaoVeterinario;
import com.clinica.dominio.modelo.TipoConsulta;
import com.clinica.dominio.modelo.Veterinario;
import com.clinica.dominio.porta.entrada.PortaAgendaConsulta;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;
import com.clinica.dominio.porta.saida.PortaConsultaRepositorio;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;
import com.clinica.dominio.porta.saida.PortaVeterinarioRepositorio;
import com.clinica.dominio.servico.ServicoAgendaConsulta;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoConsole;
import com.clinica.infraestrutura.adaptador.notificacao.NotificacaoCsv;
import com.clinica.infraestrutura.adaptador.persistencia.AnimalRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.ConsultaRepositorioMemoria;
import com.clinica.infraestrutura.adaptador.persistencia.VeterinarioRepositorioMemoria;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        PortaAnimalRepositorio animais = new AnimalRepositorioMemoria();
        PortaVeterinarioRepositorio vets = new VeterinarioRepositorioMemoria();
        PortaConsultaRepositorio consultas = new ConsultaRepositorioMemoria();

        PortaNotificacaoTutor notifConsole = new NotificacaoConsole();
        PortaAgendaConsulta agendaConsole = new ServicoAgendaConsulta(animais, vets, consultas, notifConsole);

        PortaNotificacaoTutor notifCsv = new NotificacaoCsv("notificacoes.csv");
        PortaAgendaConsulta agendaCsv = new ServicoAgendaConsulta(animais, vets, consultas, notifCsv);

        Animal thor = new Animal(null, "Thor", "Cachorro", "Labrador", LocalDate.of(2020, 3, 10), "João Silva");
        Animal luna = new Animal(null, "Luna", "Gato", "Siamês", LocalDate.of(2021, 8, 20), "Maria Souza");
        animais.salvar(thor);
        animais.salvar(luna);

        Veterinario beatriz = new Veterinario(null, "Dra. Beatriz", "CRMV-GO 12345", "Clínica Geral", SituacaoVeterinario.DISPONIVEL);
        Veterinario marcos = new Veterinario(null, "Dr. Marcos", "CRMV-GO 67890", "Emergência", SituacaoVeterinario.DISPONIVEL);
        vets.salvar(beatriz);
        vets.salvar(marcos);

        Consulta consultaRotina = agendaConsole.agendarConsulta(
            thor.getId(),
            beatriz.getId(),
            LocalDate.of(2025, 7, 15),
            LocalTime.of(14, 30),
            TipoConsulta.ROTINA
        );

        Consulta consultaEmergencia = agendaCsv.agendarConsulta(
            luna.getId(),
            marcos.getId(),
            LocalDate.of(2025, 7, 16),
            LocalTime.of(10, 0),
            TipoConsulta.EMERGENCIA
        );

        Consulta realizada = agendaConsole.realizarConsulta(consultaRotina.getId(), "Exame de rotina sem alterações.");
        System.out.println("[CONSULTA REALIZADA] Animal: " + realizada.getAnimal().getNome() + " | Obs.: " + realizada.getObservacoes());

        agendaConsole.cancelarConsulta(consultaEmergencia.getId());
        System.out.println("Veterinário " + marcos.getNome() + " disponível após cancelamento? " + marcos.estaDisponivel());

        exibirHistoricoAnimal("Thor", agendaConsole.obterHistoricoAnimal(thor.getId()));
        exibirAgendaVeterinario("Dra. Beatriz", agendaConsole.obterAgendaVeterinario(beatriz.getId()));

        System.out.println("\nArquivo notificacoes.csv gerado com a notificação feita pelo adaptador CSV.");
    }

    private static void exibirHistoricoAnimal(String nomeAnimal, List<Consulta> historico) {
        System.out.println("\n=== Histórico de " + nomeAnimal + " ===");
        for (Consulta consulta : historico) {
            System.out.println(formatarConsulta(consulta));
        }
    }

    private static void exibirAgendaVeterinario(String nomeVeterinario, List<Consulta> agenda) {
        System.out.println("\n=== Agenda de " + nomeVeterinario + " ===");
        for (Consulta consulta : agenda) {
            System.out.println(formatarConsulta(consulta));
        }
    }

    private static String formatarConsulta(Consulta consulta) {
        return "Consulta #" + consulta.getId() + " — " + consulta.getData() + " " + consulta.getHora() +
            " | " + consulta.getTipo() + " | " + consulta.getSituacao();
    }
}
