package com.bjtutravel.bjtutravelagency.plan.adapter.holder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;

import ru.noties.markwon.Markwon;

public class TextViewHolder extends BaseViewHolder {
    public final TextView textView;

    public TextViewHolder(View itemView) {
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.text_view);
    }

    @Override
    public void onBind(ItemPlan itemPlan) {
        Markwon.setMarkdown(textView, itemPlan.getContent());
    }

    @Override
    public String toString() {
        return super.toString() + " '" + textView.getText() + "'";
    }
}