package br.com.wal.delivery.validator;

import br.com.wal.delivery.exception.InvalidDeliveryMapException;
import br.com.wal.delivery.model.DeliveryMap;

/**
 * Created by marcelotozzi on 01/09/14.
 */
public class DeliveryMapValidator {
    public static void validateDeliveryMap(DeliveryMap deliveryMap) throws InvalidDeliveryMapException {
        if (!isValid(deliveryMap) ||
                !existsDeliveryRoute(deliveryMap)) {
            throw new InvalidDeliveryMapException("Malha inválida");
        }
    }

    private static boolean isValid(DeliveryMap deliveryMap) {
        return deliveryMap != null &&
                deliveryMap.getName() != null &&
                !"".equals(deliveryMap.getName());
    }


    private static boolean existsDeliveryRoute(DeliveryMap deliveryMap) {
        return deliveryMap.getDeliveryRoutes() != null &&
                !deliveryMap.getDeliveryRoutes().isEmpty() &&
                deliveryMap.getDeliveryRoutes().get(0) != null;
    }
}
