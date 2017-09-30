/*
 * Copyright (C) 2013-2014 Dominik Schürmann <dominik@dominikschuermann.de>
 * Copyright (C) 2013 Mohammed Lakkadshaw
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.htmlgap.htmlgap;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.text.style.LeadingMarginSpan;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Some parts of this code are based on android.text.Html
 */
public class HtmlTagHandler implements Html.TagHandler  {
    private static final boolean DEBUG = true;
    private static final String TAG = "HtmlTagHandler";
    private int mListItemCount = 0;
    private final Vector<String> mListParents = new Vector<String>();
    public List<Integer> indexs=new ArrayList<>();//填空标识下标集合
    public ArrayList<ClickSpan> mSpans = new ArrayList<ClickSpan>();
    private TextView tv;
    private Context mContext;
    private RelativeLayout rl;
    private ClickController clickController;
    private String mReplaceText;//替换的文本
    public HtmlTagHandler(TextView tv, RelativeLayout rl, Context context, ClickController clickController, String replaceText){
        this.tv = tv;
        this.mContext = context;
        this.rl = rl;
        this.clickController = clickController;
        this.mReplaceText = replaceText;
    }
    int idCount=0;
    @Override
    public void handleTag(final boolean opening, final String tag, Editable output,
                          final XMLReader xmlReader) {
        if (opening) {  //加载html  body 标签
            // opening tag
            if (DEBUG) {
                Log.d(TAG, "opening, output: " + output.toString());
            }
        } else {//加载主体部分
            // closing tag
            if (DEBUG) {
                int index = -1;
                int lastIndex =0;
                String line = "__________";
                outputSpan(output, line, lastIndex);
            }
        }
    }
    private int  outputSpan(Editable output, String line, int lastIndex) {
        int index = -1;
        while (-1 != (index = output.toString().indexOf(mReplaceText, lastIndex))) {
            lastIndex = index;
            indexs.add(lastIndex);//将下标添加到集合
            //将下标替换
            output.replace(lastIndex, lastIndex + mReplaceText.length(), line);
            //创建ClickableSpan 实现点击
            ClickSpan clickSpan = new ClickSpan(output.toString(), mContext, tv, rl, idCount, clickController);
            idCount++;
            clickController.addContent("");
            mSpans.add(clickSpan);
            output.setSpan(clickSpan, lastIndex, lastIndex + line.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return lastIndex;
    }
    /**
     * Mark the opening tag by using private classes
     *
     * @param output
     * @param mark
     */
    private void start(Editable output, Object mark) {
        int len = output.length();
        output.setSpan(mark, len, len, Spannable.SPAN_MARK_MARK);

        if (DEBUG) {
            Log.d(TAG, "len: " + len);
        }
    }

    private void end(Editable output, Class kind, Object repl, boolean paragraphStyle) {
        Object obj = getLast(output, kind);
        // start of the tag
        int where = output.getSpanStart(obj);
        // end of the tag
        int len = output.length();

        output.removeSpan(obj);

        if (where != len) {
            // paragraph styles like AlignmentSpan need to end with a new line!
            if (paragraphStyle) {
                output.append("\n");
                len++;
            }
            output.setSpan(repl, where, len, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (DEBUG) {
            Log.d(TAG, "where: " + where);
            Log.d(TAG, "len: " + len);
        }
    }

    /**
     * Get last marked position of a specific tag kind (private class)
     * 
     * @param text
     * @param kind
     * @return
     */
    private Object getLast(Editable text, Class kind) {
        Object[] objs = text.getSpans(0, text.length(), kind);
        if (objs.length == 0) {
            return null;
        } else {
            for (int i = objs.length; i > 0; i--) {
                if (text.getSpanFlags(objs[i - 1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i - 1];
                }
            }
            return null;
        }
    }

    private void handleListTag(Editable output) {
        if (mListParents.lastElement().equals("ul")) {
            output.append("\n");
            String[] split = output.toString().split("\n");

            int lastIndex = split.length - 1;
            int start = output.length() - split[lastIndex].length() - 1;
            output.setSpan(new BulletSpan(15 * mListParents.size()), start, output.length(), 0);
        } else if (mListParents.lastElement().equals("ol")) {
            mListItemCount++;

            output.append("\n");
            String[] split = output.toString().split("\n");

            int lastIndex = split.length - 1;
            int start = output.length() - split[lastIndex].length() - 1;
            output.insert(start, mListItemCount + ". ");
            output.setSpan(new LeadingMarginSpan.Standard(15 * mListParents.size()), start,
                    output.length(), 0);
        }
    }


}
