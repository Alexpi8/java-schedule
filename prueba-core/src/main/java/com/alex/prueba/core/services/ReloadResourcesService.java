package com.alex.prueba.core.services;

import com.alex.prueba.core.models.MeepRouteResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReloadResourcesService {

    public static final String LOWER_LEFT_LAT_LON = "38.711046,-9.160096";
    public static final String UPPER_RIGHT_LAT_LON = "38.739429,9.137115";
    public static final String COMPANY_ZONE_IDS = "545,467,473";

    private final Map<String, Boolean> resourcesStatus = new HashMap<>();
    private final Map<String, MeepRouteResourceModel> availableResources = new HashMap<>();

    @Autowired
    private MeepClient meepClient;

    public List<MeepRouteResourceModel> getAvailableResources() {
        return new ArrayList<>(availableResources.values());
    }

    @Scheduled(fixedDelay = 60000)
    public void reloadResources() {
        resetResourcesStatus();

        List<MeepRouteResourceModel> routesResources = meepClient.getRoutesResources(LOWER_LEFT_LAT_LON, UPPER_RIGHT_LAT_LON, COMPANY_ZONE_IDS);
        routesResources.forEach(this::addAvailableResource);

        removeNotAvailableResources();

        System.out.println("TOTAL resources availables: " + availableResources.size());
    }

    private void resetResourcesStatus() {
        resourcesStatus.clear();
        availableResources.keySet().forEach(id -> resourcesStatus.put(id, false));
    }

    private void addAvailableResource(MeepRouteResourceModel meepRouteResourceModel) {
        if (isNewResource(meepRouteResourceModel)) {
            System.out.println("- New resource available: " + meepRouteResourceModel.getId());
        }

        resourcesStatus.put(meepRouteResourceModel.getId(), true);
        availableResources.put(meepRouteResourceModel.getId(), meepRouteResourceModel);
    }

    private boolean isNewResource(MeepRouteResourceModel meepRouteResourceModel) {
        return !availableResources.containsKey(meepRouteResourceModel.getId());
    }

    private void removeNotAvailableResources() {
        resourcesStatus.keySet().stream()
            .filter(id -> !resourcesStatus.get(id))
            .forEach(id -> {
                MeepRouteResourceModel remove = availableResources.remove(id);
                System.out.println("- Resource NOT available!! : " + remove.getId());
            });
    }
}
