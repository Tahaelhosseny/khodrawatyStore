package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class NewOrders extends AppCompatActivity
{

    TextView textView ;
    ImageButton imageButton ;

    String id ;
    String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);
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
        textView.setText("متابعه الطلبات");
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    public void waiting(View view)
    {
        startActivity(new Intent(this , WaitingList.class));
    }

    public void prepare(View view) {startActivity(new Intent(this , PrepareList.class));}

    public void onWay(View view) {startActivity(new Intent(this , OnWay.class));}

    public void finished(View view) {startActivity(new Intent(this , Finished.class));}

    public void canceled(View view) {startActivity(new Intent(this , Canceled.class));}
}
