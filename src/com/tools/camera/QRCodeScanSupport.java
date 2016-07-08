package com.tools.camera;

import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import com.tools.cameras.AutoFocusListener;
import com.tools.cameras.CameraManager;
import com.tools.cameras.CameraSurfaceCallback;

/**
 * @date : 2015-03-05 ��װɨ��֧�ֹ���
 */
public class QRCodeScanSupport {

	public static final String TAG = QRCodeScanSupport.class.getSimpleName();

	private final CameraManager mCameraManager;
	private final SurfaceView mSurfaceView;
	private final QRCodeDecode mQRCodeDecode = new QRCodeDecode.Builder()
			.build();;
	private ImageView mCapturePreview = null;
	private OnScanResultListener mOnScanResultListener;

	private final CameraSurfaceCallback mCallback = new CameraSurfaceCallback() {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			initCamera(holder);
		}
	};

	/**
	 * ����Ԥ��ͼƬ
	 */
	private final Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {

		private PreviewQRCodeDecodeTask mDecodeTask;

		@Override
		public void onPreviewFrame(byte[] data, Camera camera) {
			if (mDecodeTask != null) {
				mDecodeTask.cancel(true);
			}
			mDecodeTask = new PreviewQRCodeDecodeTask(mQRCodeDecode);
			QRCodeDecodeTask.CameraPreview preview = new QRCodeDecodeTask.CameraPreview(
					data, camera);
			mDecodeTask.execute(preview);
		}
	};

	/**
	 * �Զ��Խ�����ص�
	 */
	private final AutoFocusListener mAutoFocusListener = new AutoFocusListener() {
		@Override
		public void onFocus(boolean focusSuccess) {
			// �Խ��ɹ������󴥷����� **һ��** Ԥ��ͼƬ
			if (focusSuccess)
				mCameraManager.requestPreview(mPreviewCallback);
		}
	};

	/**
	 * ����ɨ����������
	 * 
	 * @param onScanResultListener
	 *            ɨ����������
	 */
	public void setOnScanResultListener(
			OnScanResultListener onScanResultListener) {
		mOnScanResultListener = onScanResultListener;
	}

	/**
	 * ������ʾԤ����ͼ��ImageView
	 * 
	 * @param capturePreview
	 *            ImageView
	 */
	public void setCapturePreview(ImageView capturePreview) {
		this.mCapturePreview = capturePreview;
	}

	public QRCodeScanSupport(SurfaceView surfaceView, FinderView finderView) {
		this(surfaceView, finderView, null);
	}

	public QRCodeScanSupport(SurfaceView surfaceView, FinderView finderView,
			OnScanResultListener listener) {
		mCameraManager = new CameraManager(surfaceView.getContext()
				.getApplicationContext());
		finderView.setCameraManager(mCameraManager);
		mSurfaceView = surfaceView;
		mOnScanResultListener = listener;
	}

	/**
	 * ��Activity��onResume�е���
	 * 
	 * @param activity
	 *            Activity
	 */
	public void onResume(Activity activity) {
		SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
		surfaceHolder.addCallback(mCallback);
	}

	/**
	 * ��Activity��onPause�е���
	 * 
	 * @param activity
	 *            Activity
	 */
	public void onPause(Activity activity) {
		SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
		surfaceHolder.removeCallback(mCallback);
		// �ر�����ͷ
		mCameraManager.stopPreview();
		mCameraManager.closeDriver();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (mCameraManager.isOpen())
			return;
		try {
			mCameraManager.openDriver(surfaceHolder);
			mCameraManager.requestPreview(mPreviewCallback);
			mCameraManager.startPreview(mAutoFocusListener);
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
		}
	}

	private class PreviewQRCodeDecodeTask extends QRCodeDecodeTask {

		public PreviewQRCodeDecodeTask(QRCodeDecode qrCodeDecode) {
			super(qrCodeDecode);
		}

		@Override
		protected void onPostDecoded(String result) {
			if (mOnScanResultListener == null) {
				Log.w(TAG, "WARNING ! QRCode result ignored !");
			} else {
				mOnScanResultListener.onScanResult(result);
			}
		}

		@Override
		protected void onDecodeProgress(Bitmap capture) {
			if (mCapturePreview != null) {
				mCapturePreview.setImageBitmap(capture);
			}
		}
	}

	public interface OnScanResultListener {
		void onScanResult(String notNullResult);
	}
}
