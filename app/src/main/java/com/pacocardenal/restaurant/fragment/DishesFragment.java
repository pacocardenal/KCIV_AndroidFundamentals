package com.pacocardenal.restaurant.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewSwitcher;
import java.util.LinkedList;
import com.pacocardenal.restaurant.R;
import com.pacocardenal.restaurant.activity.DishDetailActivity;
import com.pacocardenal.restaurant.adapter.DishesRecyclerViewAdapter;
import com.pacocardenal.restaurant.model.Dish;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;

public class DishesFragment extends Fragment implements DishesRecyclerViewAdapter.OnDishClickListener{

    private static final int PROGRESS_INDEX = 0;
    private static final int LIST_INDEX = 1;
    private static final String TABLE_INDEX = "TABLE_INDEX";

    private RecyclerView mList;
    private ViewSwitcher mViewSwitcher;
    private Dish mDish;
    private LinkedList<Dish> mMainDishList;
    private int mTableIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() !=null) {
            mTableIndex = getArguments().getInt(TABLE_INDEX);
        }
    }

    public static DishesFragment newInstance(int tableIndex) {
        Bundle arguments = new Bundle();
        arguments.putInt(TABLE_INDEX, tableIndex);
        DishesFragment dishesFragment = new DishesFragment();
        dishesFragment.setArguments(arguments);

        return dishesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_dishes, container, false);
        mViewSwitcher = (ViewSwitcher) root.findViewById(R.id.view_switcher);
        mViewSwitcher.setInAnimation(getActivity(), android.R.anim.fade_in);
        mViewSwitcher.setOutAnimation(getActivity(), android.R.anim.fade_out);
        mList = root.findViewById(R.id.recycler_view);
        mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.setItemAnimator(new DefaultItemAnimator());
        mList.setAdapter(new DishesRecyclerViewAdapter(new LinkedList<Dish>(), this));
        setDishList(mMainDishList);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDishClick(int position, Dish dish, View view) {
        Intent intent = new Intent(getActivity(), DishDetailActivity.class);
        intent.putExtra(DishDetailActivity.EXTRA_DISH, dish);
        intent.putExtra(DishDetailActivity.EXTRA_INDEX, mTableIndex);

        startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        view,
                        getString(R.string.detail_transition) // El nombre dentro de la vista destino
                ).toBundle());
    }

    public void downloadMainDishList() {
        AsyncTask<Dish, Integer, LinkedList<Dish>> dishesDownloader = new AsyncTask<Dish, Integer, LinkedList<Dish>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mViewSwitcher.setDisplayedChild(PROGRESS_INDEX);
            }

            @Override
            protected LinkedList<Dish> doInBackground(Dish... dishModels) {
                URL url = null;
                InputStream input = null;

                try {
                    url = new URL(String.format(getString(R.string.mocky_url)));

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int responseLength = connection.getContentLength();
                    byte data[] = new byte[1024];
                    long currentBytes = 0;
                    int downloadedBytes;
                    input = connection.getInputStream();
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((downloadedBytes = input.read(data)) != -1 && !isCancelled()) {
                        stringBuilder.append(new String(data, 0, downloadedBytes));
                        publishProgress((int)(currentBytes * 100) / responseLength);
                    }

                    JSONObject jsonRoot = new JSONObject(stringBuilder.toString());
                    JSONArray dishListJSON = jsonRoot.getJSONArray("dishes");
                    LinkedList<Dish> dishList = new LinkedList<Dish>();

                    for (int i = 0; i < dishListJSON.length(); i++) {
                        JSONObject dish = dishListJSON.getJSONObject(i);

                        String name = dish.getString("name");
                        String description = dish.getString("description");
                        String allergens = dish.getString("allergens");
                        float price = Float.parseFloat(dish.getString("price"));
                        String photo = dish.getString("image");
                        int imageNumber = Integer.parseInt(photo);
                        int imageIcon = R.drawable.one;

                        switch (imageNumber) {
                            case 1:
                                imageIcon = R.drawable.one;
                                break;
                            case 2:
                                imageIcon = R.drawable.two;
                                break;
                            case 3:
                                imageIcon = R.drawable.three;
                                break;
                        }

                        mDish = new Dish(name, description, allergens, price, imageIcon);
                        dishList.add(mDish);
                    }

                    return dishList;

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(LinkedList<Dish> dishList) {
                super.onPostExecute(dishList);

                if (dishList != null) {
                    setDishList(dishList);
                }
                else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                    alertDialog.setTitle(R.string.error);
                    alertDialog.setMessage(R.string.error);
                    alertDialog.setPositiveButton(R.string.retry_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            downloadMainDishList();
                        }
                    });
                    alertDialog.show();
                }
            }
        };

        dishesDownloader.execute(mDish);
    }

    private void setDishList(LinkedList<Dish> dishList) {
        if (dishList == null) {
            downloadMainDishList();

        } else {
            mViewSwitcher.setDisplayedChild(LIST_INDEX);
            mList.setAdapter(new DishesRecyclerViewAdapter(dishList, this));
        }
    }
}
