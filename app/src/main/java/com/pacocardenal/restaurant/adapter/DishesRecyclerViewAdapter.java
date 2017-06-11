package com.pacocardenal.restaurant.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pacocardenal.restaurant.R;
import com.pacocardenal.restaurant.model.Dish;

import java.util.LinkedList;

public class DishesRecyclerViewAdapter extends RecyclerView.Adapter<DishesRecyclerViewAdapter.DishListViewHolder>{

    private LinkedList<Dish> mMainDishList;
    private OnDishClickListener mOnDishClickListener;

    public DishesRecyclerViewAdapter(LinkedList<Dish> mainDishList, OnDishClickListener onDishClickListener) {
        super();
        mMainDishList = mainDishList;
        mOnDishClickListener = onDishClickListener;
    }

    @Override
    public DishListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cardview_dish, parent, false);
        return new DishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DishListViewHolder holder, final int position) {
        holder.bindDish(mMainDishList.get(position));
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnDishClickListener != null) {
                    mOnDishClickListener.onDishClick(position, mMainDishList.get(position), view);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMainDishList.size();
    }

    protected class DishListViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mPrice;
        private TextView mAllergens;
        private ImageView mImage;
        private View mView;

        public  DishListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mName = (TextView) itemView.findViewById(R.id.dish_name);
            mPrice = (TextView) itemView.findViewById(R.id.dish_price);
            mAllergens = (TextView) itemView.findViewById(R.id.dish_allergens);
            mImage = (ImageView) itemView.findViewById(R.id.dish_image);
        }

        public void bindDish(Dish dish) {
            mName.setText(dish.getName());
            mPrice.setText(String.valueOf(dish.getPrice()));
            mAllergens.setText(String.valueOf(dish.getAllergens()));
            mImage.setImageResource(dish.getImage());
        }

        public View getView() {
            return mView;
        }
    }

    public interface OnDishClickListener {
        public void onDishClick(int position, Dish dish, View view);
    }
}
