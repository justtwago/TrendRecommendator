package com.example.artyomvlasov.trendrecommendator.util.choiceHelper;

import java.util.List;
import java.util.Map;

public class ChoiceHelper {
	private Map<ClothItem, List<ItemConnection>> connectionsMap;

	public ChoiceHelper(Map<ClothItem, List<ItemConnection>> connectionsMap) {
		this.connectionsMap = connectionsMap;
	}

	public ClothItem suggest(ClothItem base, ApiType wantedType) {
		List<ItemConnection> specificConnections = connectionsMap.get(base);
		if (specificConnections == null) return ClothItem.UNKNOWN;

		return bestPair(base, wantedType, specificConnections);
	}

	private ClothItem bestPair(ClothItem base, ApiType wantedType, List<ItemConnection> specificConnections) {
		double maxWeight = -1;
		ClothItem bestPair = ClothItem.UNKNOWN;
		for (ItemConnection connection : specificConnections) {
			ClothItem pair = connection.pairTo(base);
			if (pair.type().equals(wantedType))
				if (connection.weight() > maxWeight) {
					bestPair = pair;
					maxWeight = connection.weight();
				}
		}
		return bestPair;
	}
}
