package cn.zilin.secretdiary.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.zilin.secretdiary.bean.DiaryBean;

public class DiaryDao {

    private DBHelper dbHelper;

    public DiaryDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public boolean isDiaryExist() {
        SQLiteDatabase readDB = null;
        Cursor cursor = null;
        try {
            readDB = dbHelper.getReadableDatabase();
            String sql = "select count(*) from diary";
            cursor = readDB.rawQuery(sql, null);
            if (cursor.moveToFirst() && cursor.getInt(0) > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (readDB != null)
                readDB.close();

        }
        return false;

    }

    public ArrayList<DiaryBean> selectAllDiary() {

        ArrayList<DiaryBean> DiaryList = new ArrayList<DiaryBean>();
        DiaryBean diary;
        SQLiteDatabase readDB = null;
        Cursor cursor = null;
        try {
            readDB = dbHelper.getReadableDatabase();
            String sql = "select * from diary order by time desc";
            cursor = readDB.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                diary = new DiaryBean();
                diary.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                diary.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                diary.setContent(cursor.getString(cursor
                        .getColumnIndex("content")));
                diary.setMood(cursor.getString(cursor.getColumnIndex("mood")));
                diary.setSign(cursor.getString(cursor.getColumnIndex("sign")));
                diary.setTape(cursor.getString(cursor.getColumnIndex("tape")));
                diary.setTime(cursor.getString(cursor.getColumnIndex("time")));
                DiaryList.add(diary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (readDB != null)
                readDB.close();

        }
        return DiaryList;

    }

    public ArrayList<DiaryBean> selectDiary(String msg) {

        ArrayList<DiaryBean> DiaryList = new ArrayList<DiaryBean>();
        DiaryBean diary;
        SQLiteDatabase readDB = null;
        Cursor cursor = null;
        try {
            readDB = dbHelper.getReadableDatabase();
            String sql = "select * from diary where title like ? order by time desc";
            cursor = readDB.rawQuery(sql, new String[]{"%" + msg + "%"});
            while (cursor.moveToNext()) {
                diary = new DiaryBean();
                diary.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                diary.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                diary.setContent(cursor.getString(cursor
                        .getColumnIndex("content")));
                diary.setMood(cursor.getString(cursor.getColumnIndex("mood")));
                diary.setSign(cursor.getString(cursor.getColumnIndex("sign")));
                diary.setTape(cursor.getString(cursor.getColumnIndex("tape")));
                diary.setTime(cursor.getString(cursor.getColumnIndex("time")));
                DiaryList.add(diary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (readDB != null)
                readDB.close();

        }
        return DiaryList;

    }

    public ArrayList<DiaryBean> selectSignDiary() {

        ArrayList<DiaryBean> DiaryList = new ArrayList<DiaryBean>();
        DiaryBean diary;
        SQLiteDatabase readDB = null;
        Cursor cursor = null;
        try {
            readDB = dbHelper.getReadableDatabase();
            String sql = "select * from diary where sign = '1' order by time desc";
            cursor = readDB.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                diary = new DiaryBean();
                diary.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                diary.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                diary.setContent(cursor.getString(cursor
                        .getColumnIndex("content")));
                diary.setMood(cursor.getString(cursor.getColumnIndex("mood")));
                diary.setSign(cursor.getString(cursor.getColumnIndex("sign")));
                diary.setTape(cursor.getString(cursor.getColumnIndex("tape")));
                diary.setTime(cursor.getString(cursor.getColumnIndex("time")));
                DiaryList.add(diary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (readDB != null)
                readDB.close();

        }
        return DiaryList;

    }

    public void insertDiary(DiaryBean diary) {
        SQLiteDatabase writeDB = null;
        try {
            writeDB = dbHelper.getWritableDatabase();
            String sql = "insert into diary(title, content, mood, sign, tape, time) values (?, ?, ?, ?, ?, ?)";
            writeDB.execSQL(
                    sql,
                    new String[]{diary.getTitle(), diary.getContent(),
                            diary.getMood(), diary.getSign(), diary.getTape(), diary.getTime()});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writeDB != null)
                writeDB.close();
        }
    }


    public void insertDiaryList(List<DiaryBean> diaryList) {
        SQLiteDatabase writeDB = null;
        Cursor cursor = null;
        try {
            writeDB = dbHelper.getWritableDatabase();
            String iSql = "insert into diary(_id, title, content, mood, sign, tape, time) values (?, ?, ?, ?, ?, ?, ?)";
            String sSql = "select * from diary where _id = ?";
            for (DiaryBean diary : diaryList) {
                cursor = writeDB.rawQuery(sSql, new String[]{diary.getId() + ""});
                if (cursor.moveToNext()) {
                    continue;
                }
                writeDB.execSQL(
                        iSql,
                        new String[]{diary.getId() + "", diary.getTitle(), diary.getContent(),
                                diary.getMood(), diary.getSign(), diary.getTape(), diary.getTime()});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
            if (writeDB != null)
                writeDB.close();
        }
    }

    public void deleteDiary(DiaryBean diary) {
        SQLiteDatabase writeDB = null;
        try {
            writeDB = dbHelper.getWritableDatabase();
            String sql = "delete from diary where _id = ?";
            writeDB.execSQL(sql, new Object[]{diary.getId()});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writeDB != null)
                writeDB.close();
        }
    }

    public void deleteAllDiary() {
        SQLiteDatabase writeDB = null;
        try {
            writeDB = dbHelper.getWritableDatabase();
            String sql = "delete from diary";
            writeDB.execSQL(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writeDB != null)
                writeDB.close();
        }
    }

    public void updateDiary(DiaryBean diary) {
        SQLiteDatabase writeDB = null;
        try {
            writeDB = dbHelper.getWritableDatabase();
            String sql = "update diary set title=?, content=?, mood=?, sign=?, tape=?, time=? where _id = ?";
            writeDB.execSQL(sql, new Object[]{diary.getTitle(), diary.getContent(), diary.getMood(), diary.getSign(), diary.getTape(), diary.getTime(), diary.getId()});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writeDB != null)
                writeDB.close();
        }
    }

    public void updateSign(DiaryBean diary) {
        SQLiteDatabase writeDB = null;
        try {
            writeDB = dbHelper.getWritableDatabase();
            String sql = "update diary set sign = ? where _id = ?";
            writeDB.execSQL(sql, new Object[]{diary.getSign(), diary.getId()});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writeDB != null)
                writeDB.close();
        }
    }

}
