package com.bjtutravel.bjtutravelagency.plan.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.InfoPlan;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;

public class InfoPlanViewHolder extends BaseViewHolder {
    private InfoPlan mInfoPlan;
    private boolean mModeAdmin;

    public TextView titleView;
    public TextView dateView;
    public TextView userView;

    public InfoPlanViewHolder(View itemView, InfoPlan infoPlan, boolean modeAdmin) {
        super(itemView);

        mInfoPlan = infoPlan;
        mModeAdmin = modeAdmin;

        titleView = itemView.findViewById(R.id.info_plan_title);
        dateView = itemView.findViewById(R.id.info_plan_date);
        userView = itemView.findViewById(R.id.info_plan_user);
    }

    @Override
    public void onBind(ItemPlan itemPlan) {
        titleView.setText(mInfoPlan.getTitle());
        dateView.setText(mInfoPlan.getDate());
        if (mModeAdmin)
            userView.setText("To: " + mInfoPlan.getUserName());
        else
            userView.setText("By: " + mInfoPlan.getStaffName());
    }
}
