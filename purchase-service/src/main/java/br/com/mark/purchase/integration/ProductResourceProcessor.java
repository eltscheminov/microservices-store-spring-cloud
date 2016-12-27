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
package br.com.mark.purchase.integration;

import br.com.mark.purchase.Purchase;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Marcus on 27/12/2016.
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductResourceProcessor implements ResourceProcessor<Resource<Purchase>> {

    private final @NonNull DiscoveredResource productResource;


    @Override
    public Resource<Purchase> process(Resource<Purchase> resource) {
        Purchase customer = resource.getContent();
        List<Long> products = customer.getProducts();
        log.info("Products {}", products);
        log.info("Resource link {}", productResource.getLink());
        Optional<Link> link = Optional.ofNullable(productResource.getLink());
        link.ifPresent(it -> {
            log.info("link present", it);
            products.forEach(id -> {
                if (id == null) {
                    return;
                }
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("id", id);

                resource.add(it.expand(parameters).withRel("products"));
            });

        });
        return resource;
    }
}
