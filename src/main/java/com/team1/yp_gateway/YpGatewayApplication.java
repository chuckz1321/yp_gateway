package com.team1.yp_gateway;

import com.team1.yp_gateway.Entity.ResponseMessage;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Applications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableEurekaClient
@RestController
public class YpGatewayApplication {

    @Autowired
    private EurekaClient eurekaClient;

    @Value("${spring.application.name}")
    private String gatewayName;

    public static void main(String[] args) {
        SpringApplication.run(YpGatewayApplication.class, args);
    }

    @RequestMapping(value = "/getMicroServicesList", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public ResponseMessage testController1(){
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
        return rm;
    }

}

