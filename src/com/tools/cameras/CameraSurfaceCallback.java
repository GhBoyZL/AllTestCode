package com.tools.cameras;

import android.view.SurfaceHolder;

/**
 * �����Ԥ���ӿڣ��������õĽӿ�
 */
public abstract class CameraSurfaceCallback implements SurfaceHolder.Callback {

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
