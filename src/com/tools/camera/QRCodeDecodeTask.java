package com.tools.camera;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.text.TextUtils;

/**
 * @author liufengkai
 * @date : 2015-03-03 ��ά������߳�
 */
public abstract class QRCodeDecodeTask extends
		AsyncTask<QRCodeDecodeTask.CameraPreview, Bitmap, String> {

	private final QRCodeDecode mQRCodeDecode;

	public QRCodeDecodeTask(QRCodeDecode qrCodeDecode) {
		mQRCodeDecode = qrCodeDecode;
	}

	@Override
	final protected String doInBackground(CameraPreview... params) {
		if (params.length == 0) {
			throw new IllegalArgumentException(
					"Parameter required when call 'execute(CameraPreview)'; ");
		}
		final Bitmap progress = capture(params[0]);
		this.publishProgress(progress);
		return mQRCodeDecode.decode(progress);
	}

	@Override
	final protected void onPostExecute(String s) {
		if (!TextUtils.isEmpty(s)) {
			onPostDecoded(s);
		}
	}

	/**
	 * �������
	 * 
	 * @param result
	 *            �������
	 */
	protected abstract void onPostDecoded(String result);

	@Override
	final protected void onProgressUpdate(Bitmap... values) {
		onDecodeProgress(values[0]);
	}

	/**
	 * �����ͼƬ
	 * 
	 * @param capture
	 *            ͼƬ
	 */
	protected void onDecodeProgress(Bitmap capture) {
		// Override if need
	}

	private Bitmap capture(CameraPreview cameraPreview) {
		final Camera.Parameters parameters = cameraPreview.mCamera
				.getParameters();
		final int width = parameters.getPreviewSize().width;
		final int height = parameters.getPreviewSize().height;
		final YuvImage yuv = new YuvImage(cameraPreview.mData,
				parameters.getPreviewFormat(), width, height, null);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		yuv.compressToJpeg(new Rect(0, 0, width, height), 100, out);// Best
		final byte[] bytes = out.toByteArray();
		final Bitmap src = BitmapFactory
				.decodeByteArray(bytes, 0, bytes.length);
		final Matrix matrix = new Matrix();
		matrix.setRotate(90);
		// ȡͼƬ�м䲿��
		final int originWidth = src.getWidth();
		final int originHeight = src.getHeight();
		final int targetWH = originWidth > originHeight ? originHeight
				: originWidth;
		final int offsetX = originWidth > originHeight ? (originWidth - originHeight) / 2
				: 0;
		final int offsetY = originWidth > originHeight ? 0
				: (originHeight - originWidth) / 2;
		// �����Ԥ��ͼƬͨ�� progress ���̴��ݸ��ϲ�
		return Bitmap.createBitmap(src, offsetX, offsetY, targetWH, targetWH,
				matrix, true);
	}

	public static final class CameraPreview {

		private final byte[] mData;
		private final Camera mCamera;

		public CameraPreview(byte[] data, Camera camera) {
			mData = data;
			mCamera = camera;
		}
	}
}
