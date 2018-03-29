package com.example.artyomvlasov.trendrecommendator.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artyomvlasov.trendrecommendator.ColorUtils;
import com.example.artyomvlasov.trendrecommendator.R;

public class PhotoResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_result);

        Bundle extras = getIntent().getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        setPhoto(imageBitmap);
        setDominantColorView(imageBitmap);
    }

    private void setPhoto(Bitmap imageBitmap) {
        ImageView photoView = findViewById(R.id.photo);
        photoView.setImageBitmap(imageBitmap);
    }

    private void setDominantColorView(Bitmap imageBitmap) {
        View colorView = findViewById(R.id.colorView);
        TextView colorText = findViewById(R.id.colorText);

        Palette.from(imageBitmap)
                .generate(palette -> {
                    Palette.Swatch textSwatch = palette.getVibrantSwatch();

                    int dominantColor;
                    if (textSwatch != null) {
                        dominantColor = textSwatch.getRgb();
                    } else {
                        dominantColor = palette.getDominantColor(Color.BLACK);
                    }
                    colorView.setBackgroundColor(dominantColor);
                    colorText.setText(new ColorUtils().getColorName(dominantColor));
                });
    }
}
