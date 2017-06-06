package rjsv.morphos;

import android.animation.Animator;

import java.util.ArrayList;

import rjsv.morphos.enumerators.AnimationType;
import rjsv.morphos.listeners.SwarmListener;

/**
 * Description
 *
 * @author <a href="mailto:ricardo.vieira@xpand-it.com">RJSV</a>
 * @version $Revision : 1 $
 */

public class Swarm {

    private ArrayList<Morpho> swarm;
    private SwarmListener swarmListener;
    private Animator.AnimatorListener internalListener;

    // Constructor
    public Swarm() {
        swarm = new ArrayList<>();
    }

    // Add new Morpho
    public Swarm add(Morpho... morphList) {
        if (morphList != null) {
            for (Morpho m : morphList) {
                if (m != null) {
                    swarm.add(m);
                }
            }
        }
        return this;
    }

    // Play Method
    public Swarm play(AnimationType animationType) {
        int swarmSize = swarm != null ? swarm.size() : 0;
        if (swarmSize > 0) {
            if (animationType == AnimationType.COMBINED) {
                playCombined();
            } else {
                playSequential(0);
            }
        }
        return this;
    }

    // reverse all Morpho inside the Swarm
    public Swarm reverse() {
        boolean swarmExists = swarm != null && swarm.size() > 0;
        if (swarmExists) {
            for (Morpho m : swarm) {
                m.reverse();
            }
            if (swarmListener != null) {
                swarmListener.onSwarmRestore();
            }
        }
        return this;
    }

    public Swarm cancel() {
        boolean swarmExists = swarm != null && swarm.size() > 0;
        if (swarmExists) {
            for (Morpho m : swarm) {
                m.cancel();
            }
            if (swarmListener != null) {
                swarmListener.onSwarmRestore();
            }
        }
        return this;
    }

    public Swarm reset() {
        if (swarm != null) {
            swarm.clear();
        } else {
            swarm = new ArrayList<>();
        }
        return this;
    }

    // Internal callback && Helper Methods
    private void playSequential(final int index) {
        Morpho m;
        final int swarmSize = swarm.size();
        if (index >= 0 && index < swarmSize) {
            m = swarm.get(index);
            final boolean isFirstAnimator = index == 0;
            final boolean isLastAnimator = index + 1 == swarmSize;
            internalListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    if (isFirstAnimator && swarmListener != null) {
                        swarmListener.onSwarmStart();
                    }
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (isLastAnimator && swarmListener != null) {
                        swarmListener.onSwarmEnd();
                    }
                    playSequential(index + 1);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            };
            m.play(true, internalListener);
        }
    }

    private void playCombined() {
        int longestMorphos = getLongestMorphos();
        int index = 0;
        if (this.swarmListener != null) {
            swarmListener.onSwarmStart();
        }
        for (Morpho m : swarm) {
            if (longestMorphos == index) {
                internalListener = new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (swarmListener != null) {
                            swarmListener.onSwarmEnd();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                };
            }
            index += 1;
            m.play(true, internalListener);
        }
    }

    private int getLongestMorphos() {
        int swarmSize = swarm != null ? swarm.size() : 0;
        int index = -1;
        if (swarmSize > 0) {
            int duration = 0;
            for (int i = 0; i < swarmSize; i++) {
                int currentMorphosDuration = swarm.get(i).getAnimationDuration();
                if (currentMorphosDuration > duration) {
                    duration = currentMorphosDuration;
                    index = i;
                }
            }
        }
        return index;
    }

    // Swarm Listener
    public Swarm setListener(SwarmListener listener) {
        if (listener != null) {
            this.swarmListener = listener;
        }
        return this;
    }

}
