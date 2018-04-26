package com.bjtutravel.bjtutravelagency.plan.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;

public class TextViewHolder extends BaseViewHolder {
    public final TextView textView;

    public TextViewHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.text_view);
    }

    @Override
    public void onBind(ItemPlan itemPlan) {
        textView.setText(itemPlan.getContent());
    }

    /*private boolean setValue(Object value, TextView textView) {
        if (value != null) {
            if (!value.toString().isEmpty()) {
                textView.setText(String.valueOf(value));
                textView.setVisibility(View.VISIBLE);
                return true;
            }
        } else
            textView.setVisibility(View.INVISIBLE);
        return false;
    }*/

    @Override
    public String toString() {
        return super.toString() + " '" + textView.getText() + "'";
    }
}