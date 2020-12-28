package com.example.records_of_consumption;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;

import com.example.records_of_consumption.Util.AppUtil;
import com.example.records_of_consumption.databases.Cost_Record;
import com.example.records_of_consumption.databases.Dao;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;


import java.util.ArrayList;

public class Analyse_Activity extends AppCompatActivity {
    //年消费记录
    LineChart myLineChart;
    ArrayList<Entry> mvalues_year_cost;

    //月消费记录
    LineChart mmLineChart;
    ArrayList<Entry> values_month_cost;
    private ArrayList<String>monthList;

    //周消费记录
    LineChart dLineChart;
    ArrayList<Entry> values_week_cost;
    private ArrayList<String>weekList;

    //年消费饼状图
    PieChart ypiechart;
    PieChart mpiechart;
    PieChart dpiechart;

    //消费记录数据表操作对象
    private Dao costDao;
    //从数据库获得的数据集
    private ArrayList<Cost_Record> orign_datas;
    private ArrayList<String>mmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_);
        setTitle("图表分析");
        costDao=new Dao(Analyse_Activity.this);
        orign_datas=costDao.getALL();
        mvalues_year_cost= AppUtil.getdatas_for_year_cost(orign_datas);
        myLineChart=findViewById(R.id.yLineChart);
        mmList=new ArrayList<>();
        for (int i=0;i<12;i++)
        {
            mmList.add(i+1+"月");
        }
        mmLineChart=findViewById(R.id.mLineChart);
        values_month_cost= AppUtil.getdatas_for_month_cost(orign_datas);
        monthList=new ArrayList<>();
        for (int i=0;i<3;i++)
        {
            if (i==0)monthList.add("上旬");
            if (i==1)monthList.add("中旬");
            if (i==2)monthList.add("下旬");
        }
        dLineChart=findViewById(R.id.dLineChart);
        values_week_cost= AppUtil.getdatas_for_week_cost(orign_datas);
        weekList=new ArrayList<>();
        weekList.add("周一");
        weekList.add("周二");
        weekList.add("周三");
        weekList.add("周四");
        weekList.add("周五");
        weekList.add("周六");
        weekList.add("周日");
        set_cost_chart(dLineChart,"本周消费情况","时间",weekList,values_week_cost,7,6f);
        set_cost_chart(myLineChart,"本年消费情况","月份",mmList,mvalues_year_cost,12,11f);
        set_cost_chart(mmLineChart,"本月消费情况","时间段",monthList,values_month_cost,3,2f);
        //模拟数据
        ArrayList<PieEntry> yentries = AppUtil.get_pie_datasbyyear(orign_datas);
        ArrayList<PieEntry> mentries = AppUtil.get_pie_datasbymonth(orign_datas);
        ArrayList<PieEntry> wentries = AppUtil.get_pie_datasbyweek(orign_datas);
        ypiechart = (PieChart) findViewById(R.id.yPieChart);
        set_cost_pie("本年消费组成",ypiechart,yentries);
        mpiechart = (PieChart) findViewById(R.id.mPieChart);
        set_cost_pie("本月消费组成",mpiechart,mentries);
        dpiechart = (PieChart) findViewById(R.id.dPieChart);
        set_cost_pie("本周消费组成",dpiechart,wentries);

    }

    private void set_cost_chart(LineChart yLineChart,String label, String x_label, final ArrayList<String> mList, ArrayList<Entry> values_year_cost, final int xcnt, float xmaximum)
    {
        LineDataSet dataSet = new LineDataSet(values_year_cost, label);
        dataSet.setValueTextSize(10);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setLineWidth(5f); //设置线条宽度
        dataSet.setCircleColor(Color.RED);    //可以设置Entry节点的颜色
        dataSet.setCircleRadius(5f);  //设置节点的大小
        dataSet.setDrawCircleHole(false); //是否定制节点圆心的颜色，若为false，则节点为单一的同色点，若为true则可以设置节点圆心的颜色
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value+"";
            }
        });

        XAxis xAxis = yLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//值：BOTTOM,BOTH_SIDED,BOTTOM_INSIDE,TOP,TOP_INSIDE
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xcnt);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(xmaximum);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value %xcnt;
                return mList.get(index);
            }
        });
        YAxis leftYAxis = yLineChart.getAxisLeft();
        YAxis rightYAxis = yLineChart.getAxisRight();
//        leftYAxis.setAxisMinimum(0f);
//        leftYAxis.setAxisMaximum(100f);
//        rightYAxis.setAxisMinimum(0f);
//        rightYAxis.setAxisMaximum(100f);
        LineData lineData = new LineData(dataSet);
        rightYAxis.setEnabled(false); //右侧Y轴不显示
        rightYAxis.setGranularity(1f);
        rightYAxis.setLabelCount(11,false);
        rightYAxis.setTextColor(Color.BLUE); //文字颜色
        rightYAxis.setGridColor(Color.RED); //网格线颜色
        rightYAxis.setAxisLineColor(Color.GREEN); //Y轴颜色
        /*
        图例
         */
        Legend legend = yLineChart.getLegend();
        legend.setTextColor(Color.RED); //设置Legend 文本颜色
        legend.setTextSize(15);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        /*
        描述
         */
        Description description = new Description();
        description.setText(x_label);
        description.setTextSize(10);
        description.setTextColor(Color.RED);
        yLineChart.setDescription(description);
        yLineChart.setData(lineData);
        yLineChart.invalidate();
    }

    private void set_cost_pie(String title,PieChart ypiechart,ArrayList<PieEntry> entries){
        //1.初始化组件
        ypiechart.setUsePercentValues(true); //设置是否使用百分值,默认不显示
        ypiechart.getDescription().setEnabled(false);
        ypiechart.setDragDecelerationFrictionCoef(0.95f);

        //是否设置中心文字
//        ypiechart.set(true);
        //绘制中间文字
        SpannableString sp = new SpannableString(title);
        ypiechart.setCenterText(sp);
        ypiechart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        //设置是否为实心图，以及空心时 中间的颜色
        ypiechart.setDrawHoleEnabled(true);
        ypiechart.setHoleColor(Color.WHITE);

        //设置圆环信息
        ypiechart.setTransparentCircleColor(Color.WHITE);//设置透明环颜色
        ypiechart.setTransparentCircleAlpha(110);//设置透明环的透明度
        ypiechart.setHoleRadius(30f);//设置内圆的大小
        ypiechart.setTransparentCircleRadius(60f);//设置透明环的大小

        ypiechart.setRotationAngle(0);
        // 触摸旋转
        ypiechart.setRotationEnabled(true);
        //选中变大
        ypiechart.setHighlightPerTapEnabled(true);


        //设置数据
        setData(entries,ypiechart);
        //默认动画
        ypiechart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        //设置图例
        Legend l = ypiechart.getLegend();
        //设置显示的位置，低版本用的是setPosition();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        //设置是否显示图例
        l.setDrawInside(false);
        l.setEnabled(true);

        // 输入图例标签样式
        ypiechart.setEntryLabelColor(Color.BLUE);
        ypiechart.setEntryLabelTextSize(10f);
    }

    //设置数据
    private void setData(ArrayList<PieEntry> entries,PieChart mPieChart) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        //设置个饼状图之间的距离
        dataSet.setSliceSpace(0f);
        //颜色的集合，按照存放的顺序显示
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //设置折线
        dataSet.setValueLinePart1OffsetPercentage(80.f);
        //设置线的长度
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.3f);
        //设置文字和数据图外显示
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        //百分比设置
        data.setValueFormatter(new PercentFormatter());
        //文字的颜色
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);
        // 撤销所有的亮点
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
    }
}