package com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.adapter.SearchBarAdapter;
import com.adapter.SearchBarLevelAdapter;
import com.androidfresco.R;
import com.view.expandtab.ViewBaseAction;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 搜索科目
 */
public class ViewSubject  extends LinearLayout implements ViewBaseAction {

    private ListView regionListView;
    private ListView plateListView;
    private ArrayList<String> groups = new ArrayList<String>();
    private LinkedList<String> childrenItem = new LinkedList<String>();
    private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();
    private SearchBarLevelAdapter plateListViewLevelAdapter;
    private SearchBarAdapter earaListViewAdapter;
    private OnSelectListener mOnSelectListener;
    private int tEaraPosition = 0;
    private int tBlockPosition = 0;
    private String showString = "不限";
    private String showText = "item1";

    public ViewSubject(Context context) {
        super(context);
        init(context);
    }

    public ViewSubject(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ViewSubject(Context context,ArrayList<String> groups,SparseArray<LinkedList<String>> children ) {
        super(context);
        this.groups = groups;
        this.children = children;
        init(context);
    }

    public void update(ArrayList<String> groups,SparseArray<LinkedList<String>> children ){
        this.groups = groups;
        this.children = children;
        earaListViewAdapter.notifyDataSetChanged();
        plateListViewLevelAdapter.notifyDataSetChanged();
    }

    public void updateShowText(String showArea, String showBlock) {
        if (showArea == null || showBlock == null) {
            return;
        }
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).equals(showArea)) {
                earaListViewAdapter.setSelectedPosition(i);
                childrenItem.clear();
                if (i < children.size()) {
                    childrenItem.addAll(children.get(i));
                }
                tEaraPosition = i;
                break;
            }
        }
        for (int j = 0; j < childrenItem.size(); j++) {
            if (childrenItem.get(j).replace("不限", "").equals(showBlock.trim())) {
                plateListViewLevelAdapter.setSelectedPosition(j);
                tBlockPosition = j;
                break;
            }
        }
        setDefaultSelect();
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_region, this, true);
        regionListView = (ListView) findViewById(R.id.listViewLeft);
        plateListView = (ListView) findViewById(R.id.listViewRight);

        earaListViewAdapter = new SearchBarAdapter(context, groups,false);
//        earaListViewAdapter.setTextSize(28);
        earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
        regionListView.setAdapter(earaListViewAdapter);
        earaListViewAdapter
                .setOnItemClickListener(new SearchBarAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        if (position < children.size()) {
                            childrenItem.clear();
                            childrenItem.addAll(children.get(position));
                            plateListViewLevelAdapter.notifyDataSetChanged();
                        }
                    }
                });
        if (tEaraPosition < children.size())
            childrenItem.addAll(children.get(tEaraPosition));
        plateListViewLevelAdapter = new SearchBarLevelAdapter(context, childrenItem,R.color.select_color);
//        plateListViewLevelAdapter.setTextSize(15);
        plateListViewLevelAdapter.setSelectedPositionNoNotify(tBlockPosition);
        plateListView.setAdapter(plateListViewLevelAdapter);
        plateListViewLevelAdapter
                .setOnItemClickListener(new SearchBarLevelAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {

                        showString = childrenItem.get(position);
                        if (mOnSelectListener != null) {
                            mOnSelectListener.getValue(showString);
                        }

                    }
                });
        if (tBlockPosition < childrenItem.size())
            showString = childrenItem.get(tBlockPosition);
        if (showString.contains("不限")) {
            showString = showString.replace("不限", "");
        }
        setDefaultSelect();

    }

    public void setDefaultSelect() {
        regionListView.setSelection(tEaraPosition);
        plateListView.setSelection(tBlockPosition);
    }

    public String getShowText() {
        return showString;
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    public interface OnSelectListener {
        public void getValue(String showText);
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
