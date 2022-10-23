package mhdmoh.marquee_recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class MarqueeBackground extends View {

    private Path whitePath;
    private Paint whitePaint;
    private RectF rightOval;
    private RectF leftOval;
    private int mainColor;
    private int marqueeColor;
    private float spacingWidth;
    private int style = 0;

    public MarqueeBackground(Context context) {
        super(context);
        init(context, null, -1, -1);
    }

    public MarqueeBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, -1, -1);
    }

    public MarqueeBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, -1);
    }

    public MarqueeBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setStyle(int style) {
        this.style = style;
        invalidate();
    }

    public void setMainColor(int mainColor) {
        this.mainColor = mainColor;
        invalidate();
    }

    public void setMarqueeColor(int marqueeColor) {
        this.marqueeColor = marqueeColor;
        invalidate();
    }

    public void setSpacingWidth(float spacingWidth) {
        this.spacingWidth = spacingWidth;
        whitePath = new Path();
        whitePaint = new Paint();
        invalidate();
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mainColor = ContextCompat.getColor(context, R.color.purple);
        spacingWidth = 0.1f;
        marqueeColor = Color.WHITE;
        whitePath = new Path();
        whitePaint = new Paint();

        rightOval = new RectF();
        leftOval = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mainColor);
        boolean isAr = Utils.isAr(getContext());

        whitePaint.setColor(marqueeColor);
        float height = 0.75f;
        if (style != 0)
            height = 1;

        if (isAr) {
            rightOval.set(
                    getWidth() * (1 - spacingWidth) - (getHeight() * height),
                    0,
                    getWidth() * (1 - spacingWidth),
                    getHeight() * height);


            leftOval.set(
                    getWidth() * spacingWidth,
                    getHeight() * height,
                    getWidth() * spacingWidth + (getHeight() * (1 - height)),
                    getHeight()
            );
            whitePath.moveTo(0, 0);
            whitePath.lineTo(getWidth() * (1 - spacingWidth), 0);
            whitePath.lineTo(getWidth() * (1 - spacingWidth), getHeight() * height);
            if (style == 0)
                whitePath.arcTo(rightOval, 0, 90, false);
            whitePath.lineTo(getWidth() * spacingWidth, getHeight() * height);
            if (style == 0)
                whitePath.arcTo(leftOval, 270, -90, false);
            whitePath.lineTo(getWidth() * spacingWidth, getHeight());
            whitePath.lineTo(0, getHeight());
            whitePath.close();
        } else {
            rightOval.set(
                    getWidth() * spacingWidth,
                    0,
                    getWidth() * spacingWidth + getHeight() * height,
                    getHeight() * height
            );


            leftOval.set(
                    getWidth() * (1 - spacingWidth) - getHeight() * (1 - height),
                    getHeight() * height,
                    getWidth() * (1 - spacingWidth),
                    getHeight()
            );
            whitePath.moveTo(getWidth(), 0);
            whitePath.lineTo(getWidth() * spacingWidth, 0);
            whitePath.lineTo(getWidth() * spacingWidth, getHeight() * (0.75f / 2));
            if (style == 0)
                whitePath.arcTo(rightOval, -90, -180, false);
            whitePath.lineTo(getWidth() * (1 - spacingWidth), getHeight() * height);
            if (style == 0)
                whitePath.arcTo(leftOval, 270, 90, false);
            whitePath.lineTo(getWidth() * (1 - spacingWidth), getHeight());
            whitePath.lineTo(getWidth(), getHeight());
        }


        canvas.drawPath(whitePath, whitePaint);
    }
}
