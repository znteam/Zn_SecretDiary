package cn.zilin.secretdiary.util;

import android.app.Activity;
import android.widget.LinearLayout;
import cn.zilin.secretdiary.ui.R;

import com.adview.AdViewLayout;

public class AdUtil {
	
	/*// �������н������ڵ��ԣ�����ǰע�͵�
		AdViewTargeting.setRunMode(RunMode.TEST);
		AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME);*/

	public static void initAd(Activity act) {
		LinearLayout layout = (LinearLayout)act.findViewById(R.id.adLayout);
		AdViewLayout adViewLayout = new AdViewLayout(act, "SDK201221143803518naa8geo2pgkdoo");
		//adViewLayout.setAdViewInterface(this);
		layout.addView(adViewLayout);
		layout.invalidate();
	}
}
