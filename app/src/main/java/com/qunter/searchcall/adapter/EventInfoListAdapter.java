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

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitleTv,eventTimeTv;
        public ViewHolder(View view){
            super(view);
            eventTitleTv = (TextView) view.findViewById(R.id.event_title);
            eventTimeTv = (TextView) view.findViewById(R.id.event_time);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        EventInfo eventInfo = schoolInfoList.get(position);
        holder.eventTitleTv.setText(eventInfo.getEventTitle());
        holder.eventTimeTv.setText("活动时间为"+eventInfo.getEventStartDate().toString());
    }

    @Override
    public int getItemCount() {
        return schoolInfoList.size();
    }
}
