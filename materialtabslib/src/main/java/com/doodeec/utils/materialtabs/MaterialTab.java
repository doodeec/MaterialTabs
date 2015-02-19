package com.doodeec.utils.materialtabs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Refactored and simplified neokree's implementation
 * Tab now supports only text mode
 * Ripple is disabled because:
 * the ripple library doesn't have enough support for pre-Lollipop devices, when other action
 * is invoked by touch event
 *
 * @author dusan.bartos
 * @author neokree [original author]
 */
public class MaterialTab extends RelativeLayout implements View.OnClickListener {

    private Button mTextButton;
    private ImageView mActiveTabSelector;

    private IMaterialTabListener mTabListener;

    private int mTextColor;
    private int mAccentColor;

    private boolean mIsActive;
    private int mPosition;

    public MaterialTab(Context context) {
        this(context, null);
    }

    public MaterialTab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.tab, this);

        mTextButton = (Button) findViewById(R.id.text);
        mActiveTabSelector = (ImageView) findViewById(R.id.selector);

        mIsActive = false;
        mTextColor = Color.WHITE;

        mTextButton.setOnClickListener(this);

        // fallback for not supported ripple effect
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mTextButton.setBackgroundResource(R.drawable.tab_selector);
        }
    }

    public void setAccentColor(int color) {
        this.mAccentColor = color;
        this.mTextColor = color;
    }

    public void setTextColor(int color) {
        mTextColor = color;
        if (mTextButton != null) {
            mTextButton.setTextColor(color);
        }
    }

    public MaterialTab setText(CharSequence mTextButton) {
        this.mTextButton.setText(mTextButton.toString());
        return this;
    }

    public void disableTab() {
        // set 60% alpha to mTextButton color
        if (mTextButton != null) {
            mTextButton.setTextColor(Color.argb(0x99, Color.red(mTextColor), Color.green(mTextColor), Color.blue(mTextColor)));
        }

        // set transparent the selector view
        mActiveTabSelector.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        mIsActive = false;

        if (mTabListener != null) {
            mTabListener.onTabUnselected(this);
        }
    }

    public void activateTab() {
        // set full color mTextButton
        if (mTextButton != null) {
            mTextButton.setTextColor(mTextColor);
        }

        // set accent color to selector view
        mActiveTabSelector.setBackgroundColor(mAccentColor);

        mIsActive = true;
    }

    public boolean isSelected() {
        return mIsActive;
    }

    @Override
    public void onClick(View v) {
        if (mTabListener != null) {
            if (mIsActive) {
                // if the tab is active when the user click on it it will be reselect
                mTabListener.onTabReselected(this);
            } else {
                mTabListener.onTabSelected(this);
            }
        }

        // if the tab is not activated, it will be active
        if (!mIsActive) {
            this.activateTab();
        }
    }

    public MaterialTab setTabListener(IMaterialTabListener listener) {
        this.mTabListener = listener;
        return this;
    }

    public IMaterialTabListener getTabListener() {
        return mTabListener;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    private int getTextLength() {
        String textString = mTextButton.getText().toString();
        Rect bounds = new Rect();
        Paint textPaint = mTextButton.getPaint();
        textPaint.getTextBounds(textString, 0, textString.length(), bounds);
        return bounds.width();
    }

    public int getTabMinWidth() {
        return getTextLength();
    }
}
