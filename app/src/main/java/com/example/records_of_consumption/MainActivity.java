package com.example.records_of_consumption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.records_of_consumption.Util.AppUtil;
import com.example.records_of_consumption.databases.Cost_Record;
import com.example.records_of_consumption.databases.Dao;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageButton btn_tubiao;
    private ImageButton btn_add;
    private ImageButton btn_liushui;

    private TextView this_day;
    private TextView this_mounth;
    private TextView this_year;
    private TextView this_week;

    //消费记录数据表操作对象
    private Dao costDao;
    //从数据库获得的数据集
    private ArrayList<Cost_Record> orign_datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("总览");
        setContentView(R.layout.activity_main);
        //从数据库获取数据
        costDao=new Dao(MainActivity.this);
        orign_datas=new ArrayList<>();
        orign_datas=costDao.getALL();

        btn_add=findViewById(R.id.btn_add);
        btn_liushui=findViewById(R.id.btn_liushui);
        btn_tubiao=findViewById(R.id.btn_tubiao);
        this_day=findViewById(R.id.text_this_day);
        this_mounth=findViewById(R.id.text_this_mounth);
        this_year=findViewById(R.id.text_this_year);
        this_week=findViewById(R.id.text_this_week);

        String cost_day= AppUtil.day_all(orign_datas);
        String cost_month= AppUtil.month_all(orign_datas);
        String cost_year= AppUtil.year_all(orign_datas);
        String cost_week= AppUtil.week_all(orign_datas);
        this_day.setText(cost_day);
        this_mounth.setText(cost_month);
        this_year.setText(cost_year);
        this_week.setText(cost_week);

        /*
        给按钮添加点击事件
         */
        btn_tubiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Analyse_Activity.class);
                startActivity(intent);
            }
        });
        btn_liushui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Record_Activity.class);
                startActivity(intent);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ADD_Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        costDao=new Dao(MainActivity.this);
        orign_datas=new ArrayList<>();
        orign_datas=costDao.getALL();

        btn_add=findViewById(R.id.btn_add);
        btn_liushui=findViewById(R.id.btn_liushui);
        btn_tubiao=findViewById(R.id.btn_tubiao);
        this_day=findViewById(R.id.text_this_day);
        this_mounth=findViewById(R.id.text_this_mounth);
        this_year=findViewById(R.id.text_this_year);
        this_week=findViewById(R.id.text_this_week);

        String cost_day= AppUtil.day_all(orign_datas);
        String cost_month= AppUtil.month_all(orign_datas);
        String cost_year= AppUtil.year_all(orign_datas);
        String cost_week= AppUtil.week_all(orign_datas);
        this_day.setText(cost_day);
        this_mounth.setText(cost_month);
        this_year.setText(cost_year);
        this_week.setText(cost_week);
    }
}