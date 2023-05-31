package io.fusion.air.microservice.domain.ports.services;

import io.fusion.air.microservice.domain.entities.example.CartItemEntity;
import io.fusion.air.microservice.domain.models.example.CartItem;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public interface CartReactiveService {

    /**
     * Get All Cart Items by Customer ID
     * @param customerId
     * @return
     */
    public Flux<CartItemEntity> findByCustomerId(UUID customerId);

    /**
     * Save Cart Item
     * @param cartItem
     */
    public void saveCartItem(CartItem cartItem);

    /**
     * Delete Cart Item
     * @param itemId
     */
    public void deleteCartItem(UUID itemId);
}
