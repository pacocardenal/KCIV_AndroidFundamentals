package com.pacocardenal.restaurant.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pacocardenal.restaurant.R;
import com.pacocardenal.restaurant.model.Dish;
import com.pacocardenal.restaurant.model.Tables;

public class DishDetailActivity extends AppCompatActivity{

    public static final String EXTRA_DISH = "EXTRA_DISH";
    public static final String EXTRA_INDEX = "EXTRA_INDEX";

    private TextView mDishName;
    private TextView mPrice;
    private TextView mDescription;
    private TextView mAllergens;
    private ImageView mImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPrice = (TextView) findViewById(R.id.dish_price);
        mDescription = (TextView) findViewById(R.id.dish_description);
        mAllergens = (TextView) findViewById(R.id.dish_allergens);
        mImage = (ImageView) findViewById(R.id.dish_image);

        final Dish dish = (Dish) getIntent().getSerializableExtra(EXTRA_DISH);
        getSupportActionBar().setTitle(dish.getName());

        mPrice.setText(dish.getPrice());
        mDescription.setText(dish.getDescription());
        mAllergens.setText(getString(R.string.Allergens) + ": " + (CharSequence) dish.getAllergens());
        mImage.setImageResource(dish.getImage());

        findViewById(R.id.addDishToTable).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int tableIndex = getIntent().getIntExtra(EXTRA_INDEX, 0);
                Tables.sharedInstance()
                        .getTable(tableIndex)
                        .getDishes()
                        .add(dish);

                Toast.makeText(DishDetailActivity.this, "Added ok", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
