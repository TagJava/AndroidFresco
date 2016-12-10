package com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.adapter.SearchBarAdapter;
import com.androidfresco.R;
import com.view.expandtab.ViewBaseAction;

public class FirstLevelSearchView extends RelativeLayout implements ViewBaseAction {

    private ListView mListView;
    private String[] items;//显示字段
    private String[] itemsVaule;//隐藏id
    private OnSelectListener mOnSelectListener;
    private SearchBarAdapter adapter;
    private String mDistance;
    private String showText = "item1";
    private Context mContext;
    private IViewType mIViewType;

    public String getShowText() {
        return showText;
    }

    public FirstLevelSearchView(Context context) {
        super(context);
        init(context);
    }

    public enum IViewType {
        View_Stage,
        View_Type,
        View_Sort
    }

    public IViewType getmIViewType() {
        return mIViewType;
    }

    public FirstLevelSearchView(Context context, String[] items, String[] itemsVaule, IViewType type) {
        super(context);
        this.items = items;
        this.itemsVaule = itemsVaule;
        this.mIViewType = type;
        init(context);
    }

    public void update(String[] items, String[] itemsVaule) {
        this.items = items;
        this.itemsVaule = itemsVaule;
        adapter.notifyDataSetChanged();
    }

    public FirstLevelSearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public FirstLevelSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_distance, this, true);
        mListView = (ListView) findViewById(R.id.listView);
        adapter = new SearchBarAdapter(context, items, R.drawable.checkbox_singleselection, R.drawable.choose_eara_item_selector);
        adapter.setTextSize(17);
        if (mDistance != null) {
            for (int i = 0; i < itemsVaule.length; i++) {
                if (itemsVaule[i].equals(mDistance)) {
                    adapter.setSelectedPositionNoNotify(i);
                    showText = items[i];
                    break;
                }
            }
        }
        mListView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SearchBarAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {

                if (mOnSelectListener != null) {
                    showText = items[position];
                    mOnSelectListener.getValue(itemsVaule[position], items[position]);
                }
            }
        });
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        public void getValue(String distance, String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
