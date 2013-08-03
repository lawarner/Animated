package org.apps.awarner.animated;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * View to hold animations.
 */
public class AnimatedView extends View implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    private static final String TAG = "AnimatedView";

    public final ArrayList<ShapeHolder> balls = new ArrayList<ShapeHolder>();


    public AnimatedView(Activity activity) {
        super(activity);

        ValueAnimator colorAnim = ObjectAnimator.ofInt(this, "backgroundColor", Color.BLUE, Color.BLUE);
        colorAnim.setDuration(30000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
//        colorAnim.start();

        addBall(100f, 100f);
        addBall(400f, 900f);
        addBall(200f, 600f);
        addBall(300f, 300f);
        addBall(500f, 500f);
    }


    private ShapeHolder addBall(float x, float y) {
        float radius = 64f;

        OvalShape circle = new OvalShape();
        circle.resize(radius, radius);
        ShapeDrawable drawable = new ShapeDrawable(circle);
        ShapeHolder shapeHolder = new ShapeHolder(drawable);
        shapeHolder.setXY(new XYHolder(x - radius / 2, y - radius / 2));
        int red = (int)(Math.random() * 230) + 25;
        int green = (int)(Math.random() * 230) + 25;
        int blue = (int)(Math.random() * 230) + 25;
        int color = 0xff000000 | red << 16 | green << 8 | blue;
        Paint paint = drawable.getPaint(); //new Paint(Paint.ANTI_ALIAS_FLAG);
        int darkColor = 0xff000000 | red/4 << 16 | green/4 << 8 | blue/4;
        RadialGradient gradient = new RadialGradient(37.5f, radius/4,
                radius, color, darkColor, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        shapeHolder.setPaint(paint);
        balls.add(shapeHolder);

        return shapeHolder;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < balls.size(); ++i) {
            ShapeHolder shapeHolder = balls.get(i);
            canvas.save();
            canvas.translate(shapeHolder.getX(), shapeHolder.getY());
            shapeHolder.getShape().draw(canvas);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return false;
        }

        Random rnd = new Random();
        ArrayList<Animator> toAnimations = new ArrayList<Animator>();
        ArrayList<Animator> fromAnimations = new ArrayList<Animator>();

        XYHolder touchXY = new XYHolder(event.getX(), event.getY());
        boolean clockWise = false;
        for (ShapeHolder ball : balls) {
            long duration = 3600;
            clockWise = !clockWise;

            XYHolder ballXY = new XYHolder(ball.getX(), ball.getY());
            ValueAnimator flytoAnim = ObjectAnimator.ofObject(ball, "xY", new XYArcEvaluator(clockWise), ballXY, touchXY);
            flytoAnim.setDuration(duration);
            flytoAnim.setRepeatCount(0);
            flytoAnim.setInterpolator(new AccelerateInterpolator());
            if (toAnimations.size() == 0) {
                flytoAnim.addUpdateListener(this);
            }
            toAnimations.add(flytoAnim);

            XYHolder endXY = new XYHolder(rnd.nextInt(getWidth()), rnd.nextInt(getHeight()));
            ValueAnimator flybackAnim = ObjectAnimator.ofObject(ball, "xY", new XYEvaluator(), touchXY, endXY);
            flybackAnim.setDuration(270);
            flybackAnim.setRepeatCount(0);
            flybackAnim.setInterpolator(new DecelerateInterpolator());
            if (fromAnimations.size() == 0)
                flybackAnim.addUpdateListener(this);

            fromAnimations.add(flybackAnim);
        }

        if (toAnimations.size() > 0) {
            AnimatorSet anim1 = new AnimatorSet();
            AnimatorSet anim2 = new AnimatorSet();
            anim1.playTogether(toAnimations);
            anim2.playTogether(fromAnimations);

            AnimatorSet animator = new AnimatorSet();

            animator.play(anim1).before(anim2);
            animator.addListener(this);
            animator.start();
        }

        return true;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        invalidate();
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        Log.d(TAG, "animator ended.");
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
