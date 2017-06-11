package com.pacocardenal.restaurant.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.pacocardenal.restaurant.R;
import com.pacocardenal.restaurant.fragment.DishesFragment;

public class DishesActivity extends AppCompatActivity{

    public static final String EXTRA_INDEX = "EXTRA_INDEX";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dishes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_title);

        FragmentManager fm = getFragmentManager();
        DishesFragment dishesFragment = DishesFragment.newInstance(getIntent().getIntExtra(EXTRA_INDEX, 0));

        if (fm.findFragmentById(R.id.fragment_dishes) == null) {
            fm.beginTransaction()
                    .add(R.id.fragment_dishes, dishesFragment)
                    .commit();
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
}
