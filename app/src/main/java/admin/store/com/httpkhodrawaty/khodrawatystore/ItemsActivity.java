package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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

import admin.store.com.httpkhodrawaty.khodrawatystore.Adapter.CatAdapter;
import admin.store.com.httpkhodrawaty.khodrawatystore.Adapter.ItemAdapter;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.CategoryModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.ItemModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class ItemsActivity extends AppCompatActivity
{

    String title ;
    String cat_Id;
    TextView textView ;
    ImageButton imageButton;
    String id ;
    String token;

    RecyclerView recyclerView ;

    ItemAdapter itemAdapter ;
    List<ItemModel> itemModels;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        title = getIntent().getStringExtra("name");
        cat_Id = getIntent().getStringExtra("cat_id");

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


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.set_title);
        View v = getSupportActionBar().getCustomView();
        textView = (TextView) v.findViewById(R.id.mytext);
        imageButton = (ImageButton) v.findViewById(R.id.imageButton);
        imageButton.setVisibility(View.VISIBLE);
        textView.setText(title);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        itemModels = new ArrayList<>();
        itemAdapter = new ItemAdapter(getApplicationContext() , itemModels);
        recyclerView = (RecyclerView) findViewById(R.id.cat_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.notifyDataSetChanged();
        request();
    }





    private void request()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("category_id",cat_Id);


        MakeRequest makeRequest = new MakeRequest("/Requests/get_product", "1", params, this);

        makeRequest.request(new VolleyCallback() {
            @Override
            public void onSuccess(Map<String, String> result)
            {
                //mProgressDialog.dismiss();
                if (result.get("status").toString().contains("ok"))
                {
                    Log.e("ggggggggggggg",token +"\n"+result.get("res").toString()+id);

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
                            Toast.makeText(getApplicationContext() , "res " + data , Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0 ; i<jsonArray.length() ; i++)
                            {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                Toast.makeText(getApplicationContext() , "jsonObject1 " + jsonObject1.toString() , Toast.LENGTH_SHORT).show();
                               // ItemModel itemModel = new ItemModel(jsonObject1.getString("id"),jsonObject1.getString("name"),"http://khodrawaty.com/uploads/"+jsonObject1.getString("image")  , jsonObject1.getString("available"), jsonObject1.getString("details"), jsonObject1.getString("weight"),"30");
                                ItemModel itemModel = new ItemModel(jsonObject1.getString("id"),jsonObject1.getString("name"),"http://khodrawaty.com/uploads/"+jsonObject1.getString("image"),jsonObject1.getString("available"),"30");
                                itemModels.add(itemModel);
                            }
                            itemAdapter.notifyDataSetChanged();
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




    public void AddItemActivity(View view)
    {
    }
}
