package com.pacocardenal.restaurant.model;

import java.util.LinkedList;

public class Menu {

    private LinkedList<Dish> mDishes;

    public Menu(LinkedList<Dish> dishes) {
        mDishes = dishes;
    }

    public Menu() {
        mDishes = new LinkedList<>();
    }

    public LinkedList<Dish> getDishes() {
        return mDishes;
    }

    public void setDishes(LinkedList<Dish> dishes) {
        mDishes = dishes;
    }

    public Dish getDish(int position) {return  mDishes.get(position); }

    public int getNumberOfDishes() {
        return mDishes.size();
    }

    public void addDish (Dish dish) {
        mDishes.add(dish);
    }
}
