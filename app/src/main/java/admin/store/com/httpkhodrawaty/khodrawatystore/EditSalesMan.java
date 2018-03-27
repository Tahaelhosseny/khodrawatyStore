package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.CityModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.SalesManModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class EditSalesMan extends AppCompatActivity
{
    String id;
    String token ;
    List<CityModel> cityModels ;

    TextView textView ;
    ImageButton imageButton ;

    AppCompatSpinner cites;
    ArrayAdapter citesArrayAdapter;

    String city_id;


    EditText namee ;
    EditText email ;
    EditText phone ;
    EditText password ;
    EditText cPassword ;



    String str_namee;
    String str_email ;
    String str_phone ;
    String str_password ;
    String str_cPassword ;


    SalesManModel salesManModel = new SalesManModel() ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sales_man);
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        init();
        setData();
        setCity();

    }


    private void setData()
    {
        Intent intent = getIntent();
        salesManModel.setName(intent.getStringExtra("name"));
        salesManModel.setCity(intent.getStringExtra("city"));
        salesManModel.setCity_id(intent.getStringExtra("city_id"));
        salesManModel.setEmail(intent.getStringExtra("email"));
        salesManModel.setPassword(intent.getStringExtra("password"));
        salesManModel.setPhone(intent.getStringExtra("phone"));
        salesManModel.setId(intent.getStringExtra("id"));


        namee.setText(salesManModel.getName());
        email.setText(salesManModel.getEmail());
        phone.setText(salesManModel.getPhone());
        password.setText(salesManModel.getPassword());
        cPassword.setText(salesManModel.getPassword());





    }


    private void init()
    {

        SharedPreferences prefs = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE);
        id = prefs.getString("id",null);
        token = prefs.getString("token",null);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.set_title);
        View v = getSupportActionBar().getCustomView();
        textView = (TextView) v.findViewById(R.id.mytext);
        imageButton = (ImageButton) v.findViewById(R.id.imageButton);
        imageButton.setVisibility(View.VISIBLE);
        textView.setText("تعديل بيانات المندوب ");
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        namee = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        cPassword = (EditText) findViewById(R.id.cpassword);


    }



    private void setCity()
    {
        cites = (AppCompatSpinner) findViewById(R.id.city);
        cityModels = new ArrayList<>();
        cityModels.add(new CityModel("-1","أختر مدينه"));
        citesArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cityModels);
        citesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cites.setAdapter(citesArrayAdapter);
        cites.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                city_id = cityModels.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });




        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        MakeRequest makeRequest = new MakeRequest("/Requests/get_location", "1", params, this);
        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    try {
                        JSONArray jsonArray = new JSONArray(result.get("res").toString());
                        for (int i = 0 ; i<jsonArray.length() ; i++)
                        {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            CityModel cityModel = new CityModel(jsonObject1.getString("id"),jsonObject1.getString("name"));
                            cityModels.add(cityModel);
                        }
                        for (int i = 0 ; i < cityModels.size() ; i++ )
                        {
                            if(cityModels.get(i).getId().equals(salesManModel.getCity_id()))
                            {
                                cites.setSelection(i);
                                citesArrayAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                        citesArrayAdapter.notifyDataSetChanged();

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });




    }





    public void edit(View view)
    {
        String str_cityId =cityModels.get(cites.getSelectedItemPosition()).getId();

        str_namee = namee.getText().toString();
        str_email = email.getText().toString();
        str_phone = phone.getText().toString();
        str_password = password.getText().toString();
        str_cPassword = cPassword.getText().toString();




        int x = 0 ;
        if(str_cityId.equals("-1"))
        {
            x++;
            Toast.makeText(getApplicationContext() , "من فضلك قم بأختيار المدينه" , Toast.LENGTH_SHORT).show();
        }

        if(str_namee.isEmpty())
        {
            x++;
            namee.setError("ادخل اسم المندوب");
        }
        if(str_email.isEmpty())
        {
            x++;
            email.setError("ادخل البريد الالكترونى");
        }
        if(str_phone.isEmpty())
        {
            x++;
            phone.setError("ادخل رقم الهاتف");
        }
        if(str_password.isEmpty())
        {
            x++;
            password.setError("ادخل الرقم السرى");
        }
        if(str_cPassword.isEmpty())
        {
            x++;
            cPassword.setError("تأكيد الرقم السرى");
        }
        if(!str_cPassword.equals(str_password))
        {
            x++;
            cPassword.setError("كلمتا السر غير منتبقطان");
            password.setError("كلمتا السر غير منتبقطان" );
        }
        if(x==0)
        {
            add(str_namee , str_email , str_phone , str_password , str_cPassword);
        }
    }



    private void  add(String str_namee , String str_email, String str_phone, String str_password, String str_cityId)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("name", str_namee);
        params.put("email", str_email);
        params.put("password", str_phone);
        params.put("phone", str_password);
        params.put("city_id", city_id);
        params.put("user_id" , salesManModel.getId());

        MakeRequest makeRequest = new MakeRequest("/Requests/edit_salesman", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res").toString());
                        String status = jsonObject.get("status").toString();
                        if(status.equals("error"))
                        {
                            Toast.makeText(getApplicationContext() , " لقد وقعت مشكله الرجاء اعاده المحاوله" , Toast.LENGTH_SHORT).show();
                        }

                        else if(status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("ok"))
                        {
                            Toast.makeText(getApplicationContext() , "تم تعديل بينات المندوب بنجاح " , Toast.LENGTH_LONG).show();


                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
