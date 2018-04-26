package com.bjtutravel.bjtutravelagency.plan.adapter.holder;

import android.view.View;
import android.widget.EditText;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;

public class EditTextViewHolder extends BaseViewHolder {
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
