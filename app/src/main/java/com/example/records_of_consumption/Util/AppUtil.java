package com.example.records_of_consumption.Util;

import android.util.Log;

import com.example.records_of_consumption.databases.Cost_Record;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.text.Collator;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/*/
封装静态工具函数
 */
public class AppUtil {


    /*
    返回一周的消费和
     */
    public static String week_all(ArrayList<Cost_Record> datas)
    {
        double sum=0.00;
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(now.getTime());
        Date today=getDateString(dateString);
        if (datas.size()==0)
        {
            return "0.00";
        }
        else {
            for (Cost_Record cost_record: datas)
            {
                if(isInDateRange(getFirstDayOfWeek(today),getLastDayOfWeek(today),getDateString(cost_record.getTime()))){
                    sum+=cost_record.getCost();
                }
            }
            return sum+"";
        }
    }

    /*
    返回列表中同一天的消费和
     */
    public static String day_all(ArrayList<Cost_Record> datas)
    {
        Calendar now = Calendar.getInstance();
        String day= now.get(Calendar.DAY_OF_MONTH)+"";
        String month= now.get(Calendar.MONTH)+1+"";
        String year= now.get(Calendar.YEAR)+"";
        Log.e("s",day);
        Log.e("s",month);
        Log.e("s",year);
        double sum=0.00;
        if(datas.size()==0)
        {
            return "0.00";
        }
        else
        {
            for (Cost_Record cost_record: datas)
            {
                String[] s=cost_record.getTime().split("-");
                if (s[2].equals(day)&&s[0].equals(year)&&s[1].equals(month))
                {
                    sum+=cost_record.getCost();
                }
            }
            return sum+"";
        }
    }

    /*
   返回列表中同月的消费和
    */
    public static String month_all(ArrayList<Cost_Record> datas)
    {
        Calendar now = Calendar.getInstance();
        String day= now.get(Calendar.MONTH)+1+"";
        String year= now.get(Calendar.YEAR)+"";
        Log.e("ss",day+"");
        double sum=0.00;
        if(datas.size()==0)
        {
            return "0.00";
        }
        else
        {
            for (Cost_Record cost_record: datas)
            {
                String[] s=cost_record.getTime().split("-");
                if (s[1].equals(day)&&s[0].equals(year))
                {
                    sum+=cost_record.getCost();
                }
            }
            return sum+"";
        }
    }

    /*
    返回列表中同年的消费和
*/
    public static String year_all(ArrayList<Cost_Record> datas)
    {
        Calendar now = Calendar.getInstance();
        String day= now.get(Calendar.YEAR)+"";
        double sum=0.00;
        if(datas.size()==0)
        {
            return "0.00";
        }
        else
        {
            for (Cost_Record cost_record: datas)
            {
                String[] s=cost_record.getTime().split("-");
                if (s[0].equals(day))
                {
                    sum+=cost_record.getCost();
                }
            }
            return sum+"";
        }
    }

