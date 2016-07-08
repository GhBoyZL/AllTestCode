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
 * 图片处理（压缩，截图）
 * 
 */
public class PicTools {
	private static final String TAG = PicTools.class.getName();

	private PicTools() {

	}

	/**
	 * 截取webview
	 * 
	 * @param context
	 *            上下文
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
	 * 截取scrollview
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
	 * 截取整个listview生成bitmap
	 * 
	 * @param listView
	 * @return 返回bitmap对象
	 */
	public static Bitmap createBitmap(ListView listView) {
		int h = 0;
		Bitmap bitmap = null;
		// 获取listView实际高度
		for (int i = 0; i < listView.getChildCount(); i++) {
			h += listView.getChildAt(i).getHeight();
		}
		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(listView.getWidth(), h,
				Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		listView.draw(canvas);
		// 测试输出
		return bitmap;
	}

	/**
	 * 获取指定的activity的截屏
	 * 
	 * @param activity
	 *            指定的activity
	 * @return 返回bitmap对象
	 */
	public static Bitmap createBitmap(Activity activity) {
		// View是你需要截图的View
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();

		// 获取状态栏高度
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		System.out.println(statusBarHeight);

		// 获取屏幕长和高
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		// 去掉标题栏
		// Bitmap b = Bitmap.createBitmap(b1, 0, 25, 320, 455);
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return b;
	}

	/**
	 * 将View转化为bitmap
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
	 * 自动压缩图片(压缩到500kb以下)
	 * 
	 * @param image
	 *            传入图片bitmap
	 * @return bitmap
	 */
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayInputStream isBm = null;
		ByteArrayOutputStream baoS = new ByteArrayOutputStream();
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		int options = 100;// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baoS中
		image.compress(Bitmap.CompressFormat.JPEG, 100, baoS);
		options = (int) ((1000.0 / baoS.toByteArray().length * 1024) * 100);// 直接计算option的值(不大于1000)
		if (options < 100) {
			baoS.reset();// 重置baoS即清空baoS
			image.compress(Bitmap.CompressFormat.JPEG, options, baoS);// 压缩options%，把压缩后的数据存放到baos中
		}
		isBm = new ByteArrayInputStream(baoS.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		bmOptions.inSampleSize = 4;
		return BitmapFactory.decodeStream(isBm, null, bmOptions);// 把ByteArrayInputStream数据生成图片
	}

	/**
	 * 指定压缩的质量压缩图片
	 * 
	 * @param image
	 *            传入图片的Bitmap
	 * @param options
	 *            压缩图片质量
	 * @return bitmap
	 */
	public static Bitmap compressImage(Bitmap image, int options) {
		ByteArrayOutputStream baoS = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, options, baoS);
		ByteArrayInputStream isBm = new ByteArrayInputStream(baoS.toByteArray());
		return BitmapFactory.decodeStream(isBm, null, null);
	}

}
