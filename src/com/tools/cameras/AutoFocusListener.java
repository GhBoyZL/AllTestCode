package com.tools.cameras;

public interface AutoFocusListener {

	/**
	 * 聚集完成时此接口被回调
	 * 
	 * @param focusSuccess
	 *            是否聚集成功
	 */
	void onFocus(boolean focusSuccess);
}
