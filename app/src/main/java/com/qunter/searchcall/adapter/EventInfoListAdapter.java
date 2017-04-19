package com.qunter.searchcall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qunter.searchcall.R;
import com.qunter.searchcall.entity.EventInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18.
 */

public class EventInfoListAdapter extends RecyclerView.Adapter<EventInfoListAdapter.ViewHolder>{
    private List<EventInfo> schoolInfoList;
    private Context context;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitleTv,eventTimeStartTv,eventTimeEndTv;
        public ViewHolder(View view){
            super(view);
            eventTitleTv = (TextView) view.findViewById(R.id.event_title);
            eventTimeStartTv = (TextView) view.findViewById(R.id.event_time_start);
            eventTimeEndTv = (TextView) view.findViewById(R.id.event_time_end);
        }
    }
    public EventInfoListAdapter(Context context,List<EventInfo> list,RecyclerView recyclerView){
        this.context = context;
        this.schoolInfoList = list;
        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_info,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        EventInfo eventInfo = schoolInfoList.get(position);
        holder.eventTitleTv.setText(eventInfo.getEventTitle());
        holder.eventTimeStartTv.setText("活动开始时间为： "+eventInfo.getEventStartDate().getDate());
        holder.eventTimeEndTv.setText("活动结束时间为： "+eventInfo.getEventEndDate().getDate());
        if(onItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return schoolInfoList.size();
    }

    /**
     * 点击事件接口
     */
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    /**
     * 设置点击事件方法
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
}
