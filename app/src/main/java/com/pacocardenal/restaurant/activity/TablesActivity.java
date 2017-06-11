package com.pacocardenal.restaurant.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.pacocardenal.restaurant.R;
import com.pacocardenal.restaurant.fragment.TablesFragment;
import com.pacocardenal.restaurant.model.Table;

public class TablesActivity extends AppCompatActivity implements TablesFragment.OnTableSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getFragmentManager();

        if (findViewById(R.id.tables_list) != null) {
            if (fm.findFragmentById(R.id.tables_list) == null) {
                fm.beginTransaction()
                        .add(R.id.tables_list, new TablesFragment())
                        .commit();
            }
        }
    }

    public void onTableSelected(Table table, int position) {
        Intent intent = new Intent(this, TableDishActivity.class);
        intent.putExtra(TableDishActivity.EXTRA_INDEX, position);
        startActivity(intent);
    }
}
