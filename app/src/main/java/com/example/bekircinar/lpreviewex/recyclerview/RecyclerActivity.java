package com.example.bekircinar.lpreviewex.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.bekircinar.lpreviewex.R;

import java.util.ArrayList;

/**
 * Created by bekir.cinar on 07/07/14.
 */
public class RecyclerActivity extends Activity {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;

    private Button mAddButton;
    private Button mRemoveButton;

    private ArrayList<Item> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        getWindow().setEnterTransition(new Explode());

        setContentView(R.layout.activity_recycler);

        findViews();

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        //start from below
        //mLayoutManager.setReverseLayout(true);

        //make it horizontall
        //mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setLayoutManager(new MyLayoutManager(this));

        //DefaultItemAnimator, if not specified any!!
        //-1 to no color
        mRecyclerView.setItemAnimator(new MyAnimation(getResources().getColor(R.color.red)));

        mRecyclerView.setHasFixedSize(false);

        //mock data!!
        setMockDatas();

        mAdapter = new MyAdapter(this, mItems);
        mAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(View view) {
                int position = mRecyclerView.getChildPosition(view);
                mAdapter.removeItem(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mItems.size();
                mAdapter.addItem(new Item(position + ""), 1);
            }
        });
        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mItems.size() - 1;
                mAdapter.removeItem(position);
            }
        });


    }

    private void findViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAddButton = (Button) findViewById(R.id.my_button_add);
        mRemoveButton = (Button) findViewById(R.id.my_button_remove);
    }

    private void setMockDatas() {
        mItems = new ArrayList<Item>();
        for (int i = 0; i < 5; i++) {
            mItems.add(new Item(i + ""));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
