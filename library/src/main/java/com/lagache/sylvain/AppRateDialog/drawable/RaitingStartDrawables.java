package com.lagache.sylvain.AppRateDialog.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.lagache.sylvain.AppRateDialog.R;

/**
 * Rating bar drawables
 */
public class RaitingStartDrawables {

    public static LayerDrawable getRatingStar(Context context){
        LayerDrawable star = (LayerDrawable) ContextCompat.getDrawable(context, R.drawable.rate_dialog_rating_bar);
        star.setDrawableByLayerId(android.R.id.background, getEmptyStar(context));
        star.setDrawableByLayerId(android.R.id.secondaryProgress, getEmptyStar(context));
        star.setDrawableByLayerId(android.R.id.progress, getFilledStar(context));
        return star;
    }

    public static StateListDrawable getEmptyStar(Context context){

        Drawable emptyStarGrey = ContextCompat.getDrawable(context, R.drawable.ic_star_border_grey_36dp);
        Drawable emptyStarColored = ContextCompat.getDrawable(context, R.drawable.ic_star_border_green_36dp);
        emptyStarColored.setColorFilter(getAccentColor(context), PorterDuff.Mode.SRC_IN);

        return new StateDrawableBuilder()
                .setNormalDrawable(emptyStarGrey)
                .setSelectedDrawable(emptyStarColored)
                .setPressedDrawable(emptyStarColored)
                .setDisabledDrawable(emptyStarGrey)
                .build();
    }

    public static StateListDrawable getFilledStar(Context context){

        Drawable starColored = ContextCompat.getDrawable(context, R.drawable.ic_star_green_36dp);
        starColored.setColorFilter(getAccentColor(context), PorterDuff.Mode.SRC_IN);

        return new StateDrawableBuilder()
                .setNormalDrawable(starColored)
                .setSelectedDrawable(starColored)
                .setPressedDrawable(starColored)
                .setDisabledDrawable(starColored)
                .build();
    }

    private static int getAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorAccent });
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }
}
