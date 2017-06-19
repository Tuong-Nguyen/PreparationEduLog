package com.edulog.driverportal.routeselection.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.edulog.driverportal.R;
import com.edulog.driverportal.routeselection.model.RouteModel;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder> {
    private List<RouteModel> routeModels;
    private Context context;

    public SearchResultAdapter(List<RouteModel> routeModels, Context context) {
        this.routeModels = routeModels;
        this.context = context;
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        public SearchResultViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_search_route_result, parent, false));


        }
    }

    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new SearchResultViewHolder(inflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(SearchResultViewHolder searchResultViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return routeModels.size();
    }
}
