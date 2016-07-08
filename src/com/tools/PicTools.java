package com.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * ͼƬ����ѹ������ͼ��
 * 
 */
public class PicTools {
	private static final String TAG = PicTools.class.getName();

	private PicTools() {

	}

	/**
	 * ��ȡwebview
	 * 
	 * @param context
	 *            ������
	 * @param webview
	 * @return
	 */
	public static Bitmap createBitmap(Context context, WebView webview) {
		Bitmap bmp = null;
		Picture snapShot = webview.capturePicture();
		bmp = Bitmap.createBitmap(snapShot.getWidth(), snapShot.getHeight(),
				Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(bmp);
		snapShot.draw(canvas);
		return bmp;
	}

	/**
	 * ��ȡscrollview
	 * 
	 * @param context
	 * @param scrollview
	 * @return
	 */
	public static Bitmap createBitmap(Context context, ScrollView scrollview) {
		int width = 0, height = 0;
		for (int i = 0; i < scrollview.getChildCount(); i++) {
			width += scrollview.getChildAt(i).getWidth();
			height += scrollview.getChildAt(i).getHeight();
		}
		if (width <= 0 || height <= 0) {
			return null;
		}
		int h = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
				.getHeight();
		if (height < h)
			height = h;
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		scrollview.draw(canvas);
		return bitmap;
	}

	/**
	 * ��ȡ����listview����bitmap
	 * 
	 * @param listView
	 * @return ����bitmap����
	 */
	public static Bitmap createBitmap(ListView listView) {
		int h = 0;
		Bitmap bitmap = null;
		// ��ȡlistViewʵ�ʸ߶�
		for (int i = 0; i < listView.getChildCount(); i++) {
			h += listView.getChildAt(i).getHeight();
		}
		// ������Ӧ��С��bitmap
		bitmap = Bitmap.createBitmap(listView.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		listView.draw(canvas);
		// �������
		return bitmap;
	}

	/**
	 * ��ȡָ����activity�Ľ���
	 * 
	 * @param activity
	 *            ָ����activity
	 * @return ����bitmap����
	 */
	public static Bitmap createBitmap(Activity activity) {
		// View������Ҫ��ͼ��View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// ��ȡ״̬���߶�
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

		// ��ȡ��Ļ���͸�
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		// ȥ��������
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	/**
	 * ��Viewת��Ϊbitmap
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),
				view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable = view.getBackground();
		if (bgDrawable != null)
			bgDrawable.draw(canvas);
		else
			canvas.drawColor(Color.WHITE);
		view.draw(canvas);
		return returnedBitmap;
	}

	/**
	 * �Զ�ѹ��ͼƬ(ѹ����500kb����)
	 * 
	 * @param image
	 *            ����ͼƬbitmap
	 * @return bitmap
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayInputStream isBm = null;
		ByteArrayOutputStream baoS = new ByteArrayOutputStream();
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		int options = 100;// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baoS��
		image.compress(Bitmap.CompressFormat.JPEG, 100, baoS);
		options = (int) ((1000.0 / baoS.toByteArray().length * 1024) * 100);// ֱ�Ӽ���option��ֵ(������1000)
		if (options < 100) {
			baoS.reset();// ����baoS�����baoS
			image.compress(Bitmap.CompressFormat.JPEG, options, baoS);// ѹ��options%����ѹ��������ݴ�ŵ�baos��
		}
		isBm = new ByteArrayInputStream(baoS.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		bmOptions.inSampleSize = 4;
		return BitmapFactory.decodeStream(isBm, null, bmOptions);// ��ByteArrayInputStream��������ͼƬ
	}

	/**
	 * ָ��ѹ��������ѹ��ͼƬ
	 * 
	 * @param image
	 *            ����ͼƬ��Bitmap
	 * @param options
	 *            ѹ��ͼƬ����
	 * @return bitmap
	 */
	public static Bitmap compressImage(Bitmap image, int options) {
		ByteArrayOutputStream baoS = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, options, baoS);
		ByteArrayInputStream isBm = new ByteArrayInputStream(baoS.toByteArray());
		return BitmapFactory.decodeStream(isBm, null, null);
	}

}
