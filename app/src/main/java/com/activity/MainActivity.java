package com.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.androidfresco.R;
import com.view.FirstLevelSearchView;
import com.view.ViewSubject;
import com.view.expandtab.ExpandTabView;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private ExpandTabView expandTabView;
    private ArrayList<View> mViewArray = new ArrayList<View>();
    /**专题、科目*/
    private com.view.ViewSubject viewSubject;
    /**阶段*/
    private com.view.FirstLevelSearchView viewStage;
    /**类型*/
    private com.view.FirstLevelSearchView viewType;
    /**排序*/
    private com.view.FirstLevelSearchView viewSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Uri uri = Uri.parse("http://182.92.113.211/file/T1uaxTBKKv1RCvBVdK.jpg");
//        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
//        draweeView.setImageURI(uri);
//
//        List<Drawable> backgroundsList;
//        List<Drawable> overlaysList;
//        GenericDraweeHierarchyBuilder builder =
//                new GenericDraweeHierarchyBuilder(getResources());
//        GenericDraweeHierarchy hierarchy = builder
//                .setFadeDuration(300)
////                .setPlaceholderImage(new MyCustomDrawable())
////                .setBackgrounds(backgroundList)
////                .setOverlays(overlaysList)
//                .setProgressBarImage(new CustomProgressBar())
//                .build();
//        draweeView.setHierarchy(hierarchy);
















        //交互
        //数据结构
        //存储本地
        //测试无网络交互
        //整体代码逻辑整理
        //UI更换


        /***
         *  显示占位图直到加载完成；
         *  下载图片；缓存图片；
         *  图片不再显示时，从内存中移除；
         */

        /**DraweeView
         *  一般情况下，使用 SimpleDraweeView 即可。 你可以在 XML
         * 或者在 Java 代码中使用它，通过 setImageUri 给它设置一个
         * URI 来使用，这里有简单的入门教学：开始使用
         * */

        /**
         * DraweeHierarchy
         *  DraweeHierarchy 用于组织和维护最终绘制和呈现的 Drawable 对象，相当于MVC中的M。
         * 你可以通过它来在Java代码中自定义图片的展示
         */

        /***
         * DraweeController
         *  DraweeController 负责和 image loader 交互（ Fresco 中默认为 image pipeline
         * , 当然你也可以指定别的），可以创建一个这个类的实例，来实现对所要显示的图片做更多的控制。
         */

        /**
         * DraweeControllerBuilder
         *  DraweeControllers 由 DraweeControllerBuilder 采用 Builder
         * 模式创建，创建之后，不可修改。具体参见: 使用ControllerBuilder。
         */

        /**
         * Listeners
         * 使用 ControllerListener 的一个场景就是设置一个 Listener监听图片的下载。
         */

        /**
         * The Image Pipeline
         *      Fresco 的 Image Pipeline 负责图片的获取和管理。图片可以来自远程服务器，
         * 本地文件，或者Content Provider，本地资源。压缩后的文件缓存在本地存储中，Bitmap数据缓存在内存中。
         *
         *     在5.0系统以下，Image Pipeline 使用 pinned purgeables 将Bitmap数据避开Java堆内存，
         * 存在ashmem中。这要求图片不使用时，要显式地释放内存。
         *
         *      SimpleDraweeView自动处理了这个释放过程，所以没有特殊情况，尽量使用SimpleDraweeView，
         * 在特殊的场合，如果有需要，也可以直接控制Image Pipeline。
         */

        initView();
        initVaule();
        initListener();

    }

    private ArrayList<String> groups = new ArrayList<String>();
    private SparseArray<LinkedList<String>> children = new SparseArray<LinkedList<String>>();

    private void initView() {

        expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view);

        for(int i=0;i<10;i++){
            groups.add(i+"行");
            LinkedList<String> tItem = new LinkedList<String>();
            for(int j=0;j<15;j++){
                tItem.add(i+"行"+j+"列");
            }
            children.put(i, tItem);
        }
        viewSubject = new ViewSubject(this,groups,children);
        viewStage = new FirstLevelSearchView(this,
                new String[] { "全部", "一轮复习", "二轮复习", "白天冲刺", "押题串讲"},
                new String[] { "1", "2", "3", "4", "5"},
                FirstLevelSearchView.IViewType.View_Stage);
        viewType = new FirstLevelSearchView(this,
                new String[] { "全部", "精讲", "大招", "刷题", "扩展"},
                new String[] { "1", "2", "3", "4", "5" },
                FirstLevelSearchView.IViewType.View_Type);
        viewSort = new FirstLevelSearchView(this,
                new String[] { "默认", "最热", "最新"},
                new String[] { "1", "2", "3"},
                FirstLevelSearchView.IViewType.View_Sort);

    }

    private void initVaule() {

        mViewArray.add(viewSubject);
        mViewArray.add(viewStage);
        mViewArray.add(viewType);
        mViewArray.add(viewSort);
        ArrayList<String> mTextArray = new ArrayList<String>();
        mTextArray.add("专题");
        mTextArray.add("阶段");
        mTextArray.add("类型");
        mTextArray.add("排序");
        expandTabView.setValue(mTextArray, mViewArray);
        expandTabView.setTitle("专题", 0);
        expandTabView.setTitle("阶段", 1);
        expandTabView.setTitle("类型", 2);
        expandTabView.setTitle("排序", 3);

    }


    private void initListener() {

        viewSubject.setOnSelectListener(new ViewSubject.OnSelectListener() {
            @Override
            public void getValue(String showText) {
                onRefresh(viewSubject,showText);
            }
        });

        viewStage.setOnSelectListener(new FirstLevelSearchView.OnSelectListener() {
            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewStage, showText);
            }
        });

        viewType.setOnSelectListener(new FirstLevelSearchView.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewType, showText);
            }
        });

        viewSort.setOnSelectListener(new FirstLevelSearchView.OnSelectListener() {

            @Override
            public void getValue(String distance, String showText) {
                onRefresh(viewSort, showText);
            }
        });

    }

    private void onRefresh(View view, String showText) {

        expandTabView.onPressBack();
        int position = getPositon(view);
        if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
            expandTabView.setTitle(showText, position);
        }
        if(view instanceof ViewSubject){
            Toast.makeText(MainActivity.this, showText+"ViewSubject", Toast.LENGTH_SHORT).show();
        }else if(view instanceof FirstLevelSearchView){
            FirstLevelSearchView mFirstLevelSearch = (FirstLevelSearchView) view;
            FirstLevelSearchView.IViewType mIViewType =mFirstLevelSearch.getmIViewType();
            if(mIViewType == FirstLevelSearchView.IViewType.View_Type){
                Toast.makeText(MainActivity.this, showText+"View_Type", Toast.LENGTH_SHORT).show();
            }else if(mIViewType == FirstLevelSearchView.IViewType.View_Stage){
                Toast.makeText(MainActivity.this, showText+"ViewStage", Toast.LENGTH_SHORT).show();
            }else if(mIViewType == FirstLevelSearchView.IViewType.View_Sort){
                Toast.makeText(MainActivity.this, showText+"View_Sort", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private int getPositon(View tView) {
        for (int i = 0; i < mViewArray.size(); i++) {
            if (mViewArray.get(i) == tView) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBackPressed() {
        if (!expandTabView.onPressBack()) {
            finish();
        }
    }

}
