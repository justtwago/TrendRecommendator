package com.example.artyomvlasov.trendrecommendator.util.choiceHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum ApiCategory {
    TOP, BOTTOM, SHOES;

    public static Map<ApiType, ApiCategory> categoriesByType = new HashMap<ApiType, ApiCategory>() {{
        put(ApiType.SHOES, SHOES);
        put(ApiType.BLOUSE, TOP);
        put(ApiType.JEANS, BOTTOM);
        put(ApiType.SHIRT, TOP);
        put(ApiType.JACKET, TOP);
        put(ApiType.SKIRT, BOTTOM);
        put(ApiType.TROUSERS, BOTTOM);
        put(ApiType.DRESS, BOTTOM);
        put(ApiType.SWEATER, TOP);
        put(ApiType.CARDIGAN, TOP);
        put(ApiType.SUIT, TOP);
        put(ApiType.T_SHIRT, TOP);
        put(ApiType.COAT, TOP);
        put(ApiType.UNKNOWN, TOP);
    }};
}
