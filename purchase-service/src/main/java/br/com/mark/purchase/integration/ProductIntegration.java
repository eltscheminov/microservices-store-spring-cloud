package br.com.mark.purchase.integration;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Marcus on 27/12/2016.
 */
@FeignClient("product")
public interface ProductIntegration {
    @RequestMapping(value = "/products/{id}/unavailable", method = RequestMethod.POST)
    public void setProductUnavailable(@PathVariable("id") long id);

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public Product getProduct(@PathVariable("id") long id);
}
