package com.edulog.driverportal.routes.presentation.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseActivity;
import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routes.data.service.RouteServiceImpl;
import com.edulog.driverportal.routes.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routes.domain.service.RouteService;
import com.edulog.driverportal.routes.presentation.presenter.RouteSelectionPresenter;
import com.edulog.driverportal.routes.presentation.presenter.RouteSelectionPresenterImpl;
import com.edulog.driverportal.routes.presentation.view.RouteSelectionView;
import com.edulog.driverportal.routes.presentation.view.fragment.RouteSelectionFragment;
import com.edulog.driverportal.routes.presentation.view.fragment.RouteIdSuggestionsFragment;
import com.edulog.driverportal.routes.presentation.view.fragment.SearchRoutesFragment;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RouteSelectionActivity extends BaseActivity implements RouteSelectionView {
    private RouteSelectionPresenter routeSelectionPresenter;
    private RouteIdSuggestionsFragment routeIdSuggestionsFragment;

    private MenuItem searchItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpActionBar();

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                boolean canBack = getSupportFragmentManager().getBackStackEntryCount() > 0;
                getSupportActionBar().setDisplayHomeAsUpEnabled(canBack);
                if (!canBack) {
                    searchItem.collapseActionView();
                    SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
                    searchView.onActionViewCollapsed();
                }
            }
        });

        Fragment fragment = RouteSelectionFragment.newInstance();
        openAsRoot(fragment);

        Scheduler postExecutionScheduler = AndroidSchedulers.mainThread();
        RouteService routeService = new RouteServiceImpl();
        RouteIdSuggestionsUseCase routeIdSuggestionsUseCase = new RouteIdSuggestionsUseCase(postExecutionScheduler, routeService);
        routeSelectionPresenter = new RouteSelectionPresenterImpl(routeIdSuggestionsUseCase);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_route_selection, menu);

        searchItem = menu.findItem(R.id.actionSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnSearchClickListener(v -> {
            routeIdSuggestionsFragment = RouteIdSuggestionsFragment.newInstance();
            moveToFragment(routeIdSuggestionsFragment);
        });
        RxSearchView.queryTextChanges(searchView)
                .debounce(200, TimeUnit.MILLISECONDS)
                .filter(query -> query.length() != 0)
                .map(query -> query.toString())
                .subscribe(routeSelectionPresenter::suggestRouteIds);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction().equals(Intent.ACTION_SEARCH)) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRoutesFragment fragment = SearchRoutesFragment.newInstance(query);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, fragment)
                    .commit();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return routeSelectionPresenter;
    }

    @Override
    protected BaseView getView() {
        return this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showRouteIdsSuggestion(List<String> routeIds) {
        routeIdSuggestionsFragment.showRouteIdSuggestions(routeIds);
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.routes));
        }
    }
}
