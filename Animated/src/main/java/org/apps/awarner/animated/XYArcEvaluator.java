package org.apps.awarner.animated;

import android.animation.TypeEvaluator;
import android.graphics.PointF;
import android.util.Log;

/**
 * Custom evaluator that makes a spiral path.
 * Created by andy on 7/7/13.
 */
public class XYArcEvaluator implements TypeEvaluator {
    private static final String TAG = "AnimatedXYArc";

    private int nrRotations;

    public XYArcEvaluator(boolean clockWise) {
        super();

        nrRotations = clockWise ? 10 : -10;
    }

    public Object evaluate(float fraction, Object startValue, Object endValue) {
        XYHolder startXY = (XYHolder) startValue;
        XYHolder endXY = (XYHolder) endValue;

        double x = Math.pow(Math.abs(endXY.getX() - startXY.getX()), 2);
        double y = Math.pow(Math.abs(endXY.getY() - startXY.getY()), 2);
        double dist = Math.sqrt(x + y) * (1 - fraction);

        double startRad = Math.atan2(startXY.getY() - endXY.getY(), 
				     startXY.getX() - endXY.getX());
        double rad = startRad + fraction * Math.PI * nrRotations;  // Make # rotations

        x = endXY.getX() + Math.cos(rad) * dist;
        y = endXY.getY() + Math.sin(rad) * dist;

        return new XYHolder((float) x, (float) y);
    }
}
