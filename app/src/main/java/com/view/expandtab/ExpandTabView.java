package com.view.expandtab;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidfresco.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 菜单控件头部，封装了下拉动画，动态生成头部按钮个数
 */

public class ExpandTabView extends LinearLayout implements PopupWindow.OnDismissListener {

    private LinearLayout selectedView;
    private ArrayList<String> mTextArray = new ArrayList<String>();
    private ArrayList<RelativeLayout> mViewArray = new ArrayList<RelativeLayout>();
    private ArrayList<TextView> mTextViewList = new ArrayList<TextView>();
    private Context mContext;
    private final int SMALL = 0;
    private int displayWidth;
    private int displayHeight;
    private PopupWindow popupWindow;
    private ImageView imageView;
    private Map<Integer, Boolean> mapBoolean = new HashMap<Integer, Boolean>();
    private ArrayList<ImageView> mImageViewList = new ArrayList<ImageView>();
    private int selectPosition;


    public ExpandTabView(Context context) {
        super(context);
        init(context);
    }

    public ExpandTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 根据选择的位置设置tabitem显示的值
     */
    public void setTitle(String valueText, int position) {
        if (position < mTextViewList.size()) {
            mTextViewList.get(position).setText(valueText);
        }
    }

    public void setTitle(String title) {

    }

    /**
     * 根据选择的位置获取tabitem显示的值
     */
    public String getTitle(int position) {
        if (position < mTextViewList.size() && mTextViewList.get(position).getText() != null) {
            return mTextViewList.get(position).getText().toString();
        }
        return "";
    }

    /**
     * 设置tabitem的个数和初始值
     */
    public void setValue(ArrayList<String> textArray, ArrayList<View> viewArray) {
        if (mContext == null) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mTextArray = textArray;
        for (int i = 0; i < viewArray.size(); i++) {
            final RelativeLayout r = new RelativeLayout(mContext);
            int maxHeight = (int) (displayHeight * 0.4);
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, maxHeight);
            r.addView(viewArray.get(i), rl);
            mViewArray.add(r);
            r.setTag(SMALL);
            LinearLayout inflateView = (LinearLayout) inflater.inflate(R.layout.view_toggle_button, this, false);
            inflateView.setTag(i);
            mapBoolean.put(i, true);
            TextView textView = (TextView) inflateView.findViewById(R.id.textView);
            addView(inflateView);
//            View line = new TextView(mContext);
//            if (i < viewArray.size() - 1) {
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(2, LinearLayout.LayoutParams.FILL_PARENT);
//                addView(line, lp);
//            }
            mTextViewList.add(textView);
            imageView = (ImageView) inflateView.findViewById(R.id.imageView);
            mImageViewList.add(imageView);
            textView.setText(mTextArray.get(i));
            r.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPressBack();
                }
            });
            r.setBackgroundColor(mContext.getResources().getColor(R.color.popup_main_background));
            inflateView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout clickView = (LinearLayout) view;
                    selectedView = clickView;
                    selectPosition = (Integer) selectedView.getTag();
                    if (selectedView != null && selectedView != clickView) {
                        mapBoolean.put(selectPosition, false);
                    }
                    ImageView image = mImageViewList.get(selectPosition);
                    startAnimation();
                    if (mapBoolean.get(selectPosition)) {
                        image.setBackground(getResources().getDrawable(R.drawable.filtermenu_collapse_icon));
                    } else {
                        image.setBackground(getResources().getDrawable(R.drawable.filtermenu_expand_icon));
                    }
                    if (mOnButtonClickListener != null && mapBoolean.get(selectPosition)) {
                        mOnButtonClickListener.onClick(selectPosition);
                    }
                    mapBoolean.put(selectPosition, !mapBoolean.get(selectPosition));
                    for (int i = 0; i < mapBoolean.size(); i++) {
                        if (i != selectPosition) {
                            mapBoolean.put(i, true);
                            if (mImageViewList.get(i) != null) {
                                mImageViewList.get(i).setBackground(getResources().getDrawable(R.drawable.filtermenu_expand_icon));
                            }
                        }
                    }
                }
            });
        }
    }

    private void startAnimation() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(mViewArray.get(selectPosition), LayoutParams.MATCH_PARENT, displayHeight);
            popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(true);
        }
        if (mapBoolean.get(selectPosition)) {
            if (!popupWindow.isShowing()) {
                showPopup(selectPosition);
                changeTextViewColor(selectPosition);
            } else {
                popupWindow.setOnDismissListener(this);
                popupWindow.dismiss();
                changeTextViewColor(selectPosition);
                hideView();
            }
        } else {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
                changeTextViewColor(-1);
                hideView();
            }
        }
    }

    private void showPopup(int position) {
        View tView = mViewArray.get(selectPosition).getChildAt(0);
        if (tView instanceof ViewBaseAction) {
            ViewBaseAction f = (ViewBaseAction) tView;
            f.show();
        }
        if (popupWindow.getContentView() != mViewArray.get(position)) {
            popupWindow.setContentView(mViewArray.get(position));
        }
        popupWindow.showAsDropDown(this, 0, 0);
    }

    /**
     * 如果菜单成展开状态，则让菜单收回去
     */
    public boolean onPressBack() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            hideView();
            changeTextViewColor(-1);
            mapBoolean.put(selectPosition, true);
            if (mImageViewList.get(selectPosition) != null) {
                mImageViewList.get(selectPosition).setBackground(getResources().getDrawable(R.drawable.filtermenu_expand_icon));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改栏目文字颜色
     * @param index
     */
    public void changeTextViewColor(int index){
        for (int i = 0 ;i < mTextViewList.size() ; i++){
            if(i != index){
                mTextViewList.get(i).setTextColor(getResources().getColor(R.color.no_select_text_color));
            }else {
                mTextViewList.get(i).setTextColor(getResources().getColor(R.color.select_text_color));
            }
        }
    }

    private void hideView() {
        View tView = mViewArray.get(selectPosition).getChildAt(0);
        if (tView instanceof ViewBaseAction) {
            ViewBaseAction f = (ViewBaseAction) tView;
            f.hide();
        }
    }

    private void init(Context context) {
        mContext = context;
        displayWidth = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth();
        displayHeight = ((Activity) mContext).getWindowManager().getDefaultDisplay().getHeight();
        setOrientation(LinearLayout.HORIZONTAL);
    }

    @Override
    public void onDismiss() {
        showPopup(selectPosition);
        popupWindow.setOnDismissListener(null);
    }

    private OnButtonClickListener mOnButtonClickListener;

    /**
     * 设置tabitem的点击监听事件
     */
    public void setOnButtonClickListener(OnButtonClickListener l) {
        mOnButtonClickListener = l;
    }

    /**
     * 自定义tabitem点击回调接口
     */
    public interface OnButtonClickListener {
        public void onClick(int selectPosition);
    }
}
