package com.zino.translatermobilizationmvp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;



public class UtilsDrawable {

    public static Drawable getTintDrawable(Context context, Drawable drawable, int colorRes) {
        Drawable drawable2 = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(drawable2, context.getResources().getColor(colorRes));
        return drawable;
    }

}
