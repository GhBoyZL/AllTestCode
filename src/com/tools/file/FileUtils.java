package com.tools.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

public class FileUtils {

	private static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/";

	private FileUtils() {
	}

	public static String getSDPATH() {
		return SDPATH;
	}

	/**
	 * ��sd���ϴ������ļ�
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File creatSDFile(String fileName) throws IOException {
		File file = new File(SDPATH + fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * create a dir in the sd �����ļ���
	 * 
	 * @param dirName
	 *            the path
	 * @return the created file
	 */
	public static File creatSDDir(String dirName) {
		File dir = new File(SDPATH + dirName);
		dir.mkdir();
		return dir;
	}

	/**
	 * ����bitmap��sdcard\"path"\"picName"
	 * 
	 * @param bitmap
	 * @param path
	 * @param picName
	 * @return
	 */
	public static Boolean saveBitmap(Bitmap bitmap, String path, String picName) {
		File file = new File(SDPATH + path, picName);
		if (file.exists()) {
			file.delete();
		}
		try {
			@SuppressWarnings("resource")
			FileOutputStream fout = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, fout);
			fout.flush();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * �������� input ͨ������� д���ļ����洢��sdcade
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static File write2SDFromInputStream(String path, String fileName,
			InputStream input) {
		File file = null; // �ļ���������
		OutputStream os = null; // �������д�ļ�
		try {
			creatSDDir(path); // ����Ŀ¼

			file = creatSDFile(path + fileName); // ����һ�����ļ�

			os = new FileOutputStream(file);// ���������ļ�file�������ͨ����׼��д�ļ�

			byte buffer[] = new byte[4 * 1024];// ��������
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				// д�����ٶ�����
				os.write(buffer, 0, len);
			}
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

}
