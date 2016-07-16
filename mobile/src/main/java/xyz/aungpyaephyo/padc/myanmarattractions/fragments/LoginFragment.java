package xyz.aungpyaephyo.padc.myanmarattractions.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import xyz.aungpyaephyo.padc.myanmarattractions.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        EditText edUserName = (EditText)view.findViewById(R.id.ed_username_login);
        EditText edPassword = (EditText)view.findViewById(R.id.ed_password_loign);
        Button btnLogin = (Button)view.findViewById(R.id.btn_login);


        String userName = edUserName.getText().toString();
        String password = edPassword.getText().toString();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Login kwar",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public static interface LoginController{
        public void onLogin();
    }

}
