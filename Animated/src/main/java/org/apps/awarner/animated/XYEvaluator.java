package org.apps.awarner.animated;

import android.animation.TypeEvaluator;

public class XYEvaluator implements TypeEvaluator {
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        XYHolder startXY = (XYHolder) startValue;
        XYHolder endXY = (XYHolder) endValue;

        float x = startXY.getX() + fraction * (endXY.getX() - startXY.getX());
        float y = startXY.getY() + fraction * (endXY.getY() - startXY.getY());

        return new XYHolder(x, y);
    }
}
