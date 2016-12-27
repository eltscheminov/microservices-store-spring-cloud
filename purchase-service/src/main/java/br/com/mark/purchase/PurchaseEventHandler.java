/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.mark.purchase;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import br.com.mark.purchase.integration.Product;
import br.com.mark.purchase.integration.ProductIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

/**
 * Created by Marcus on 27/12/2016.
 */
@RepositoryEventHandler(Purchase.class)
@Slf4j
public class PurchaseEventHandler {
    @Autowired
    private ProductIntegration product;

    @HandleBeforeCreate
    public void handlePurchase(Purchase purchase) {
        try {
            log.debug("Handling purchase save!!!");
            purchase.getProducts().forEach(aLong -> {
                Product product = this.product.getProduct(aLong);
                if (!product.getIsAvailable()) {
                    throw new RuntimeException("Nao foi possivel realizar a transacao, produto indisponivel para a compra");
                }
                this.product.setProductUnavailable(aLong);
            });
        } catch (HystrixRuntimeException  ex) {
            throw new RuntimeException("Servico nao disponivel, por favor tente novamente.", ex);
        }
    }
}
