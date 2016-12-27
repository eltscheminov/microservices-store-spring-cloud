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

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Id;

/**
 * Created by Marcus on 27/12/2016.
 */
@Data
@NoArgsConstructor
@Entity
public class Product {
	@Id
    //NOTE: not recommended depending on the storage and distribution of the application
	@GeneratedValue(strategy = GenerationType.AUTO)
 	long id;
	String name;
	Double value;
	Boolean isAvailable;
    Long inclusionTimestamp;



}
