package com.example.artyomvlasov.trendrecommendator.util.choiceHelper;

import android.app.Activity;
import android.content.Context;

import com.example.artyomvlasov.trendrecommendator.util.colorClassification.ApiColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelperFactory {
    private final static String RULE_PARTS_SEPARATOR = "==";
    private final static String ITEM_COLOR_TYPE_SEPARATOR = ";";

    public static ChoiceHelper fromRules(List<ItemConnection> connections) {
        return fromStream(connections.stream());
    }

    public static ChoiceHelper fromFile(Context activity, String path) throws IOException {
        try (Stream<String> stream = new BufferedReader(new InputStreamReader(activity.getAssets()
                .open(path))).lines()) {
            return fromStream(stream.map(line -> {
                String[] parts = line.split(RULE_PARTS_SEPARATOR);
                return new ItemConnection(lineToItem(parts[0]), lineToItem(parts[1]), Double.valueOf(parts[2]));
            }));
        }
    }

    private static ChoiceHelper fromStream(Stream<ItemConnection> freshStream) {
        return new ChoiceHelper(freshStream.collect(Collectors.groupingBy(ItemConnection::item1)));
    }

    private static ClothItem lineToItem(String line) {
        String[] split = line.split(ITEM_COLOR_TYPE_SEPARATOR);
        return new ClothItem(ApiColor.valueOf(split[0]), ApiType.valueOf(split[1]));
    }
}
