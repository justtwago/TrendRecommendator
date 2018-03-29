package com.example.artyomvlasov.trendrecommendator.ui;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.artyomvlasov.trendrecommendator.ColorUtils;
import com.example.artyomvlasov.trendrecommendator.ImageTools;
import com.example.artyomvlasov.trendrecommendator.R;

public class PhotoResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_result);

        Bundle extras = getIntent().getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        setDominantColorView(imageBitmap);
    }

    private void setDominantColorView(Bitmap imageBitmap) {
        int dominantColor = ImageTools.getDominantColor(imageBitmap);
        View colorView = findViewById(R.id.colorView);
        TextView colorText = findViewById(R.id.colorText);
        colorView.setBackgroundColor(dominantColor);
        colorText.setText(new ColorUtils().getColorName(dominantColor));
    }
}
