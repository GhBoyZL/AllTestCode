package com.exa.alltestcode;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tools.camera.FinderView;
import com.tools.camera.QRCodeScanSupport;
import com.tools.camera.QRCodeScanSupport.OnScanResultListener;

/**
 * 二维码扫描器，直接跳转到这个activity以使用
 * 
 */
@SuppressLint("NewApi")
public class CameraScan extends Activity {
	private static final int REQUEST_CODE = 0; // 请求码
	private static final int RESULT_OK = -1; // 请求状态码

	private SurfaceView surface = null;
	private FinderView finderVier = null;

	private QRCodeScanSupport support = null;

	private ImageButton back, album;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.camera_scan);

		init();
	}

	// 初始化
	private void init() {
		surface = (SurfaceView) findViewById(R.id.surfaceView_pre);
		finderVier = (FinderView) findViewById(R.id.finderView);

		support = new QRCodeScanSupport(surface, finderVier);
		support.setOnScanResultListener(new OnScanResultListener() {

			@Override
			public void onScanResult(String notNullResult) {
				// 执行扫描结果，notNullResult为扫描出的字符串！！！
			}
		});

		// 查看相册,跳转到相册选择二维码并在onActivityResult方法中处理返回结果
		album = ((ImageButton) findViewById(R.id.img_btn_scan_album));
		album.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = OpenAlbum.open();
				CameraScan.this.startActivityForResult(intent, REQUEST_CODE);
			}
		});
		back = ((ImageButton) findViewById(R.id.img_btn_back));
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		// 设置点击效果
		back.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					back.setBackgroundResource(R.drawable.icon_return_black);
					break;
				case MotionEvent.ACTION_UP:
					back.setBackgroundResource(R.drawable.icon_return_white);
					break;
				}
				return false;
			}
		});
		TextView tv = (TextView) findViewById(R.id.text_scan);
		if (tv.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
			RelativeLayout.LayoutParams lp = (android.widget.RelativeLayout.LayoutParams) tv
					.getLayoutParams();
			lp.setMargins(0, 0, 0, 30);
			tv.setLayoutParams(lp);
		}
	}

	/**
	 * 在这里处理相册返回的信息
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE:
				// 读取图片成功，进行处理，扫描二维码
				try {
					Bitmap bm = OpenAlbum.getAlbumBitmap(getContentResolver(),
							data.getData());
					String content = OpenAlbum.deCodeQRBitmap(bm);// content为扫描到的二维码内容

					Toast.makeText(CameraScan.this, "扫描结果:" + "\n" + content,
							Toast.LENGTH_SHORT).show();
					finish();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (OutOfMemoryError e) {
					Toast.makeText(CameraScan.this, "未扫描到二维码",
							Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		support.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		support.onPause(this);
	}

}
