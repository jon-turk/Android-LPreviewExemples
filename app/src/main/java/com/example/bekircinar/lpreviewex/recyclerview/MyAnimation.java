package com.example.bekircinar.lpreviewex.recyclerview;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by bekir.cinar on 07/07/14.
 */
public class MyAnimation extends RecyclerView.ItemAnimator {

    private ArrayList<RecyclerView.ViewHolder> mPendingRemovals = new ArrayList<RecyclerView.ViewHolder>();
    private ArrayList<RecyclerView.ViewHolder> mPendingAdditions = new ArrayList<RecyclerView.ViewHolder>();
    private ArrayList<MoveInfo> mPendingMoves = new ArrayList<MoveInfo>();

    private ArrayList<RecyclerView.ViewHolder> mAdditions = new ArrayList<RecyclerView.ViewHolder>();
    private ArrayList<MoveInfo> mMoves = new ArrayList<MoveInfo>();

    private ArrayList<RecyclerView.ViewHolder> mAddAnimations = new ArrayList<RecyclerView.ViewHolder>();
    private ArrayList<RecyclerView.ViewHolder> mMoveAnimations = new ArrayList<RecyclerView.ViewHolder>();
    private ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<RecyclerView.ViewHolder>();

    public MyAnimation(int removeColorID) {
        mRemoveBackgroundColor = removeColorID;
    }

    private final int NULL = -1;
    private int mRemoveBackgroundColor = NULL;

    private static class MoveInfo {
        public RecyclerView.ViewHolder holder;
        public int fromX, fromY, toX, toY;

        private MoveInfo(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
            this.holder = holder;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }
    }

    @Override
    public void runPendingAnimations() {
        boolean removalsPending = !mPendingRemovals.isEmpty();
        boolean movesPending = !mPendingMoves.isEmpty();
        boolean additionsPending = !mPendingAdditions.isEmpty();
        if (!removalsPending && !movesPending && !additionsPending) {
            // nothing to animate
            return;
        }
        // First, remove stuff
        for (RecyclerView.ViewHolder holder : mPendingRemovals) {
            animateRemoveImpl(holder);
        }
        mPendingRemovals.clear();
        // Next, move stuff
        if (movesPending) {
            mMoves.addAll(mPendingMoves);
            mPendingMoves.clear();
            Runnable mover = new Runnable() {
                @Override
                public void run() {
                    for (MoveInfo moveInfo : mMoves) {
                        animateMoveImpl(moveInfo.holder, moveInfo.fromX, moveInfo.fromY,
                                moveInfo.toX, moveInfo.toY);
                    }
                    mMoves.clear();
                }
            };
            if (removalsPending) {
                View view = mMoves.get(0).holder.itemView;
                ViewCompat.postOnAnimationDelayed(view, mover, getRemoveDuration());
            } else {
                mover.run();
            }
        }
        // Next, add stuff
        if (additionsPending) {
            mAdditions.addAll(mPendingAdditions);
            mPendingAdditions.clear();
            Runnable adder = new Runnable() {
                public void run() {
                    for (RecyclerView.ViewHolder holder : mAdditions) {
                        animateAddImpl(holder);
                    }
                    mAdditions.clear();
                }
            };
            if (removalsPending || movesPending) {
                View view = mAdditions.get(0).itemView;
                ViewCompat.postOnAnimationDelayed(view, adder,
                        (removalsPending ? getRemoveDuration() : 0) +
                                (movesPending ? getMoveDuration() : 0)
                );
            } else {
                adder.run();
            }
        }
    }

    @Override
    public boolean animateRemove(final RecyclerView.ViewHolder holder) {
        mPendingRemovals.add(holder);
        return true;
    }

