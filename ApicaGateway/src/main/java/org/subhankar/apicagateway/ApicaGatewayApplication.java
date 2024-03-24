package org.subhankar.apicagateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApicaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApicaGatewayApplication.class, args);
    }

}
