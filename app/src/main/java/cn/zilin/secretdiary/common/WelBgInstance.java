package cn.zilin.secretdiary.common;

import java.util.ArrayList;
import java.util.Random;

public class WelBgInstance {

	private static WelBgInstance welBgInstance;
	private ArrayList<String> welBgList = new ArrayList<String>();
	
	private WelBgInstance(){
		welBgList.add("wel_bg_01.png");
		welBgList.add("wel_bg_02.png");
		welBgList.add("wel_bg_03.png");
		welBgList.add("wel_bg_04.png");
		welBgList.add("wel_bg_05.png");
		welBgList.add("wel_bg_06.png");
		welBgList.add("wel_bg_07.png");
		welBgList.add("wel_bg_08.png");
		welBgList.add("wel_bg_09.png");
	}
	
	public static WelBgInstance getInstance(){
		if(welBgInstance == null){
			welBgInstance = new WelBgInstance();
		}
		return welBgInstance;
	}
	
	public ArrayList<String> getMoodList(){
		return welBgList;
	}

	public String getRandomBg(){
		int index = new Random().nextInt(welBgList.size());
		return welBgList.get(index);
	}
}
