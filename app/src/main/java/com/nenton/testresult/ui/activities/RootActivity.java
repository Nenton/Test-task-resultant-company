package com.nenton.testresult.ui.activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.nenton.testresult.BuildConfig;
import com.nenton.testresult.R;
import com.nenton.testresult.di.DaggerService;
import com.nenton.testresult.di.components.AppComponent;
import com.nenton.testresult.di.modules.RootModule;
import com.nenton.testresult.di.sqopes.RootScope;
import com.nenton.testresult.flow.TreeKeyDispatcher;
import com.nenton.testresult.mvp.presenters.MenuItemHolder;
import com.nenton.testresult.mvp.presenters.RootPresenter;
import com.nenton.testresult.mvp.views.IActionBarView;
import com.nenton.testresult.mvp.views.IRootView;
import com.nenton.testresult.mvp.views.IView;
import com.nenton.testresult.ui.screens.main.MainScreen;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class RootActivity extends AppCompatActivity implements IRootView, IActionBarView {

    @Inject
    RootPresenter mRootPresenter;
    @BindView(R.id.root_frame)
    FrameLayout mFrameContainer;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.root_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.root_coordinator)
    CoordinatorLayout mCoordinatorLayout;

    private ActionBar mActionBar;
    private List<MenuItemHolder> mActionBarMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        ButterKnife.bind(this);
        RootComponent rootComponent = DaggerService.getDaggerComponent(this);
        rootComponent.inject(this);
        mRootPresenter.takeView(this);
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
    }

    @Override
    protected void onDestroy() {
        mRootPresenter.dropView(this);
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new MainScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();
        super.attachBaseContext(newBase);
    }

    @Override
    public Object getSystemService(String name) {
        MortarScope rootActivityScope = MortarScope.findChild(getApplicationContext(), RootActivity.class.getName());
        return rootActivityScope.hasService(name) ? rootActivityScope.getService(name) : super.getSystemService(name);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
//        FirebaseCrash.log("ROOT VIEW EXCEPTION");
//        FirebaseCrash.report(e);
        // TODO: 02.02.2018 implement me
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Что-то пошло не так. Попробуйте повторить позже");
        }
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public void setMenuItem(List<MenuItemHolder> items) {
        mActionBarMenuItems = items;
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mActionBarMenuItems != null && !mActionBarMenuItems.isEmpty()) {
            for (MenuItemHolder menuItem : mActionBarMenuItems) {
                MenuItem item = menu.add(menuItem.getTitle());
                item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
                        .setIcon(menuItem.getIconResId())
                        .setOnMenuItemClickListener(menuItem.getListener());
            }
        } else {
            menu.clear();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Nullable
    @Override
    public IView getCurrentScreen() {
        return (IView) mFrameContainer.getChildAt(0);
    }

    @Override
    public void onBackPressed() {
        if (getCurrentScreen() != null && !getCurrentScreen().viewOnBackPressed() && !Flow.get(this).goBack()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выход")
                    .setPositiveButton("Да", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("Нет", (dialog, which) -> dialog.cancel())
                    .setMessage("Вы действительно хотите выйти?")
                    .show();
        }
    }

    //region ========================= DI =========================

    @dagger.Component(dependencies = AppComponent.class, modules = {RootModule.class})
    @RootScope
    public interface RootComponent {
        void inject(RootActivity rootActivity);

        void inject(RootPresenter rootPresenter);

        RootPresenter getRootPresenter();
    }
    //endregion
}
