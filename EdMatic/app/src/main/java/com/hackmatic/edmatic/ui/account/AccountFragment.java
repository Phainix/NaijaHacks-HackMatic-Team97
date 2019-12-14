package com.hackmatic.edmatic.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.hackmatic.edmatic.R;
import com.hackmatic.edmatic.SigninActivity;
import com.hackmatic.edmatic.ui.detail.DetailFragment;
import com.hackmatic.edmatic.ui.home.HomeFragment;

public class AccountFragment extends Fragment {

    Button mLogout;
    Button mEdit;

    private AccountViewModel accountViewModel;
    private View mRoot;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel.class);
        mRoot = inflater.inflate(R.layout.fragment_account, container, false);

        setUpClickListeners();

        return mRoot;
    }

    private void setUpClickListeners() {
        mLogout = (Button) mRoot.findViewById(R.id.logout);
        mEdit = (Button) mRoot.findViewById(R.id.edit);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(mRoot.getContext(), SigninActivity.class);
                startActivity(intent);
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DetailFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}