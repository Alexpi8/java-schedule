package com.alex.prueba.core.services;

import com.alex.prueba.core.constants.FeignConstants;
import com.alex.prueba.core.models.MeepRouteResourceModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "meep-service", url = FeignConstants.MEEP_SERVICE_URL)
public interface MeepClient {

    @GetMapping(value = FeignConstants.MEEP_SERVICE_PATH_V1 + "/routers/lisboa/resources",
        produces = MediaType.APPLICATION_JSON_VALUE)
    List<MeepRouteResourceModel> getRoutesResources(
        @RequestParam("lowerLeftLatLon") String lowerLeftLatLon,
        @RequestParam("upperRightLatLon") String upperRightLatLon,
        @RequestParam("companyZoneIds") String companyZoneIds);
}
