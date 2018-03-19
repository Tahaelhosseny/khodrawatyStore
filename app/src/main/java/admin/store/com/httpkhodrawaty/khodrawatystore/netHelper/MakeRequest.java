package admin.store.com.httpkhodrawaty.khodrawatystore.netHelper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import admin.store.com.httpkhodrawaty.khodrawatystore.Login;

/**
 * Created by Taha on 3/10/2018.
 */

public class MakeRequest
{



    String BaseUrl = "http://khodrawaty.com/" ; //www.example.com

    String Sub_Url ; //.../example/example

    String Type ;    // post Or Get   1 for POST 0 For GET

    String cUrl ;  //Base+Sub

    Map <String , String> map = new HashMap <String , String >() ;  //parameter send to server

    Context context ;

    RequestQueue requestQueue ;

    Map<String , String> Responce = new HashMap<String, String>();







    public MakeRequest(String sub_Url , String Type , Map<String ,String> map,Context context )
    {
        this.Sub_Url =sub_Url ;
        this.Type = Type ;
        this.map = map ;
        this.context = context ;
        cUrl = BaseUrl.concat(Sub_Url);
        requestQueue =Volley.newRequestQueue(context);


    }


    public MakeRequest(String sub_Url , String Type , Context context )
    {
        this.Sub_Url =sub_Url ;
        this.Type = Type ;
        this.map = map ;
        this.context = context ;
        cUrl = BaseUrl.concat(Sub_Url);
        requestQueue =Volley.newRequestQueue(context);
    }



    public void request (final VolleyCallback callback)
    {


        StringRequest stringRequest = new StringRequest(Integer.valueOf(Type),cUrl , new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Responce.put("status" , "ok");
                Responce.put("res" , response);
                callback.onSuccess(Responce);
        }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Responce.put("status" , "not");
                Responce.put("res" , error.toString());
                callback.onSuccess(Responce);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params = map ;
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }


}
