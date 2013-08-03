/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apps.awarner.animated;

import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.view.View;

/**
 * A data structure that holds a Shape and various properties that can be used to define
 * how the shape is drawn.
 */
public class ShapeHolder {
    private XYHolder xy = new XYHolder(0f, 0f);
    private ShapeDrawable shape;
    private int color;
    private RadialGradient gradient;
    private float alpha = 1f;
    private Paint paint;

    public void setPaint(Paint value) {
        paint = value;
    }
    public Paint getPaint() {
        return paint;
    }

    public void setX(float value) {
        xy.setX(value);
    }
    public float getX() {
        return xy.getX();
    }
    public void setY(float value) {
        xy.setY(value);
    }
    public float getY() {
        return xy.getY();
    }
    public XYHolder getXY() {
        return new XYHolder(xy.getX(), xy.getY());
    }
    public void setXY(XYHolder _xy) {
        xy = _xy;
    }
    public void setShape(ShapeDrawable value) {
        shape = value;
    }
    public ShapeDrawable getShape() {
        return shape;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int value) {
        shape.getPaint().setColor(value);
        color = value;
    }
    public void setGradient(RadialGradient value) {
        gradient = value;
    }
    public RadialGradient getGradient() {
        return gradient;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        shape.setAlpha((int)((alpha * 255f) + .5f));
    }

    public float getWidth() {
        return shape.getShape().getWidth();
    }
    public void setWidth(float width) {
        Shape s = shape.getShape();
        s.resize(width, s.getHeight());
    }

    public float getHeight() {
        return shape.getShape().getHeight();
    }
    public void setHeight(float height) {
        Shape s = shape.getShape();
        s.resize(s.getWidth(), height);
    }

    public ShapeHolder(ShapeDrawable s) {
        shape = s;
    }
}
