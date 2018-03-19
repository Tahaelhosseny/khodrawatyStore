package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    String idd ;
    String tokenn;
    String namee ;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;



    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
        textView.setText(namee);





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        new MenuInflater(this).inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.logOut)
            logOut();
        return super.onOptionsItemSelected(item);
    }




    private void logOut()
    {
        editor.putString("token", null);
        editor.putString("id", null);
        editor.putString("name",null);
        editor.apply();
        finish();
    }

    public void openClose(View view)
    {
        startActivity(new Intent(getApplicationContext() , OpenClose.class));
    }

    public void editPage(View view)
    {
        startActivity(new Intent(getApplicationContext() , DeleteCatActivity.class));
    }

    public void editCat(View view)
    {
        startActivity(new Intent(getApplicationContext() , CategoryActivity.class));

    }
}
