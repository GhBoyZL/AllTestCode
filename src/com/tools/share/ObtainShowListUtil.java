package com.tools.share;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * ��ȡ�ɽ���ͼƬ�����app�б�
 * 
 */
public class ObtainShowListUtil {

	private IGetShareIntentList iGetShareIntentList;
	private Context context;

	public ObtainShowListUtil(Context context) {
		this.context = context;
	}

	// ��ȡiGetShareIntentListʵ�����󶨣�
	public void setIGetShareIntentList(IGetShareIntentList iGetShareIntentList) {
		this.iGetShareIntentList = iGetShareIntentList;
	}

	// ��ȡ�б�(ִ��)
	public void getList() {
		if (iGetShareIntentList != null) {
			iGetShareIntentList.onSuccess(getShareApps(context));
		}
	}

	/**
	 * ��ȡ���ܷ������
	 * 
	 * @return ���ؿ��õķ����б���Ϣ
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
