package com.example.yp_gateway;

import com.example.yp_gateway.Entity.ResponseMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class YpGatewayApplication {

    @Autowired
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String gatewayName;

    public static void main(String[] args) {
        SpringApplication.run(YpGatewayApplication.class, args);
    }

    @Bean
    public RequestTimeGatewayFilterFactory requestTimeGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }
    @RequestMapping(value = "/getMicroServicesList", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String testController1(){
        StringBuilder sb = new StringBuilder();
        Applications applications = eurekaClient.getApplications();
        Map<String, Integer> serviceMap = new HashMap();
        for(Application application : applications.getRegisteredApplications()) {
            if(serviceMap.containsKey(application.getName()) || application.getName().toLowerCase() == gatewayName) continue;
            serviceMap.put(application.getName(),1);
        }
        ResponseMessage<Map> rm = new ResponseMessage<>();
        rm.setHttpCode("200");
        rm.setResponseBody(serviceMap);
        Gson gson = new Gson();
        return gson.toJson(rm);
    }

}

