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
public class RegisterFragment extends Fragment {


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        EditText edUserName = (EditText)view.findViewById(R.id.ed_username_register);
        EditText edEmail = (EditText)view.findViewById(R.id.ed_email_register);
        EditText edPassword = (EditText)view.findViewById(R.id.ed_password_register);
        EditText edDateOfBirth = (EditText)view.findViewById(R.id.ed_date_of_birth_register);
        EditText edCountry = (EditText)view.findViewById(R.id.ed_country_register);
        Button btnRegister = (Button)view.findViewById(R.id.btn_register);

        String userName = edUserName.getText().toString();
        String userEmail = edEmail.getText().toString();
        String dateOfBirth = edDateOfBirth.getText().toString();
        String country = edCountry.getText().toString();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Register kwar",Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
