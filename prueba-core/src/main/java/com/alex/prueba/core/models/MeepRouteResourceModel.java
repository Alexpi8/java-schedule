package com.alex.prueba.core.models;

import lombok.Data;

@Data
public class MeepRouteResourceModel {

    private String id;
    private String name;
    private Double x;
    private Double y;
    private String licencePlate;
    private Integer range;
    private Integer batteryLevel;
    private Integer seats;
    private Integer helmets;
    private String model;
    private String resourceImageId;
    private Boolean realTimeData;
    private String resourceType;
    private Integer companyZoneId;
}
