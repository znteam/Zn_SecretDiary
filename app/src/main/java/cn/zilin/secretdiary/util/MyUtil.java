package cn.zilin.secretdiary.util;

import java.text.SimpleDateFormat;

import android.os.Environment;

public class MyUtil {

	/**
	 * ������ֵת��yyyy/MM/dd hh:mm:ss��ʽ��ʱ��
	 * 
	 * @param time
	 * @return
	 */
	public static String convertTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		long tt = -1;
		try {
			tt = Long.parseLong(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tt == -1 ? null : format.format(tt);
	}

	/**
	 * ���ת��Ϊȫ��
	 * 
	 * @param input
	 * @return
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String getTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
		return format.format(System.currentTimeMillis());
	}

	public static String getSDpath() {
		return Environment.getExternalStorageDirectory().toString();
	}

}
