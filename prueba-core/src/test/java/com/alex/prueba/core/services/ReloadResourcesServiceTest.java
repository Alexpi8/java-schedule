package com.alex.prueba.core.services;

import com.alex.prueba.core.models.MeepRouteResourceModel;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(JMockit.class)
public class ReloadResourcesServiceTest {

    private static final String LOWER_LEFT_LAT_LON = "38.711046,-9.160096";
    private static final String UPPER_RIGHT_LAT_LON = "38.739429,9.137115";
    private static final String COMPANY_ZONE_IDS = "545,467,473";

    private static final String RESOURCE_ID_1 = "id-1";
    private static final String RESOURCE_ID_2 = "id-2";
    private static final String RESOURCE_ID_3 = "id-3";

    @Tested
    private ReloadResourcesService reloadResourcesService;

    @Injectable
    private MeepClient meepClient;

    @Test
    public void testReloadResources() {

        new Expectations() {{
            meepClient.getRoutesResources(LOWER_LEFT_LAT_LON, UPPER_RIGHT_LAT_LON, COMPANY_ZONE_IDS);
            returns(buildFirstResourceList(), buildSecondResourceList());
            times = 2;
        }};

        reloadResourcesService.reloadResources();
        reloadResourcesService.reloadResources();

        List<MeepRouteResourceModel> availableResources = reloadResourcesService.getAvailableResources();

        Map<String, MeepRouteResourceModel> map = availableResources.stream()
            .collect(Collectors.toMap(MeepRouteResourceModel::getId, r -> r));

        assertEquals(2, availableResources.size());
        assertTrue(map.containsKey(RESOURCE_ID_1));
        assertFalse(map.containsKey(RESOURCE_ID_2));
        assertTrue(map.containsKey(RESOURCE_ID_3));
    }

    private List<MeepRouteResourceModel> buildFirstResourceList() {
        return Arrays.asList(
            buildMeepRouteResourceModel(RESOURCE_ID_1),
            buildMeepRouteResourceModel(RESOURCE_ID_2)
        );
    }

    private List<MeepRouteResourceModel> buildSecondResourceList() {
        return Arrays.asList(
            buildMeepRouteResourceModel(RESOURCE_ID_1),
            buildMeepRouteResourceModel(RESOURCE_ID_3)
        );
    }

    private MeepRouteResourceModel buildMeepRouteResourceModel(String id) {
        MeepRouteResourceModel meepRouteResourceModel = new MeepRouteResourceModel();
        meepRouteResourceModel.setId(id);
        return meepRouteResourceModel;
    }
}