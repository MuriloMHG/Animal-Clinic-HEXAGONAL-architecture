package com.clinica.infraestrutura.adaptador.notificacao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.modelo.Consulta;
import com.clinica.dominio.porta.saida.PortaNotificacaoTutor;

public class NotificacaoCsv implements PortaNotificacaoTutor {
    private final String caminhoArquivo;

    public NotificacaoCsv(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        criarCabecalhoSeNecessario();
    }

    @Override
    public void notificarAgendamento(String tutor, Animal animal, Consulta consulta) {
        String linha = LocalDateTime.now() + ",AGENDAMENTO," + tutor + "," + animal.getNome() + "," +
            consulta.getVeterinario().getNome() + "," + consulta.getData() + "T" + consulta.getHora();
        escreverLinha(linha);
    }

    @Override
    public void notificarCancelamento(String tutor, Animal animal, String motivo) {
        String linha = LocalDateTime.now() + ",CANCELAMENTO," + tutor + "," + animal.getNome() + ",N/A,N/A";
        escreverLinha(linha);
    }

    private void criarCabecalhoSeNecessario() {
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists() || arquivo.length() == 0) {
            escreverLinha("timestamp,tipo_evento,tutor,animal,veterinario,data_consulta");
        }
    }

    private void escreverLinha(String linha) {
        try (FileWriter writer = new FileWriter(caminhoArquivo, true)) {
            writer.write(linha + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever notificação CSV: " + e.getMessage(), e);
        }
    }
}
