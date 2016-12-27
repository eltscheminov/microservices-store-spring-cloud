package br.com.mark.purchase.integration;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * Created by Marcus on 27/12/2016.
 */
@Data
@NoArgsConstructor
public class Product {
    long id;
    String name;
    Double value;
    Boolean isAvailable;
    Long inclusionTimestamp;

}
