package cn.zilin.secretdiary.util;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class ViewUtil {

	public static PopupWindow getPw(View contentView){
		PopupWindow pw = new PopupWindow(contentView.getContext());
		pw.setContentView(contentView);
		pw.setWidth(LayoutParams.FILL_PARENT);
		pw.setHeight(LayoutParams.FILL_PARENT);
		pw.setOutsideTouchable(true);
		pw.setTouchable(true);
		return pw;
	}
	
}
