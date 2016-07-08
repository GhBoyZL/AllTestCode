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
 * ��ά��ɨ������ֱ����ת�����activity��ʹ��
 * 
 */
@SuppressLint("NewApi")
public class CameraScan extends Activity {
	private static final int REQUEST_CODE = 0; // ������
	private static final int RESULT_OK = -1; // ����״̬��

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

	// ��ʼ��
	private void init() {
		surface = (SurfaceView) findViewById(R.id.surfaceView_pre);
		finderVier = (FinderView) findViewById(R.id.finderView);

		support = new QRCodeScanSupport(surface, finderVier);
		support.setOnScanResultListener(new OnScanResultListener() {

			@Override
			public void onScanResult(String notNullResult) {
				// ִ��ɨ������notNullResultΪɨ������ַ���������
			}
		});

		// �鿴���,��ת�����ѡ���ά�벢��onActivityResult�����д����ؽ��
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
		// ���õ��Ч��
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
	 * �����ﴦ����᷵�ص���Ϣ
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE:
				// ��ȡͼƬ�ɹ������д���ɨ���ά��
				try {
					Bitmap bm = OpenAlbum.getAlbumBitmap(getContentResolver(),
							data.getData());
					String content = OpenAlbum.deCodeQRBitmap(bm);// contentΪɨ�赽�Ķ�ά������

					Toast.makeText(CameraScan.this, "ɨ����:" + "\n" + content,
							Toast.LENGTH_SHORT).show();
					finish();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (OutOfMemoryError e) {
					Toast.makeText(CameraScan.this, "δɨ�赽��ά��",
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
