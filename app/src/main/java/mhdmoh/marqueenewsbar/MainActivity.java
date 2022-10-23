package mhdmoh.marqueenewsbar;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

import mhdmoh.marquee_recyclerview.MarqueeItem;
import mhdmoh.marquee_recyclerview.MarqueeRecyclerView;
import mhdmoh.marquee_recyclerview.Utils;

public class MainActivity extends AppCompatActivity {
    boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MarqueeRecyclerView marqueeRecyclerView = findViewById(R.id.marquee_recycler_view);
        Button btn = findViewById(R.id.btn);
        String url = "https://w7.pngwing.com/pngs/135/52/png-transparent-bein-sports-1-bein-sports-2-television-channel-bein-sport-purple-television-violet.png";
        int src = R.drawable.logo;
        List<MarqueeItem> items = new ArrayList<>();
        if (Utils.isAr(this)) {
            items.add(new MarqueeItem("هذا هو النص الاول ", ""));
            items.add(new MarqueeItem("هذا هو النص الثاني ", src));
            items.add(new MarqueeItem("هذا هو النص الثالث ", src));
            items.add(new MarqueeItem("هذا هو النص الرابع ", src));
            items.add(new MarqueeItem("هذا هو النص الخامس ", src));
            items.add(new MarqueeItem("هذا هو النص السادس ", src));
            items.add(new MarqueeItem("هذا هو النص السابع ", src));
            items.add(new MarqueeItem("هذا هو النص الثامن ", src));
        } else {
            items.add(new MarqueeItem("This is the first item", src));
            items.add(new MarqueeItem("This is the second item", src));
            items.add(new MarqueeItem("This is the third item",  src));
            items.add(new MarqueeItem("This is the fourth item", src));
            items.add(new MarqueeItem("This is the fifth item",  src));
            items.add(new MarqueeItem("This is the sixth item",  src));
            items.add(new MarqueeItem("This is the seventh item", src));
            items.add(new MarqueeItem("This is the eighth item",src));
        }

        if (marqueeRecyclerView != null) {
            marqueeRecyclerView.setMarqueeItems(items);
            btn.setOnClickListener(view -> {
                if (isPlaying) {
                    btn.setText("Start");
                    marqueeRecyclerView.pauseMarquee();
                } else {
                    btn.setText("Pause");
                    marqueeRecyclerView.startMarquee();
                }
                isPlaying = !isPlaying;
            });
        }


        Slider spacingSlider = findViewById(R.id.slider_spacing_width);
        Slider scrollWidth = findViewById(R.id.slider_scroll_width);

        spacingSlider.addOnChangeListener((slider, value, fromUser) -> {
            assert marqueeRecyclerView != null;
            marqueeRecyclerView.setSpacingWidth(value);
        });

        scrollWidth.addOnChangeListener((slider, value, fromUser) -> {
            assert marqueeRecyclerView != null;
            marqueeRecyclerView.setScrollAmount((int) value);
        });

    }
}