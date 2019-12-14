package com.hackmatic.edmatic.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.hackmatic.edmatic.R;

public class DetailFragment extends Fragment {

    private DetailViewModel detailViewModel;
    private View mRoot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        detailViewModel =
                ViewModelProviders.of(this).get(DetailViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_details, container, false);

        return mRoot;
    }
}