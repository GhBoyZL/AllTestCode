package com.exa.alltestcode;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.tools.camera.QRCodeDecode;

/**
 * 集合了相册中二维码扫描的方法
 * 
 */
public class OpenAlbum {

	private OpenAlbum() {
	}

	/**
	 * 获取打开相册的Intent
	 * 
	 */
	public static Intent open() {
		Intent intent = new Intent();
		// if (Build.VERSION.SDK_INT < 19) {
		// } else {
		// intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		// }
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		intent.putExtra("return-data", true);
		return intent;
	}

	/**
	 * 在调用此函数的onActivityResult中获取返回的图片
	 * 
	 * @param contentResolver
	 *            getContentResolver()
	 * @param uri
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Bitmap getAlbumBitmap(ContentResolver contentResolver, Uri uri)
			throws FileNotFoundException, IOException {
		return MediaStore.Images.Media.getBitmap(contentResolver, uri);
	}

	/**
	 * 将bitmap（二维码）转换成字符串（解码）
	 * 
	 * @param bitmap
	 * @return 解码成功返回二维码信息，否则返回空字符串
	 */
	public static String deCodeQRBitmap(Bitmap bitmap) {
		QRCodeDecode.Builder builder = new QRCodeDecode.Builder();
		QRCodeDecode qrDecode = builder.build();
		String text = qrDecode.decode(bitmap);
		if (TextUtils.isEmpty(text)) {
			return "";
		}
		return text;
	}
}
