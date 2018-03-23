package admin.store.com.httpkhodrawaty.khodrawatystore;

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
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.WeightModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class NewItem extends AppCompatActivity
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



    RecyclerView recyclerView ;

    ImageAdapter imageAdapter ;
    private List<Image> imageList;


    AppCompatSpinner weight ;
    AppCompatSpinner cat;
    private List<WeightModel> weightModels;
    private List<CategoryModel> categoryModels;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);



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
        textView.setText("اضافه صنف جديد  ");
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

    public void addNew(View view)
    {

        int x = 0;

        str_title = title.getText().toString();
        str_details = details.getText().toString();
        str_price = price.getText().toString();
        List<Image> temp = imageAdapter.getList();
        int item = -1;

        for (int i = 0 ; i < temp.size(); i ++)
        {
            if(temp.get(i).getChecked())
            {
                item = i;
                break;
            }
        }

        if(item!=-1)
        {
            if(str_title.isEmpty())
            {
                title.setError("من فضلك ادخل اسم الصنف");
                x++;
            }

            if(str_price.isEmpty())
            {
                price.setError("من فضلك ادخل سعر الصنف");
                x++;
            }

            if(str_details.isEmpty())
            {
                details.setError("من فضلك ادخل وصف الصنف");
                x++;
            }

            if(cat_id.equals("-1"))
            {
                Toast.makeText(getApplicationContext() , "من فضلك اختار القسم " , Toast.LENGTH_SHORT).show();
                x++;
            }

            if(weight_id.equals("-1"))
            {
                Toast.makeText(getApplicationContext() , "من فضلك وحده الصنف  " , Toast.LENGTH_SHORT).show();
                x++;
            }


            if(x==0)
            {
                saveCat(str_title,temp.get(item).getServerName(),str_price,str_details,cat_id,weight_id);
            }
        }
        else
            Toast.makeText(getApplicationContext() , "plase Chosse Image ",Toast.LENGTH_LONG).show();


    }


   private void saveCat(String name ,String image , String price , String details , String category_id ,String weight_id)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("name", name);
        params.put("image", image);
        params.put("price", price);
        params.put("details", details);
        params.put("category_id", category_id);
        params.put("weight_id", weight_id);


        Toast.makeText(getApplicationContext() , image ,Toast.LENGTH_LONG).show();


        MakeRequest makeRequest = new MakeRequest("/Requests/add_product", "1", params, this);

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
                            Toast.makeText(getApplicationContext() , "تم اضافه القسم الجديد بنجاح " , Toast.LENGTH_LONG).show();
                            title.setText("");


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
        final ArrayAdapter weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightModels);
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
        final ArrayAdapter catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryModels);
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

                } else
                    Toast.makeText(getApplicationContext(), "something go wrong try again", Toast.LENGTH_SHORT).show();

            }
        });

    }



}
