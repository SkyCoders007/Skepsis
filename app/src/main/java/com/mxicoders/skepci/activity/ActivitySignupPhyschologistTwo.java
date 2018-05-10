package com.mxicoders.skepci.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mxicoders.skepci.R;
import com.mxicoders.skepci.model.Category;
import com.mxicoders.skepci.network.CommanClass;
import com.mxicoders.skepci.network.Const;
import com.mxicoders.skepci.utils.AndyUtils;
import com.mxicoders.skepci.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ActivitySignupPhyschologistTwo extends AppCompatActivity {

    String id;
    CommanClass cc;
    TextView tvLanguage;
    EditText edCpfno, edCrpno, edAddress, edCity, edApproach;

    int abc;
    TextView tvNextAgree;
    Spinner spinnerState;

    String convertMale, convertFemale;

    String name, email, pass, cpass;

    String crp_no, cpf_no, address, city, approach;

  /*  String convertDay;
    String convertMonth;
    String convertYear;*/

    String dateInString;

    String gender = "";

    String[] statename = {};
    JSONObject jsonobject;
    JSONArray jsonarray;
    ProgressDialog pDialog;
    ArrayList<Category> world;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_physchologist_two);


        cc = new CommanClass(ActivitySignupPhyschologistTwo.this);

        try {
            name = getIntent().getStringExtra("name");
            email = getIntent().getStringExtra("email");
            pass = getIntent().getStringExtra("password");
            dateInString = getIntent().getStringExtra("b_date");
           /* convertDay = getIntent().getStringExtra("b_day");
            convertMonth = getIntent().getStringExtra("b_month");
            convertYear = getIntent().getStringExtra("b_year");*/
            gender = getIntent().getStringExtra("gender");

           // printDateInMyFormat(convertDay, convertMonth, convertYear);


            Log.i("@@data", name.toString());
            Log.i("@@data", email.toString());
            Log.i("@@data", pass.toString());
            Log.i("@@data", gender.toString());
           /* Log.i("@@data", convertDay.toString());
            Log.i("@@data", convertMonth.toString());
            Log.i("@@data", convertYear.toString());*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        edCrpno = (EditText) findViewById(R.id.ed_crp_no);
        edCpfno = (EditText) findViewById(R.id.ed_cpf_no);
        edAddress = (EditText) findViewById(R.id.ed_address);
        edCity = (EditText) findViewById(R.id.ed_city);
        edApproach = (EditText) findViewById(R.id.ed_approach);

        tvNextAgree = (TextView) findViewById(R.id.tv_next_agree);

        spinnerState = (Spinner) findViewById(R.id.sp_state);

        getState();


//        stateArrayAdapter = new ArrayAdapter<Category>(getApplicationContext(), R.layout.custom_state_spinner, states);

//        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        tvNextAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignUpValidation();
                //printDateInMyFormat(convertDay, convertMonth, convertYear);
            }
        });



    }

    private void getState() {

        StringRequest jsonObjReq = new StringRequest(Request.Method.GET,Const.ServiceType.GET_STATE,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("200")) {

                                cc.showToast(jsonObject.getString("message"));
                                JSONArray jsonArray = jsonObject.optJSONArray("state_data");
                                final String[] items = new String[jsonArray.length()];
                                //Iterate the jsonArray and print the info of JSONObjects
                                for(int i=0; i < jsonArray.length(); i++){
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                                    String name = jsonObject1.optString("state_name").toString();
                                    id = jsonObject1.optString("id").toString();
                                    items[i]=jsonObject1.getString("state_name");


                                    Log.d("nameee", name);
                                    Log.d("id", id);
                                }
                                adapter= new ArrayAdapter<String>(ActivitySignupPhyschologistTwo.this,  android.R.layout.simple_spinner_item, items);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerState.setAdapter(adapter);
                            } else {

                                jsonObject.getString("message");

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Login Error",e.toString());
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                AndyUtils.showToast(ActivitySignupPhyschologistTwo.this,getString(R.string.ws_error));
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();


                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("user_token", cc.loadPrefString("user_token"));
                headers.put("Authorization", cc.loadPrefString("Authorization"));
                Log.i("request header", headers.toString());

                Log.i("token",cc.loadPrefString("user_token"));
                return headers;
            }


        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");

    }

    /*public void printDateInMyFormat(String date, String month, String year) {
        // This is the format you are requesting for.
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        dateInString = date + "-" + month + "-" + year;
        System.out.println("dateInString : " + dateInString);

        try {

            Date dt = formatter.parse(dateInString);
            System.out.println("My Date2::" + dt);
            System.out.println("My Required Date String2 :: " + formatter.format(dt));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/

    private void SignUpValidation() {

        crp_no = edCrpno.getText().toString().trim();
        cpf_no = edCpfno.getText().toString().trim();
        address = edAddress.getText().toString().trim();
        city = edCity.getText().toString().trim();
        approach = edApproach.getText().toString();

        if (!cc.isConnectingToInternet()) {
            AndyUtils.showToast(ActivitySignupPhyschologistTwo.this, getString(R.string.no_internet));
        } else if (crp_no.equals("")) {
            AndyUtils.showToast(ActivitySignupPhyschologistTwo.this, getString(R.string.crp));

            edCrpno.requestFocus();
        } else if (cpf_no.equals("")) {
            AndyUtils.showToast(ActivitySignupPhyschologistTwo.this, getString(R.string.cpf));

            edCpfno.requestFocus();
        } else if (address.equals("")) {
            AndyUtils.showToast(ActivitySignupPhyschologistTwo.this, getString(R.string.address));

            edAddress.requestFocus();
        } else if (city.equals("")) {
            AndyUtils.showToast(ActivitySignupPhyschologistTwo.this, getString(R.string.city));

            edCity.requestFocus();
        } else if (approach.equals("")) {
            AndyUtils.showToast(ActivitySignupPhyschologistTwo.this, getString(R.string.approach));

            edApproach.requestFocus();
        } else {


            goToNext(name, email, pass, dateInString,crp_no,
                    cpf_no, address, city,id,approach,gender);

            //printDateInMyFormat(convertDay, convertMonth, convertYear);
        }
    }

    private void goToNext(String name, String email, String pass, String dateInString,
                          String crp_no, String cpf_no, String address, String city,
                          String id, String approach, String gender) {

        Intent inn = new Intent(ActivitySignupPhyschologistTwo.this,ActivitySignupPhyschologistThree.class);
        inn.putExtra("name", name)
                .putExtra("email", email)
                .putExtra("password", pass)
                .putExtra("birth_date",dateInString)
                .putExtra("crp_no",crp_no)
                .putExtra("cpf_no",cpf_no)
                .putExtra("address",address)
                .putExtra("city",city)
                .putExtra("state_id",id)
                .putExtra("approach",approach)
                .putExtra("gender",gender);
        inn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        inn.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        inn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        inn.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(0,0);
        startActivity(inn);

        /*startActivity(new Intent(ActivitySignupPhyschologistTwo.this, ActivitySignupPhyschologistThree.class)
                .putExtra("name", name)
                .putExtra("email", email)
                .putExtra("password", pass)
                .putExtra("birth_date",dateInString)
                .putExtra("crp_no",crp_no)
                .putExtra("cpf_no",cpf_no)
                .putExtra("address",address)
                .putExtra("city",city)
                .putExtra("state_id",id)
                .putExtra("approach",approach)
                .putExtra("gender",gender));*/




    }

    /*private void makeJsonSignup(final String name, final String email, final String pass, final String dateInString,
                                final String crp_no, final String cpf_no,
                                final String address, final String city, final String approach) {

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, Const.ServiceType.REGISTER2,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("response:signup", response);

                        jsonParseSignup(response);
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                AndyUtils.showToast(ActivitySignupPhyschologistTwo.this, getString(R.string.ws_error));
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("name", name);
                params.put("email", email);
                params.put("password", pass);
                params.put("birth_date", dateInString);

                params.put("gender",gender);

                params.put("city", city);
                params.put("state_id", id);
                params.put("device_id", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
                params.put("device_type", "Android");
                params.put("crp_number", crp_no);
                params.put("cpf_number", cpf_no);
                params.put("address", address);
                params.put("approach", approach);

                Log.i("request signup", params.toString());

                return params;
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                headers.put(Const.Params.Authorization, Const.Params.Authorization_value);
                Log.i("request header", headers.toString());
                return headers;
            }


        };

        AppController.getInstance().addToRequestQueue(jsonObjReq, "Temp");


    }

    private void jsonParseSignup(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            pDialog.dismiss();
            if (jsonObject.getString("status").equals("200")) {

                cc.showToast(jsonObject.getString("message"));

                Intent tv_log = new Intent(ActivitySignupPhyschologistTwo.this, ActivitySignupPhyschologistThree.class);
                startActivity(tv_log);
                finish();

            } else {

                cc.showToast(jsonObject.getString("message"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Login Error", e.toString());
        }
    }*/

}