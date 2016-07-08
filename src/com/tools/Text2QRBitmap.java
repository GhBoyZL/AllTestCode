package com.tools;

import android.graphics.Bitmap;

import com.tools.camera.QRCodeEncode;
import com.tools.camera.QRCodeEncode.Builder;

/**
 * 将字符串转化为二维码图片（编码字符串），可指定图片大小
 */
public class Text2QRBitmap {

	private static QRCodeEncode qrCodeEncode;
	private static Builder builder;

	private Text2QRBitmap() {

	}

	/**
	 * 调用函数 start(String content, int width, int height) 默认长宽均为521
	 * 
	 * @param content
	 * @return
	 */
	public static Bitmap start(String content) {
		return start(content, 521, 521);
	}

	/**
	 * 将文本信息生成二维码图片，指定输出图片宽度和高度
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
