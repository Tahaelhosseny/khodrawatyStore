package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class ChangePassword extends Activity
{


    EditText oldPassword ;
    EditText newPassword ;
    EditText cNewPassword;


    String str_oldPassword ;
    String str_newPassword ;
    String str_cNewPassword;



    String id ;
    String token ;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);




    }


    @Override
    protected void onResume()
    {
        super.onResume();

        init();
    }


    private void init()
    {

        SharedPreferences prefs = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE);
        id = prefs.getString("id",null);
        token = prefs.getString("token",null);

        oldPassword = (EditText) findViewById(R.id.oldpass);
        newPassword = (EditText) findViewById(R.id.newpass);
        cNewPassword = (EditText) findViewById(R.id.cnewpass);
    }


    public void changePassword(View view)
    {
        str_oldPassword = oldPassword.getText().toString();

        str_newPassword = newPassword.getText().toString();

        str_cNewPassword = cNewPassword.getText().toString();


        if(str_oldPassword.isEmpty())
        {
            oldPassword.setError("هذا حقل مطلوب");
        }
        else if(str_newPassword.isEmpty())
        {
            newPassword.setError("هذا حقل مطلوب");
        }
        else if(str_cNewPassword.isEmpty())
        {
            cNewPassword.setError("هذا حقل مطلوب");
        }
        else if (!str_newPassword.equals(str_cNewPassword))
        {
            newPassword.setError("كلمتا المرور لا تنطبقان");
            cNewPassword.setError("كلمتا المرور لا تنطبقان");

        }
        else if(str_newPassword.equals(str_oldPassword))
        {
            newPassword.setError("كلمتا المرور لا يجب ان تنطبقان");
            oldPassword.setError("كلمتا المرور لا يجب ان تنطبقان");
        }
        else if (id.isEmpty()||token.isEmpty())
        {
           Toast.makeText(getApplicationContext() , "حاول تسجيل الخروج ثم تسجيل الدخول مره اخرى " ,Toast.LENGTH_LONG).show();

        }
        else
            {
                request ();
            }
    }

    private void request()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("newpassword", str_newPassword);
        params.put("oldpassword", str_oldPassword);

        Toast.makeText(getApplicationContext() ,params.get("oldpassword"),Toast.LENGTH_LONG).show();


        MakeRequest makeRequest = new MakeRequest("Requests/resetpassword", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res").toString());
                        String status = jsonObject.getString("status");
                        if(status.equals("ok"))
                        {
                            Toast.makeText(getApplicationContext() , "تم تغير كلمه المرور بنجاح" , Toast.LENGTH_LONG).show();
                            finish();
                        }else if (status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                            finish();
                        }else if (status.equals("password"))
                        {
                            Toast.makeText(getApplicationContext() , "رقم المرور الحالى غير صحيح \n ادخل الرقم الصحيح وحاول مره اخرى  " , Toast.LENGTH_LONG).show();
                            finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
