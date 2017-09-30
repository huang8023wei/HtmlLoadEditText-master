package com.htmlgap.htmlgap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmlgap.ImmFocus;


/**
 * Created by hzp on 2017/9/13.
 */

public class ClickSpan extends ClickableSpan {
    private String txt;
    private int mFontT; // 字体top
    private int mFontB;// 字体bottom
    public static int mOldSpan = -1;
    private String mStr;
    public String mWidthStr="__________";;
    public int id = 0;//回调中的对应Span的ID


    protected ImmFocus mFocus = new ImmFocus();
    private TextView textView;
    private RectF mRf;
    private Context mContext;
    private RelativeLayout rl;
    private ClickController mClickController;
    public ClickSpan(String txt, Context context, TextView textView, RelativeLayout rl, int id, ClickController mClickController) {
        this.txt = txt;
        this.id = id;
        this.mContext = context;
        this.textView = textView;
        this.rl =rl;
        this.mClickController = mClickController;
    }

    @Override
    public void onClick(View widget) {
        Log.d("点击了==","ClickSpan");
        Toast.makeText(mContext,""+id, Toast.LENGTH_LONG).show();
        mOldSpan = id;
        String s = mClickController.getmInputList().get(id);
        mClickController.setData(s,null,mOldSpan);
//        //如果当前span身上有值，先赋值给et身上
//        mEditText.setText(TextUtils.isEmpty(mInputList.get(id))?"":mInputList.get(id));
        setEtXY(textView, drawSpanRect(textView, this));
    }


    //获取出对应Span的RectF数据
    public RectF drawSpanRect(TextView v, ClickSpan s) {
        Layout layout = v.getLayout();
        Spannable buffer = (Spannable) v.getText();
        int l = buffer.getSpanStart(s);
        int r = buffer.getSpanEnd(s);
        int line = layout.getLineForOffset(l);
        int l2 = layout.getLineForOffset(r);
        if (mRf == null) {
            mRf = new RectF();
            Rect rt = new Rect();
            v.getPaint().getTextBounds("TgQyYjJ", 0, 7, rt);
            mFontT = rt.top;
            mFontB = rt.bottom;
        }
        mRf.left = layout.getPrimaryHorizontal(l);
        mRf.right = layout.getSecondaryHorizontal(r);
        // 通过基线去校准
        line = layout.getLineBaseline(line);
        mRf.top = line + mFontT;
        mRf.bottom = line + mFontB;
        return mRf;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    //添加edittext到指定位置
    public void setEtXY(TextView tv, RectF rf) {
        EditText et = new EditText(mContext);
        et.setId(id);
        et.setSingleLine(true );
        et.setBackgroundColor(0);
        et.setTextColor(Color.parseColor("#000000"));
        //设置et w,h的值
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) et.getLayoutParams();
        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams((int)(rf.right - rf.left),dip2px(mContext,mClickController.getEditHeight()));
//        lp.width = (int) (rf.right - rf.left);
//        lp.height = (int) (rf.bottom - rf.top);
        //设置et 相对于tv x,y的相对位置
        lp.leftMargin = (int) (tv.getLeft() + rf.left);
        lp.topMargin = (int) (tv.getTop() + rf.top)-dip2px(mContext,15);
        et.setLayoutParams(lp);
        rl.addView(et);
        //获取焦点，弹出软键盘
        et.setFocusable(true);
        et.requestFocus();
        et.setSelection(et.getText().length());
        showImm(true, et);
        et.addTextChangedListener(mWatcher);
        et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mWidthStr.length())});
    }
    //输入填空的监听
    private TextWatcher mWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try{
                mClickController.setData(s.toString(),null,id);
//                for (int i=0;i<mClickController.getmInputList().size();i++){
//                    Log.d("输入集合----",mClickController.getmInputList().get(i));
//                }
                CallBackListenerImp.mCallbackLisntener.callBack(mClickController.getmInputList());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    public void showImm(boolean bOn, View focus) {
        try {
            if (bOn) {
                if (focus != null) {
                    ImmFocus.show(true, focus);
                } else {
                    mFocus.setFocus(focus);
                }
            } else {
                ImmFocus.show(false, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        //根据自己的需求定制文本的样式
//        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
}
