package com.tools.clipboard;

import android.content.Context;
import android.os.Build;

import java.util.ArrayList;

public abstract class ClipboardManagerCompat {

	protected final ArrayList<OnPrimaryClipChangedListener> mPrimaryClipChangedListeners = new ArrayList<OnPrimaryClipChangedListener>();

	//
	protected OnAddText mAddText;

	/**
	 * 剪贴板兼容器，获取不同对象
	 * 
	 * @param context
	 * @return
	 */
	public static ClipboardManagerCompat create(Context context) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return new ClipboardManagerImpl11(context);
		} else {
			return new ClipboardManagerImpl9(context);
		}
	}

	/**
	 * 添加监听器，监听剪切板内容变化
	 * 
	 * @param listener
	 */
	public void addPrimaryClipChangedListener(
			OnPrimaryClipChangedListener listener) {
		synchronized (mPrimaryClipChangedListeners) {
			mPrimaryClipChangedListeners.add(listener);
		}
	}

	/**
	 * 当剪切板内容改变时，调用此函数
	 */
	protected final void notifyPrimaryClipChanged() {
		synchronized (mPrimaryClipChangedListeners) {
			for (int i = 0; i < mPrimaryClipChangedListeners.size(); i++) {
				mPrimaryClipChangedListeners.get(i).onPrimaryClipChanged(
						getText());// 调用gettext()，获得剪贴板的item，
			}
		}
	}

	/**
	 * 移除监听器
	 * 
	 * @param listener
	 */
	public void removePrimaryClipChangedListener(
			OnPrimaryClipChangedListener listener) {
		synchronized (mPrimaryClipChangedListeners) {
			mPrimaryClipChangedListeners.remove(listener);
		}
	}

	/**/
	/**
	 * @param mAddText
	 */
	public void setOnAddText(OnAddText mAddText) {
		synchronized (mAddText) {
			this.mAddText = mAddText;
		}
	}

	/**
	 * 复制到剪贴板 执行复制动作{@link OnAddText}
	 */
	public abstract void addText();

	/**
	 * 读取剪贴板的第一个item
	 * 
	 * @return
	 */
	public abstract CharSequence getText();

	/**
	 * 内容改变监听器 改变时执行onPrimaryClipChanged(CharSequence text)
	 */
	public interface OnPrimaryClipChangedListener {
		void onPrimaryClipChanged(CharSequence text);
	}

	/**/
	/**
	 * 返回将要复制到剪贴板的字符串
	 */
	public interface OnAddText {
		CharSequence onAddText(); // 返回将要复制到剪贴板的字符串
	}
}
