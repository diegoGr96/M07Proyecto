package com.diego.m07proyecto.ui.send;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.diego.m07proyecto.HistoriasAdapter;
import com.diego.m07proyecto.MenuPrincipal;
import com.diego.m07proyecto.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static androidx.core.content.ContextCompat.getSystemService;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private RecyclerView mRecyclerView;
    private HistoriasAdapter mAdapter;

    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshTemas;

    private EditText textoBuscar;
    private ImageButton btnBuscar;
    private ImageButton btnCerrarBuscar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        /*final TextView textView = root.findViewById(R.id.text_send);
        sendViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        textoBuscar = root.findViewById(R.id.textoBuscarChat);
        btnBuscar =  root.findViewById(R.id.btnBuscarChat);
        btnCerrarBuscar = root.findViewById(R.id.btnCerrarBuscar);

        textoBuscar.setVisibility(View.INVISIBLE);
        btnCerrarBuscar.setVisibility(View.INVISIBLE);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textoBuscar.getVisibility() == View.INVISIBLE){
                    textoBuscar.setVisibility(View.VISIBLE);
                    btnCerrarBuscar.setVisibility(View.VISIBLE);
                    textoBuscar.requestFocus();
                }else{
                    Toast.makeText(getContext(),"Hola Mundo",Toast.LENGTH_LONG).show();
                    hideKeyboard(getActivity());

                }
            }
        });

        btnCerrarBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
                textoBuscar.setVisibility(View.INVISIBLE);
                btnCerrarBuscar.setVisibility(View.INVISIBLE);

            }
        });

        return root;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}