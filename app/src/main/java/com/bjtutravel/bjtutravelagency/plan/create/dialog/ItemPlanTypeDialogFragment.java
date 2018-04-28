package com.bjtutravel.bjtutravelagency.plan.create.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.bjtutravel.bjtutravelagency.R;

public class ItemPlanTypeDialogFragment extends DialogFragment {
    private static final String TAG = "ItemPlanTypeDialog";

    private ItemPlanTypeDialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setSingleChoiceItems(R.array.item_plan_types, 0, null)
                .setPositiveButton(R.string.add_button_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        int selectedPosition = ((AlertDialog)dialogInterface).getListView().getCheckedItemPosition();

                        mListener.onDialogPositiveClick(selectedPosition);
                    }
                })
                .setNegativeButton(R.string.cancel_button_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        mListener.onDialogDismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host context implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ItemPlanTypeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public interface ItemPlanTypeDialogListener {
        public void onDialogPositiveClick(int typeIndex);
        public void onDialogDismiss();
    }
}
