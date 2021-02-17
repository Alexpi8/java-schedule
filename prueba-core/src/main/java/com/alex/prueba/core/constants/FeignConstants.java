package com.alex.prueba.core.constants;

public final class FeignConstants {

    public static final String MEEP_SERVICE_URL = "https://apidev.meep.me/tripplan/api";
    public static final String MEEP_SERVICE_PATH_V1 = "/v1";


    private FeignConstants() {
        throw new IllegalStateException("Utility class");
    }
}
