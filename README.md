#LoadingDotView
一个简单的加载控件，几个圆点来回闪烁，速度快的话有点像闪光灯。
######效果图如下

![](http://i.imgur.com/NhZdAVW.gif)
###使用方法

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

###属性简介
- dot_color 圆点颜色
- dot_radius 圆点半径
- dot_scaled_radius 圆点放大后的半径
- dot_space 圆点之间的间隔
- anim_speed 圆点缩放的时间间隔
- dot_num 圆点的数量
- scale_interpolator 插值器类型