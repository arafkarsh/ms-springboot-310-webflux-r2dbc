package io.fusion.air.microservice.adaptersReactive.service;

import io.fusion.air.microservice.adaptersReactive.repository.CartItemRepository;
import io.fusion.air.microservice.domain.entities.example.CartItemEntity;
import io.fusion.air.microservice.domain.models.example.CartItem;
import io.fusion.air.microservice.domain.ports.services.CartReactiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
@Component
public class CartReactiveServiceImpl implements CartReactiveService {

    // @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Flux<CartItemEntity> findByCustomerId(UUID customerId) {
        return cartItemRepository.findByCustomerId(customerId);
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(new CartItemEntity(cartItem));
    }

    @Override
    public void deleteCartItem(UUID itemId) {

    }
}
