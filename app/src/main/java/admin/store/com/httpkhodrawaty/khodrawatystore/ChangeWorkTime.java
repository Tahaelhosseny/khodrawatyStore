package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class ChangeWorkTime extends Activity
{


    EditText add ;
    String id ;
    String token ;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_work_time);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("جارى تحميل مواعيد العمل");
        init();
    }



    private void  init()
    {
        add = (EditText) findViewById(R.id.add);
        SharedPreferences prefs = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE);
        id = prefs.getString("id",null);
        token = prefs.getString("token",null);

        getData();


    }

    public void changeTime(View view)
    {


        String time = add.getText().toString();

        mProgressDialog.setTitle("جارى حفظ التعديلات");

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("time", time);
        MakeRequest makeRequest = new MakeRequest("/Requests/edit_worktime", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res"));
                        String status = jsonObject.getString("status");
                        if(status.equals("error"))
                        {
                            Toast.makeText(getApplicationContext() , "لم نستطع الوصول للمعلومات حاول مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("ok"))
                        {
                            String res = jsonObject.getString("status");
                            if(res.equals("ok"))
                            {
                                Toast.makeText(getApplicationContext() , "تم تعديل موايد العمل بنجاح" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void getData()
    {
        mProgressDialog.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);   //"Admin@Admin.com"
        params.put("token", token);    //"Password#1"
        final MakeRequest makeRequest = new MakeRequest("/Requests/get_worktime", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    add.setText(result.get("res"));
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res"));
                        String status = jsonObject.getString("status");
                        if(status.equals("error"))
                        {
                            Toast.makeText(getApplicationContext() , "لم نستطع الوصول للمعلومات حاول مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("ok"))
                        {
                            add.setText(jsonObject.getString("data"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mProgressDialog.dismiss();
                } else
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}
