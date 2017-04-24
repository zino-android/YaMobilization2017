package com.zino.translatermobilizationmvp.screens.translate.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zino.translatermobilizationmvp.R;



public class MicrophoneView extends View {

    private float oneDP;
    private Paint paint;
    private Paint background;
    private ValueAnimator valueAnimator;
    private boolean animationStarted = false;
    private int backgroundColor;
    private int oneColor;
    private int twoColor;
    private int threeColor;
    private int fourColor;

    public MicrophoneView(Context context) {
        super(context);
        init();
    }

    public MicrophoneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public MicrophoneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public void startAnimation() {
        valueAnimator.start();
        animationStarted = true;
        setVisibility(VISIBLE);
    }

    public void stopAnimation() {
        valueAnimator.cancel();
        animationStarted = false;
        setVisibility(GONE);
    }

    private void init() {

        backgroundColor = Color.parseColor("#fff8e0");
        oneColor = Color.parseColor("#fde48f");
        twoColor = Color.parseColor("#ffdc61");
        threeColor = Color.parseColor("#ffd326");
        fourColor = Color.parseColor("#ffcc00");

        oneDP = getResources().getDimension(R.dimen.one_dp);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        background = new Paint();

        background.setFlags(Paint.ANTI_ALIAS_FLAG);
        background.setColor(backgroundColor);
        valueAnimator = ValueAnimator.ofFloat(14f, 48f);
        valueAnimator.setDuration(1500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                invalidate();
            }
        });

    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {

        if (animationStarted) {
            background.setColor(backgroundColor);
            canvas.drawRect(0, 0, getWidth(), getHeight(), background);

            float anim = (float) valueAnimator.getAnimatedValue();

            //находим центр
            int x = getWidth() - (int) oneDP * 26;
            int y = (int) oneDP * 24;

            if (anim < 48 && anim > 36) {
                paint.setColor(oneColor);
                canvas.drawCircle(x, y, oneDP * anim, paint);
                paint.setColor(twoColor);
                canvas.drawCircle(x, y, oneDP * 36, paint);
                paint.setColor(threeColor);
                canvas.drawCircle(x, y, oneDP * 24, paint);
                paint.setColor(fourColor);
                canvas.drawCircle(x, y, oneDP * 14, paint);
            }

            if (anim < 36 && anim > 24) {
                paint.setColor(twoColor);
                canvas.drawCircle(x, y, oneDP * anim, paint);
                paint.setColor(threeColor);
                canvas.drawCircle(x, y, oneDP * 24, paint);
                paint.setColor(fourColor);
                canvas.drawCircle(x, y, oneDP * 14, paint);
            }

            if (anim < 24 && anim > 14) {
                paint.setColor(threeColor);
                canvas.drawCircle(x, y, oneDP * anim, paint);
                paint.setColor(fourColor);
                canvas.drawCircle(x, y, oneDP * 14, paint);
            }


        } else {
            background.setColor(Color.WHITE);
            canvas.drawRect(0, 0, getWidth(), getHeight(), background);
        }
    }
}

