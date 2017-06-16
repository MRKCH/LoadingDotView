package com.kch.loadingdotview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.kch.loadingdotview_lib.LoadingDotView;

public class MainActivity extends AppCompatActivity {

    private LoadingDotView loading_dot_view1;
    private LoadingDotView loading_dot_view2;
    private LoadingDotView loading_dot_view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading_dot_view1 = (LoadingDotView) findViewById(R.id.loading_dot_view1);
        loading_dot_view2 = (LoadingDotView) findViewById(R.id.loading_dot_view2);
        loading_dot_view3 = (LoadingDotView) findViewById(R.id.loading_dot_view3);

        loading_dot_view1.setVisibility(View.VISIBLE);
        loading_dot_view2.setVisibility(View.VISIBLE);
        loading_dot_view3.setVisibility(View.VISIBLE);

        loading_dot_view1.setDotNum(7);
        loading_dot_view1.setDotRadius(35);
        loading_dot_view2.setDotColor(Color.CYAN);
        loading_dot_view2.setMaxDotRadius(250);
        loading_dot_view3.setDotSpace(35);
        loading_dot_view3.setInterpolator(AnimationUtils.loadInterpolator(this,android.R.interpolator.anticipate));
    }

    public void show(View view) {

    }

    public void hide(View view) {
        loading_dot_view1.setVisibility(View.INVISIBLE);
        loading_dot_view2.setVisibility(View.INVISIBLE);
        loading_dot_view3.setVisibility(View.INVISIBLE);
    }

    private void initView() {
        loading_dot_view1 = (LoadingDotView) findViewById(R.id.loading_dot_view1);
        loading_dot_view2 = (LoadingDotView) findViewById(R.id.loading_dot_view2);
        loading_dot_view3 = (LoadingDotView) findViewById(R.id.loading_dot_view3);
    }
}
