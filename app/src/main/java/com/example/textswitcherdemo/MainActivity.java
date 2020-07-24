package com.example.textswitcherdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int index = 0;//textview上下滚动下标
    private Handler handler = new Handler();
    private Handler handlerClear = new Handler();
    private Handler handlerAdd = new Handler();
    private Handler handlerAdd2 = new Handler();
    private boolean isFlipping = false; // 是否启用预警信息轮播
    private List<String> mWarningTextList = new ArrayList<>();
    private TextSwitcher mTextSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextSwitcher = findViewById(R.id.textSwitcher);
        mWarningTextList.add("1111111");
        mWarningTextList.add("2222222");
        mWarningTextList.add("3333333");
        setTextSwitcher();
        setData();
        runnableClear.run();
        runnableAdd.run();
        runnableAdd2.run();
    }


    private void setTextSwitcher() {
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getApplicationContext());
                textView.setSingleLine();
                textView.setTextSize(12);//字号
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                textView.setSingleLine();
                textView.setGravity(Gravity.CENTER);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.gravity = Gravity.CENTER;
                textView.setLayoutParams(params);
                textView.setPadding(25, 0, 25, 0);
                return textView;
            }
        });
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isFlipping) {
                return;
            }
            index++;
            if (mWarningTextList.size() > 0)

                mTextSwitcher.setText(mWarningTextList.get(index % mWarningTextList.size()));
            if (index == mWarningTextList.size()) {
                index = 0;
            }
            startFlipping();
        }
    };

    private Runnable runnableClear = new Runnable() {
        @Override
        public void run() {
            mWarningTextList.clear();
            startClear();
        }
    };

    private Runnable runnableAdd = new Runnable() {
        @Override
        public void run() {
            mWarningTextList.add(String.valueOf((Math.random() * 9 + 1) * 100000));
            startAdd();
        }
    };

    private Runnable runnableAdd2 = new Runnable() {
        @Override
        public void run() {
            mWarningTextList.add(String.valueOf((Math.random() * 9 + 1) * 100000));
            startAdd2();
        }
    };

    // 开启信息轮播
    public void startFlipping() {
        handler.removeCallbacks(runnable);
        isFlipping = true;
        handler.postDelayed(runnable, 1000);
    }

    public void startClear() {
        handlerClear.removeCallbacks(runnableClear);
        handlerClear.postDelayed(runnableClear, 3000);
    }

    public void startAdd() {
        handlerAdd.removeCallbacks(runnableAdd);
        handlerAdd.postDelayed(runnableAdd, 500);
    }

    public void startAdd2() {
        handlerAdd2.removeCallbacks(runnableAdd2);
        handlerAdd2.postDelayed(runnableAdd2, 500);
    }

    // 关闭信息轮播
    public void stopFlipping() {
        if (mWarningTextList.size() > 1) {
            isFlipping = false;
            handler.removeCallbacks(runnable);
        }
    }

    // 设置数据
    private void setData() {
        if (mWarningTextList.size() == 1) {
            mTextSwitcher.setText(mWarningTextList.get(0));
            index = 0;
        }
        if (mWarningTextList.size() > 1) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTextSwitcher.setText(mWarningTextList.get(0));
                    index = 0;
                }
            }, 1000);
            mTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom));
            mTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_top));
            startFlipping();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startFlipping();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopFlipping();
    }

}
