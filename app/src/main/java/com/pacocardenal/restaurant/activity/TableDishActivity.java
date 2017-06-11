package com.pacocardenal.restaurant.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.pacocardenal.restaurant.R;
import com.pacocardenal.restaurant.fragment.TableDishFragment;

public class TableDishActivity extends AppCompatActivity implements TableDishFragment.OnButtonClickListener{

    public static final String EXTRA_INDEX = "EXTRA_INDEX";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_table_dish);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getString(R.string.table_name) + " " + String.valueOf(getIntent().getIntExtra(EXTRA_INDEX, 0)+1);
        getSupportActionBar().setTitle(title);

        FragmentManager fm = getFragmentManager();
        TableDishFragment dishListFragment = TableDishFragment.newInstance(getIntent().getIntExtra(EXTRA_INDEX, 0));

        if (findViewById(R.id.table_dish_list) != null) {
            if (fm.findFragmentById(R.id.table_dish_list) == null) {
                fm.beginTransaction()
                        .add(R.id.table_dish_list, dishListFragment)
                        .commit();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return superValue;
    }

    @Override
    public void onButtonClick() {
        Intent intent = new Intent(this, DishesActivity.class);
        intent.putExtra(TableDishActivity.EXTRA_INDEX, getIntent().getIntExtra(EXTRA_INDEX, 0));
        startActivity(intent);
    }
}
