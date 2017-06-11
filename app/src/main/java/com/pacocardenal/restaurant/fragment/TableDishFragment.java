package com.pacocardenal.restaurant.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.pacocardenal.restaurant.R;
import com.pacocardenal.restaurant.model.Dish;
import com.pacocardenal.restaurant.model.Tables;
import java.util.LinkedList;

public class TableDishFragment extends Fragment {

    private static final String TABLE_INDEX = "TABLE_INDEX";

    private int mIndex;
    private OnDishSelectedListener mOnDishSelectedListener;
    private OnButtonClickListener mOnButtonClickListener;
    private LinkedList<Dish> mDishes;
    private ListView mListView;
    private ArrayAdapter<Dish> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_table_dish, container, false);
        mListView = (ListView) root.findViewById(android.R.id.list);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mOnDishSelectedListener != null) {
                    mOnDishSelectedListener.onDishSelected(mDishes.get(position), position);
                }
            }
        });

        FloatingActionButton addButton = (FloatingActionButton) root.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onButtonClick();
                }
            }
        });

        return root;
    }

    public static TableDishFragment newInstance(int tableIndex) {
        Bundle arguments = new Bundle();
        arguments.putInt(TABLE_INDEX, tableIndex);
        TableDishFragment tableDishFragment = new TableDishFragment();
        tableDishFragment.setArguments(arguments);

        return tableDishFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() !=null) {
            mIndex = getArguments().getInt(TABLE_INDEX, 0);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        LinkedList dishList = Tables.sharedInstance()
                .getTable(mIndex)
                .getDishes();

        adapter = new ArrayAdapter<Dish>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                dishList
        );

        mListView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof OnDishSelectedListener) {
            mOnDishSelectedListener = (OnDishSelectedListener) getActivity();
        }
        if (getActivity() instanceof OnButtonClickListener) {
            mOnButtonClickListener = (OnButtonClickListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof OnDishSelectedListener) {
            mOnDishSelectedListener = (OnDishSelectedListener) getActivity();
        }
        if (getActivity() instanceof OnButtonClickListener) {
            mOnButtonClickListener = (OnButtonClickListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnDishSelectedListener = null;
        mOnButtonClickListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_table_dish,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue = super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.calculate_bill) {
            String totalBill = String.format("%.02f", Tables.sharedInstance().getTable(mIndex).getBill());
            Snackbar.make(mListView, "Total: " + totalBill + getString(R.string.euro), Snackbar.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.clear_table) {
            Tables.sharedInstance().getTable(mIndex).clearTable();
            Toast.makeText(getActivity(), "Table cleared", Toast.LENGTH_SHORT).show();
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
        return superValue;
    }

    public interface OnDishSelectedListener {
        void onDishSelected(Dish dish, int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick();
    }

}
