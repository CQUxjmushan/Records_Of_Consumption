package com.example.records_of_consumption;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.records_of_consumption.databases.Cost_Record;

import java.util.ArrayList;

public class CostAdapter extends RecyclerView.Adapter<CostAdapter.viewholder> {
    private ArrayList<Cost_Record> datas;
    private Context context;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onItemLongClick(View view , int pos);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CostAdapter(ArrayList<Cost_Record> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public ArrayList<Cost_Record> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<Cost_Record> datas) {
        this.datas = datas;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CostAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewholder myViewHolder = new viewholder(LayoutInflater.from(context).inflate(R.layout.item_show_cost,parent,false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CostAdapter.viewholder holder, final int position) {
        holder.textView_date.setText((String)datas.get(position).getTime());
        holder.textView_cost.setText(datas.get(position).getCost()+"");
        holder.textView_cost_class.setText((String)datas.get(position).getCost_class());
        holder.textView_cost_use.setText((String)datas.get(position).getCost_use());
        holder.itemView.setTag(datas.get(position).getUid());
        String cost_class =datas.get(position).getCost_class();
        if (cost_class.equals("生活日常"))
        {
            holder.cost_class_img.setImageResource(R.drawable.shenghuo);
        }
        else if(cost_class.equals("教育工作"))
        {
            holder.cost_class_img.setImageResource(R.drawable.jiaoyu);
        }
        else if (cost_class.equals("休闲娱乐"))
        {
            holder.cost_class_img.setImageResource(R.drawable.yule);
        }
        else {
            holder.cost_class_img.setImageResource(R.drawable.touzi);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener!=null)
                {
                    onItemClickListener.onItemLongClick(holder.itemView,position);
                }
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        private TextView textView_date;
        private TextView textView_cost;
        private TextView textView_cost_class;
        private TextView textView_cost_use;
        private ImageView cost_class_img;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            textView_cost=itemView.findViewById(R.id.item_cost);
            textView_date=itemView.findViewById(R.id.item_cost_date);
            textView_cost_class=itemView.findViewById(R.id.item_cost_class);
            textView_cost_use=itemView.findViewById(R.id.item_cost_use);
            cost_class_img=itemView.findViewById(R.id.item_cost_class_img);
        }
    }
}
