package com.edulog.driverportal.routes.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edulog.driverportal.R;
import com.edulog.driverportal.routes.model.RouteModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private List<RouteModel> routeModels;
    private Context context;
    private final PublishSubject<RouteModel> onClickSubject = PublishSubject.create();

    public SearchResultAdapter(List<RouteModel> routeModels, Context context) {
        this.routeModels = routeModels;
        this.context = context;
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        public TextView routeIdTextView;
        public TextView routeNameTextView;
        public TextView stopCountTextView;

        public SearchResultViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_search_routes_result, parent, false));

            routeIdTextView = (TextView)itemView.findViewById(R.id.tvRouteId);
            routeNameTextView = (TextView)itemView.findViewById(R.id.tvRouteName);
            stopCountTextView = (TextView)itemView.findViewById(R.id.tvStopCount);
        }
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new SearchResultViewHolder(inflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder searchResultViewHolder, int i) {
        RouteModel routeModel = routeModels.get(i);

        searchResultViewHolder.routeIdTextView.setText(routeModel.getId());
        searchResultViewHolder.routeNameTextView.setText(routeModel.getName());
        searchResultViewHolder.stopCountTextView.setText(String.valueOf(routeModel.getStopCount()));

        searchResultViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSubject.onNext(routeModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routeModels.size();
    }

    public Observable<RouteModel> getItemClickObservable() {
        return onClickSubject;
    }
}
