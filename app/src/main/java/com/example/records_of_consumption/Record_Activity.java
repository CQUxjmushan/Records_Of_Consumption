package com.example.records_of_consumption;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.records_of_consumption.Util.AppUtil;
import com.example.records_of_consumption.databases.Cost_Record;
import com.example.records_of_consumption.databases.Dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Record_Activity extends AppCompatActivity {
    private TextView txt_date_bg;
    private TextView txt_date_ed;
    private EditText edt_cost_bg;
    private EditText edt_cost_ed;
    private EditText edt_cost_use;
    private ImageButton btn_search;
    private ImageButton btn_back;
    private Spinner sp_cost_class;
    //recyclerview适配器
    private CostAdapter rev_adapter;
    private RecyclerView recyclerView;
    //消费记录数据表操作对象
    private Dao costDao;
    //从数据库获得的数据集
    private ArrayList<Cost_Record> orign_datas;
    //实际展示datas
    private ArrayList<Cost_Record> datas;
    //下拉框数据源
    static String[] spinner_use_classes={"生活日常","休闲娱乐","教育工作","投资理财"};
    TimePickerView pvTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("流水明细");
        setContentView(R.layout.activity_record_);
        txt_date_bg=findViewById(R.id.text_date_bg);
        txt_date_ed=findViewById(R.id.text_date_ed);
        edt_cost_bg=findViewById(R.id.edt_cost_bg);
        edt_cost_ed=findViewById(R.id.edt_cost_ed);
        edt_cost_use=findViewById(R.id.edt_sec_cost_use);
        sp_cost_class=findViewById(R.id.sp_sec_cost_class);
        btn_back=findViewById(R.id.btn_rec_back);
        btn_search=findViewById(R.id.btn_search);
        recyclerView=findViewById(R.id.rec);
        costDao=new Dao(Record_Activity.this);
        orign_datas=costDao.getALL();
        datas=costDao.getALL();
        //初始化spinner适配器
        ArrayAdapter<String> adapter_cost_class=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinner_use_classes);
        if(sp_cost_class==null)
        {
            Log.e("null","yes");
        }
        sp_cost_class.setAdapter(adapter_cost_class);
        txt_date_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show(txt_date_bg);
            }
        });
        txt_date_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show(txt_date_ed);
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
        //定义recyclerview适配器
        AppUtil.sortbydate(datas);
        AppUtil.sortbydate(orign_datas);
        rev_adapter=new CostAdapter(datas,Record_Activity.this);
        //设置布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // adapter是适配器对象
        rev_adapter.setOnItemClickListener(new CostAdapter.OnItemClickListener() {
            @Override
            public void onItemLongClick(final View view, final int pos) {
                PopupMenu popupMenu = new PopupMenu(Record_Activity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());

                //弹出式菜单的菜单项点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.removeItem){
                        Cost_Record record=new Cost_Record();
                        record.setUid(datas.get(pos).getUid());
                        record.setTime(datas.get(pos).getTime());
                        record.setCost(datas.get(pos).getCost());
                        record.setCost_class(datas.get(pos).getCost_class());
                        record.setCost_use(datas.get(pos).getCost_use());
                        costDao.delete(record);
                        datas.remove(pos);
                        rev_adapter.notifyDataSetChanged();}

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //添加适配器
        recyclerView.setAdapter(rev_adapter);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date_bg=(String) txt_date_bg.getText();
                String date_ed=(String) txt_date_ed.getText();
                double cost_bg= Double.valueOf(edt_cost_bg.getText().toString());
                double cost_ed= Double.valueOf(edt_cost_ed.getText().toString());
                String cost_class=sp_cost_class.getSelectedItem().toString();
                String cost_use=edt_cost_use.getText().toString();
                if(date_bg.equals("开始日期")||date_ed.equals("结束日期"))
                {
                    //不完整，弹出提示框
                    AlertDialog.Builder builder  = new AlertDialog.Builder(Record_Activity.this);
                    builder.setTitle("提示" ) ;
                    builder.setMessage("日期选择填写不完整，请重新填写" ) ;
                    builder.setPositiveButton("ok" ,  null );
                    builder.show();
                }
                else{
                    AppUtil.itemfilter(datas,orign_datas,date_bg,date_ed,cost_bg,cost_ed,cost_class,cost_use);
                    rev_adapter.notifyDataSetChanged();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Record_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }

    private String getTimes(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}