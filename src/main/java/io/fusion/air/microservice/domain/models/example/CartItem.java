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
package io.fusion.air.microservice.domain.models.example;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
 * @author: Araf Karsh Hamid
 * @version:
 * @date:
 */
public class CartItem {

    @NotNull(message = "The Product Details is  required.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$", message = "Invalid Customer UUID")
    private String customerId;

    @NotBlank(message = "The Product Name is required.")
    @Size(min = 3, max = 32, message = "The length of Product Name must be 3-32 characters.")
    private String productName;

    @NotNull(message = "The Product ID is  required.")
    @Pattern(regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$", message = "Invalid Product UUID")
    private String productId;

    @NotNull(message = "The Price is required.")
    private BigDecimal productPrice;

    @NotNull(message = "The Quantity is required.")
    private BigDecimal quantity;


    /**
     * Empty Cart Item Entity
     */
    public CartItem() {
    }

    /**
     * Create Cart Item Entity
     *
     * @param _cId
     * @param _pName
     * @param _productId
     * @param _pPrice
     * @param _qty
     */
    public CartItem(String _cId, String _pName, String _productId, BigDecimal _pPrice, BigDecimal _qty) {
        this.customerId     = _cId;
        this.productName    = _pName;
        this.productId      = _productId;
        this.productPrice   = _pPrice;
        this.quantity       = _qty;
    }

    /**
     * Returns the Customer Id
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

    /**
     * Return This Item ID / Product Id / Name
     * @return
     */
    public String toString() {
        return getCustomerId() + "|" + productId + "|" + productName;
    }
}
