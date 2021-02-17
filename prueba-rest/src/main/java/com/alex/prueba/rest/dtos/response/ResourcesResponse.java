package com.alex.prueba.rest.dtos.response;

import com.alex.prueba.core.models.MeepRouteResourceModel;
import lombok.*;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ResourcesResponse {

    private List<MeepRouteResourceModel> resources;
}
