package com.tools.share;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * 获取可接受图片分享的app列表
 * 
 */
public class ObtainShowListUtil {

	private IGetShareIntentList iGetShareIntentList;
	private Context context;

	public ObtainShowListUtil(Context context) {
		this.context = context;
	}

	// 获取iGetShareIntentList实例（绑定）
	public void setIGetShareIntentList(IGetShareIntentList iGetShareIntentList) {
		this.iGetShareIntentList = iGetShareIntentList;
	}

	// 获取列表(执行)
	public void getList() {
		if (iGetShareIntentList != null) {
			iGetShareIntentList.onSuccess(getShareApps(context));
		}
	}

	/**
	 * 获取接受分享程序
	 * 
	 * @return 返回可用的分享列表信息
	 */
	private List<ResolveInfo> getShareApps(Context context) {
		// int i = 0;
		List<ResolveInfo> mApps = new ArrayList<>();
		Intent intent = new Intent(Intent.ACTION_SEND, null);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("image/png");
		PackageManager pManager = context.getPackageManager();
		mApps = pManager.queryIntentActivities(intent,
				PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		return mApps;
	}

}
