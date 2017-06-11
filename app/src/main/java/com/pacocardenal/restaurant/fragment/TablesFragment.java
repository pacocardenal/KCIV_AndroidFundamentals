package com.pacocardenal.restaurant.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.pacocardenal.restaurant.R;
import com.pacocardenal.restaurant.model.Tables;
import com.pacocardenal.restaurant.model.Table;

public class TablesFragment extends Fragment{

    private TablesFragment.OnTableSelectedListener mOnTableSelectedListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_tables, container, false);
        ListView listView = (ListView) root.findViewById(R.id.table_list);

        final Tables tables = Tables.sharedInstance();
        ArrayAdapter<Table> adapter = new ArrayAdapter<Table>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                tables.getTableList()
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mOnTableSelectedListener != null) {
                    mOnTableSelectedListener.onTableSelected(tables.getTable(position), position);
                }
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (getActivity() instanceof OnTableSelectedListener) {
            mOnTableSelectedListener = (OnTableSelectedListener) getActivity();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getActivity() instanceof OnTableSelectedListener) {
            mOnTableSelectedListener = (OnTableSelectedListener) getActivity();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mOnTableSelectedListener = null;
    }

    public interface OnTableSelectedListener {
        void onTableSelected(Table table, int position);
    }
}