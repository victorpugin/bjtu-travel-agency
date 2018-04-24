package com.bjtutravel.bjtutravelagency.request.list;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bjtutravel.bjtutravelagency.R;
import com.bjtutravel.bjtutravelagency.models.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestRecyclerViewAdapter extends RecyclerView.Adapter<RequestRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Request> mValues;

    public RequestRecyclerViewAdapter() {
        mValues = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getTitle());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "CLICK");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView title;
        public Request mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            title = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }
    }

    // DATA OPERATIONS
    public void resetList() {
        mValues.clear();
    }

    public void addRequest(Request request) {
        mValues.add(request);
    }
}
