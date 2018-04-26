package com.bjtutravel.bjtutravelagency.plan.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bjtutravel.bjtutravelagency.models.ItemPlan;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(ItemPlan itemPlan);
}