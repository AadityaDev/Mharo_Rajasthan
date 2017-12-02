package com.technawabs.mharorajasthan;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.technawabs.mharorajasthan.network.RequestHandler;
import com.technawabs.mharorajasthan.pojo.AppError;
import com.technawabs.mharorajasthan.pojo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ServiceRequestActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private TextView fullName, mobile, aadhar, address, state, pincode;
    private CardView submit;
    private RequestHandler requestHandler = new RequestHandler();
    private String body, response;
    private ServiceSaveUserDetail serviceTask = null;
    private ServiceGetUserDetail servicegetUserDetail = null;
    private ServiceGetUserCount serviceGetUserCount = null;
    private ServiceRequestSubmit serviceRequestSubmit=null;
    private View mProgressView, mLoginFormView;
    private int idCount = 0;
    private Handler handler;

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

        mLoginFormView = findViewById(R.id.request_form);
        mProgressView = findViewById(R.id.login_progress);
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

            }
        });

        getUserDetails();

    }

    private void sendUserDetails() {
        boolean cancel = false;
        View focusView = null;
        // Check for a valid name, if the user entered one.
        if (TextUtils.isEmpty(fullName.getText())) {
            fullName.setError("Field is empty");
            focusView = fullName;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobile.getText())) {
            mobile.setError("Field is empty");
            focusView = mobile;
            cancel = true;
        }
        if (TextUtils.isEmpty(aadhar.getText())) {
            aadhar.setError("Field is empty");
            focusView = aadhar;
            cancel = true;
        }
        if (TextUtils.isEmpty(address.getText())) {
            address.setError("Field is empty");
            focusView = address;
            cancel = true;
        }
        if (TextUtils.isEmpty(state.getText())) {
            state.setError("Field is empty");
            focusView = state;
            cancel = true;
        }
        if (TextUtils.isEmpty(pincode.getText())) {
            pincode.setError("Field is empty");
            focusView = pincode;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

                    serviceTask = new ServiceSaveUserDetail(fullName.getText().toString(), mobile.getText().toString(),
                            aadhar.getText().toString(), address.getText().toString(), state.getText().toString(), pincode.getText().toString());
                    serviceTask.execute();

        }
    }

    private void getUserDetails() {
        showProgress(true);
        User user = new User();
        servicegetUserDetail = new ServiceGetUserDetail(user);
        servicegetUserDetail.execute((Void) null);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private String getUserDetailObject(String fullName, String mobile, String aadhar, String address, String state, String pincode) throws JSONException {
        JSONObject jsonObject = new JSONObject()
                .put("type", "select")
                .put("args", new JSONObject()
                        .put("table", "user")
                        .put("columns", new JSONArray()
                                .put("*")
                        )
                        .put("where", new JSONObject()
                                .put("id", new JSONObject()
                                        .put("$eq", "1")
                                )
                        )
                );
        Log.d(ServiceRequestActivity.class.getSimpleName(), jsonObject.toString());
        return jsonObject.toString();
    }

    private String getUserDetailObject() throws JSONException {
        JSONObject jsonObject = new JSONObject()
                .put("type", "select")
                .put("args", new JSONObject()
                        .put("table", "user")
                        .put("columns", new JSONArray()
                                .put("*")
                        )
                        .put("where", new JSONObject()
                                .put("id", new JSONObject()
                                        .put("$eq", "1")
                                )
                        )
                );
        Log.d(ServiceRequestActivity.class.getSimpleName(), jsonObject.toString());
        return jsonObject.toString();
    }

    private String fillObjectDetailObject(String fullName, String mobile, String aadhar, String address, String state, String pincode) throws JSONException {
        JSONObject jsonObject = new JSONObject()
                .put("type", "insert")
                .put("args", new JSONObject()
                        .put("table", "user")
                        .put("objects", new JSONArray()
                                .put(new JSONObject()
                                        .put("address", "sdds")
                                        .put("state", "adsasd")
                                        .put("pin_code", "6787687")
                                        .put("sex", "adsa")
                                        .put("aadhar_card_no", "690877676667")
                                        .put("mobile", "87879879789")
                                        .put("email", "ads@gmail.com")
                                        .put("name", "Aniket")
                                )
                        )
                        .put("returning", new JSONArray()
                                .put("id")
                        )
                );
        Log.d(ServiceRequestActivity.class.getSimpleName(), jsonObject.toString());
        return jsonObject.toString();
    }

    public class ServiceSaveUserDetail extends AsyncTask<Void, Void, Boolean> {

        private final String mfullName, mmobile, maadhar, maddress, mstate, mpincode;

        ServiceSaveUserDetail(String fullName, String mobile, String aadhar, String address, String state, String pincode) {
            mfullName = fullName;
            mmobile = mobile;
            maadhar = aadhar;
            maddress = address;
            mstate = state;
            mpincode = pincode;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Looper.prepare();
                Thread.sleep(2000);
                body = fillObjectDetailObject(mfullName, mmobile, maadhar, maddress, mstate, mpincode);
                response = requestHandler.makePostRequest("https://data.dunce90.hasura-app.io/v1/query", body);
                Log.d(ServiceRequestActivity.class.getName(), response);
                if (response != null) {
                    Gson gson = new Gson();
//                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
                    Log.d(ServiceRequestActivity.class.getSimpleName(), response);
                    if (response.contains("error")) {
                        AppError erro = gson.fromJson(response, AppError.class);
                        if (erro != null) {
                            if (erro.getError() != null) {
                                Log.d(ServiceRequestActivity.class.getSimpleName(), erro.getError());
                            }
                        }
                        Log.d(ServiceRequestActivity.class.getSimpleName(), response);
                    } else {
                        if (response.contains("affected_rows")) {
                            Log.d(ServiceRequestActivity.class.getSimpleName(), "record added");
                        }
                    }
                }
            } catch (InterruptedException | IOException | JSONException e) {
                return false;
            }
            if (response != null && !response.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            serviceTask = null;
            showProgress(false);
            if (success) {
//                startActivity(new Intent(ServiceRequestActivity.this,MainActivity.class));
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            serviceTask = null;
            showProgress(false);
        }
    }

    public class ServiceGetUserDetail extends AsyncTask<Void, Void, Boolean> {

        private User mUser;

        ServiceGetUserDetail(User user) {
            mUser = new User();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Looper.prepare();
                // Simulate network access.
                Thread.sleep(2000);
                body = getUserDetailObject();
                response = requestHandler.makePostRequest("https://data.dunce90.hasura-app.io/v1/query", body);
                Log.d(ServiceRequestActivity.class.getName(), response);
                if (response != null) {
                    Gson gson = new Gson();
//                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
                    Log.d(ServiceRequestActivity.class.getSimpleName(), response);
                    if (response.contains("error")) {
                        AppError erro = gson.fromJson(response, AppError.class);
                        if (erro != null) {
                            if (erro.getError() != null) {
                                Log.d(ServiceRequestActivity.class.getSimpleName(), erro.getError());
                            }
                        }
                        Log.d(ServiceRequestActivity.class.getSimpleName(), response);
                    } else {
                        JSONArray res = new JSONArray(response);
                        if ((res != null) && (res.get(0) != null)) {
                            User user = gson.fromJson(res.get(0).toString(), User.class);
                            fullName.setText(user.getName());
                            mobile.setText(user.getMobile());
                            aadhar.setText(user.getAadhar_card_no());
                            address.setText(user.getAddress());
                            state.setText(user.getState());
                            pincode.setText(user.getPin_code());
                        }
                        Log.d(ServiceRequestActivity.class.getSimpleName(), "record added");
                    }
                }
            } catch (InterruptedException | IOException e) {
                return false;
            } catch (JSONException e) {
                return false;
            }
            if (response != null && !response.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            serviceTask = null;
            showProgress(false);
            if (success) {
//                startActivity(new Intent(ServiceRequestActivity.this,MainActivity.class));
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            serviceTask = null;
            showProgress(false);
        }
    }

    public class ServiceGetUserCount extends AsyncTask<Void, Void, Boolean> {

        private final String mfullName, mmobile, maadhar, maddress, mstate, mpincode;

        ServiceGetUserCount(String fullName, String mobile, String aadhar, String address, String state, String pincode) {
            mfullName = fullName;
            mmobile = mobile;
            maadhar = aadhar;
            maddress = address;
            mstate = state;
            mpincode = pincode;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Looper.prepare();
                // Simulate network access.
                Thread.sleep(2000);
                JSONObject jsonObject = new JSONObject()
                        .put("type", "count")
                        .put("args", new JSONObject()
                                .put("table", "user")
                                .put("where", new JSONObject()
                                )
                        );
                body = jsonObject.toString();
                response = requestHandler.makePostRequest("https://data.dunce90.hasura-app.io/v1/query", body);
                Log.d(ServiceRequestActivity.class.getName(), response);
                if (response != null) {
                    Gson gson = new Gson();
//                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
                    Log.d(ServiceRequestActivity.class.getSimpleName(), response);
                    if (response.contains("error")) {
                        AppError erro = gson.fromJson(response, AppError.class);
                        if (erro != null) {
                            if (erro.getError() != null) {
                                Log.d(ServiceRequestActivity.class.getSimpleName(), erro.getError());
                            }
                        }
                        Log.d(ServiceRequestActivity.class.getSimpleName(), response);
                    } else {
                        if (response.contains("count")) {
                            JSONObject coun = new JSONObject(response);
                            Log.d(ServiceRequestActivity.class.getSimpleName(), "count: " + coun.getInt("count"));
                        }
                    }
                }
            } catch (InterruptedException | IOException | JSONException e) {
                return false;
            }
            if (response != null && !response.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            serviceTask = null;
            showProgress(false);
            if (success) {
//                startActivity(new Intent(ServiceRequestActivity.this,MainActivity.class));
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            serviceTask = null;
            showProgress(false);
        }
    }

    public class ServiceRequestSubmit extends AsyncTask<Void, Void, Boolean> {


        ServiceRequestSubmit() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Looper.prepare();
                // Simulate network access.
                Thread.sleep(2000);
                body = getUserDetailObject();
                response = requestHandler.makePostRequest("https://data.dunce90.hasura-app.io/v1/query", body);
                Log.d(ServiceRequestActivity.class.getName(), response);
                if (response != null) {
                    Gson gson = new Gson();
//                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
                    Log.d(ServiceRequestActivity.class.getSimpleName(), response);
                    if (response.contains("error")) {
                        AppError erro = gson.fromJson(response, AppError.class);
                        if (erro != null) {
                            if (erro.getError() != null) {
                                Log.d(ServiceRequestActivity.class.getSimpleName(), erro.getError());
                            }
                        }
                        Log.d(ServiceRequestActivity.class.getSimpleName(), response);
                    } else {
                        JSONArray res = new JSONArray(response);
                        if ((res != null) && (res.get(0) != null)) {
                            User user = gson.fromJson(res.get(0).toString(), User.class);
                            fullName.setText(user.getName());
                            mobile.setText(user.getMobile());
                            aadhar.setText(user.getAadhar_card_no());
                            address.setText(user.getAddress());
                            state.setText(user.getState());
                            pincode.setText(user.getPin_code());
                        }
                        Log.d(ServiceRequestActivity.class.getSimpleName(), "record added");
                    }
                }
            } catch (InterruptedException | IOException e) {
                return false;
            } catch (JSONException e) {
                return false;
            }
            if (response != null && !response.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            serviceRequestSubmit = null;
            showProgress(false);
            if (success) {
//                startActivity(new Intent(ServiceRequestActivity.this,MainActivity.class));
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            serviceRequestSubmit = null;
            showProgress(false);
        }
    }

}
