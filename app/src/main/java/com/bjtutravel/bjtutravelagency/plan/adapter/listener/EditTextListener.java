package com.bjtutravel.bjtutravelagency.plan.adapter.listener;

import android.text.Editable;
import android.text.TextWatcher;

import com.bjtutravel.bjtutravelagency.plan.adapter.PlanRecyclerViewAdapter;

public class EditTextListener implements TextWatcher {

    private PlanRecyclerViewAdapter adapter;
    private int position;

    public EditTextListener(PlanRecyclerViewAdapter adapter) {
        this.adapter = adapter;
    }

    public void updatePosition(int position) {
        this.position = position;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        adapter.getItemPlan(position).setContent(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public PlanRecyclerViewAdapter getAdapter() {
        return adapter;
    }
}
