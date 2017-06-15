package com.edulog.driverportal.routeselection.presentation.view.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseActivity;
import com.edulog.driverportal.common.presentation.navigator.Navigator;
import com.edulog.driverportal.common.presentation.navigator.NavigatorImpl;
import com.edulog.driverportal.routeselection.presentation.presenter.SearchRoutesPresenter;
import com.edulog.driverportal.routeselection.presentation.presenter.SearchRoutesPresenterImpl;
import com.edulog.driverportal.routeselection.presentation.view.fragment.RouteSelectionFragment;
import com.edulog.driverportal.routeselection.presentation.view.fragment.SearchResultFragment;

public class RouteSelectionActivity extends BaseActivity {
    private SearchRoutesPresenter searchRoutesPresenter;
    private Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);

        navigator = new NavigatorImpl(getSupportFragmentManager(), R.id.contentFrame);
        searchRoutesPresenter = new SearchRoutesPresenterImpl();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = RouteSelectionFragment.newInstance();
            navigator.openAsRoot(fragment);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Fragment fragment = SearchResultFragment.newInstance(query);
            navigator.moveToFragment(fragment);
        }
    }

    public SearchRoutesPresenter getSearchRoutesPresenter() {
        return searchRoutesPresenter;
    }

    public Navigator getNavigator() {
        return navigator;
    }
}
