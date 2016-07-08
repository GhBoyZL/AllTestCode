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
 * ����������ж�ά��ɨ��ķ���
 * 
 */
public class OpenAlbum {

	private OpenAlbum() {
	}

	/**
	 * ��ȡ������Intent
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
	 * �ڵ��ô˺�����onActivityResult�л�ȡ���ص�ͼƬ
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
	 * ��bitmap����ά�룩ת�����ַ��������룩
	 * 
	 * @param bitmap
	 * @return ����ɹ����ض�ά����Ϣ�����򷵻ؿ��ַ���
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
