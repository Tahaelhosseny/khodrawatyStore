package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DeleteCatActivity extends AppCompatActivity
{


    String idd ;
    String tokenn;
    String namee ;




    SharedPreferences prefs;
    SharedPreferences.Editor editor;



    TextView textView ;
    ImageButton imageButton ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        init();


    }


    private void init()
    {

        prefs = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE);
        editor = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE).edit();
        idd = prefs.getString("id",null);
        tokenn = prefs.getString("token",null);
        namee = prefs.getString("name",null);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.set_title);
        View v = getSupportActionBar().getCustomView();
        textView = (TextView) v.findViewById(R.id.mytext);
        imageButton = (ImageButton) v.findViewById(R.id.imageButton);
        textView.setText("تعديل البيانات ");
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    public void changePassword(View view)
    {
        startActivity(new Intent(getApplicationContext() , ChangePassword.class));
    }

    public void changeLocation(View view)
    {
        startActivity(new Intent(getApplicationContext() , ChangeLocaInfo.class));

    }

    public void changeTime(View view)
    {
        startActivity(new Intent(getApplicationContext() , ChangeWorkTime.class));
    }
}
