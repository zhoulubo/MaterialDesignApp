package com.refresh.materialdesignapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.refresh.materialdesignapp.model.QQMessage;
import com.refresh.materialdesignapp.R;

import java.util.Collections;
import java.util.List;

public class QQAdapter extends Adapter<QQAdapter.MyViewHolder> {
	private List<QQMessage> list;
	private OnStartDragListener onStartDragListener;
	public interface OnStartDragListener{
		void onStartDrag(RecyclerView.ViewHolder viewHolder);
	}

	public QQAdapter(List<QQMessage> list,OnStartDragListener onStartDragListener) {
		this.list = list;
		this.onStartDragListener =onStartDragListener;
	}
	
	class MyViewHolder extends ViewHolder{

		private ImageView iv_logo;
		private TextView tv_name;
		private TextView tv_Msg;
		private TextView tv_time;

		public MyViewHolder(View itemView) {
			super(itemView);
			iv_logo = (ImageView)itemView.findViewById(R.id.iv_logo);
			tv_name = (TextView)itemView.findViewById(R.id.tv_name);
			tv_Msg = (TextView)itemView.findViewById(R.id.tv_lastMsg);
			tv_time = (TextView)itemView.findViewById(R.id.tv_time);
		}
		
	}

	@Override
	public int getItemCount() {
		return list.size();
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int location) {

		QQMessage qqMessage = list.get(location);
		holder.iv_logo.setImageResource(qqMessage.getLogo());
		holder.tv_name.setText(qqMessage.getName());
		holder.tv_Msg.setText(qqMessage.getLastMsg());
		holder.tv_time.setText(qqMessage.getTime());

		// 头像时触发拽拖
		holder.iv_logo.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					onStartDragListener.onStartDrag(holder);
				}
				return false;
			}
		});
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
		return new MyViewHolder(view);
	}


}
