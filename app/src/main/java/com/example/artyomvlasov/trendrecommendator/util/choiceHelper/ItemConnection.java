package com.example.artyomvlasov.trendrecommendator.util.choiceHelper;

public class ItemConnection {
	private ClothItem item1;
	private ClothItem item2;
	private double weight;

	public ItemConnection(ClothItem item1, ClothItem item2, double weight) {
		this.item1 = item1;
		this.item2 = item2;
		this.weight = weight;
	}

	public boolean containsItem(ClothItem item) {
		return item1.equals(item) || item2.equals(item);
	}

	public ClothItem pairTo(ClothItem item) {
		if (item1.equals(item)) return item2;
		if (item2.equals(item)) return item1;

		return ClothItem.UNKNOWN;
	}

	public ClothItem item1() {return item1;}

	public double weight() {
		return weight;
	}


}
