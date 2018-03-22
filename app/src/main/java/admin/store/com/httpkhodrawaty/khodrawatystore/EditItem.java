package admin.store.com.httpkhodrawaty.khodrawatystore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import admin.store.com.httpkhodrawaty.khodrawatystore.Modle.ItemModel;

public class EditItem extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent i = getIntent();

        ItemModel itemModel = (ItemModel) i.getSerializableExtra("item");


    }

    public void saveEdit(View view) {
    }
}
