package com.technawabs.mharorajasthan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ServiceRequestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private TextView fullName, mobile, aadhar, address, state, pincode;
    private CardView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);

        spinner = (Spinner) findViewById(R.id.service_selection_spinner);
        spinner.setOnItemSelectedListener(this);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.services_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        fullName = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        aadhar = findViewById(R.id.aadhar_number);
        address = findViewById(R.id.address);
        state = findViewById(R.id.state);
        pincode = findViewById(R.id.pin_code);
        submit = findViewById(R.id.submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullName.getText() != null) {

                }
                if (mobile.getText() != null) {

                }
                if (aadhar.getText() != null) {

                }
                if (address.getText() != null) {

                }
                if (state.getText() != null) {

                }
                if (pincode.getText() != null) {

                }
                if ((fullName.getText() != null) && (mobile.getText() != null) && (aadhar.getText() != null)
                        && (address.getText() != null) && (state.getText() != null)&&(pincode.getText() != null)) {
                    
                }
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
