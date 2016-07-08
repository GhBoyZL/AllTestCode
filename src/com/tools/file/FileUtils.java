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
	 * 在sd卡上创建空文件
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
	 * create a dir in the sd 创建文件夹
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
	 * 保存bitmap到sdcard\"path"\"picName"
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
	 * 将输入流 input 通过输出流 写成文件并存储到sdcade
	 * 
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static File write2SDFromInputStream(String path, String fileName,
			InputStream input) {
		File file = null; // 文件对象申明
		OutputStream os = null; // 输出流，写文件
		try {
			creatSDDir(path); // 创建目录

			file = creatSDFile(path + fileName); // 创建一个空文件

			os = new FileOutputStream(file);// 创建到空文件file的输出流通道，准备写文件

			byte buffer[] = new byte[4 * 1024];// 缓存区域
			int len = 0;
			while ((len = input.read(buffer)) != -1) {
				// 写缓存再读缓存
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
