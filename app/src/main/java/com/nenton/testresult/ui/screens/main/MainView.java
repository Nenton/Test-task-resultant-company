package com.nenton.testresult.ui.screens.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;

import com.nenton.testresult.R;
import com.nenton.testresult.di.DaggerService;
import com.nenton.testresult.mvp.views.AbstractView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainView extends AbstractView<MainScreen.MainPresenter> {

    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_button_fl)
    FrameLayout mFrameRefreshButton;
    @BindView(R.id.refresh_btn)
    Button button;

    @OnClick(R.id.refresh_btn)
    void clickOnButton(){
        mPresenter.clickOnRefresh();
    }

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
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(manager);
    }

    public MainAdapter getAdapter(){
        return mAdapter;
    }

    public void showRefreshButton(){
        mFrameRefreshButton.setVisibility(VISIBLE);
    }

    public void hideRefreshButton(){
        mFrameRefreshButton.setVisibility(INVISIBLE);
    }
}
