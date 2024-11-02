//package com.sevtinge.hyperceiler.widget;
//
//import android.animation.Animator;
//import android.animation.AnimatorSet;
//import android.animation.ObjectAnimator;
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.os.Handler;
//import android.os.Message;
//import android.os.UserHandle;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.animation.DecelerateInterpolator;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.sevtinge.hyperceiler.BuildConfig;
//import com.sevtinge.hyperceiler.R;
//import com.sevtinge.hyperceiler.controller.LogoAnimationController;
//import com.sevtinge.hyperceiler.utils.SettingsFeatures;
//import com.sevtinge.hyperceiler.utils.devicesdk.DisplayUtils;
//import com.sevtinge.hyperceiler.view.CubicEaseOutInterpolater;
//
//import fan.cardview.HyperCardView;
//import fan.core.utils.MiuiBlurUtils;
//import fan.internal.utils.ViewUtils;
//
//public class VersionCard extends FrameLayout implements View.OnClickListener {
//
//    private String versionName;
//
//    private int scrollValue;
//    private int modeValue = 0;
//    private boolean mNeedStartAnim = true;
//    private boolean mNeedUpdate = true;
//
//    ViewGroup mRootView;
//    private View cardClickView;
//    private ImageView mIconLogoView;
//    private ImageView mTextLogoView;
//    private ViewGroup mVersionLayout;
//    private HyperCardView mUpdateText;
//
//    private AnimatorSet mAnimatorSet;
//    private CubicEaseOutInterpolater mInterpolater;
//    private DecelerateInterpolator mDecelerateInterpolator;
//
//    public LogoAnimationController mAnimationController;
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            if (isAttachedToWindow()) {
//                checkUpdate();
//                if (mNeedUpdate) {
//                    mAnimationController.iniData(getContext(), mNeedUpdate);
//                    performLogoAnimation(false);
//                    if (scrollValue != 0) {
//                        mAnimationController.startButtonAnimation(scrollValue, mUpdateText);
//                    }
//                } else {
//                    mHandler.sendEmptyMessageDelayed(0, 1500L);
//                }
//            }
//        }
//    };
//
//    public VersionCard(@NonNull Context context) {
//        super(context);
//        initView();
//    }
//
//    public VersionCard(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        initView();
//    }
//
//    private void initView() {
//        mRootView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.layout_version_card, this, true);
//        mIconLogoView = findViewById(R.id.app_icon_logo_view);
//        mTextLogoView = findViewById(R.id.app_text_logo_view);
//
//        mVersionLayout = findViewById(R.id.version_layout);
//
//        mAnimatorSet = new AnimatorSet();
//        mInterpolater = new CubicEaseOutInterpolater();
//        mDecelerateInterpolator = new DecelerateInterpolator();
//        checkUpdate();
//        mAnimationController = new LogoAnimationController(getContext(), mNeedUpdate);
//        if (!mNeedUpdate) {
//            mIconLogoView.setAlpha(1.0f);
//            mTextLogoView.setAlpha(1.0f);
//            mVersionLayout.setAlpha(1.0f);
//            mHandler.sendEmptyMessageDelayed(0, 1500L);
//        }
//        setLogoBlur();
//    }
//
//    public void checkUpdate() {
//        mNeedUpdate = !TextUtils.isEmpty(getUpdateInfo());
//    }
//
//    public void refreshUpdateStatus() {
//        if ((!TextUtils.isEmpty(getUpdateInfo())) != mNeedUpdate) {
//            mNeedStartAnim = true;
//            mNeedUpdate = !TextUtils.isEmpty(getUpdateInfo());
//            mRootView.removeAllViews();
//            initView();
//            invalidate();
//        }
//    }
//
//    public static String getUpdateInfo() {
//        return "";
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//    }
//
//    @Override
//    protected void dispatchDraw(@NonNull Canvas canvas) {
//        super.dispatchDraw(canvas);
//        performLogoAnimation(true);
//    }
//
//    public void performLogoAnimation(boolean z) {
//        if (mNeedStartAnim && mNeedUpdate) {
//            mNeedStartAnim = false;
//            mIconLogoView.setPivotX(0.0f);
//            mTextLogoView.setPivotX(0.0f);
//            AnimatorSet animatorSet = new AnimatorSet();
//            if (scrollValue == 0) {
//                if (z) {
//                    animatorSet.playTogether(
//                            ObjectAnimator.ofFloat(mIconLogoView, "alpha", 0.0f, 1.0f),
//                            ObjectAnimator.ofFloat(mTextLogoView, "alpha", 0.0f, 1.0f),
//                            ObjectAnimator.ofFloat(mVersionLayout, "alpha", 0.0f, 1.0f),
//                            ObjectAnimator.ofFloat(mUpdateText, "alpha", 0.0f, 1.0f)
//                    );
//                } else {
//                    animatorSet.playTogether(ObjectAnimator.ofFloat(mUpdateText, "alpha", 0.0f, 1.0f));
//                }
//            }
//            animatorSet.setDuration(800L);
//            animatorSet.setInterpolator(mDecelerateInterpolator);
//            Animator[] animatorItems = new Animator[4];
//            animatorItems[0] = ObjectAnimator.ofFloat(
//                    mIconLogoView, "translationY",
//                    SettingsFeatures.isSplitTabletDevice() ? DisplayUtils.dp2px(getContext(), -27.0f) :
//                            DisplayUtils.dp2px(getContext(), -30.0f)
//            );
//            animatorItems[1] = ObjectAnimator.ofFloat(
//                    mTextLogoView, "translationY",
//                    SettingsFeatures.isSplitTabletDevice() ? DisplayUtils.dp2px(getContext(), -27.0f) :
//                            DisplayUtils.dp2px(getContext(), -30.0f)
//            );
//            animatorItems[2] = ObjectAnimator.ofFloat(
//                    mVersionLayout, "translationY",
//                    SettingsFeatures.isSplitTabletDevice() ? DisplayUtils.dp2px(getContext(), -27.0f) :
//                            DisplayUtils.dp2px(getContext(), -30.0f)
//            );
//            animatorItems[3] = animatorSet;
//            mAnimatorSet.playTogether(animatorItems);
//            mAnimatorSet.setDuration(1000L);
//            mAnimatorSet.setInterpolator(mInterpolater);
//            mAnimatorSet.setStartDelay(100L);
//            mAnimatorSet.start();
//            mUpdateText.setClickable(true);
//            mUpdateText.setOnClickListener(this);
//        }
//    }
//
//    private void setLogoBlur() {
//        if (MiuiBlurUtils.isEnable() && MiuiBlurUtils.isEffectEnable(getContext())) {
//            MiuiBlurUtils.setBackgroundBlur(mRootView, (int) ((getResources().getDisplayMetrics().density * 50.0f) + 0.5f));
//            MiuiBlurUtils.setViewBlurMode(mRootView, 0);
//            int[] iArr = {getResources().getColor(R.color.logo_color1),
//                    getResources().getColor(R.color.logo_color2),
//                    getResources().getColor(R.color.logo_color3)
//            };
//            modeValue = ViewUtils.isNightMode(getContext()) ? 18 : 19;
//            enableTextBlur(mIconLogoView, true, iArr, new int[]{modeValue, 100, 106});
//            enableTextBlur(mTextLogoView, true, iArr, new int[]{modeValue, 100, 106});
//            mIconLogoView.setBackgroundResource(R.drawable.ic_hyperceiler_logo);
//            mTextLogoView.setBackgroundResource(R.drawable.ic_text_logo);
//            Log.d("VersionCard", "start logoBlur: ");
//        } else {
//            mIconLogoView.setBackgroundResource(R.drawable.ic_hyperceiler_settings_v140);
//            mTextLogoView.setBackgroundResource(R.drawable.ic_text_logo_lite);
//        }
//    }
//
//    private void enableTextBlur(View view, boolean z, int[] iArr, int[] iArr2) {
//        if (z) {
//            MiuiBlurUtils.setViewBlurMode(view, 3);
//            for (int i = 0; i < iArr.length; i++) {
//                MiuiBlurUtils.addBackgroundBlenderColor(view, iArr[i], iArr2[i]);
//            }
//        } else {
//            MiuiBlurUtils.setViewBlurMode(view, 0);
//            MiuiBlurUtils.clearBackgroundBlenderColor(view);
//        }
//    }
//    public void stopLogoAnimation() {
//        if (mAnimatorSet != null) {
//            mAnimatorSet.end();
//        }
//    }
//
//    public void setScrollValue(int value) {
//        scrollValue = value;
//    }
//
//    public void setAnimation(int scrollY, View bgEffectView) {
//        mAnimationController.startAnimation(scrollY, mIconLogoView, mTextLogoView, mUpdateText, mVersionLayout, bgEffectView);
//    }
//
//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        mHandler.removeMessages(0);
//    }
//}
