package com.example.artyomvlasov.trendrecommendator.util.colorClassification;

import java.util.Arrays;
import java.util.List;

import static com.example.artyomvlasov.trendrecommendator.util.colorClassification.ApiColor.*;

public class ApiColors {
	private static final List<ColorRange> colorRanges = Arrays.asList(
			new ColorRange(Black) {
				public boolean contains(int hue, double sat, double lum) {
					return lum < 5;
				}
			},
			new ColorRange(White) {
				public boolean contains(int hue, double sat, double lum) {
					return lum > 98;
				}
			},
			new ColorRange(Beige) {
				public boolean contains(int hue, double sat, double lum) {
					return lum > 95;
				}
			},
			new ColorRange(Gray) {
				public boolean contains(int hue, double sat, double lum) {
					return sat < 2;
				}
			},
			new ColorRange(Red) {
				public boolean contains(int hue, double sat, double lum) {
					return hue < 20 || 350 < hue;
				}
			},
			new ColorRange(Brown) {
				public boolean contains(int hue, double sat, double lum) {
					return 20 <= hue && hue + lum < 80;
				}
			},
			new ColorRange(Orange) {
				public boolean contains(int hue, double sat, double lum) {
					return 20 < hue && hue < 55;
				}
			},
			new ColorRange(Yellow) {
				public boolean contains(int hue, double sat, double lum) {
					return 55 < hue && hue < 70;
				}
			},
			new ColorRange(Gold) {
				public boolean contains(int hue, double sat, double lum) {
					return false;
				}
			},
			new ColorRange(Green) {
				public boolean contains(int hue, double sat, double lum) {
					return 75 <= hue && hue < 160;
				}
			},
			// place for Cyan 160 <= hue < 175
			new ColorRange(Blue) {
				public boolean contains(int hue, double sat, double lum) {
					return 175 < hue && hue < 255;
				}
			},
			new ColorRange(Purple) {
				public boolean contains(int hue, double sat, double lum) {
					return 255 <= hue && hue < 300;
				}
			},
			new ColorRange(Pink) {
				public boolean contains(int hue, double sat, double lum) {
					return 300 <= hue;
				}
			}
	);

	public static ApiColor classify(int hue, double sat, double lum) {
		for (ColorRange colorRange : ApiColors.colorRanges)
			if (colorRange.contains(hue, sat, lum)) return colorRange.color();

		return Undefined;
	}


}
