package com.example.artyomvlasov.trendrecommendator.util.choiceHelper;

import com.example.artyomvlasov.trendrecommendator.util.colorClassification.ApiColor;

import java.util.Objects;

public class ClothItem {
	public final static ClothItem UNKNOWN = new ClothItem(ApiColor.Cyan, ApiType.UNKNOWN);

	private ApiColor color;
	private ApiType type;

	public ClothItem(ApiColor color, ApiType type) {
		this.color = color;
		this.type = type;
	}

	public ApiColor color() {
		return color;
	}

	public ApiType type() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ClothItem clothItem = (ClothItem) o;
		return color == clothItem.color &&
				Objects.equals(type, clothItem.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, type);
	}
}
