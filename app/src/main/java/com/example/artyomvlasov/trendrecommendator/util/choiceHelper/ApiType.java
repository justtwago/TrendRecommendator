package com.example.artyomvlasov.trendrecommendator.util.choiceHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum ApiType {
    SHOES, BLOUSE, JEANS, SHIRT, JACKET, SKIRT, TROUSERS, DRESS, SWEATER, CARDIGAN, SUIT, T_SHIRT, COAT, UNKNOWN;

    private static Map<ApiType, String> names = new HashMap<ApiType, String>() {
        {
            put(SHOES, "shoes");
            put(BLOUSE, "blouse");
            put(JEANS, "jeans");
            put(SHIRT, "shirt");
            put(JACKET, "jacket");
            put(SKIRT, "skirt");
            put(TROUSERS, "trousers");
            put(DRESS, "dress");
            put(SWEATER, "sweater/hoodie");
            put(CARDIGAN, "cardigan");
            put(SUIT, "suit");
            put(T_SHIRT, "t-shirt");
            put(COAT, "coat");
            put(UNKNOWN, "unknown");
        }
    };

    public static Collection<ApiType> ALL_TYPES = names.keySet();

    public static ApiType getCategory(String name) {
        for (ApiType apiType : ALL_TYPES) {
            if (names.get(apiType).equals(name)) {
                return apiType;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return names.get(this);
    }
}
