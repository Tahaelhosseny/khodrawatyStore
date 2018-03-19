package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.app.Activity;
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

public class Login extends Activity
{


    String sub_url = "/ApiController/login";

    EditText email ;
    EditText password;


    String str_email = "" ;
    String str_password = "" ;


    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences prefs = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE);
        String idd = prefs.getString("id",null);
        String tokenn = prefs.getString("token",null);
        String namee = prefs.getString("name",null);

        if(idd!=null&&tokenn!=null&&namee!=null)
        {
            startActivity(new Intent(getApplicationContext() , MainActivity.class));
            finish();
        }
        else init();
    }



    private void  init ()
    {
        email = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        editor = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE).edit();
        editor.putString("token", null);
        editor.putString("id", null);
        editor.putString("name",null);
        editor.apply();
    }


    public void Login(View view) {

        //mProgressDialog.show();

        str_email = email.getText().toString();
        str_password = password.getText().toString();
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", str_email);   //"Admin@Admin.com"
        params.put("password", str_password);    //"Password#1"
        MakeRequest makeRequest = new MakeRequest("/ApiController/login", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res").toString());
                        editor.putString("token", jsonObject.getString("token"));
                        editor.putString("id", jsonObject.getString("id"));
                        editor.putString("name",jsonObject.getString("name"));
                        editor.apply();
                        startActivity(new Intent(getApplicationContext() , MainActivity.class));
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });

    }




}
