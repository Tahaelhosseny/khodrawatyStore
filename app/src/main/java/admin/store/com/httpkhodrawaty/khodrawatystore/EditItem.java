package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.CategoryModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.Image;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.ItemModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.WeightModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class EditItem extends AppCompatActivity
{


    TextView textView ;
    ImageButton imageButton ;
    EditText title ;
    EditText price;
    EditText details;
    String id ;
    String token ;
    String str_title;
    String str_price;
    String str_details;
    String cat_id="-1";
    String weight_id="-1";
    String available ;
    String product_id;
    String image;
    RadioButton active;
    RadioButton notActive;
    RadioGroup radioGroup ;
    RecyclerView recyclerView ;
    ImageAdapter imageAdapter ;
    private List<Image> imageList;
    AppCompatSpinner weight ;
    AppCompatSpinner cat;
    private List<WeightModel> weightModels;
    private List<CategoryModel> categoryModels;
    ArrayAdapter weightAdapter;
    ArrayAdapter catAdapter;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        intent = getIntent();
        setValues(intent);
        init();



    }


    private void setValues(Intent intent)
    {
        product_id =intent.getStringExtra("id");
        str_title =intent.getStringExtra("name");
        available = intent.getStringExtra("avilable");
        str_price = intent.getStringExtra("price");
        str_details = intent.getStringExtra("details");
        image     =     intent.getStringExtra("serverName");
        weight_id = intent.getStringExtra("weight");


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
        textView.setText("تعديل الاصناف ");
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        title = (EditText) findViewById(R.id.title);
        price = (EditText) findViewById(R.id.price);
        details = (EditText) findViewById(R.id.details);
        imageList = new ArrayList<>();
        imageAdapter = new ImageAdapter(getApplicationContext() , imageList);
        recyclerView = (RecyclerView) findViewById(R.id.cat_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        imageAdapter.notifyDataSetChanged();
        active = (RadioButton) findViewById(R.id.active);
        notActive = (RadioButton) findViewById(R.id.not_active);
        radioGroup =(RadioGroup) findViewById(R.id.avilabilty);
        title.setText(str_title);
        price.setText(str_price);
        details.setText(str_details);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if(checkedId == R.id.active)
                {
                    available = "1" ;
                }
                else if(checkedId == R.id.not_active)
                {
                    available = "0" ;
                }
            }
        });

        if(available.equals("1"))
        {
            active.setChecked(true);
        }
        else
        {
            notActive.setChecked(true);
        }
        request();

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
                                if(imageModel.getServerName().equals(image))
                                    imageModel.setChecked(true);
                                imageList.add(imageModel);
                            }
                            imageAdapter.notifyDataSetChanged();
                            setCat();

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
    private void setWeight()
    {
        weight = (AppCompatSpinner) findViewById(R.id.weight);
        weightModels = new ArrayList<>();
        weightModels.add(new WeightModel("-1","أختر الوحده"));
        weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightModels);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weight.setAdapter(weightAdapter);
        weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                weight_id = weightModels.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);

        MakeRequest makeRequest = new MakeRequest("/Requests/get_weight", "1", params, this);
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
                                WeightModel categoryModel = new WeightModel(jsonObject1.getString("id"),jsonObject1.getString("name"));
                                weightAdapter.add(categoryModel);
                            }
                            weightAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    weight_id = intent.getStringExtra("weight");
                    for (int x = 0 ; x<weightModels.size() ;x++)
                    {
                        if(weightModels.get(x).getName().equals(weight_id))
                        {
                            weight.setSelection(x);
                            break;
                        }
                    }
                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });

    }



    private void setCat()
    {
        cat = (AppCompatSpinner) findViewById(R.id.cat);
        categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel("-1","أختر القسم"));
        catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryModels);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(catAdapter);
        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                cat_id = categoryModels.get(position).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });




        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        cat_id = intent.getStringExtra("cat");

        MakeRequest makeRequest = new MakeRequest("/Requests/get_category", "1", params, this);
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
                                CategoryModel categoryModel = new CategoryModel(jsonObject1.getString("id"),jsonObject1.getString("name"),"http://khodrawaty.com/uploads/"+jsonObject1.getString("image")  , jsonObject1.getString("available"));
                                categoryModels.add(categoryModel);
                            }
                            catAdapter.notifyDataSetChanged();
                            setWeight();
                        }
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                    cat_id = intent.getStringExtra("cat");
                    for (int x = 0 ; x<categoryModels.size() ;x++)
                    {
                        if(categoryModels.get(x).getId().equals(cat_id))
                        {
                            cat.setSelection(x);

                            break;
                        }
                    }

                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void saveUpdates(View view)
    {
        str_title = title.getText().toString() ;
        str_details = details.getText().toString();
        str_price = price.getText().toString();
        weight_id = weightModels.get((int)weight.getSelectedItemId()).getId();
        cat_id = categoryModels.get((int)cat.getSelectedItemId()).getId();
        saveCat(str_title , image ,str_price , str_details , cat_id ,weight_id );

    }


    private void saveCat(String name ,String image , String pricee , String detailss , String category_id ,String weight_id)
    {

        for (int i = 0 ; i <imageList.size() ; i ++)
        {
            if(imageList.get(i).getChecked())
            {
                image = imageList.get(i).getServerName();
            }
        }

        int x = 0;

        if(name.isEmpty())
        {
            x++;
            title.setError("ادخل اسم الصنف ");
        }
        if(pricee.isEmpty())
        {
            x++;
            price.setError("ادخل اسم الصنف ");
        }
        if(detailss.isEmpty())
        {
            x++;
            details.setError("ادخل اسم الصنف ");
        }


        if(cat_id.equals("-1"))
        {
            x++;
            Toast.makeText(getApplicationContext() ,  "قم بأختيار القسم " , Toast.LENGTH_SHORT).show();
        }


        if(weight_id.equals("-1"))
        {
            x++;
            Toast.makeText(getApplicationContext() ,  "قم بأختيار الوحده " , Toast.LENGTH_SHORT).show();
        }

        if(image.isEmpty())
        {
            x++;
            Toast.makeText(getApplicationContext() ,  "قم بأختيار صوره " , Toast.LENGTH_SHORT).show();
        }

        if(x==0)
        {
            Map<String, String> params = new HashMap<String, String>();
            params.put("id", id);
            params.put("token", token);
            params.put("name", name);
            params.put("image", image);
            params.put("price", pricee);
            params.put("details", detailss);
            params.put("category_id", cat_id);
            params.put("weight_id", weight_id);
            params.put("product_id", product_id);
            params.put("available", available);
            Toast.makeText(getApplicationContext() , image ,Toast.LENGTH_LONG).show();


            MakeRequest makeRequest = new MakeRequest("/Requests/edit_product", "1", params, this);

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
                            }
                            else if(status.equals("unauthorized"))
                            {
                                Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                            }
                            else if(status.equals("ok"))
                            {
                                Toast.makeText(getApplicationContext() , "تم تعديل الصنف بنجاح " , Toast.LENGTH_LONG).show();
                                // title.setText("");


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
}
