package com.example.artyomvlasov.trendrecommendator;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageTools {
    public static int getDominantColor(Bitmap bitmap) {
        List<Palette.Swatch> swatchesTemp = Palette.from(bitmap).generate().getSwatches();
        List<Palette.Swatch> swatches = new ArrayList<>(swatchesTemp);
        Collections.sort(swatches, (swatch1, swatch2) -> swatch2.getPopulation() - swatch1.getPopulation());
        return swatches.size() > 0 ? swatches.get(0).getRgb() : 0;
    }

}
