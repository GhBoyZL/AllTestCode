package com.tools.share;

import java.util.List;

import android.content.pm.ResolveInfo;

/**
 * 用于获取分享的接口
 */
public interface IGetShareIntentList {

	public void onSuccess(List<ResolveInfo> intents);

}