    /**
     * 获取指定日期所在周的周一
     *
     * @param date      日期
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        c.add(Calendar.DATE, c.getFirstDayOfWeek() - c.get(Calendar.DAY_OF_WEEK) + 1);
        return c.getTime();
    }
    /**
     * 获取指定日期所在周的周日
     *
     * @param date      日期
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 如果是周日直接返回
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            return date;
        }
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        c.add(Calendar.DATE, 7 - c.get(Calendar.DAY_OF_WEEK) + 1);
        return c.getTime();
    }

    /*
    DATE to int   2018-11-1  to 2018111
     */
    public static int getStringDate(Date currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return Integer.valueOf(dateString.replaceAll("-",""));
    }
    /*
    String to date
 */
    public static Date getDateString(String currentTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(currentTime, pos);
        return strtodate;
    }
    /*
    判断当前日期是否在给定范围内
     */
    public static Boolean isInDateRange(Date begin,Date end,Date date)
    {
        int bg=getStringDate(begin);
        int ed=getStringDate(end);
        int c=getStringDate(date);
        if(c>=bg&&c<=ed)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
        获取当前日期所在年的每月消费数组
     */
    public static ArrayList<Entry> getdatas_for_year_cost(ArrayList<Cost_Record> datas){
        Calendar now = Calendar.getInstance();
        String day= now.get(Calendar.YEAR)+"";
        double sum1=0.0;
        double sum2=0.0;
        double sum3=0.0;
        double sum4=0.0;
        double sum5=0.0;
        double sum6=0.0;
        double sum7=0.0;
        double sum8=0.0;
        double sum9=0.0;
        double sum10=0.0;
        double sum11=0.0;
        double sum12=0.0;
        for (Cost_Record record: datas){
            if (record.getTime().split("-")[0].equals(day))
            {
                switch (Integer.valueOf(record.getTime().split("-")[1]))
                {
                    case 1:
                        sum1+=record.getCost();
                        break;
                    case 2:
                        sum2+=record.getCost();
                        break;
                    case 3:
                        sum3+=record.getCost();
                        break;
                    case 4:
                        sum4+=record.getCost();
                        break;
                    case 5:
                        sum5+=record.getCost();
                        break;
                    case 6:
                        sum6+=record.getCost();
                        break;
                    case 7:
                        sum7+=record.getCost();
                        break;
                    case 8:
                        sum8+=record.getCost();
                        break;
                    case 9:
                        sum9+=record.getCost();
                        break;
                    case 10:
                        sum10+=record.getCost();
                        break;
                    case 11:
                        sum11+=record.getCost();
                        break;
                    case 12:
                        sum12+=record.getCost();
                        break;
                }
            }
        }
        ArrayList<Entry> values = new ArrayList<Entry>();
        values.add(new Entry(0,(float) sum1));
        values.add(new Entry(1,(float) sum2));
        values.add(new Entry(2,(float) sum3));
        values.add(new Entry(3,(float) sum4));
        values.add(new Entry(4,(float) sum5));
        values.add(new Entry(5,(float) sum6));
        values.add(new Entry(6,(float) sum7));
        values.add(new Entry(7,(float) sum8));
        values.add(new Entry(8,(float) sum9));
        values.add(new Entry(9,(float) sum10));
        values.add(new Entry(10,(float) sum11));
        values.add(new Entry(11,(float) sum12));
        return values;
    }
    /*
 跟据日期范围返回相应的分类数据集
  */
    public static ArrayList<PieEntry> get_pie_datas(Date bg, Date ed, ArrayList<Cost_Record> datas){
        double sum1=0.0;
        double sum2=0.0;
        double sum3=0.0;
        double sum4=0.0;
        ArrayList<PieEntry> data=new ArrayList<>();
        for (Cost_Record cost_record: datas)
        {
            if(isInDateRange(bg,ed,getDateString(cost_record.getTime()))){
                String s=cost_record.getCost_class();
                if (s.equals("生活日常"))
                {
                    sum1+=cost_record.getCost();
                }
                else if (s.equals("休闲娱乐"))
                {
                    sum2+=cost_record.getCost();
                }
                else if (s.equals("教育工作"))
                {
                    sum3+=cost_record.getCost();
                }
                else {
                    sum4+=cost_record.getCost();
                }
            }
        }
        data.add(new PieEntry((float) sum1,"生活日常:"+sum1));
        data.add(new PieEntry((float) sum2,"休闲娱乐:"+sum2));
        data.add(new PieEntry((float) sum3,"教育工作:"+sum3));
        data.add(new PieEntry((float) sum4,"投资理财:"+sum4));
        return data;
    }

    /*
    返回年分类消费数据集
     */
    public static ArrayList<PieEntry> get_pie_datasbyyear(ArrayList<Cost_Record> datas)
    {
        Calendar now = Calendar.getInstance();
        String year= now.get(Calendar.YEAR)+"";
        String bg=year+"-01-01";
        String ed=year+"-12-31";
        return get_pie_datas(getDateString(bg),getDateString(ed),datas);
    }

    /*
    返回月分类消费数据集
 */
    public static ArrayList<PieEntry> get_pie_datasbymonth(ArrayList<Cost_Record> datas)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat firstDay= new SimpleDateFormat("yyyy-MM-dd");
        String bg=firstDay.format(calendar.getTime());

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, calendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat lastDay= new SimpleDateFormat("yyyy-MM-dd");
        String ed=lastDay.format(calendar1.getTime());
        return get_pie_datas(getDateString(bg),getDateString(ed),datas);
    }

    /*
    返回周分类消费数据集
     */
    public static ArrayList<PieEntry> get_pie_datasbyweek(ArrayList<Cost_Record> datas)
    {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(now.getTime());
        Date date_now=getDateString(dateString);
        Log.e("bg",getStringDate(getFirstDayOfWeek(date_now))+"");
        Log.e("ed",getStringDate(getLastDayOfWeek(date_now))+"");
        return get_pie_datas(getFirstDayOfWeek(date_now),getLastDayOfWeek(date_now),datas);
    }

