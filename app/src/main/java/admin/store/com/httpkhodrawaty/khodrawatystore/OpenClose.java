package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

public class OpenClose extends AppCompatActivity
{


    TextView textView ;
    ImageButton imageButton ;
    SwitchCompat aSwitch ;
    ImageView imageView ;



    String idd ;
    String tokenn;
    String namee ;
    String openClose;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close);

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        init();


    }


    private void init()
    {

        prefs  = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE);
        editor = getSharedPreferences("admin.store.com.httpkhodrawaty.khodrawatystore", MODE_PRIVATE).edit();
        idd = prefs.getString("id",null);
        tokenn = prefs.getString("token",null);
        namee = prefs.getString("name",null);
        openClose = prefs.getString("openClose","1") ;
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



        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked())
                {
                    imageView.setBackgroundResource(R.drawable.open);
                    editor.putString("openClose", "0");  //zero is open
                    editor.apply();
                }
                else
                {
                    imageView.setBackgroundResource(R.drawable.close);
                    editor.putString("openClose", "1");  //zero is close
                    editor.apply();
                }
            }
        });



        if(openClose.equals("0"))
        {
            imageView.setBackgroundResource(R.drawable.open);
            aSwitch.setChecked(true);
        }
        else
        {
            imageView.setBackgroundResource(R.drawable.close);
            aSwitch.setChecked(false);

        }


    }

    public void saveStatus(View view)

    {
        if(aSwitch.isChecked())
        {
            editor.putString("openClose", "0");  //zero is open
            editor.apply();
        }
        else if (! aSwitch.isChecked())
        {
            editor.putString("openClose", "1");  //zero is close
            editor.apply();
        }
    }
}
