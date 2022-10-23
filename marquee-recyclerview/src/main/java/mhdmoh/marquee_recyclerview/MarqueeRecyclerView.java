package mhdmoh.marquee_recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MarqueeRecyclerView extends ConstraintLayout {
    final long totalScrollTime = Long.MAX_VALUE;
    int scrollAmount = 2;
    int scrollInterval = 10;
    private RecyclerView recyclerView;
    private ImageView logoIV;
    private LinearLayoutManager layoutManager;
    private MarqueeRecyclerViewAdapter adapter;
    private CountDownTimer timer;
    private List<MarqueeItem> items;
    private int lastPosition = 0;
    private MarqueeBackground marqueeBackground;
    private float spacingWidth = -1;
    private int marqueeColor = -1;
    private int style = 0;
    private int[] logoPaddingArr = {0, 0, 0, 0};

    public MarqueeRecyclerView(@NonNull Context context) {
        super(context);
        init(context, null, -1, -1);
    }

    public MarqueeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1, -1);
    }

    public MarqueeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, -1);
    }

    public MarqueeRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollInterval(int scrollInterval) {
        this.scrollInterval = scrollInterval;
        invalidate();
    }

    public void loadLogoFromUrl(String fullUrl) {
        Picasso.get().load(fullUrl).into(logoIV);
        invalidate();
    }


    /**
     * setScrollAmount
     * this is used to control the amount the list scroll each interval
     * default value is 20
     * minimum is smother
     */
    public void setScrollAmount(int scrollAmount) {
        this.scrollAmount = scrollAmount;
        invalidate();
    }

    /**
     * @param items are whats going to show in the list,
     * @implNote each MarqueeItem has text and image
     * the image can be a url using iconUrl or resource iconResource
     * @see MarqueeItem
     */
    public void setMarqueeItems(List<MarqueeItem> items) {
        this.items = items;
        adapter.setItems(items);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        //adding background
        if (getId() == -1)
            setId(R.id.constraint_layout);

        marqueeBackground = new MarqueeBackground(context);
        marqueeBackground.setId(R.id.background);

        addView(
                marqueeBackground
        );

        //add Recyclerview
        recyclerView = new RecyclerView(context);
        recyclerView.setId(R.id.recyclerview);
        setListConstrainedWidth(0.9f);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(100);
        addView(recyclerView);

        //setup adapter
        adapter = new MarqueeRecyclerViewAdapter(context, () -> {
            if (layoutManager.findLastVisibleItemPosition() > 1) {
                MarqueeItem first = items.get(0);
                items.remove(0);
                items.add(first);
                adapter.notifyItemMoved(0, adapter.getItemCount() - 1);
            }
        });
        recyclerView.setAdapter(adapter);
        //add Logo
        logoIV = new ImageView(context);
        logoIV.setId(R.id.logo);
        setLogoConstrainedWidth(0.1f);
        addView(logoIV);

        logoIV.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo));

        //setup layout manager
        layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        setUpTimer();

        if (attrs != null && defStyleAttr == -1 && defStyleRes == -1) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarqueeRecyclerView, defStyleAttr, -1);
            //main background color
            marqueeBackground.setMainColor(typedArray.getColor(R.styleable.MarqueeRecyclerView_main_background_color, ContextCompat.getColor(context, R.color.purple)));

            //marquee background color
            marqueeColor = typedArray.getColor(R.styleable.MarqueeRecyclerView_marquee_background_color, ContextCompat.getColor(context, R.color.white));
            marqueeBackground.setMarqueeColor(marqueeColor);

            //style
            style = typedArray.getInteger(R.styleable.MarqueeRecyclerView_marquee_style, 0);

            //logo src [Drawable]
            logoIV.setImageResource(typedArray.getResourceId(R.styleable.MarqueeRecyclerView_logo_src, R.drawable.ic_logo));
            boolean showLogo = typedArray.getBoolean(R.styleable.MarqueeRecyclerView_show_logo, true);
            if (!showLogo)
                logoIV.setVisibility(GONE);

            float logoPaddings = typedArray.getDimension(R.styleable.MarqueeRecyclerView_logo_padding, -1);
            float logoPaddingTop = typedArray.getDimension(R.styleable.MarqueeRecyclerView_logo_paddingTop, 0);
            float logoPaddingLeft = typedArray.getDimension(R.styleable.MarqueeRecyclerView_logo_paddingLeft, 0);
            float logoPaddingBottom = typedArray.getDimension(R.styleable.MarqueeRecyclerView_logo_paddingBottom, 0);
            float logoPaddingRight = typedArray.getDimension(R.styleable.MarqueeRecyclerView_logo_paddingRight, 0);
            logoPaddingArr = new int[4];
            if (logoPaddings != -1) {
                logoPaddingArr[0] = (int) logoPaddings;
                logoPaddingArr[1] = (int) logoPaddings;
                logoPaddingArr[2] = (int) logoPaddings;
                logoPaddingArr[3] = (int) logoPaddings;
            } else {
                logoPaddingArr[0] = (int) logoPaddingLeft;
                logoPaddingArr[1] = (int) logoPaddingTop;
                logoPaddingArr[2] = (int) logoPaddingBottom;
                logoPaddingArr[3] = (int) logoPaddingRight;
            }


            //spacing
            spacingWidth = Math.max(Math.min(typedArray.getFloat(R.styleable.MarqueeRecyclerView_spacing_width, 0.1f), 0.45f), 0.0f);
            setMarqueeBackgroundConstrainedWidth(spacingWidth);
            setLogoConstrainedWidth(spacingWidth);
            setListConstrainedWidth(1 - spacingWidth);
            marqueeBackground.setStyle(style);

            //speed
            setScrollInterval(typedArray.getInteger(R.styleable.MarqueeRecyclerView_scroll_interval, scrollInterval));
            setScrollAmount(typedArray.getInteger(R.styleable.MarqueeRecyclerView_scroll_amount, scrollAmount));

            //marquee list item
            //- divider
            boolean showDivider = typedArray.getBoolean(R.styleable.MarqueeRecyclerView_show_divider, true);
            float dividerWidth = typedArray.getDimension(R.styleable.MarqueeRecyclerView_divider_width, -1);
            int dividerColor = typedArray.getColor(R.styleable.MarqueeRecyclerView_divider_color, ContextCompat.getColor(context, R.color.purple));
            float dividerMargins = typedArray.getDimension(R.styleable.MarqueeRecyclerView_divider_margin, -1);
            float dividerMarginTop = typedArray.getDimension(R.styleable.MarqueeRecyclerView_divider_marginTop, 16);
            float dividerMarginsLeft = typedArray.getDimension(R.styleable.MarqueeRecyclerView_divider_marginLeft, 0);
            float dividerMarginsBottom = typedArray.getDimension(R.styleable.MarqueeRecyclerView_divider_marginBottom, 16);
            float dividerMarginsRight = typedArray.getDimension(R.styleable.MarqueeRecyclerView_divider_marginRight, 0);

            adapter.setShowDivider(showDivider);
            adapter.setDividerBackgroundColor(dividerColor);
            if (dividerWidth != -1)
                adapter.setDividerWidth(dividerWidth);
            int[] dividerMarginArr = new int[4];
            if (dividerMargins != -1) {
                dividerMarginArr[0] = (int) dividerMargins;
                dividerMarginArr[1] = (int) dividerMargins;
                dividerMarginArr[2] = (int) dividerMargins;
                dividerMarginArr[3] = (int) dividerMargins;
            } else {
                dividerMarginArr[0] = (int) dividerMarginsLeft;
                dividerMarginArr[1] = (int) dividerMarginTop;
                dividerMarginArr[2] = (int) dividerMarginsRight;
                dividerMarginArr[3] = (int) dividerMarginsBottom;
            }
            adapter.setDividerMargins(dividerMarginArr);

            //- text
            //--color
            adapter.setTextColor(typedArray.getColor(R.styleable.MarqueeRecyclerView_android_textColor, ContextCompat.getColor(getContext(), R.color.black)));

            //--size
            float textSize = typedArray.getDimension(R.styleable.MarqueeRecyclerView_android_textSize, -1);
            if (textSize != -1)
                adapter.setTextSize(textSize);

            //--style
            int textStyle = typedArray.getInteger(R.styleable.MarqueeRecyclerView_textStyle, 0);
            adapter.setTextStyle(textStyle);

            //--font family
            if (typedArray.hasValue(R.styleable.MarqueeRecyclerView_android_fontFamily)) {
                int fontId = typedArray.getResourceId(R.styleable.MarqueeRecyclerView_android_fontFamily, -1);
                Typeface typeface = ResourcesCompat.getFont(context, fontId);
                if (typeface != null) {
                    adapter.setTextFontFamily(typeface);
                }
            }


            //--margins
            float textMargins = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_margin, -1);
            float textMarginTop = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_marginTop, 0);
            float textMarginsLeft = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_marginLeft, 0);
            float textMarginsBottom = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_marginBottom, 0);
            float textMarginsRight = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_marginRight, 0);
            int[] textMarginArr = new int[4];
            if (textMargins != -1) {
                textMarginArr[0] = (int) textMargins;
                textMarginArr[1] = (int) textMargins;
                textMarginArr[2] = (int) textMargins;
                textMarginArr[3] = (int) textMargins;
            } else {
                textMarginArr[0] = (int) textMarginsLeft;
                textMarginArr[1] = (int) textMarginTop;
                textMarginArr[2] = (int) textMarginsBottom;
                textMarginArr[3] = (int) textMarginsRight;
            }
            adapter.setTextMargins(textMarginArr);

            //--paddings
            float textPaddings = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_padding, -1);
            float textPaddingTop = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_paddingTop, 0);
            float textPaddingsLeft = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_paddingLeft, 0);
            float textPaddingsBottom = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_paddingBottom, 0);
            float textPaddingsRight = typedArray.getDimension(R.styleable.MarqueeRecyclerView_text_paddingRight, 0);
            int[] textPaddingArr = new int[4];
            if (textPaddings != -1) {
                textPaddingArr[0] = (int) textPaddings;
                textPaddingArr[1] = (int) textPaddings;
                textPaddingArr[2] = (int) textPaddings;
                textPaddingArr[3] = (int) textPaddings;
            } else {
                textPaddingArr[0] = (int) textPaddingsLeft;
                textPaddingArr[1] = (int) textPaddingTop;
                textPaddingArr[2] = (int) textPaddingsBottom;
                textPaddingArr[3] = (int) textPaddingsRight;
            }
            adapter.setTextPaddings(textPaddingArr);

            typedArray.recycle();

        }
    }

    private void setListConstrainedWidth(float percentage) {
        LayoutParams rvlp = new LayoutParams(0, 0);
        rvlp.matchConstraintPercentWidth = percentage;
        if (style == 0) {
            rvlp.matchConstraintPercentHeight = 0.75f;
        } else {
            rvlp.topToTop = getId();
            rvlp.bottomToBottom = getId();

            if (marqueeColor == -1) {
                recyclerView.setBackgroundColor(Color.WHITE);
            } else
                recyclerView.setBackgroundColor(marqueeColor);
        }
        rvlp.startToEnd = R.id.logo;
        rvlp.endToEnd = getId();
        recyclerView.setLayoutParams(rvlp);
        invalidate();
    }


    private void setMarqueeBackgroundConstrainedWidth(float percentage) {
        marqueeBackground.setSpacingWidth(percentage);
        invalidate();
    }

    private void setLogoConstrainedWidth(float percentage) {
        LayoutParams ivlp = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        ivlp.matchConstraintPercentWidth = percentage;
        ivlp.startToStart = getId();
        ivlp.endToStart = R.id.recyclerview;
        logoIV.setLayoutParams(ivlp);
        logoIV.setPadding(logoPaddingArr[0], logoPaddingArr[1], logoPaddingArr[2], logoPaddingArr[3]);
        if (percentage == 0)
            logoIV.setVisibility(GONE);
        else
            logoIV.setVisibility(VISIBLE);
        invalidate();
    }

    private void setUpTimer() {
        boolean isAr = Utils.isAr(getContext());
        timer = new CountDownTimer(totalScrollTime, scrollInterval) {
            public void onTick(long millisUntilFinished) {
                if (isAr) {
                    lastPosition -= scrollAmount;
                    recyclerView.scrollTo(lastPosition, 0);
                    recyclerView.scrollBy(-scrollAmount, 0);
                } else {
                    lastPosition += scrollAmount;
                    recyclerView.scrollTo(lastPosition, 0);
                    recyclerView.scrollBy(scrollAmount, 0);
                }

                if (layoutManager.findFirstVisibleItemPosition() >= 1) {
                    adapter.load();
                }
            }

            public void onFinish() {
            }
        };
    }

    public void setSpacingWidth(float spacingWidth) {
        this.spacingWidth = spacingWidth;
        setMarqueeBackgroundConstrainedWidth(spacingWidth);
        setLogoConstrainedWidth(spacingWidth);
        setListConstrainedWidth(1 - spacingWidth);
        invalidate();
    }

    public void startMarquee() {
        if (timer == null)
            setUpTimer();
        timer.start();
    }

    public void resetMarquee() {
        lastPosition = 0;
    }

    public void pauseMarquee() {
        timer.cancel();
    }
}
