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

    public EditTextViewHolder(View itemView, EditTextListener editTextListener) {
        super(itemView);

        textEdt = (EditText) itemView.findViewById(R.id.edit_text);
        mEditTextListener = editTextListener;
        textEdt.addTextChangedListener(editTextListener);
        adapter = mEditTextListener.getAdapter();
    }

    @Override
    public void onBind(ItemPlan itemPlan) {
        mEditTextListener.updatePosition(this.getAdapterPosition());
        textEdt.setText(adapter.getItemPlan(this.getAdapterPosition()).getContent());
        /*mItem = itemPlan;
        textEdt.setText(itemPlan.getContent());*/
        //setValue(itemPlan.getContent(), textEdt);

    }

    /*private boolean setValue(Object value, EditText editText) {
        if (value != null) {
            if (!value.toString().isEmpty()) {
                editText.setText(String.valueOf(value));
                editText.setVisibility(View.VISIBLE);
                return true;
            }
        } else
            editText.setVisibility(View.INVISIBLE);
        return false;
    }*/

    @Override
    public String toString() {
        return super.toString() + " '" + textEdt.getText() + "'";
    }
}
