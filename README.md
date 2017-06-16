LoadingDotView<br /> 
=

一个简单的加载控件，几个圆点来回闪烁，速度快的话有点像跑马灯。
效果图如下
-

![](http://i.imgur.com/NhZdAVW.gif)

xml布局
-

     <com.kch.loadingdotview_lib.LoadingDotView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/loading_dot_view1"
        app:dot_color="#B452CD"
        app:dot_radius="10dp"
        app:dot_num="3"
        app:dot_space="5dp"
        app:anim_speed="180"
        app:dot_scaled_radius="15dp"
        android:layout_marginBottom="20dp"/>

属性简介
-
- dot_color 圆点颜色
- dot_radius 圆点半径
- dot_scaled_radius 圆点放大后的半径
- dot_space 圆点之间的间隔
- anim_speed 圆点缩放的时间间隔
- dot_num 圆点的数量
- scale_interpolator 插值器类型

使用方法
---
使用起来相当简单，获取到实例控件后，设置相关属性即可

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loading_dot_view1 = (LoadingDotView) findViewById(R.id.loading_dot_view1);
        loading_dot_view2 = (LoadingDotView) findViewById(R.id.loading_dot_view2);
        loading_dot_view3 = (LoadingDotView) findViewById(R.id.loading_dot_view3);

        loading_dot_view1.setDotNum(7);
        loading_dot_view1.setDotRadius(35);
        loading_dot_view2.setDotColor(Color.CYAN);
        loading_dot_view2.setMaxDotRadius(250);
        loading_dot_view3.setDotSpace(35);
        loading_dot_view3.setInterpolator(AnimationUtils.loadInterpolator(this,android.R.interpolator.anticipate));

    }
