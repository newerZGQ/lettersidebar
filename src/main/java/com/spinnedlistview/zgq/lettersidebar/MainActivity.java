package com.spinnedlistview.zgq.lettersidebar;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.spinnedlistview.zgq.lettersidebar.letterview.LetterView;
import com.spinnedlistview.zgq.lettersidebar.letterview.SlipLetterView;

public class MainActivity extends AppCompatActivity {
    int y;
    SlipLetterView slipLetterView;

    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SideBar sideBar = (SideBar) findViewById(R.id.view);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {

            }

            @Override
            public void onSideBarSliping(int yy) {
//                slipLetterView.setVisibility(View.VISIBLE);
//                y = yy;
//                slipLetterView.transLetterView(y);
            }

            @Override
            public void onNoTouch() {
//                slipLetterView.setVisibility(View.GONE);
            }
        });
      handler = new MyHandler();
    }

    public class MyHandler extends Handler{
        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            slipLetterView.transLetterView(y);
        }
    }
}
