package com.bjtutravel.bjtutravelagency.plan.adapter.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;
import com.bumptech.glide.Glide;

public class ImageViewHolder extends BaseViewHolder {

    private final Context mContext;
    public final ImageView imageView;

    public ImageViewHolder(View itemView, Context context) {
        super(itemView);

        mContext = context;
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    @Override
    public void onBind(ItemPlan itemPlan) {
        Glide.with(mContext).load(itemPlan.getContent()).into(imageView);
    }
}
