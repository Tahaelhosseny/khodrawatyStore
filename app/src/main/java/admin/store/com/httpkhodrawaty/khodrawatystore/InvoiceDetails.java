package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import admin.store.com.httpkhodrawaty.khodrawatystore.Adapter.InvoiceProductAdapter;
import admin.store.com.httpkhodrawaty.khodrawatystore.Adapter.OrderAdapter;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.InvoiceProduct;
import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.OrderModel;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.MakeRequest;
import admin.store.com.httpkhodrawaty.khodrawatystore.netHelper.VolleyCallback;

public class InvoiceDetails extends AppCompatActivity
{


    String id;
    String token;


    String strStatus ;
    int intStatus ;


    Button button ;
    Button cancel ;
    Intent intent ;
    TextView invoice ;
    TextView name ;
    TextView phone ;
    TextView date;
    TextView price;

    String invoiceId;

    int next ;


    RecyclerView recyclerView ;

    InvoiceProductAdapter invoiceProductAdapter ;
    private List<InvoiceProduct> invoiceProducts;


    TextView textView ;
    ImageButton imageButton ;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_details);


        intent = getIntent();



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
        textView.setText("الفاتوره");
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        invoiceProducts = new ArrayList<>();
        invoiceProductAdapter = new InvoiceProductAdapter( this, invoiceProducts);
        recyclerView = (RecyclerView) findViewById(R.id.rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(invoiceProductAdapter);
        invoiceProductAdapter.notifyDataSetChanged();
        button = (Button) findViewById(R.id.change);
        cancel = (Button) findViewById(R.id.cancel);
        invoice = (TextView) findViewById(R.id.invoice);
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        date = ( TextView) findViewById(R.id.date);
        price = (TextView) findViewById(R.id.price);
        getItems();


    }



    private void getItems()
    {
        String str = intent.getStringExtra("product");
        strStatus = intent.getStringExtra("status");
        intStatus = Integer.valueOf(strStatus);
        price.setText(intent.getStringExtra("price" )+ " ريال سعودى ");
        invoice.setText(intent.getStringExtra("id")+"#");
        invoiceId = intent.getStringExtra("id");
        name.setText(intent.getStringExtra("name"));
        phone.setText(intent.getStringExtra("phone"));
        date.setText(intent.getStringExtra("time"));
        if(strStatus.equals("0"))
        {
            button.setText("تحت التجهيز");
        }
        else if(strStatus.equals("1"))
        {
            button.setText("قيد التوصيل");
        }
        else if(strStatus.equals("2"))
        {
            button.setText("اكتملت");
        }
        else if(strStatus.equals("3"))
        {
            button.setVisibility(View.GONE);
        }
        else if(strStatus.equals("4"))
        {
            button.setText("4");
            button.setVisibility(View.GONE);
            cancel.setVisibility(View.GONE);
        }

        try {
            JSONArray jsonArray = new JSONArray(str);
            for (int i =0 ; i < jsonArray.length() ; i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                InvoiceProduct invoiceProduct = new InvoiceProduct(jsonObject.getString("name") , jsonObject.getString("quantity") ,jsonObject.getString("price"));
                invoiceProducts.add(invoiceProduct);
                Toast.makeText(getApplicationContext() , jsonObject.toString() , Toast.LENGTH_SHORT).show();
            }

            invoiceProductAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext() , str , Toast.LENGTH_SHORT).show();
    }





    private void request()
    {
        /*if(button.getText().toString().equals("تحت التجهيز"))
        {
            strStatus ="1";
            button.setText("قيد التوصيل");
        }
        else if(button.getText().toString().equals("قيد التوصيل"))
        {
            strStatus ="2";
            button.setText("اكتملت");
        }
       else if(button.getText().toString().equals("اكتملت"))
        {
            strStatus ="3";
            //button.setText("اكتملت");
            button.setVisibility(View.GONE);
        }
        else if(strStatus.equals("3"))
        {
            strStatus ="4";
            //cancel.setVisibility(View.GONE);
        }*/

        next =Integer.valueOf(strStatus)+1;
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("status" ,String.valueOf(next));
        params.put("order_id" , invoiceId);


        MakeRequest makeRequest = new MakeRequest("/Requests/order_status", "1", params, this);

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
                            Toast.makeText(getApplicationContext() , "لم يتم تغير الحاله الرجاء اعاده المحاوله " , Toast.LENGTH_LONG).show();

                        }
                        else if(status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("ok"))
                        {
                            if(button.getText().toString().equals("تحت التجهيز"))
                            {
                                strStatus ="1";
                                button.setText("قيد التوصيل");
                            }
                            else if(button.getText().toString().equals("قيد التوصيل"))
                            {
                                strStatus ="2";
                                button.setText("اكتملت");
                            }
                            else if(button.getText().toString().equals("اكتملت"))
                            {
                                strStatus ="3";
                                //button.setText("اكتملت");
                                button.setVisibility(View.GONE);
                            }
                            else if(strStatus.equals("3"))
                            {
                                strStatus ="4";
                                //cancel.setVisibility(View.GONE);
                            }

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


    private void cancellll()
    {
        /*if(button.getText().toString().equals("تحت التجهيز"))
        {
            strStatus ="1";
            button.setText("قيد التوصيل");
        }
        else if(button.getText().toString().equals("قيد التوصيل"))
        {
            strStatus ="2";
            button.setText("اكتملت");
        }
       else if(button.getText().toString().equals("اكتملت"))
        {
            strStatus ="3";
            //button.setText("اكتملت");
            button.setVisibility(View.GONE);
        }
        else if(strStatus.equals("3"))
        {
            strStatus ="4";
            //cancel.setVisibility(View.GONE);
        }*/

        next =Integer.valueOf(strStatus)+1;
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("token", token);
        params.put("status" ,"4");
        params.put("order_id" , invoiceId);


        MakeRequest makeRequest = new MakeRequest("/Requests/order_status", "1", params, this);

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
                            Toast.makeText(getApplicationContext() , "لم يتم تغير الحاله الرجاء اعاده المحاوله " , Toast.LENGTH_LONG).show();

                        }
                        else if(status.equals("unauthorized"))
                        {
                            Toast.makeText(getApplicationContext() , "الرجاء تسجيل الخروج ومن ثم اعاده تسجيل الدخول ومحاوله التغير مره اخرى " , Toast.LENGTH_LONG).show();
                        }
                        else if(status.equals("ok"))
                        {
                            Toast.makeText(getApplicationContext() , "تم الغاء الطلب ", Toast.LENGTH_SHORT).show();
                            cancel.setVisibility(View.GONE);
                            button.setVisibility(View.GONE);
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


    public void changeStatus(View view)
    {
        request();
    }

    public void cancell(View view)
    {
        cancellll();

    }
}
