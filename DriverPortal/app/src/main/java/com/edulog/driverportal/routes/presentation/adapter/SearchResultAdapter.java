package com.edulog.driverportal.routes.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edulog.driverportal.R;
import com.edulog.driverportal.routes.model.Route;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private final PublishSubject<Route> onClickSubject = PublishSubject.create();
    private List<Route> routes;
    private Context context;

    public SearchResultAdapter(List<Route> routes, Context context) {
        this.routes = routes;
        this.context = context;
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new SearchResultViewHolder(inflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder searchResultViewHolder, int i) {
        Route route = routes.get(i);

        searchResultViewHolder.routeIdTextView.setText(route.getId());
        searchResultViewHolder.routeNameTextView.setText(route.getName());
        searchResultViewHolder.stopCountTextView.setText(String.valueOf(route.getStopCount()));

        searchResultViewHolder.itemView.setOnClickListener(v -> onClickSubject.onNext(route));
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public Observable<Route> getItemClickObservable() {
        return onClickSubject;
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        public TextView routeIdTextView;
        public TextView routeNameTextView;
        public TextView stopCountTextView;

        public SearchResultViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_search_routes_result, parent, false));

            routeIdTextView = (TextView) itemView.findViewById(R.id.tvRouteId);
            routeNameTextView = (TextView) itemView.findViewById(R.id.tvRouteName);
            stopCountTextView = (TextView) itemView.findViewById(R.id.tvStopCount);
        }
    }
}
