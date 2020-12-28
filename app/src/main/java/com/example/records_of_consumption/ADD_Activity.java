package com.example.records_of_consumption;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.records_of_consumption.databases.Cost_Record;
import com.example.records_of_consumption.databases.Dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ADD_Activity extends AppCompatActivity {
    private EditText edt_cost;
    private EditText edt_cost_use;
    private TextView text_date;
    //消费记录数据表操作对象
    private Dao costDao;
    //从数据库获得的数据集
    private ArrayList<Cost_Record> orign_datas;
    private Spinner spinner_cost_class;
    //下拉框数据源
    static String[] spinner_use_classes={"生活日常","休闲娱乐","教育工作","投资理财"};
    /*
    时间选择器
     */
    TimePickerView pvTime;
    private ImageButton btn_add_back;
    private ImageButton btn_add_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加记录");
        setContentView(R.layout.activity_a_d_d_);
        edt_cost=findViewById(R.id.edt_cost);
        edt_cost_use=findViewById(R.id.edt_cost_use);
        text_date=findViewById(R.id.text_date);
        btn_add_back=findViewById(R.id.btn_add_back);
        btn_add_submit=findViewById(R.id.btn_add_submit);
        spinner_cost_class=findViewById(R.id.sp_cost_class);
        costDao=new Dao(ADD_Activity.this);
        //初始化spinner适配器
        ArrayAdapter<String> adapter_csot_class=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinner_use_classes);
        spinner_cost_class.setAdapter(adapter_csot_class);

        text_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show(text_date);
            }
        });

        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                TextView btn = (TextView) v;
                btn.setText(getTimes(date));
            }
        })//年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "", "")
                .isCenterLabel(true)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDecorView(null)
                .build();

        btn_add_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ADD_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btn_add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cost_Record cost_record=new Cost_Record();
                String time=text_date.getText().toString();
                String cost=edt_cost.getText().toString();
                String cost_use=edt_cost_use.getText().toString();
                String cost_class=spinner_cost_class.getSelectedItem().toString();
                //判定消息是否填写完整
                if(time.equals("点击添加日期"))
                {
                    //不完整，弹出提示框
                    AlertDialog.Builder builder  = new AlertDialog.Builder(ADD_Activity.this);
                    builder.setTitle("提示" ) ;
                    builder.setMessage("未选择日期，请选择" ) ;
                    builder.setPositiveButton("ok" ,  null );
                    builder.show();
                }
                else {
                    cost_record.setUid(UUID.randomUUID().toString());
                    cost_record.setTime(time);
                    cost_record.setCost_use(cost_use);
                    cost_record.setCost_class(cost_class);
                    cost_record.setCost(Double.valueOf(cost));
                    costDao.insert(cost_record);
                    //弹出添加成功提示框
                    AlertDialog.Builder builder  = new AlertDialog.Builder(ADD_Activity.this);
                    builder.setTitle("提示" ) ;
                    builder.setMessage("添加成功" ) ;
                    builder.setPositiveButton("ok" ,  null );
                    builder.show();
                    }
                    }
            });

    }

    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}