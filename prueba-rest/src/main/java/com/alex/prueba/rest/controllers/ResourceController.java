package com.alex.prueba.rest.controllers;

import com.alex.prueba.core.services.ReloadResourcesService;
import com.alex.prueba.rest.dtos.response.ResourcesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resources")
public class ResourceController {

    @Autowired
    private ReloadResourcesService reloadResourcesService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResourcesResponse> findPrice() {
        ResourcesResponse resourcesResponse = ResourcesResponse.builder()
            .resources(reloadResourcesService.getAvailableResources())
            .build();

        return new ResponseEntity<>(resourcesResponse, HttpStatus.OK);
    }
}
