package com.example.records_of_consumption.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Dao
{
    private final DatabaseHelper helper;

    public Dao(Context context){
        helper=new DatabaseHelper(context);
    }

    public void insert(Cost_Record cost_record){
        SQLiteDatabase db=helper.getWritableDatabase();
        //实例化常量值
        ContentValues cValue = new ContentValues();
        //添加主键
        cValue.put("uid",cost_record.getUid());
        //添加时间
        cValue.put("time",cost_record.getTime());
        //添加消费金额
        cValue.put("cost",cost_record.getCost());
        //添加消费分类
        cValue.put("cost_class",cost_record.getCost_class());
        //添加消费用途
        cValue.put("cost_use",cost_record.getCost_use());
        //调用insert()方法插入数据
        db.insert("Cost",null,cValue);
        db.close();
    }

    public void delete(Cost_Record cost_record){
        SQLiteDatabase db=helper.getWritableDatabase();
        //删除条件
        String whereClause = "uid=?";
        //删除条件参数
        String[] whereArgs = {cost_record.getUid()};
        //执行删除
        db.delete("Cost",whereClause,whereArgs);
        db.close();
    }

    public ArrayList<Cost_Record> getALL(){
        SQLiteDatabase db=helper.getReadableDatabase();
        ArrayList<Cost_Record> result=new ArrayList<>();
        //查询获得游标
        Cursor cursor = db.query ("Cost",null,null,null,null,null,null);
        //遍历列表
        while (cursor.moveToNext())
        {
            Cost_Record cost_record=new Cost_Record();
            //读取信息
            cost_record.setUid(cursor.getString(cursor.getColumnIndex("uid")));
            cost_record.setTime(cursor.getString(cursor.getColumnIndex("time")));
            cost_record.setCost(cursor.getDouble(cursor.getColumnIndex("cost")));
            cost_record.setCost_class(cursor.getString(cursor.getColumnIndex("cost_class")));
            cost_record.setCost_use(cursor.getString(cursor.getColumnIndex("cost_use")));
            result.add(cost_record);
        }
        cursor.close();
        db.close();
        return result;
    }

    public void update(Cost_Record cost_record){
        SQLiteDatabase db=helper.getWritableDatabase();
        //实例化常量值
        ContentValues cValue = new ContentValues();
        //添加主键
        cValue.put("uid",cost_record.getUid());
        //添加时间
        cValue.put("time",cost_record.getTime());
        //添加消费金额
        cValue.put("cost",cost_record.getCost());
        //添加消费分类
        cValue.put("cost_class",cost_record.getCost_class());
        //添加消费用途
        cValue.put("cost_use",cost_record.getCost_use());
        //修改条件
        String whereClause = "uid=?";
        //修改添加参数
        String[] whereArgs={cost_record.getUid()};
        //修改
        db.update("Cost",cValue,whereClause,whereArgs);
        db.close();
    }


}
