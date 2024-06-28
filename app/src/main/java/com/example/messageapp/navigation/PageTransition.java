package com.example.messageapp.navigation;

import android.app.Activity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import java.util.Arrays;
import java.util.List;

public class PageTransition {

    private static final Long animationDuration = 2000L;

    public static void transitionTo(Activity activity, Transition transition) {
        switch (transition) {
            case LEFT_TO_RIGHT:
                activity.overridePendingTransition(createTranslateAnimation(1, 0), createTranslateAnimation(0, 1));
                break;
            case RIGHT_TO_LEFT:
                activity.overridePendingTransition(createTranslateAnimation(-1, 0), createTranslateAnimation(0, -1));
                break;
            case BOTTOM_TO_TOP:
                activity.overridePendingTransition(createTranslateAnimation(0, 1), createTranslateAnimation(0, -1));
                break;
            case TOP_TO_BOTTOM:
                activity.overridePendingTransition(createTranslateAnimation(0, -1), createTranslateAnimation(0, 1));
                break;
            case FADE_IN_OUT:
                activity.overridePendingTransition(createFadeAnimation(0, 1), createFadeAnimation(1, 0));
                break;
            case SCALE_IN_OUT:
                activity.overridePendingTransition(createScaleAnimation(0, 1), createScaleAnimation(1, 0));
                break;
        }
    }

    public static void transitionBack(Activity activity, Transition transition) {
        switch (transition) {
            case LEFT_TO_RIGHT:
            case BOTTOM_TO_TOP:
                activity.overridePendingTransition(createTranslateAnimation(0, -1), createTranslateAnimation(-1, 0));
                break;
            case RIGHT_TO_LEFT:
            case TOP_TO_BOTTOM:
                activity.overridePendingTransition(createTranslateAnimation(0, 1), createTranslateAnimation(1, 0));
                break;
            case FADE_IN_OUT:
                activity.overridePendingTransition(createFadeAnimation(1, 0), createFadeAnimation(0, 1));
                break;
            case SCALE_IN_OUT:
                activity.overridePendingTransition(createScaleAnimation(1, 0), createScaleAnimation(0, 1));
                break;
        }
    }

    private static int createTranslateAnimation(float fromXDelta, float toXDelta) {
        Animation anim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, fromXDelta,
                Animation.RELATIVE_TO_PARENT, toXDelta,
                Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0
        );
        anim.setDuration(animationDuration);
        anim.setFillAfter(true);
        return 0; // Placeholder: You can ignore ID generation in this example
    }

    private static int createFadeAnimation(float fromAlpha, float toAlpha) {
        AlphaAnimation anim = new AlphaAnimation(fromAlpha, toAlpha);
        anim.setDuration(animationDuration);
        anim.setFillAfter(true);
        return 0; // Placeholder: You can ignore ID generation in this example
    }

    private static int createScaleAnimation(float fromScale, float toScale) {
        ScaleAnimation anim = new ScaleAnimation(
                fromScale, toScale,
                fromScale, toScale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        anim.setDuration(animationDuration);
        anim.setFillAfter(true);
        return 0; // Placeholder: You can ignore ID generation in this example
    }

    public enum Transition {
        LEFT_TO_RIGHT(0, 0, 0, 0, Arrays.asList(1, 0), Arrays.asList(0, 1)),
        RIGHT_TO_LEFT(0, 0, 0, 0, Arrays.asList(-1, 0), Arrays.asList(0, -1)),
        BOTTOM_TO_TOP(0, 0, 0, 0, Arrays.asList(0, 1), Arrays.asList(0, -1)),
        TOP_TO_BOTTOM(0, 0, 0, 0, Arrays.asList(0, -1), Arrays.asList(0, 1)),
        FADE_IN_OUT(0, 0, 0, 0, Arrays.asList(0, 1), Arrays.asList(1, 0)),
        SCALE_IN_OUT(0, 0, 0, 0, Arrays.asList(0, 1), Arrays.asList(1, 0)),
        ;

        public final int fromX;
        private final int toX;
        private final int fromY;
        private final int toY;
        private final List<Integer> inAnim;
        private final List<Integer> outAnim;

        Transition(int fromX, int toX, int fromY, int toY, List<Integer> inAnim, List<Integer> outAnim) {
            this.fromX = fromX;
            this.toX = toX;
            this.fromY = fromY;
            this.toY = toY;
            this.inAnim = inAnim;
            this.outAnim = outAnim;
        }

        public int getFromX() {
            return fromX;
        }

        public int getToX() {
            return toX;
        }

        public int getToY() {
            return toY;
        }

        public int getFromY() {
            return fromY;
        }

        public List<Integer> getInAnim() {
            return inAnim;
        }

        public List<Integer> getOutAnim() {
            return outAnim;
        }
    }
}
