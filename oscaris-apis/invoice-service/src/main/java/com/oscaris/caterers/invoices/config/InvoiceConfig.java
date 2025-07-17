package com.oscaris.caterers.invoices.config;

import com.oscaris.caterers.invoices.client.CompanyServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Author: kev.Ameda
 */
@Configuration
public class InvoiceConfig {

    @Bean
    CompanyServiceClient companyServiceClient(){
        RestClient restClient = RestClient.create("http://COMPANY-SERVICE:8080/companies");
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(CompanyServiceClient.class);
    }
}
