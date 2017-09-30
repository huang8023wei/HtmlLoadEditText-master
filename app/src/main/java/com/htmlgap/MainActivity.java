package com.htmlgap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.htmlgap.htmlgap.AnswerEditTextActivity;
import com.htmlgap.htmlgap.ViewPagerActivity;
import com.htmlgap.nativegap.AnswerSelect;


//选择填空，完型填空的Demo
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.mBtnSelect:
            //有备选词的完型填空
            startActivity(new Intent(this,AnswerSelect.class));
            break;
        case R.id.mBtnEdit:
            //html加载图文混排edittext
            startActivity(new Intent(this,AnswerEditTextActivity.class));
            break;
            case R.id.viewpager:
                //viewpager加载图文混排edittext
                startActivity(new Intent(this,ViewPagerActivity.class));
            break;
        }
    }

}
