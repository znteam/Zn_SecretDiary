package cn.zilin.secretdiary.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import cn.zilin.secretdiary.bean.DiaryBean;
import cn.zilin.secretdiary.common.DataCommon;
import cn.zilin.secretdiary.dao.DiaryDao;

public class DiaryManage {

	private DiaryDao diaryDao;
	private Context mContext;
	
	public DiaryManage(Context context){
		diaryDao = new DiaryDao(context);
		this.mContext = context;
	}
	
	public ArrayList<DiaryBean> selectAllDiary(){
		try {
			return diaryDao.selectAllDiary();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<DiaryBean> selectDiary(String msg) {
		try {
			if(msg.equals("*")){
				return diaryDao.selectSignDiary();
			}
			return diaryDao.selectDiary(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insertDiary(DiaryBean diary){
		try {
			diaryDao.insertDiary(diary);
			mContext.sendBroadcast(new Intent(DataCommon.INSERT_DIARY_RECEIVED));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertDiaryList(List<DiaryBean> diaryList){
		try {
			diaryDao.insertDiaryList(diaryList);
			mContext.sendBroadcast(new Intent(DataCommon.INSERT_DIARY_RECEIVED));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteDiary(DiaryBean diary){
		try {
			diaryDao.deleteDiary(diary);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteAllDiary(){
		try {
			diaryDao.deleteAllDiary();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateDiary(DiaryBean diary){
		try {
			diaryDao.updateDiary(diary);
			mContext.sendBroadcast(new Intent(DataCommon.UPDATE_DIARY_RECEIVED));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateSign(DiaryBean diary) {
		try {
			diaryDao.updateSign(diary);
			mContext.sendBroadcast(new Intent(DataCommon.UPDATE_DIARY_RECEIVED));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isDiaryExist(){
		try {
			return diaryDao.isDiaryExist();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
