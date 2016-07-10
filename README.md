# QRCode
## [FinderView .java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/tools/camera/FinderView.java)
    这是扫描界面中间模拟激光扫描线效果的View。它是个纯粹的动画，与二维码识别过程没有任何关系
### public void setCameraManager(CameraManager cameraManager)
    设置相机管理器（在QRCodeScanSupport的构造函数中直接调用）
## [CameraScan.java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/exa/alltestcode/CameraScan.java)
    二维码扫描器，直接跳转到这个activity 即可使用
    
    REQUEST_CODE 为打开相册的请求码，值自定义
    RESULT_OK   为相册返回状态码，返回成功则值为-1
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
    在Activity中的`onPause()`函数中调用
### public void onResume(Activity activity)
    在Activity中的`onResume()`函数中调用
### public void setCapturePreview(ImageView capturePreview)
    设置显示预览截图的ImageView
### public void setOnScanResultListener(OnScanResultListener onScanResultListener)
    设置扫描结果监听器
```
public interface OnScanResultListener {
	void onScanResult(String notNullResult);
}
```
	其中notNullResult为扫描结果
## [PicTools.java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/tools/PicTools.java)
	工具类，图片处理
## [Text2QRBitmap.java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/tools/Text2QRBitmap.java)
	工具类，将指定字符串转换为二维码图片，图片大小可设置(调用了QRCodeEncode.java)
## [QRCodeEncode.java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/tools/camera/QRCodeEncode.java)
	工具类，将指定字符串转换为二维码图片，图片大小、颜色可设置
	需要通过内部类Builder类的build方法获取对象，可通过builder对象设置二维码图片各种属性（大小，背景色，编码块颜色，编码格式等）
# 剪贴板
## [ClipboardManagerCompat .java](https://github.com/GhBoyZL/AllTestCode/blob/master/src/com/tools/clipboard/ClipboardManagerCompat.java)
	剪贴板兼容器，通过creat方法 根据不同版本获取不同对象
### public void setOnAddText(OnAddText mAddText)
	设置接口，准备向剪贴板内写入内容
```
	public interface OnAddText {
		CharSequence onAddText(); // 返回将要复制到剪贴板的字符串
	}
```
### public abstract void addText()
	调用此函数，向剪贴板内写入内容
### public void addPrimaryClipChangedListener(OnPrimaryClipChangedListener listener)
	添加监听器，监听剪切板内容变化
```
	public interface OnPrimaryClipChangedListener {
		void onPrimaryClipChanged(CharSequence text);
	}
```
	内容改变监听器 改变时执行onPrimaryClipChanged(CharSequence text)
	text为读取到的内容
### protected final void notifyPrimaryClipChanged()
	当剪贴板内容改变时，此函数将会被调用，执行mPrimaryClipChangedListeners.get(i).onPrimaryClipChanged(getText());// 调用gettext()，获得剪贴板的item，
## 
