package com.tools.clipboard;

import android.annotation.TargetApi;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public final class ClipboardManagerImpl11 extends ClipboardManagerCompat {

	ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
		@Override
		public void onPrimaryClipChanged() {
			notifyPrimaryClipChanged();
		}
	};
	private ClipboardManager mClipboardManager;

	public ClipboardManagerImpl11(Context context) {
		mClipboardManager = (ClipboardManager) context
				.getSystemService(Context.CLIPBOARD_SERVICE);
	}

	@Override
	public void addPrimaryClipChangedListener(
			OnPrimaryClipChangedListener listener) {
		super.addPrimaryClipChangedListener(listener);
		synchronized (mPrimaryClipChangedListeners) {
			if (mPrimaryClipChangedListeners.size() == 1) {
				mClipboardManager
						.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
			}
		}
	}

	@Override
	public void removePrimaryClipChangedListener(
			OnPrimaryClipChangedListener listener) {
		super.removePrimaryClipChangedListener(listener);
		synchronized (mPrimaryClipChangedListeners) {
			if (mPrimaryClipChangedListeners.size() == 0) {
				mClipboardManager
						.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener);
			}
		}
	}

	/**/
	@Override
	public void setOnAddText(OnAddText mAddText) {
		super.setOnAddText(mAddText);
	}

	/**
	 * 返回剪切板最新一个内容
	 */
	@Override
	public CharSequence getText() {
		return mClipboardManager.getText();
	}

	@Override
	public void addText() {
		CharSequence cs = mAddText.onAddText();
		mClipboardManager.setText(cs);
	}
}
