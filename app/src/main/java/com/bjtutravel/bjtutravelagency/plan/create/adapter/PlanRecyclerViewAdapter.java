package com.bjtutravel.bjtutravelagency.plan.create.adapter;

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

    private ArrayList<ItemPlan> mValues;

    public PlanRecyclerViewAdapter() {
        this.mValues = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* switch (viewType) {
             case 0: return new ViewHolder0(...);
             case 2: return new ViewHolder2(...);
             ...
         } */
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plan_item_edit_text, parent, false);
        return new EditTextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        /* switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0)holder;
                ...
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
                ...
                break;
        } */
        EditTextViewHolder editTextHolder = (EditTextViewHolder)holder;

        editTextHolder.mItem = mValues.get(position);
        editTextHolder.textEdt.setText(mValues.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        // return position % 2 * 2;
        return super.getItemViewType(position);
    }

    // DATA OPERATIONS
    public void resetList() {
        mValues.clear();
    }

    public void addItemPlan(ItemPlan plan) {
        mValues.add(plan);
    }

    // BASE VIEW HOLDER
    class BaseViewHolder extends RecyclerView.ViewHolder {
        public ItemPlan mItem;

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    // EDIT TEXT VIEW HOLDER
    class EditTextViewHolder extends BaseViewHolder {
        public final EditText textEdt;

        public EditTextViewHolder(View itemView) {
            super(itemView);

            textEdt = (EditText) itemView.findViewById(R.id.edit_text);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textEdt.getText() + "'";
        }
    }
}
