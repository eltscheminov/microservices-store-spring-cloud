/*
 * Copyright 2014-2015 the original author or authors.
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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.hypermedia.DiscoveredResource;
import org.springframework.cloud.client.hypermedia.DynamicServiceInstanceProvider;
import org.springframework.cloud.client.hypermedia.ServiceInstanceProvider;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Marcus on 27/12/2016.
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
public class PurchaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurchaseApplication.class, args);
    }

    //HAL products
    @Bean
    public DiscoveredResource findProducts(ServiceInstanceProvider provider) {
        return new DiscoveredResource(provider, traverson -> traverson.follow("products", "search", "by-id"));
    }

    // Cloud configuration
    @Profile("default")
    @EnableDiscoveryClient
    static class CloudConfiguration {
        @Bean
        public DynamicServiceInstanceProvider dynamicServiceProvider(DiscoveryClient client) {
            return new DynamicServiceInstanceProvider(client, "product");
        }
    }

    //Configure purchase event handler to interact with another resource
    @Configuration
    static class RepositoryConfiguration {
        @Bean
        PurchaseEventHandler purchaseEvenHandler() {
            return new PurchaseEventHandler();
        }
    }

    // Some data setup in initialization
    @Autowired
    PurchaseRepository customers;

    @PostConstruct
    public void init() {
        ArrayList<Long> products = new ArrayList<>();
        products.add(1l);
        products.add(2l);
        Purchase purchase = new Purchase(1l, Calendar.getInstance().getTimeInMillis(), products);
        customers.save(purchase);
    }
}
