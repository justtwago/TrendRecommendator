package com.example.artyomvlasov.trendrecommendator.util.colorClassification;

public abstract class ColorRange {
	private ApiColor color;

	ColorRange(ApiColor color) {
		this.color = color;
	}

	ApiColor color() {
		return color;
	}

	public abstract boolean contains(int hue, double sat, double lum);
}

