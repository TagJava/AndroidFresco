package com.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidfresco.R;

import java.util.List;

/**
 * 搜索内容集合适配器
 */

public class SearchBarLevelAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mListData;
    private String[] mArrayData;
    private int selectedPos = -1;
    private String selectedText = "";
    private int normalDrawbleId;
    private Drawable selectedDrawble;
    private float textSize;
    private View.OnClickListener onClickListener;
    private OnItemClickListener mOnItemClickListener;
    private int textColor;


    public SearchBarLevelAdapter(Context context, List<String> listData, int sId, int nId) {
        super(context, R.string.no_data, listData);
        mContext = context;
        mListData = listData;
        selectedDrawble = mContext.getResources().getDrawable(sId);
        normalDrawbleId = nId;

        init();
    }

    public SearchBarLevelAdapter(Context context, List<String> listData, int colorR) {
        super(context, R.string.no_data, listData);
        mContext = context;
        mListData = listData;
        textColor = colorR;
        init();
    }

    private void init() {
        onClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectedPos = (Integer) view.getTag();
                setSelectedPosition(selectedPos);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, selectedPos);
                }
            }
        };
    }

    public SearchBarLevelAdapter(Context context, String[] arrayData, int sId, int nId) {
        super(context, R.string.no_data, arrayData);
        mContext = context;
        mArrayData = arrayData;
        selectedDrawble = mContext.getResources().getDrawable(sId);
        normalDrawbleId = nId;
        init();
    }

    /**
     * 设置选中的position,并通知列表刷新
     */
    public void setSelectedPosition(int pos) {
        if (mListData != null && pos < mListData.size()) {
            selectedPos = pos;
            selectedText = mListData.get(pos);
            notifyDataSetChanged();
        } else if (mArrayData != null && pos < mArrayData.length) {
            selectedPos = pos;
            selectedText = mArrayData[pos];
            notifyDataSetChanged();
        }

    }

    /**
     * 设置选中的position,但不通知刷新
     */
    public void setSelectedPositionNoNotify(int pos) {
        selectedPos = pos;
        if (mListData != null && pos < mListData.size()) {
            selectedText = mListData.get(pos);
        } else if (mArrayData != null && pos < mArrayData.length) {
            selectedText = mArrayData[pos];
        }
    }

    /**
     * 获取选中的position
     */
    public int getSelectedPosition() {
        if (mArrayData != null && selectedPos < mArrayData.length) {
            return selectedPos;
        }
        if (mListData != null && selectedPos < mListData.size()) {
            return selectedPos;
        }

        return -1;
    }

    /**
     * 设置列表字体大小
     */
    public void setTextSize(float tSize) {
        textSize = tSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout view;
        if (convertView == null) {
            view = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.choose_level_item, parent, false);
        } else {
            view = (LinearLayout) convertView;
        }
        view.setTag(position);
        String mString = "";
        if (mListData != null) {
            if (position < mListData.size()) {
                mString = mListData.get(position);
            }
        } else if (mArrayData != null) {
            if (position < mArrayData.length) {
                mString = mArrayData[position];
            }
        }
        TextView textView = (TextView) view.findViewById(R.id.textview);
        View viewline = view.findViewById(R.id.viewline);
        if(position == 0){
            viewline.setVisibility(View.GONE);
        }else {
            viewline.setVisibility(View.VISIBLE);
        }
        if (mString.contains("不限"))
            textView.setText("不限");
        else
            textView.setText(mString);
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

        if (selectedText != null && selectedText.equals(mString)) {
            textView.setTextColor(getContext().getResources().getColor(R.color.select_text_color));
        } else {
            textView.setTextColor(getContext().getResources().getColor(R.color.no_select_text_color));
        }
        view.setPadding(20, 0, 0, 0);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    /**
     * 重新定义菜单选项单击接口
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}
