package com.tsunglin.tsunglin00.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "v1", tags = "GCP Devops K8S")
@RequestMapping("/api/v1")
public class Testindex {

    @GetMapping("/")
    public String hello() {
        return "Kubernetes Jenkins CI/CD with Spring Boot & VUE3 & Docker";
    }

}