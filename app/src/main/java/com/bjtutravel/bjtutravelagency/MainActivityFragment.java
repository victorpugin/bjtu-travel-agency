package com.bjtutravel.bjtutravelagency;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private static final String TAG = "MainActivityFragment";

    View thisView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_main, container, false);

        update();

        return thisView;
    }

    private void update() {
        TextView textView = (TextView) thisView.findViewById(R.id.tmpText);
        textView.setText(getTag());
    }
}
