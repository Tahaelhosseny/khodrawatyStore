package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import admin.store.com.httpkhodrawaty.khodrawatystore.Adapter.ImageAdapter;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.Image;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class EditCatInfo extends AppCompatActivity
{


    TextView textView ;
    ImageButton imageButton ;

    EditText editText ;

    String id ;
    String token ;


    RecyclerView recyclerView ;

    ImageAdapter imageAdapter ;
    private List<Image> imageList;



    String cat_image ;
    String cat_id;
    String cat_name;
    String cat_isAvi;


    String name ="";

    RadioButton active;
    RadioButton notActive;
    RadioGroup radioGroup ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cat_info);

        cat_id = getIntent().getStringExtra("id");
        cat_image = getIntent().getStringExtra("image");
        cat_name = getIntent().getStringExtra("name");
        cat_isAvi = getIntent().getStringExtra("availabilty");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        init();
    }

    private void init ()
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
        textView.setText("تعديل الاقسام ");
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        editText = (EditText) findViewById(R.id.title);
        editText.setText(cat_name);

        active = (RadioButton) findViewById(R.id.active);
        notActive = (RadioButton) findViewById(R.id.not_active);
        radioGroup =(RadioGroup) findViewById(R.id.avilabilty);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if(checkedId == R.id.active)
                {
                    cat_isAvi = "1" ;
                }
                else if(checkedId == R.id.not_active)
                {
                    cat_isAvi = "0" ;
                }
            }
        });

        if(cat_isAvi.equals("1"))
        {
            active.setChecked(true);
        }
        else
        {
            notActive.setChecked(true);
        }


        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(getApplicationContext() , imageList);
        recyclerView = (RecyclerView) findViewById(R.id.rec_image);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.notifyDataSetChanged();
        request();
    }

    public void saveEdits(View view)
    {
        if(!editText.getText().toString().isEmpty())
        {
            for(int i = 0 ; i < imageList.size() ; i++)
            {
                if(imageList.get(i).getChecked())
                {
                    name = imageList.get(i).getServerName();
                    update();
                    break;
                }
            }
            if(name.isEmpty())
            {
                Toast.makeText(getApplicationContext() ,"من فضلك قم بأختيار صوره القسم " ,Toast.LENGTH_SHORT).show();
            }
        }
        else
            {
                editText.setError("من فضلك ادخل اسم القسم");
            }
    }

    private void request()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);




        MakeRequest makeRequest = new MakeRequest("/Requests/get_images", "1", params, this);

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
                            request();
                        }
                        else if(status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("ok"))
                        {
                            String data = jsonObject.getString("data");
                            JSONArray jsonArray = new JSONArray(data);

                            for (int i = 0 ; i<jsonArray.length() ; i++)
                            {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Image imageModel = new Image(jsonObject1.getString("name"),jsonObject1.getString("image"));
                                if(imageModel.getImage().equals(cat_image))
                                    imageModel.setChecked(true);
                                imageList.add(imageModel);

                            }
                            imageAdapter.notifyDataSetChanged();
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



    private void update ()
    {

        for (int i = 0 ; i < imageList.size() ; i++)
        {
            if(imageList.get(i).getChecked())
            {
                name = imageList.get(i).getServerName();
                break;
            }
        }





        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("name", editText.getText().toString());
        params.put("image", name);
        params.put("category_id",cat_id);
        params.put("available" , cat_isAvi);

        MakeRequest makeRequest = new MakeRequest("/Requests/edit_category", "1", params, this);

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
                            request();
                        }
                        else if(status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("ok"))
                        {
                            Toast.makeText(getApplicationContext() , "تم تعديل القسم بنجاح" , Toast.LENGTH_LONG).show();
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
