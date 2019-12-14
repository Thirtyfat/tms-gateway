package com.yhdx.tms.gateway;

import com.yhdx.center.common.aggregate.common.listener.SpringBootAdminListener;
import com.yhdx.center.common.aggregate.common.listener.SpringBootPreparedEventListener;
import com.yhdx.center.common.aggregate.common.listener.SpringBootStartedEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.yhdx")
public class TmsGatewayApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.listeners(new SpringBootStartedEventListener(), new SpringBootPreparedEventListener(),new SpringBootAdminListener());
        return application.sources(TmsGatewayApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TmsGatewayApplication.class);
        app.addListeners(new SpringBootStartedEventListener());
        app.addListeners(new SpringBootPreparedEventListener());
        app.addListeners(new SpringBootAdminListener());
        app.run(args);
    }
}
