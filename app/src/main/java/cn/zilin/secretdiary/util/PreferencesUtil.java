package cn.zilin.secretdiary.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtil {
	
	private final static String PASSWORD = "diary_password";
	private final static String PW_KEY = "password";
	private final static String HELPSTATUS = "help_status";
	public final static String MAINHELPSTATUS = "main_status";
	public final static String DIARYHELPSTATUS = "diary_status";

	public static boolean savePassword(Context context, String value){
		SharedPreferences preferences = context.getSharedPreferences(PASSWORD, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(PW_KEY, value);
		return editor.commit();
	}
	
	private static String getPassword(Context context){
		SharedPreferences preferences = context.getSharedPreferences(PASSWORD, Context.MODE_PRIVATE);
		return preferences.getString(PW_KEY, null);
	}
	
	public static boolean checkFirstSign(Context context){
		if(getPassword(context) == null){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean checkPassword(Context context, String check_pw){
		String pw = getPassword(context);
		if(pw.equals(check_pw)){
			return true;
		}else{
			return false;
		}
	}
	
	public static boolean saveHelpStatus(Context context, String key, String value){
		SharedPreferences preferences = context.getSharedPreferences(HELPSTATUS, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		return editor.commit();
	}
	
	private static String getHelpStatus(Context context, String key){
		SharedPreferences preferences = context.getSharedPreferences(HELPSTATUS, Context.MODE_PRIVATE);
		return preferences.getString(key, null);
	}
	
	public static boolean isHelpStatus(Context context, String key){
		if(getHelpStatus(context, key) == null){
			return true;
		}else{
			return false;
		}
	}
}
