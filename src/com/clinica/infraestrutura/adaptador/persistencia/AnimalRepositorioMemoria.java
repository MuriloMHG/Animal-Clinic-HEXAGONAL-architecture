package com.clinica.infraestrutura.adaptador.persistencia;

import com.clinica.dominio.modelo.Animal;
import com.clinica.dominio.porta.saida.PortaAnimalRepositorio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AnimalRepositorioMemoria implements PortaAnimalRepositorio {
    private final Map<Long, Animal> store = new HashMap<>();
    private long proximoId = 1L;

    @Override
    public void salvar(Animal animal) {
        // TODO: se animal.getId() for null, atribuir ID automático.
        // TODO: salvar no HashMap usando o id como chave.
    }

    @Override
    public Optional<Animal> buscarPorId(Long id) {
        // TODO: retornar Optional.ofNullable(store.get(id)).
        return Optional.empty();
    }

    @Override
    public List<Animal> listarPorTutor(String tutor) {
        // TODO: filtrar animais pelo nome do tutor, ignorando maiúsculas/minúsculas.
        return new ArrayList<>();
    }

    @Override
    public List<Animal> listarTodos() {
        // TODO: retornar todos os animais.
        return new ArrayList<>();
    }

    @Override
    public void remover(Long id) {
        // TODO: remover animal pelo id.
    }
}
