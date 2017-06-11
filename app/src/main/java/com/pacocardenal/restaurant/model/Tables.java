package com.pacocardenal.restaurant.model;

import java.util.LinkedList;

public class Tables {

    private static LinkedList<Table> mTables;
    private static Tables singleton;
    private Menu mMenu;

    private Tables() {
        if (mTables == null) {
            mTables = new LinkedList<>();
        }

        for (int i=1; i<11; i++) {
            mTables.add(new Table(i));
        }
    }
    public static Tables sharedInstance() {
        if (singleton == null) {
            singleton = new Tables();
        }
        return singleton;
    }

    public Menu getMenu() {
        return mMenu;
    }

    public void setMenu(Menu menu) {
        mMenu = menu;
    }

    public LinkedList<Table> getTableList() {
        return mTables;
    }

    public void setTableList(LinkedList<Table> tableList) {
        mTables = tableList;
    }

    public Table getTable(int position) {
        return mTables.get(position);
    }
}
