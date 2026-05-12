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

public class Main {
    public static void main(String[] args) {
        // TODO: instanciar os repositórios em memória.
        PortaAnimalRepositorio animais = new AnimalRepositorioMemoria();
        PortaVeterinarioRepositorio vets = new VeterinarioRepositorioMemoria();
        PortaConsultaRepositorio consultas = new ConsultaRepositorioMemoria();

        // TODO: criar adaptador Console e montar o serviço.
        PortaNotificacaoTutor notifConsole = new NotificacaoConsole();
        PortaAgendaConsulta agendaConsole = new ServicoAgendaConsulta(animais, vets, consultas, notifConsole);

        // TODO: criar adaptador CSV e montar outro serviço usando os mesmos repositórios.
        PortaNotificacaoTutor notifCsv = new NotificacaoCsv("notificacoes.csv");
        PortaAgendaConsulta agendaCsv = new ServicoAgendaConsulta(animais, vets, consultas, notifCsv);

        // TODO: cadastrar dois animais.
        // TODO: cadastrar dois veterinários.
        // TODO: agendar uma consulta de ROTINA usando agendaConsole.
        // TODO: agendar uma consulta de EMERGENCIA usando agendaCsv.
        // TODO: realizar a primeira consulta.
        // TODO: cancelar a segunda consulta.
        // TODO: exibir histórico do primeiro animal.
        // TODO: exibir agenda do primeiro veterinário.
    }
}
