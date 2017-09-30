package com.htmlgap.htmlgap;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmlgap.R;

import java.util.ArrayList;
import java.util.List;


public class ViewPagerActivity extends AppCompatActivity {
private NoPreloadViewPager viewPager;
    private PagerAdapter favoriteDetailAdapter;
    private List<String> list = new ArrayList<>();
    private View mCurrentView;
    private List<View> listView = new ArrayList<>();
    private  final String str = "<p><span style=\"font-size: 14px;font-family: 宋体\">过点[__Fill.Replace__]<img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153950101096441.png\" title=\"1504153950101096441.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: 宋体\">直线</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153959369086949.png\" title=\"1504153959369086949.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: 宋体\">[__Fill.Replace__]与线段</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153965505061192.png\" title=\"1504153965505061192.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: 宋体\">相交</span><span style=\"font-size: 14px;font-family: inherit, serif\">,</span><span style=\"font-size: 14px;font-family: 宋体\">若</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153971057037078.png\" title=\"1504153971057037078.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: 宋体\">和<img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153977221037497.png\" title=\"1504153977221037497.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: inherit, serif\">,</span><span style=\"font-size: 14px;font-family: 宋体\">则直线</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img width=\"5\" height=\"13\" src=\"/plugins/utf8-jsp/themes/default/images/spacer.gif\" alt=\"www.xiangpi.com\"/></span><span style=\"font-size: 14px;font-family: 宋体\">的斜率</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img width=\"9\" height=\"13\" src=\"/plugins/utf8-jsp/themes/default/images/spacer.gif\" alt=\"http://img.xiangpi.com/Formula/E1D10FB60667175EBFC750A466A1448A.gif\"/></span><span style=\"font-size: 14px;font-family: 宋体\">的取值范围是</span><span style=\"font-size: 14px;font-family: inherit, serif\">[__Fill.Replace__].</span></p>";
      ClickController mClickController;
    private String mReplaceText= "[__Fill.Replace__]";//后台标示
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        list.add(str);
        list.add(str);
        list.add(str);
        list.add(str);
        list.add(str);
        viewPager = (NoPreloadViewPager) findViewById(R.id.viepager);
        favoriteDetailAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object obj) {
                container.removeView((View) obj);
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view =null;

                if(listView.size()>position){
                    view = listView.get(position);
                }else{
                    view = View.inflate(container.getContext(), R.layout.viewpager_item1, null);
                    TextView text1 = (TextView) view.findViewById(R.id.page1);
                    TextView text2 = (TextView) view.findViewById(R.id.page2);
                    RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl);
                    Button submit  = (Button) view.findViewById(R.id.submit);

                    //添加链接
                    text1.setMovementMethod(LinkMovementMethod
                            .getInstance());
                    text2.setMovementMethod(LinkMovementMethod
                            .getInstance());
//                text1.setText("11111111");
                    mClickController  = new ClickController(text1);
                    text1.setText(Html.fromHtml(list.get(position), new URLImageGetter(list.get(position), ViewPagerActivity.this, text1), new HtmlTagHandler(text1, rl, ViewPagerActivity.this,mClickController,mReplaceText)));
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> strings = mClickController.getmInputList();
                            for (int i=0;i<strings.size();i++){
                                Log.d("提交的输入内容=",strings.get(i));
                            }
                        }
                    });
                    listView.add(view);
                }

                container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                return view;
            }
            /**
             * 得到当前view
             * @param container
             * @param position
             * @param object
             */
            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                mCurrentView = (View) object;
            }
        };
        viewPager.setAdapter(favoriteDetailAdapter);
        CallBackListenerImp.getCallbackListener(new CallBackListenerImp.CallbackListener() {
            @Override
            public void callBack(ArrayList<String> mInputList) {
                for (int i=0;i<mInputList.size();i++){
                    Log.d("提交的输入内容=",mInputList.get(i));
                }
            }
        });
        viewPager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                ArrayList<String> strings = mClickController.getmInputList();
//                for (int i=0;i<strings.size();i++){
//                    Log.d("提交的输入内容=",strings.get(i));
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
