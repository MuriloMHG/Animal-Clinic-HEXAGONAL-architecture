package com.clinica.infraestrutura.adaptador.notificacao;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

public class NotificacaoCsv implements PortaNotificacaoTutor {
    private final String caminhoArquivo;

    public NotificacaoCsv(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        // TODO: criar cabeçalho do CSV se o arquivo ainda não existir.
    }

    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {
        // TODO: adicionar uma linha CSV com o evento AGENDAMENTO.
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {
        // TODO: adicionar uma linha CSV com o evento CANCELAMENTO.
    }

    // TODO: criar método privado para escrever linha no arquivo usando FileWriter em modo append.
}
