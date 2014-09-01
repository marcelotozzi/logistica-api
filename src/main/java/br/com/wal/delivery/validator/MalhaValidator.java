package br.com.wal.delivery.validator;

import br.com.wal.delivery.exception.MalhaInvalidaException;
import br.com.wal.delivery.model.Malha;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class MalhaValidator {
    public static void validarMalha(Malha malha) throws MalhaInvalidaException {
        if (!malhaValida(malha) ||
                existeUmaRota(malha)) {
            throw new MalhaInvalidaException("Malha inválida");
        }
    }

    private static boolean malhaValida(Malha malha) {
        return malha != null &&
                malha.getNome() != null &&
                !"".equals(malha.getNome());
    }


    private static boolean existeUmaRota(Malha malha) {
        return malha.getRotas() == null &&
                malha.getRotas().isEmpty() &&
                malha.getRotas().get(0) == null;
    }
}
