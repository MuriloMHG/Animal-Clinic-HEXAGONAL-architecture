package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

public class NotificacaoConsole implements PortaNotificacaoTutor {
    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {
        // TODO: imprimir no terminal uma mensagem estruturada de agendamento.
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {
        // TODO: imprimir no terminal uma mensagem estruturada de cancelamento.
    }
}
