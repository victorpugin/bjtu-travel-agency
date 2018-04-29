package com.bjtutravel.bjtutravelagency.plan.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.InfoPlan;
import com.bjtutravel.bjtutravelagency.plan.list.adapter.InfoPlanRecyclerViewAdapter;
import com.bjtutravel.bjtutravelagency.utils.UtilFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A fragment representing a list of Items.
 */
public class PlanFragment extends Fragment {
    private static final String TAG = "PlanFragment";

    public static final String KEY_ADMIN = "com.bjtutravel.bjtutravelagency.KEY_ADMIN";

    private boolean mUserIsAdmin = false;
    private OnListFragmentInteractionListener mListener;
    private InfoPlanRecyclerViewAdapter adapter;

    public PlanFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        mUserIsAdmin = arguments.getBoolean(KEY_ADMIN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new InfoPlanRecyclerViewAdapter(mListener);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Get Firebase db and user
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        String userId = UtilFirebase.getFirebaseUserId();
        if (userId == null)
            return;

        // Change endpoint depending of privilege
        DatabaseReference ref;
        if (mUserIsAdmin)
            ref = db.getReference("info-plans");
        else
            ref = db.getReference("user-info-plans").child(userId);

        // Get requests from db and set them in the view
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adapter.resetList();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            InfoPlan infoPlan = data.getValue(InfoPlan.class);
                            infoPlan.setKey(data.getKey());
                            adapter.addInfoPlan(infoPlan);
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getInfoPlan:onCancelled", databaseError.toException());
                    }
                }
        );
    }

    // LISTENER
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(InfoPlan infoPlan);
    }

}

