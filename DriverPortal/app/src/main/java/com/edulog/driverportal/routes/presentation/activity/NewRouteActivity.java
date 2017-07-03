package com.edulog.driverportal.routes.presentation.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.edulog.driverportal.R;
import com.edulog.driverportal.base.BaseActivity;
import com.edulog.driverportal.base.BasePresenter;
import com.edulog.driverportal.base.BaseView;
import com.edulog.driverportal.base.Config;
import com.edulog.driverportal.api.Api;
import com.edulog.driverportal.routes.domain.RouteService;
import com.edulog.driverportal.routes.domain.RouteIdSuggestionsUseCase;
import com.edulog.driverportal.routes.presentation.NewRouteContract;
import com.edulog.driverportal.routes.presentation.NewRoutePresenterImpl;
import com.edulog.driverportal.routes.presentation.fragment.NewRouteFragment;
import com.edulog.driverportal.routes.presentation.fragment.RouteIdSuggestionsFragment;
import com.edulog.driverportal.routes.presentation.fragment.SearchRoutesFragment;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewRouteActivity extends BaseActivity implements NewRouteContract.View {
    private NewRouteContract.Presenter newRoutePresenter;
    private RouteIdSuggestionsFragment routeIdSuggestionsFragment;
    private SearchRoutesFragment searchRoutesFragment;

    private MenuItem searchItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpActionBar();

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            boolean canBack = getSupportFragmentManager().getBackStackEntryCount() > 0;

            setSearchViewExpandState();

            setDisplayHomeAsUp(canBack);
            if (!canBack) {
                collapseSearchView();
            }

            removeSearchFragmentIfAtRoot();
        });

        Fragment fragment = NewRouteFragment.newInstance();
        openAsRoot(fragment);

        RouteService routeService = new Api()
                .baseUrl(Config.EDULOG_URL)
                .generate(RouteService.class);
        RouteIdSuggestionsUseCase routeIdSuggestionsUseCase = new RouteIdSuggestionsUseCase(routeService);
        newRoutePresenter = new NewRoutePresenterImpl(routeIdSuggestionsUseCase);
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
                .map(CharSequence::toString)
                .subscribe(newRoutePresenter::suggestRouteIds);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
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
            searchRoutesFragment = SearchRoutesFragment.newInstance(query);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, searchRoutesFragment)
                    .commit();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return newRoutePresenter;
    }

    @Override
    protected BaseView getView() {
        return this;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showRouteIdSuggestions(List<String> routeIds) {
        routeIdSuggestionsFragment.showRouteIdSuggestions(routeIds);
    }

    public void normalizeAppBar(boolean homeAsUpEnabled) {
        setDisplayHomeAsUp(homeAsUpEnabled);
        collapseSearchView();
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getResources().getString(R.string.routes));
        }
    }

    private void setDisplayHomeAsUp(boolean enabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
        }
    }

    private void collapseSearchView() {
        searchItem.collapseActionView();
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.onActionViewCollapsed();
    }

    private void removeSearchFragmentIfAtRoot() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0 && searchRoutesFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .remove(searchRoutesFragment)
                    .commit();
        }
    }

    private void setSearchViewExpandState() {
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if (isDisplaySearchSuggestions()) {
            searchView.setIconifiedByDefault(false);
        } else {
            searchView.setIconifiedByDefault(true);
        }
    }

    private boolean isDisplaySearchSuggestions() {
        return routeIdSuggestionsFragment != null && routeIdSuggestionsFragment.isVisible();
    }
}
