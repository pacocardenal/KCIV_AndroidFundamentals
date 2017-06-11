package com.pacocardenal.restaurant.model;

import java.util.LinkedList;

public class Table {

    private LinkedList<Dish> mDishes;
    private int mTableNumber;
    private Float mBill;

    public Table(int tableNumber) {
        mTableNumber = tableNumber;
    }

    public LinkedList<Dish> getDishes() {
        if (mDishes == null) {
            mDishes = new LinkedList<>();
        }
        return mDishes;
    }

    public void setDishes(LinkedList<Dish> dishes) {
        mDishes = dishes;
    }

    public Float getBill() {
        Float bill = 0.f;
        if (mDishes != null) {
            for (Dish aDish : mDishes) {
                bill = bill + aDish.price();
            }
        }
        return bill;
    }

    public void setBill(Float bill) {
        mBill = bill;
    }

    public int getTableNumber() { return mTableNumber; }

    public void setTableNumber(int tableNumber) {
        mTableNumber = tableNumber;
    }

    public void clearTable() {
        mDishes = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "Table " + getTableNumber();
    }
}