    private void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        if (mRemoveBackgroundColor != NULL) {
            view.setBackgroundColor(mRemoveBackgroundColor);
        }
        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).
                alpha(0).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                ViewCompat.setAlpha(view, 1);
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mRemoveAnimations.add(holder);
    }

    @Override
    public boolean animateAdd(final RecyclerView.ViewHolder holder) {
        ViewCompat.setAlpha(holder.itemView, 0);
        mPendingAdditions.add(holder);
        return true;
    }

    private void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;
        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).alpha(1).setDuration(getAddDuration()).
                setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationStart(View view) {
                        super.onAnimationStart(view);
                    }

                    @Override
                    public void onAnimationCancel(View view) {
                        ViewCompat.setAlpha(view, 1);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
        mAddAnimations.add(holder);
    }

    @Override
    public boolean animateMove(final RecyclerView.ViewHolder holder, int fromX, int fromY,
                               int toX, int toY) {
        final View view = holder.itemView;
        int deltaX = toX - fromX;
        int deltaY = toY - fromY;
        if (deltaX == 0 && deltaY == 0) {
            dispatchMoveFinished(holder);
            return false;
        }
        if (deltaX != 0) {
            ViewCompat.setTranslationX(view, -deltaX);
        }
        if (deltaY != 0) {
            ViewCompat.setTranslationY(view, -deltaY);
        }
        mPendingMoves.add(new MoveInfo(holder, fromX, fromY, toX, toY));
        return true;
    }

    private void animateMoveImpl(final RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        final View view = holder.itemView;
        final int deltaX = toX - fromX;
        final int deltaY = toY - fromY;
        ViewCompat.animate(view).cancel();
        if (deltaX != 0) {
            ViewCompat.animate(view).translationX(0);
        }
        if (deltaY != 0) {
            ViewCompat.animate(view).translationY(0);
        }
        // TODO: make EndActions end listeners instead, since end actions aren't called when
        // vpas are canceled (and can't end them. why?)
        // need listener functionality in VPACompat for this. Ick.
        ViewCompat.animate(view).setDuration(getMoveDuration()).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationCancel(View view) {
                if (deltaX != 0) {
                    ViewCompat.setTranslationX(view, 0);
                }
                if (deltaY != 0) {
                    ViewCompat.setTranslationY(view, 0);
                }
            }

            @Override
            public void onAnimationEnd(View view) {
                dispatchMoveFinished(holder);
                mMoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mMoveAnimations.add(holder);
    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        final View view = item.itemView;
        ViewCompat.animate(view).cancel();
        if (mMoveAnimations.contains(item)) {
            ViewCompat.setTranslationY(view, 0);
            ViewCompat.setTranslationX(view, 0);
            dispatchMoveFinished(item);
            mMoveAnimations.remove(item);
        }
        if (mRemoveAnimations.contains(item)) {
            ViewCompat.setAlpha(view, 1);
            dispatchRemoveFinished(item);
            mRemoveAnimations.remove(item);
        }
        if (mAddAnimations.contains(item)) {
            ViewCompat.setAlpha(view, 1);
            dispatchAddFinished(item);
            mAddAnimations.remove(item);
        }
        dispatchFinishedWhenDone();
    }

    @Override
    public boolean isRunning() {
        return (!mMoveAnimations.isEmpty() ||
                !mRemoveAnimations.isEmpty() ||
                !mAddAnimations.isEmpty() ||
                !mMoves.isEmpty() ||
                !mAdditions.isEmpty());
    }

    /**
     * Check the state of currently pending and running animations. If there are none
     * pending/running, call {@link #dispatchAnimationsFinished()} to notify any
     * listeners.
     */
    private void dispatchFinishedWhenDone() {
        if (!isRunning()) {
            dispatchAnimationsFinished();
        }
    }

    @Override
    public void endAnimations() {
        if (!isRunning()) {
            return;
        }
        int count = mMoveAnimations.size();
        for (int i = count - 1; i >= 0; i--) {
            RecyclerView.ViewHolder item = mMoveAnimations.get(i);
            View view = item.itemView;
            ViewCompat.animate(view).cancel();
            ViewCompat.setTranslationY(view, 0);
            ViewCompat.setTranslationX(view, 0);
            dispatchMoveFinished(item);
            mMoveAnimations.remove(item);
        }
        count = mRemoveAnimations.size();
        for (int i = count - 1; i >= 0; i--) {
            RecyclerView.ViewHolder item = mRemoveAnimations.get(i);
            View view = item.itemView;
            ViewCompat.animate(view).cancel();
            ViewCompat.setAlpha(view, 1);
            dispatchRemoveFinished(item);
            mRemoveAnimations.remove(item);
        }
        count = mAddAnimations.size();
        for (int i = count - 1; i >= 0; i--) {
            RecyclerView.ViewHolder item = mAddAnimations.get(i);
            View view = item.itemView;
            ViewCompat.animate(view).cancel();
            ViewCompat.setAlpha(view, 1);
            dispatchAddFinished(item);
            mAddAnimations.remove(item);
        }
        mMoves.clear();
        mAdditions.clear();
        dispatchAnimationsFinished();
    }

    private static class VpaListenerAdapter implements ViewPropertyAnimatorListener {
        @Override
        public void onAnimationStart(View view) {
        }

        @Override
        public void onAnimationEnd(View view) {
        }

        @Override
        public void onAnimationCancel(View view) {
        }
    }

    ;
}
