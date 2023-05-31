/**
 * (C) Copyright 2022 Araf Karsh Hamid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fusion.air.microservice.domain.entities.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.fusion.air.microservice.domain.entities.core.AbstractBaseEntityWithUUID;
import io.fusion.air.microservice.domain.models.example.CartItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */

@Entity
@Table(name = "carts_tx")
public class CartItemEntity extends AbstractBaseEntityWithUUID {

    @NotNull(message = "The Customer ID is  required.")
    // @Size(min = 5, max = 64, message = "The length of Product Description must be 5-64 characters.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$", message = "Invalid Customer UUID")
    @Column(name = "customerId")
    private String customerId;

    @NotBlank(message = "The Product Name is required.")
    @Size(min = 3, max = 32, message = "The length of Product Name must be 3-32 characters.")
    @Column(name = "productName")
    private String productName;

    @NotNull(message = "The Product Details is  required.")
    // @Size(min = 5, max = 64, message = "The length of Product Description must be 5-64 characters.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$", message = "Invalid Product UUID")
    @Column(name = "productId")
    private String productId;

    @NotNull(message = "The Price is required.")
    @Column(name = "price")
    private BigDecimal productPrice;

    @NotNull(message = "The Quantity is required.")
    @Column(name = "quantity")
    private BigDecimal quantity;


    /**
     * Empty Cart Item Entity
     */
    public CartItemEntity() {
    }

    /**
     * Create Cart Item from the Cart DTO
     * @param _cartItem
     */
    public CartItemEntity(CartItem _cartItem) {
        this(_cartItem.getCustomerId(), _cartItem.getProductName(), _cartItem.getProductId(),
                _cartItem.getProductPrice(), _cartItem.getQuantity());
    }

    /**
     * Create Product Entity
     * @param _pName
     * @param _productId
     * @param _pPrice
     */
    public CartItemEntity(String _cId, String _pName, String _productId, BigDecimal _pPrice, BigDecimal _qty) {
        this.customerId     = _cId;
        this.productName    = _pName;
        this.productId      = _productId;
        this.productPrice   = _pPrice;
        this.quantity       = _qty;
    }

    /**
     * Get Item ID
     * @return
     */
    @JsonIgnore
    public UUID getItemId() {
        return getUuid();
    }

    /**
     * Returns Item ID as String
     * @return
     */
    @JsonIgnore
    public String getItemIdAsString() {
        return getUuid().toString();
    }

    /**
     * Returns the customer Id
     * @return
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Returns Product Name
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Returns Product ID
     * @return
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Returns Product Price
     * @return
     */
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * Returns the Item Quantity
     * @return
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    // Set Methods Provided to demonstrate the Update Part of the CRUD Operations
    // Immutable Objects are always better.
    /**
     * Set the Product Price
     * @param productPrice
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    /**
     * Set the Item Quantity
     * @param quantity
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * De-Activate Item
     */
    @JsonIgnore
    public void deActivateItem() {
        deActivate();
    }

    /**
     * Activate Item
     */
    @JsonIgnore
    public void activateItem() {
        activate();
    }

    /**
     * Return This Item ID / Name
     * @return
     */
    public String toString() {
        return super.toString() + "|" + productName;
    }

}
