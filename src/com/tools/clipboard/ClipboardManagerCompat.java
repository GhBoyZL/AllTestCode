package com.tools.clipboard;

import android.content.Context;
import android.os.Build;

import java.util.ArrayList;

public abstract class ClipboardManagerCompat {

	protected final ArrayList<OnPrimaryClipChangedListener> mPrimaryClipChangedListeners = new ArrayList<OnPrimaryClipChangedListener>();

	//
	protected OnAddText mAddText;

	/**
	 * ���������������ȡ��ͬ����
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
	 * ��Ӽ��������������а����ݱ仯
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
	 * �����а����ݸı�ʱ�����ô˺���
	 */
	protected final void notifyPrimaryClipChanged() {
		synchronized (mPrimaryClipChangedListeners) {
			for (int i = 0; i < mPrimaryClipChangedListeners.size(); i++) {
				mPrimaryClipChangedListeners.get(i).onPrimaryClipChanged(
						getText());// ����gettext()����ü������item��
			}
		}
	}

	/**
	 * �Ƴ�������
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
	 * ���Ƶ������� ִ�и��ƶ���{@link OnAddText}
	 */
	public abstract void addText();

	/**
	 * ��ȡ������ĵ�һ��item
	 * 
	 * @return
	 */
	public abstract CharSequence getText();

	/**
	 * ���ݸı������ �ı�ʱִ��onPrimaryClipChanged(CharSequence text)
	 */
	public interface OnPrimaryClipChangedListener {
		void onPrimaryClipChanged(CharSequence text);
	}

	/**/
	/**
	 * ���ؽ�Ҫ���Ƶ���������ַ���
	 */
	public interface OnAddText {
		CharSequence onAddText(); // ���ؽ�Ҫ���Ƶ���������ַ���
	}
}
