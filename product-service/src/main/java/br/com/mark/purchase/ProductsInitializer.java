/*
 * Copyright 2014 the original author or authors.
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


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Marcus on 27/12/2016.
 */
@Slf4j
@Component
public class ProductsInitializer {

	@Autowired
	public ProductsInitializer(ProductRepository repository) throws Exception {

		if (repository.count() != 0) {
			return;
		}

		List<Product> products = buildProducts();
		log.info("Importing {} products into H2…", products.size());
		repository.save(products);
		log.info("Successfully imported {} products.", repository.count());
	}

	/**
	 * Reads a file {@code products.csv} from the class path and parses it into {@link Product} instances about to
	 * persisted.
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<Product> buildProducts() throws Exception {
        //build products list
		List<Product> products = new ArrayList<Product>();
        //build some products
		Product productOne = new Product();
        productOne.setName("One");
        productOne.setInclusionTimestamp(Calendar.getInstance().getTimeInMillis());
        productOne.setIsAvailable(false);
        productOne.setValue(15.00);

		Product productTwo = new Product();
        productTwo.setName("Two");
        productTwo.setInclusionTimestamp(Calendar.getInstance().getTimeInMillis());
        productTwo.setIsAvailable(true);
        productTwo.setValue(60.00);

		Product productThree = new Product();
        productThree.setName("teste");
        productThree.setInclusionTimestamp(Calendar.getInstance().getTimeInMillis());
        productThree.setIsAvailable(true);
        productThree.setValue(120.00);

        products.add(productOne);
        products.add(productTwo);
        products.add(productThree);
		return products;
	}
}
