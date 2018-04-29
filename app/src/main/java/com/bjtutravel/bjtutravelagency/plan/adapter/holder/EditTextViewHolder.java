package com.bjtutravel.bjtutravelagency.plan.adapter.holder;

import android.view.View;
import android.widget.EditText;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.ItemPlan;
import com.bjtutravel.bjtutravelagency.plan.adapter.PlanRecyclerViewAdapter;
import com.bjtutravel.bjtutravelagency.plan.adapter.listener.EditTextListener;

public class EditTextViewHolder extends BaseViewHolder {
    public final EditText textEdt;
    public EditTextListener mEditTextListener;
    public PlanRecyclerViewAdapter adapter;

    public EditTextViewHolder(View itemView, EditTextListener editTextListener, int resHint) {
        super(itemView);

        mEditTextListener = editTextListener;
        adapter = mEditTextListener.getAdapter();

        textEdt = (EditText) itemView.findViewById(R.id.edit_text);
        textEdt.addTextChangedListener(editTextListener);
        textEdt.setHint(resHint);

    }

    @Override
    public void onBind(ItemPlan itemPlan) {
        mEditTextListener.updatePosition(this.getAdapterPosition());
        textEdt.setText(adapter.getItemPlan(this.getAdapterPosition()).getContent());
    }

    @Override
    public String toString() {
        return super.toString() + " '" + textEdt.getText() + "'";
    }
}
