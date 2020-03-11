package com.github.rubensousa.viewpagercards;

import android.util.Log;
import android.view.View;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;


public class ShadowTransformer implements ViewPager.OnPageChangeListener, ViewPager.PageTransformer {

    private ViewPager mViewPager;
    private CardAdapter mAdapter;
    private float mLastOffset;
    private boolean mScalingEnabled;
    private float scale = 0.1f;


    public void setScale(float scale) {
        this.scale = scale;
    }

    public ShadowTransformer(ViewPager viewPager, CardAdapter adapter) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        mAdapter = adapter;
    }

    public void enableScaling(boolean enable) {
        if (mScalingEnabled && !enable) {
            // shrink main card
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1);
                currentCard.animate().scaleX(1);
            }
        } else if (!mScalingEnabled && enable) {
            // grow main card
            CardView currentCard = mAdapter.getCardViewAt(mViewPager.getCurrentItem());
            if (currentCard != null) {
                currentCard.animate().scaleY(1f + scale);
                currentCard.animate().scaleX(1f + scale);
            }
        }

        mScalingEnabled = enable;
    }

    private static final float MIN_SCALE = 0.70f;
    private static final float MIN_ALPHA = 0.5f;

    private float fixPosition = -100;

    @Override
    public void transformPage(View page, float position) {
        if (fixPosition == -100) fixPosition = position;
        position = position - fixPosition;
        if (position < -1 || position > 1) {
            page.setAlpha(MIN_ALPHA);
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) { // [-1,1]
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            if (position < 0) {
                float scaleX = 1 + scale * position + scale;
                Log.d("xixitest", position + " transformPage0: scaleX:" + scaleX);
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            } else {
                float scaleX = 1 - scale * position + scale;
                Log.d("xixitest", position + " transformPage1: scaleX:" + scaleX);
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            }
            page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
     /*   int realCurrentPosition;
        int nextPosition;
        float baseElevation = mAdapter.getBaseElevation();
        float realOffset;
        boolean goingLeft = mLastOffset > positionOffset;

        // If we're going backwards, onPageScrolled receives the last position
        // instead of the current one
        if (goingLeft) {
            realCurrentPosition = position + 1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        } else {
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }
        //Log.d("xixitest", "onPageScrolled position: " + position + " , positionOffset: " + positionOffset + " , mLastOffset: " + mLastOffset);
        //Log.d("xixitest", "onPageScrolled realCurrentPosition: " + realCurrentPosition + " , nextPosition: " + nextPosition + " , realOffset: " + realOffset);

        // Avoid crash on overscroll
        if (nextPosition > mAdapter.getCount() - 1
                || realCurrentPosition > mAdapter.getCount() - 1) {
            return;
        }
        //Log.d("xixitest", "onPageScrolled goingLeft:" + goingLeft + " , baseElevation: " + baseElevation);
        //Log.d("xixitest", "onPageScrolled realCurrentPosition: " + realCurrentPosition + " , nextPosition: " + nextPosition + " , realOffset: " + realOffset);

        CardView currentCard = mAdapter.getCardViewAt(realCurrentPosition);

        // This might be null if a fragment is being used
        // and the views weren't created yet
        if (currentCard != null) {
            if (mScalingEnabled) {
                currentCard.setScaleX((float) (1 + scale * (1 - realOffset)));
                currentCard.setScaleY((float) (1 + scale * (1 - realOffset)));
            }

            currentCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (1 - realOffset)));
        }

        CardView nextCard = mAdapter.getCardViewAt(nextPosition);

        // We might be scrolling fast enough so that the next (or previous) card
        // was already destroyed or a fragment might not have been created yet
        if (nextCard != null) {
            if (mScalingEnabled) {
                nextCard.setScaleX((float) (1 + scale * (realOffset)));
                nextCard.setScaleY((float) (1 + scale * (realOffset)));
            }
            nextCard.setCardElevation((baseElevation + baseElevation
                    * (CardAdapter.MAX_ELEVATION_FACTOR - 1) * (realOffset)));
        }

        mLastOffset = positionOffset;

      */
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
