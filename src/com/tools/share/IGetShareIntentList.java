package com.tools.share;

import java.util.List;

import android.content.pm.ResolveInfo;

/**
 * ���ڻ�ȡ����Ľӿ�
 */
public interface IGetShareIntentList {

	public void onSuccess(List<ResolveInfo> intents);

}