    /*
    返回月消费折线图数据集
     */
    public static ArrayList<Entry> getdatas_for_month_cost(ArrayList<Cost_Record> datas)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat shangxunbg= new SimpleDateFormat("yyyy-MM-dd");
        String shangxun_bg=shangxunbg.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 10);
        SimpleDateFormat shangxuned= new SimpleDateFormat("yyyy-MM-dd");
        String shangxun_ed=shangxuned.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 11);
        SimpleDateFormat zhongxunbg= new SimpleDateFormat("yyyy-MM-dd");
        String zhongxun_bg=zhongxunbg.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 20);
        SimpleDateFormat zhongxuned= new SimpleDateFormat("yyyy-MM-dd");
        String zhongxun_ed=zhongxuned.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, 21);
        SimpleDateFormat xiaxunbg= new SimpleDateFormat("yyyy-MM-dd");
        String xiaxun_bg=xiaxunbg.format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat xiaxuned= new SimpleDateFormat("yyyy-MM-dd");
        String xiaxun_ed=xiaxuned.format(calendar.getTime());

        double sum1=0.0;
        double sum2=0.0;
        double sum3=0.0;
        for (Cost_Record cost_record:datas)
        {
            if(isInDateRange(getDateString(shangxun_bg),getDateString(shangxun_ed),getDateString(cost_record.getTime())))
            {
                sum1+=cost_record.getCost();
            }
            else if(isInDateRange(getDateString(zhongxun_bg),getDateString(zhongxun_ed),getDateString(cost_record.getTime())))
            {
                sum2+=cost_record.getCost();
            }
            else if(isInDateRange(getDateString(xiaxun_bg),getDateString(xiaxun_ed),getDateString(cost_record.getTime())))
            {
                sum3+=cost_record.getCost();
            }
        }
        ArrayList<Entry> data=new ArrayList<>();
        data.add(new Entry(0,(float)sum1));
        data.add(new Entry(1,(float)sum2));
        data.add(new Entry(2,(float)sum3));
        return data;
    }

    /*
    返回周消费折线图数据集
     */
    public static ArrayList<Entry> getdatas_for_week_cost(ArrayList<Cost_Record> datas)
    {
        Calendar no = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(no.getTime());
        Date date_now=getDateString(dateString);
        Date date =getFirstDayOfWeek(date_now);
        Calendar now = Calendar.getInstance();
        now.setTime(date );
        double[] sum = {0,0,0,0,0,0,0};
        for (int i=0;i<=6;i++)
        {
            int j=1;
            if (i==0)
            {
                j=0;
            }
            now.add(Calendar.DATE, j);
            SimpleDateFormat firstDay= new SimpleDateFormat("yyyy-MM-dd");
            String t=firstDay.format(now.getTime());
            Log.e("t=",t);
            for (Cost_Record cost_record :datas)
            {
                String time=cost_record.getTime();
                if (time.equals(t))
                {
                    sum[i]+=cost_record.getCost();
                }
            }
        }
        ArrayList<Entry> data= new ArrayList<>();
        for (int i=0;i<7;i++)
        {
            data.add(new Entry(i,(float) sum[i]));
        }
        return data;
    }

    /*
    对Arraylist<cost_record> 按照时间升序排列
     */
    public  static void sortbydate(ArrayList<Cost_Record> datas)
    {
        Collections.sort(datas, new Comparator<Cost_Record>() {
            @Override
            public int compare(Cost_Record o1, Cost_Record o2) {
                return Integer.valueOf(getStringDate(getDateString(o2.getTime())))-Integer.valueOf(getStringDate(getDateString(o1.getTime())));
            }
        } );
    }

    /*
    根据条件过滤
     */
    public  static void itemfilter(ArrayList<Cost_Record> datas,ArrayList<Cost_Record> orgin_datas,String date_bg
    ,String date_ed,double cost_bg,double cost_ed,String cost_class,String cost_use){

        datas.clear();
       for (Cost_Record cost_record: orgin_datas)
       {
           Boolean isindate=false;
           Boolean isincost=false;
           Boolean isinclass=false;
           Boolean isinuse=false;
           if(cost_record.getCost()>=cost_bg&&cost_record.getCost()<=cost_ed)
           {
               isincost=true;
               Log.e("test1",isincost.toString());
           }
           if(getStringDate(getDateString(cost_record.getTime()))>=getStringDate(getDateString(date_bg)) &&getStringDate(getDateString(cost_record.getTime()))<=getStringDate(getDateString(date_ed)))
           {
               isindate=true;
               Log.e("test2",isindate.toString());

           }
           if(cost_record.getCost_class().contains(cost_class))
           {
               isinclass=true;
               Log.e("test3",isinclass.toString());
           }
           if(cost_record.getCost_use().contains(cost_use))
           {
               isinuse=true;
               Log.e("test4",isinuse.toString());
           }
           if (isinclass&&isincost&&isinuse&&isindate)
           {
               Log.e("test5","in");
               Cost_Record cr=new Cost_Record();
               cr.setUid(cost_record.getUid());
               cr.setCost(cost_record.getCost());
               cr.setTime(cost_record.getTime());
               cr.setCost_class(cost_record.getCost_class());
               cr.setCost_use(cost_record.getCost_use());
               datas.add(cr);
           }
       }
        
    }
}
