package rjsv.morphos;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Collections;

import rjsv.morphos.data.ViewDefault;
import rjsv.morphos.data.animations.AlphaAnimation;
import rjsv.morphos.data.animations.Animation;
import rjsv.morphos.data.animations.AxisTranslationAnimation;
import rjsv.morphos.data.animations.DimensionsAnimation;
import rjsv.morphos.data.animations.RotationAnimation;
import rjsv.morphos.data.animations.RotationXYAnimation;
import rjsv.morphos.data.animations.ScaleAnimation;
import rjsv.morphos.data.animations.TranslationAnimation;
import rjsv.morphos.enumerators.AnimationTarget;
import rjsv.morphos.enumerators.AnimationType;

import static rjsv.morphos.data.animations.AxisTranslationAnimation.Axis;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class Morpho {

    // Default Values
    public static final boolean atLeastLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    private static final AnimationType defaultAnimationType = AnimationType.SEQUENTIAL;
    private static final Interpolator defaultInterpolator = new LinearInterpolator();

    // #1 - Initial View and respective default helper class
    private View viewToMorph;
    private ViewDefault viewDefault;
    // #2 - The reference to the Complete Animation
    private AnimatorSet completeAnimation;
    // #3 - The Complete Animation's sub-parts
    private ArrayList<Animation> animationParts;
    // #4 - The built-in configurations for the Complete Animation
    private int completeAnimationDuration = -1;
    private AnimationType completeAnimationAnimationType;
    private Interpolator completeAnimationInterpolator;
    // #5 - The Complete Animation Listener
    private Animator.AnimatorListener completeAnimationListener;

    // Constructor
    public Morpho(@NonNull View viewToMorph) {
        updateView(viewToMorph, true);
    }


    // Set & Get View
    public Morpho updateView(@NonNull View viewToMorph) {
        updateView(viewToMorph, true);
        return this;
    }

    private void updateView(@NonNull View viewToMorph, boolean shouldResetMorpho) {
        this.viewToMorph = viewToMorph;
        this.viewDefault = new ViewDefault(viewToMorph);
        this.completeAnimation = new AnimatorSet();
        if (shouldResetMorpho) {
            this.reset();
        }
    }

    public int getAnimationDuration() {
        int duration = 0;
        if (this.completeAnimationDuration >= 0) {
            duration = this.completeAnimationDuration;
        } else if (animationParts != null && animationParts.size() > 0) {
            for (Animation a : animationParts) {
                duration += a.getDuration();
            }
        }
        return duration;
    }

    // Alpha
    public Morpho alpha(double value) {
        return alpha(value, 0, defaultInterpolator);
    }

    public Morpho alpha(double value, int duration) {
        return alpha(value, duration, defaultInterpolator);
    }

    public Morpho alpha(double value, int duration, Interpolator interpolator) {
        animationParts.add(new AlphaAnimation((float) value, duration, interpolator));
        return this;
    }


    // Expansion
    public Morpho scale(double valueX, double valueY) {
        return scale(valueX, valueY, 0, defaultInterpolator);
    }

    public Morpho scale(double valueX, double valueY, int duration) {
        return scale(valueX, valueY, duration, defaultInterpolator);
    }

    public Morpho scale(double valueX, double valueY, int duration, Interpolator interpolator) {
        if (viewDefault != null) {
            animationParts.add(new ScaleAnimation(viewDefault.getExpansionScaleX(), viewDefault.getExpansionScaleY(), (float) valueX, (float) valueY, duration, interpolator));
        }
        return this;
    }


    // Translation X
    public Morpho translationX(AnimationTarget target, float valueX) {
        return translationX(target, valueX, 0, defaultInterpolator);
    }

    public Morpho translationX(AnimationTarget target, float valueX, int duration) {
        return translationX(target, valueX, duration, defaultInterpolator);
    }

    public Morpho translationX(AnimationTarget target, float valueX, int duration, Interpolator interpolator) {
        animationParts.add(new AxisTranslationAnimation(Axis.X, target, valueX, duration, interpolator));
        return this;
    }

    // Translation Y
    public Morpho translationY(AnimationTarget target, float valueY) {
        return translationY(target, valueY, 0, defaultInterpolator);
    }

    public Morpho translationY(AnimationTarget target, float valueY, int duration) {
        return translationY(target, valueY, duration, defaultInterpolator);
    }

    public Morpho translationY(AnimationTarget target, float valueY, int duration, Interpolator interpolator) {
        animationParts.add(new AxisTranslationAnimation(Axis.Y, target, valueY, duration, interpolator));
        return this;
    }

    // Translation Z
    public Morpho translationZ(AnimationTarget target, float valueZ) {
        return translationZ(target, valueZ, 0, defaultInterpolator);
    }

    public Morpho translationZ(AnimationTarget target, float valueZ, int duration) {
        return translationZ(target, valueZ, duration, defaultInterpolator);
    }

    public Morpho translationZ(AnimationTarget target, float valueZ, int duration, Interpolator interpolator) {
        animationParts.add(new AxisTranslationAnimation(Axis.Z, target, valueZ, duration, interpolator));
        return this;
    }


    // Global translation
    public Morpho translation(AnimationTarget target, float valueX, float valueY, float valueZ) {
        return translation(target, valueX, valueY, valueZ, 0, defaultInterpolator);
    }

    public Morpho translation(AnimationTarget target, float valueX, float valueY, float valueZ, int duration) {
        return translation(target, valueX, valueY, valueZ, duration, defaultInterpolator);
    }

    public Morpho translation(AnimationTarget target, float valueX, float valueY, float valueZ, int duration, Interpolator interpolator) {
        animationParts.add(new TranslationAnimation(target, valueX, valueY, valueZ, duration, interpolator));
        return this;
    }


    // Dimensions
    public Morpho dimensions(float width, float height) {
        return dimensions(width, height, 0, null);
    }

    public Morpho dimensions(float width, float height, int duration) {
        return dimensions(width, height, duration, defaultInterpolator);
    }

    public Morpho dimensions(float width, float height, int duration, Interpolator interpolator) {
        if (width >= 0 && height >= 0) {
            animationParts.add(new DimensionsAnimation(width, height, duration, interpolator));
        }
        return this;
    }


    // Rotation XY
    public Morpho rotationXY(AnimationTarget rotationType, double degreesX, double degreesY) {
        return rotationXY(rotationType, degreesX, degreesY, 0, defaultInterpolator);
    }

    public Morpho rotationXY(AnimationTarget rotationType, double degreesX, double degreesY, int duration) {
        return rotationXY(rotationType, degreesX, degreesY, duration, defaultInterpolator);
    }

    public Morpho rotationXY(AnimationTarget rotationType, double degreesX, double degreesY, int duration, Interpolator interpolator) {
        animationParts.add(new RotationXYAnimation(rotationType, (float) degreesX, (float) degreesY, duration, interpolator));
        return this;
    }


    // Rotation
    public Morpho rotation(AnimationTarget rotationType, double degrees) {
        return rotation(rotationType, degrees, 0, defaultInterpolator);
    }

    public Morpho rotation(AnimationTarget rotationType, double degrees, int duration) {
        return rotation(rotationType, degrees, duration, defaultInterpolator);
    }

    public Morpho rotation(AnimationTarget rotationType, double degrees, int duration, Interpolator interpolator) {
        animationParts.add(new RotationAnimation(rotationType, (float) degrees, duration, interpolator));
        return this;
    }


    // Build Animation Set
    public Morpho build() {
        return build(defaultAnimationType, -1, defaultInterpolator);
    }

    public Morpho build(AnimationType animationType, int duration) {
        return build(animationType, duration, defaultInterpolator);
    }

    public Morpho build(AnimationType animationType, int duration, Interpolator interpolator) {
        if (animationType != null) {
            this.completeAnimationAnimationType = animationType;
        }
        this.completeAnimationDuration = duration;
        if (interpolator != null) {
            this.completeAnimationInterpolator = interpolator;
        }
        return this;
    }


    // Animate Animator Set - User's accessible 'play' method
    public Morpho animate() {
        return animate(defaultAnimationType, -1, defaultInterpolator);
    }

    public Morpho animate(AnimationType animationType, int duration) {
        return animate(animationType, duration, defaultInterpolator);
    }

    public Morpho animate(AnimationType type, int duration, Interpolator interpolator) {
        build(type, duration, interpolator);
        // Method parameter call
        // #1 - forcePlay - this would start the animation regardless of it being running or not
        // #2 - listener - used by Swarm to chain the sequential animations
        return play(false, null);
    }


    // Play Animator Set - Swarm's accessible 'play' method
    Morpho play(boolean forcePlay, Animator.AnimatorListener swarmListener) {
        boolean animationPartsIsConfigured = this.animationParts != null && this.animationParts.size() > 0;
        boolean coreViewIsInitialized = this.viewToMorph != null && this.viewDefault != null;
        boolean animationIsNotRunning = this.completeAnimation == null || (!this.completeAnimation.isRunning() || forcePlay);
        if (animationPartsIsConfigured && coreViewIsInitialized && animationIsNotRunning) {
            // Mandatory updateView call - ensures the view is updated and the animatorSet is created
            updateView(this.viewToMorph, false);
            ArrayList<Animator> animators = new ArrayList<>();
            for (Animation a : this.animationParts) {
                Animator anim = a.buildAnimation(this.viewDefault, this.viewToMorph, false);
                if (anim != null) {
                    animators.add(anim);
                }
            }
            // Type of Animation
            if (this.completeAnimationAnimationType == AnimationType.SEQUENTIAL) {
                this.completeAnimation.playSequentially(animators);
            } else {
                this.completeAnimation.playTogether(animators);
            }
            // Duration
            if (this.completeAnimationDuration >= 0) {
                this.completeAnimation.setDuration(this.completeAnimationDuration);
            }
            // Interpolator
            if (this.completeAnimationInterpolator != null) {
                this.completeAnimation.setInterpolator(this.completeAnimationInterpolator);
            }
            // Listener
            this.completeAnimation.removeAllListeners();
            if (swarmListener != null) {
                this.completeAnimation.addListener(swarmListener);
            }
            if (this.completeAnimationListener != null) {
                this.completeAnimation.addListener(this.completeAnimationListener);
            }
            this.completeAnimation.start();
        }
        return this;
    }


    // Animator Set's Listener
    public Morpho setListener(Animator.AnimatorListener listener) {
        this.completeAnimationListener = listener;
        return this;
    }


    // Reverse Animation Set
    public Morpho reverse() {
        return reverse(defaultAnimationType, -1, defaultInterpolator);
    }

    public Morpho reverse(AnimationType animationType, int duration) {
        return reverse(animationType, duration, defaultInterpolator);
    }

    public Morpho reverse(AnimationType type, int duration, Interpolator interpolator) {
        boolean animationPartsIsConfigured = this.animationParts != null && this.animationParts.size() > 0;
        boolean coreViewIsInitialized = this.viewToMorph != null && this.viewDefault != null;
        boolean animationIsNotRunning = this.completeAnimation != null && !this.completeAnimation.isRunning();
        if (animationPartsIsConfigured && coreViewIsInitialized && animationIsNotRunning) {
            this.completeAnimation = new AnimatorSet();
            ArrayList<Animation> reversedAnimationParts = new ArrayList<>();
            reversedAnimationParts.addAll(animationParts);
            Collections.reverse(reversedAnimationParts);
            ArrayList<Animator> animators = new ArrayList<>();
            for (Animation a : reversedAnimationParts) {
                animators.add(a.buildAnimation(this.viewDefault, this.viewToMorph, true));
            }
            // Type of Animation
            if (type == AnimationType.SEQUENTIAL) {
                this.completeAnimation.playSequentially(animators);
            } else {
                this.completeAnimation.playTogether(animators);
            }
            // Duration
            if (duration >= 0) {
                this.completeAnimation.setDuration(duration);
            }
            // Interpolator
            if (interpolator != null) {
                this.completeAnimation.setInterpolator(interpolator);
            }
            this.completeAnimation.start();
            this.completeAnimation = null;
        }
        return this;
    }


    // Reset Method
    public Morpho reset() {
        completeAnimationAnimationType = defaultAnimationType;
        completeAnimationInterpolator = defaultInterpolator;
        if (animationParts != null) {
            animationParts.clear();
        } else {
            animationParts = new ArrayList<>();
        }
        return this;
    }


    // Cancel Methods
    public void cancel() {
        if (completeAnimation != null) {
            completeAnimation.end();
        }
        reverse(defaultAnimationType, 0, defaultInterpolator);
    }


    // Dispose Methods
    public void dispose() {
        viewToMorph = null;
        viewDefault = null;
        if (animationParts != null) {
            for (Animation a : animationParts) {
                a.dispose();
            }
            animationParts.clear();
            animationParts = null;
        }
        if (completeAnimation != null) {
            completeAnimation.removeAllListeners();
            completeAnimation.cancel();
            completeAnimation = null;
        }
        completeAnimationAnimationType = null;
        completeAnimationInterpolator = null;
        completeAnimationListener = null;
    }

}
