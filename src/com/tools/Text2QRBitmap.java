package com.tools;

import android.graphics.Bitmap;

import com.tools.camera.QRCodeEncode;
import com.tools.camera.QRCodeEncode.Builder;

/**
 * ���ַ���ת��Ϊ��ά��ͼƬ�������ַ���������ָ��ͼƬ��С
 */
public class Text2QRBitmap {

	private static QRCodeEncode qrCodeEncode;
	private static Builder builder;

	private Text2QRBitmap() {

	}

	/**
	 * ���ú��� start(String content, int width, int height) Ĭ�ϳ����Ϊ521
	 * 
	 * @param content
	 * @return
	 */
	public static Bitmap start(String content) {
		return start(content, 521, 521);
	}

	/**
	 * ���ı���Ϣ���ɶ�ά��ͼƬ��ָ�����ͼƬ��Ⱥ͸߶�
	 * 
	 * @param content
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap start(String content, int width, int height) {
		builder = new Builder();
		qrCodeEncode = builder.build();
		return qrCodeEncode.encode(content, width, height);
	}

}
