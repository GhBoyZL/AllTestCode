# QRCode
## [FinderView .java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/tools/camera/FinderView.java)
    这是扫描界面中间模拟激光扫描线效果的View。它是个纯粹的动画，与二维码识别过程没有任何关系
### public void setCameraManager(CameraManager cameraManager)
    设置相机管理器（在QRCodeScanSupport的构造函数中直接调用）
## [CameraScan.java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/exa/alltestcode/CameraScan.java)
    二维码扫描器，直接跳转到这个activity 即可使用
  * 注意：需要添加相机权限
  * 加载的布局文件[camera.xml](https://github.com/GhBoyZL/AllTestCode/blob/master/res/layout/camera_scan.xml)
  ```
  <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <SurfaceView
        android:id="@+id/surfaceView_pre"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.tools.camera.FinderView
        android:id="@+id/finderView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="12dp" >

        <ImageButton
            android:id="@+id/img_btn_back"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@drawable/icon_return_white" />

        <ImageButton
            android:id="@+id/img_btn_scan_album"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentRight="true"
            android:background="@drawable/icon_table_scan_album" />
    </RelativeLayout>

    <TextView
        android:id="@+id/text_scan"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:gravity="center_horizontal"
        android:text="@string/scan_qr"
        android:textColor="#a8a8a8"
        android:textSize="20sp" />

</RelativeLayout>
  ```
  
===
## [QRCodeScanSupport.java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/tools/camera/QRCodeScanSupport.java)
### public void onPause(Activity activity)
    在Activity中的`onPause`函数中调用
### public void onResume(Activity activity)
    在Activity中的`onResume`函数中调用
### public void setCapturePreview(ImageView capturePreview)
    设置显示预览截图的ImageView
