package cn.zilin.secretdiary.common;

import java.util.ArrayList;

public class MoodInstance {

	private static MoodInstance moodInstance;
	private ArrayList<String> moodList = new ArrayList<String>();
	
	private MoodInstance(){
		moodList.add("mood_bg_01.png");
		moodList.add("mood_bg_02.png");
		moodList.add("mood_bg_03.png");
		moodList.add("mood_bg_04.png");
		moodList.add("mood_bg_05.png");
		moodList.add("mood_bg_06.png");
		moodList.add("mood_bg_07.png");
		moodList.add("mood_bg_08.png");
		moodList.add("mood_bg_09.png");
		moodList.add("mood_bg_10.png");
		moodList.add("mood_bg_11.png");
		moodList.add("mood_bg_12.png");
	}
	
	public static MoodInstance getInstance(){
		if(moodInstance == null){
			moodInstance = new MoodInstance();
		}
		return moodInstance;
	}
	
	public ArrayList<String> getMoodList(){
		return moodList;
	}
	
}
