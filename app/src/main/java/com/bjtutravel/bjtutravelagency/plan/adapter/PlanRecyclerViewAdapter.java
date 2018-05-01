package com.bjtutravel.bjtutravelagency.plan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.InfoPlan;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;
import com.bjtutravel.bjtutravelagency.plan.adapter.holder.BaseViewHolder;
import com.bjtutravel.bjtutravelagency.plan.adapter.holder.EditTextViewHolder;
import com.bjtutravel.bjtutravelagency.plan.adapter.holder.HyperlinkViewHolder;
import com.bjtutravel.bjtutravelagency.plan.adapter.holder.ImageViewHolder;
import com.bjtutravel.bjtutravelagency.plan.adapter.holder.InfoPlanViewHolder;
import com.bjtutravel.bjtutravelagency.plan.adapter.holder.TextViewHolder;
import com.bjtutravel.bjtutravelagency.plan.adapter.listener.EditTextListener;

import java.util.ArrayList;

public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "PlanRecyclerViewAdapter";

    private Context mContext;
    private ArrayList<ItemPlan> mValues;
    private InfoPlan mInfoPlan;
    private boolean modeEdit;
    private boolean modeAdmin;

    public PlanRecyclerViewAdapter(Context context, InfoPlan infoPlan, boolean modeAdmin, boolean modeEdit) {
        this.mContext = context;
        this.mValues = new ArrayList<>();
        this.mInfoPlan = infoPlan;
        this.modeEdit = modeEdit;
        this.modeAdmin = modeAdmin;
    }

    public PlanRecyclerViewAdapter(Context context, boolean modeEdit) {
        this(context,null, false, modeEdit);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         switch (viewType) {
             case 0: // INFO PLAN
                 return new InfoPlanViewHolder(
                         utilLayoutInflater(parent, R.layout.content_info_plan),
                         mInfoPlan, modeAdmin
                 );

             case 1: // TEXT VIEW
                 return new TextViewHolder(
                         utilLayoutInflater(parent, R.layout.plan_item_text_view)
                 );
             case 101: // EDIT TEXT VIEW
                 return new EditTextViewHolder(
                         utilLayoutInflater(parent, R.layout.plan_item_edit_text),
                         new EditTextListener(this),
                         R.string.hint_input_text
                 );

             case 2: // IMAGE VIEW
                 return new ImageViewHolder(
                         utilLayoutInflater(parent, R.layout.plan_item_image_view),
                         mContext
                 );
             case 102: // EDIT IMAGE VIEW, edit text to set img url
                 return new EditTextViewHolder(
                         utilLayoutInflater(parent, R.layout.plan_item_edit_text),
                         new EditTextListener(this),
                         R.string.hint_input_image_url
                 );

             case 3: // HYPERLINK VIEW
                 return new HyperlinkViewHolder(
                         utilLayoutInflater(parent, R.layout.plan_item_hyperlink_view),
                         mContext
                 );
             case 103: // HYPERLINK EDIT, edit text to set kyperlink
                 return new EditTextViewHolder(
                         utilLayoutInflater(parent, R.layout.plan_item_edit_text),
                         new EditTextListener(this),
                         R.string.hint_input_hyperlink
                 );
         }

        return null;
    }



    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        int res = mValues.get(position).getType();
        if (modeEdit)
            res += 100;
        return res;
    }

    // UTIL LAYOUT INFLATER
    private View utilLayoutInflater(@NonNull ViewGroup parent, int ressource) {
        return LayoutInflater.from(parent.getContext())
                .inflate(ressource, parent, false);
    }

    // DATA OPERATIONS
    public void resetList() {
        mValues.clear();
    }

    public void addItemPlan(ItemPlan plan) {
        mValues.add(plan);
        this.notifyItemInserted(mValues.size() - 1);
    }

    public ItemPlan getItemPlan(int position) {
        return mValues.get(position);
    }

    public ArrayList<ItemPlan> getItemPlanList() {
        return mValues;
    }
}
