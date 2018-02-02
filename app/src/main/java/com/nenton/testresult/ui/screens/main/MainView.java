package com.nenton.testresult.ui.screens.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.nenton.testresult.R;
import com.nenton.testresult.data.storage.realm.CurrencyRealm;
import com.nenton.testresult.di.DaggerService;
import com.nenton.testresult.mvp.views.AbstractView;

import java.util.ArrayList;

import butterknife.BindView;

public class MainView extends AbstractView<MainScreen.MainPresenter> {

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;

    private final MainAdapter mAdapter;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mAdapter = new MainAdapter();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<MainScreen.Component>getDaggerComponent(context).inject(this);
    }

    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        ArrayList<CurrencyRealm> objects = new ArrayList<>();
        objects.add(new CurrencyRealm("Name", 30, 3.5532532f));
        objects.add(new CurrencyRealm("Name", 30, 3.5532532f));
        objects.add(new CurrencyRealm("Name", 30, 3.5532532f));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
        mAdapter.updateListCurrencies(objects);
    }

    public MainAdapter getAdapter(){
        return mAdapter;
    }
}
