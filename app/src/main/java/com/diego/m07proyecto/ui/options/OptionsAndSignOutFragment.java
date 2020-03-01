package com.diego.m07proyecto.ui.options;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.diego.m07proyecto.MainActivity;
import com.diego.m07proyecto.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class OptionsAndSignOutFragment extends Fragment {

    private OptionsAndSignOutViewModel optionsAndSignOutViewModel;
    FirebaseDatabase database;
    FirebaseAuth mAuth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        optionsAndSignOutViewModel =
                ViewModelProviders.of(this).get(OptionsAndSignOutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signout, container, false);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        mAuth.signOut();
        getActivity().finish();
        return root;
    }
}