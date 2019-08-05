package com.refresh.materialdesignapp.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.refresh.materialdesignapp.R;
import com.refresh.materialdesignapp.adapter.QQAdapter;
import com.refresh.materialdesignapp.model.DataUtils;
import com.refresh.materialdesignapp.model.QQMessage;

import java.util.Collections;
import java.util.List;

/**
 * 使用 ItemTouchHelper 配合RecycelView 实现滑动动删除和滑动更改序列
 *
 * @author Administrator
 */
public class RecycleViewDragHelpActivity extends AppCompatActivity implements QQAdapter.OnStartDragListener {
    private static final String TAG = "RecycleViewDragHelpActivity";
    RecyclerView recyclerView;
    ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view_drag_help_activity);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<QQMessage> init = DataUtils.init();
        QQAdapter qqAdapter = new QQAdapter(init, this);
        recyclerView.setAdapter(qqAdapter);


        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                // dragFlags 拽拖方向
                // 侧滑（滑出屏幕） swipeFLag 滑动方向
                return makeMovementFlags(ItemTouchHelper.DOWN | ItemTouchHelper.UP
                        , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            // 是否长按拽拖
            @Override
            public boolean isLongPressDragEnabled() {
                return super.isLongPressDragEnabled();
            }

            // 拖拽过程中回调
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder srcHolder
                    , @NonNull RecyclerView.ViewHolder targetHolder) {
                Log.i(TAG, "getAdapterPosition()" + srcHolder.getAdapterPosition() + "" + targetHolder.getAdapterPosition());
                Log.i(TAG, "getLayoutPosition()" + srcHolder.getLayoutPosition() + "" + targetHolder.getLayoutPosition());

                Collections.swap(init, srcHolder.getAdapterPosition(), targetHolder.getAdapterPosition());

                qqAdapter.notifyItemMoved(srcHolder.getLayoutPosition(), targetHolder.getLayoutPosition());
//                qqAdapter.notifyItemMoved(srcHolder.getAdapterPosition(),targetHolder.getAdapterPosition());
                return false;
            }

            @Override
            public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {

                return true/*super.canDropOver(recyclerView, current, target)*/;
            }

            @Override
            public RecyclerView.ViewHolder chooseDropTarget(@NonNull RecyclerView.ViewHolder selected, @NonNull List<RecyclerView.ViewHolder> dropTargets, int curX, int curY) {
                return super.chooseDropTarget(selected, dropTargets, curX, curY);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.i(TAG, "onSwiped remove " + viewHolder.getAdapterPosition() + " direction : " + direction);
//                init.remove(viewHolder.getAdapterPosition());
//                qqAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
                // 根据状态改变item的样式
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(
                            viewHolder.itemView.getContext().getResources().getColor(R.color.colorBackground));
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
//                        viewHolder.itemView.getContext().getResources().getColor());
                // 复用以后要重置
                viewHolder.itemView.setScaleY(1);
                viewHolder.itemView.setScaleX(1);
                viewHolder.itemView.setAlpha(1);
                super.clearView(recyclerView, viewHolder);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//                    float rate = (Math.abs(dX)) / viewHolder.itemView.getWidth();
//                    viewHolder.itemView.setAlpha(1 - 0.7f * rate);
//                    viewHolder.itemView.setScaleX(1 - 0.3f * rate);
//                    viewHolder.itemView.setScaleY(1 - 0.3f * rate);
//                }
                //要做滑动停止，设置 setTanslationX可以暂时停止视图滑出，但实际上dx还是记录了偏移出了屏幕（dx为-1080）

                Log.i(TAG, " getTranslationX " + viewHolder.itemView.getTranslationX() + " dX " + dX );

                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    viewHolder.itemView.clearAnimation();

                    if (dX < -0.3f * viewHolder.itemView.getWidth() - 1) {
                        viewHolder.itemView.setTranslationX(-0.3f * viewHolder.itemView.getWidth());
                        super.onChildDraw(c, recyclerView, viewHolder
                                , -0.3f * viewHolder.itemView.getWidth(), dY, ItemTouchHelper.ACTION_STATE_IDLE, isCurrentlyActive);
                    } else if (dX > -0.3f * viewHolder.itemView.getWidth() / 2) {
                        viewHolder.itemView.setTranslationX(dX);

                        super.onChildDraw(c, recyclerView, viewHolder
                                , dX, dY, actionState, isCurrentlyActive);
                    } else {
                        viewHolder.itemView.setTranslationX(dX);
                        super.onChildDraw(c, recyclerView, viewHolder
                                , dX, dY, actionState, isCurrentlyActive);
                    }
                }else if(actionState==ItemTouchHelper.ACTION_STATE_IDLE
                        && viewHolder.itemView.getTranslationX()> - 0.3f*viewHolder.itemView.getWidth()/2){
                    TranslateAnimation translateAnimation = new TranslateAnimation(viewHolder.itemView.getTranslationX()
                            , 0
                            ,0,0);
                    viewHolder.itemView.startAnimation(translateAnimation);
                }


            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        // 主动拽拖
        itemTouchHelper.startDrag(viewHolder);
    }


}
