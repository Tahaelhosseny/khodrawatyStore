package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class OpenClose extends AppCompatActivity
{


    TextView textView ;
    ImageButton imageButton ;
    SwitchCompat aSwitch ;
    ImageView imageView ;


    ProgressDialog mProgressDialog ;

    String idd ;
    String tokenn;
    String namee ;
    String openClose = "0";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close);

        mProgressDialog = new ProgressDialog(this);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        init();

        isOpen();
    }


    private void init()
    {

        prefs  = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE);
        editor = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE).edit();
        idd = prefs.getString("id",null);
        tokenn = prefs.getString("token",null);
        namee = prefs.getString("name",null);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.set_title);
        View v = getSupportActionBar().getCustomView();
        textView = (TextView) v.findViewById(R.id.mytext);
        aSwitch = (SwitchCompat) findViewById(R.id.status);
        imageButton = (ImageButton) v.findViewById(R.id.imageButton);
        imageView = (ImageView) findViewById(R.id.status_image);
        textView.setText("فتح او غلق المتجر ");
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });



        aSwitch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(aSwitch.isChecked())
                {
                    imageView.setBackgroundResource(R.drawable.open);
                    openClose = "1";
                }
                else
                {
                    imageView.setBackgroundResource(R.drawable.close);
                    openClose = "0" ;
                }
            }
        });



    }



    public void isOpen()
    {
        mProgressDialog.setTitle("جارى فحص حاله المحل");
        mProgressDialog.show();
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", idd);
        params.put("token", tokenn);
        MakeRequest makeRequest = new MakeRequest("/Requests/is_opened", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                if (result.get("status").toString().contains("ok"))
                {
                    Toast.makeText(getApplicationContext() , result.get("res").toString() , Toast.LENGTH_SHORT).show();

                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res").toString());
                        openClose = jsonObject.getString("opened");

                        if(openClose.equals("1"))
                        {
                            imageView.setBackgroundResource(R.drawable.open);
                            aSwitch.setChecked(true);
                        }
                        else
                        {
                            imageView.setBackgroundResource(R.drawable.close);
                            aSwitch.setChecked(false);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

                mProgressDialog.dismiss();
            }
        });
    }


    public void saveStatus(View view)
    {

        if(openClose.equals("0"))
        {
            mProgressDialog.setTitle("يتم الان اغلاق المحل");
            mProgressDialog.show();
        }
        else
            {
                mProgressDialog.setTitle("يتم الان فتح المحل");
                mProgressDialog.show();
            }
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", idd);
        params.put("token", tokenn);
        params.put("status", openClose);

        MakeRequest makeRequest = new MakeRequest("/Requests/Open_close", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {

                    try {
                        JSONObject jsonObject = new JSONObject(result.get("res"));
                        if(jsonObject.get("status").equals("ok"))
                        {
                            Toast.makeText(getApplicationContext() , "تم تغير الحاله بنجاح " , Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext() , "الحاله مطابقه للسابقه  " , Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

                mProgressDialog.dismiss();
            }
        });
    }





}
