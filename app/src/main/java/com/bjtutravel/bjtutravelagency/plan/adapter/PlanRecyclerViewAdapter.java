package com.bjtutravel.bjtutravelagency.plan.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;

import java.util.ArrayList;

public class PlanRecyclerViewAdapter extends RecyclerView.Adapter<PlanRecyclerViewAdapter.BaseViewHolder> {
    private static final String TAG = "PlanRecyclerViewAdapter";

    private ArrayList<ItemPlan> mValues;
    private boolean modeEdit;

    public PlanRecyclerViewAdapter(boolean modeEdit) {
        this.mValues = new ArrayList<>();
        this.modeEdit = modeEdit;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         switch (viewType) {
             case 1: // TEXT VIEW
                 return new TextViewHolder(utilLayoutInflater(parent, R.layout.plan_item_text_view));
             case 101: // EDIT TEXT VIEW
                 return new EditTextViewHolder(utilLayoutInflater(parent, R.layout.plan_item_edit_text));
             case 2: // TEMPORARY USELESS
                 return new EditTextViewHolder(utilLayoutInflater(parent, R.layout.plan_item_edit_text));
         }

         // USELESS, what to do ?
        return new EditTextViewHolder(utilLayoutInflater(parent, R.layout.plan_item_edit_text));
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

    public ArrayList<ItemPlan> getItemPlans() {
        return mValues;
    }

    // BASE VIEW HOLDER
    public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
        public ItemPlan mItem;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void onBind(ItemPlan itemPlan);

        public abstract ItemPlan getItemPlan();
    }

    // EDIT TEXT VIEW HOLDER
    class EditTextViewHolder extends BaseViewHolder {
        public final EditText textEdt;

        public EditTextViewHolder(View itemView) {
            super(itemView);

            textEdt = (EditText) itemView.findViewById(R.id.edit_text);
        }

        @Override
        public void onBind(ItemPlan itemPlan) {
            mItem = itemPlan;
            textEdt.setText(itemPlan.getContent());
        }

        @Override
        public ItemPlan getItemPlan() {
            mItem.setContent(textEdt.getText().toString());
            return mItem;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textEdt.getText() + "'";
        }
    }

    // EDIT TEXT VIEW HOLDER
    class TextViewHolder extends BaseViewHolder {
        public final TextView textView;

        public TextViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text_view);
        }

        @Override
        public void onBind(ItemPlan itemPlan) {
            mItem = itemPlan;
            textView.setText(itemPlan.getContent());
        }

        @Override
        public ItemPlan getItemPlan() {
            mItem.setContent(textView.getText().toString());
            return mItem;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textView.getText() + "'";
        }
    }
}
