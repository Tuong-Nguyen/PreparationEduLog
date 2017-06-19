package com.edulog.driverportal.routeselection.presentation.view.activity;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.edulog.driverportal.R;
import com.edulog.driverportal.common.presentation.BaseActivity;
import com.edulog.driverportal.common.presentation.BasePresenter;
import com.edulog.driverportal.common.presentation.BaseView;
import com.edulog.driverportal.routeselection.data.service.SearchRoutesServiceImpl;
import com.edulog.driverportal.routeselection.domain.interactor.SearchRoutesUseCase;
import com.edulog.driverportal.routeselection.domain.interactor.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routeselection.domain.service.SearchRoutesService;
import com.edulog.driverportal.routeselection.model.RouteModel;
import com.edulog.driverportal.routeselection.model.mapper.RouteModelDataMapper;
import com.edulog.driverportal.routeselection.presentation.presenter.SearchRoutesPresenter;
import com.edulog.driverportal.routeselection.presentation.presenter.SearchRoutesPresenterImpl;
import com.edulog.driverportal.routeselection.presentation.view.SearchRoutesView;
import com.edulog.driverportal.routeselection.presentation.view.fragment.RouteSelectionFragment;
import com.edulog.driverportal.routeselection.presentation.view.fragment.RouteIdSuggestionsFragment;
import com.edulog.driverportal.routeselection.presentation.view.fragment.SearchResultFragment;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class RouteSelectionActivity extends BaseActivity implements SearchRoutesView {
    private SearchRoutesPresenter searchRoutesPresenter;
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
        SearchRoutesService searchRoutesService = new SearchRoutesServiceImpl();
        RouteModelDataMapper routeModelDataMapper = new RouteModelDataMapper();
        SearchRoutesUseCase searchRoutesUseCase = new SearchRoutesUseCase(postExecutionScheduler, searchRoutesService, routeModelDataMapper);
        RouteIdSuggestionsUseCase routeIdSuggestionsUseCase = new RouteIdSuggestionsUseCase(postExecutionScheduler, searchRoutesService);
        searchRoutesPresenter = new SearchRoutesPresenterImpl(searchRoutesUseCase, routeIdSuggestionsUseCase);
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
                .subscribe(searchRoutesPresenter::suggestRouteIds);

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
            searchRoutesPresenter.searchRoutes(query);
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return searchRoutesPresenter;
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

    @Override
    public void showSearchRouteResults(List<RouteModel> routeModels) {
        SearchResultFragment searchResultFragment = SearchResultFragment.newInstance();
        moveToFragment(searchResultFragment);
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.routes));
        }
    }
}
