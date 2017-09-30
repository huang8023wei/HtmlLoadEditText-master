package com.htmlgap.htmlgap;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmlgap.R;


/**
 * 加载 html  图文  加edittext
 */
public class AnswerEditTextActivity extends Activity {
    private TextView mTv;
    private ClickController mClickController;
    private String mReplaceText= "[__Fill.Replace__]";//后台标示
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_answer_edit);
        initView();
        initData();

    }

    private RelativeLayout rl;

    private void initView() {
        mTv = (TextView) findViewById(R.id.mTv);
        rl = (RelativeLayout) findViewById(R.id.rl);
    }

    private void initData() {
        mClickController = new ClickController(mTv);
        //添加链接
        mTv.setMovementMethod(LinkMovementMethod
                .getInstance());
            mTv.setText(Html.fromHtml(str, new URLImageGetter(str, this, mTv), new HtmlTagHandler(mTv, rl, this,mClickController,mReplaceText)));
    }



    //    public int setData(String str, Object o){
//        if (mTv == null)return -2;
//        for (int i = 0; i < mSpans.size(); i++) {
//            ClickSpan span = mSpans.get(i);
//            if (TextUtils.isEmpty(span.mText)){
//                span.mText = str;
//                span.id = i;
//                mTv.invalidate();
//                return i;
//            }
//        }
//        //-1说明填空题已经填满
//        return -1;
//    }
    private static final String str = "<p><span style=\"font-size: 14px;font-family: 宋体\">过点[__Fill.Replace__]<img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153950101096441.png\" title=\"1504153950101096441.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: 宋体\">直线</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153959369086949.png\" title=\"1504153959369086949.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: 宋体\">[__Fill.Replace__]与线段</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153965505061192.png\" title=\"1504153965505061192.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: 宋体\">相交</span><span style=\"font-size: 14px;font-family: inherit, serif\">,</span><span style=\"font-size: 14px;font-family: 宋体\">若</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153971057037078.png\" title=\"1504153971057037078.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: 宋体\">和<img src=\"http://www.ennnjoy.cn/ueditor/jsp/upload/image/20170831/1504153977221037497.png\" title=\"1504153977221037497.png\" alt=\"image.png\"/></span><span style=\"font-size: 14px;font-family: inherit, serif\">,</span><span style=\"font-size: 14px;font-family: 宋体\">则直线</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img width=\"5\" height=\"13\" src=\"/plugins/utf8-jsp/themes/default/images/spacer.gif\" alt=\"www.xiangpi.com\"/></span><span style=\"font-size: 14px;font-family: 宋体\">的斜率</span><span style=\"font-size: 14px;font-family: inherit, serif\"><img width=\"9\" height=\"13\" src=\"/plugins/utf8-jsp/themes/default/images/spacer.gif\" alt=\"http://img.xiangpi.com/Formula/E1D10FB60667175EBFC750A466A1448A.gif\"/></span><span style=\"font-size: 14px;font-family: 宋体\">的取值范围是</span><span style=\"font-size: 14px;font-family: inherit, serif\">[__Fill.Replace__].</span></p>";
}