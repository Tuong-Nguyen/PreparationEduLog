package com.edulog.driverportal.routeselection.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edulog.driverportal.R;

import java.util.List;

public class RouteIdSuggestionsAdapter extends RecyclerView.Adapter<RouteIdSuggestionsAdapter.RouteIdViewHolder> {
    private List<String> routeIds;
    private Context context;

    public RouteIdSuggestionsAdapter(List<String> routeIds, Context context) {
        this.routeIds = routeIds;
        this.context = context;
    }

    class RouteIdViewHolder extends RecyclerView.ViewHolder {
        public TextView routeIdTextView;

        public RouteIdViewHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            super(inflater.inflate(R.layout.item_route_id_suggestion, viewGroup, false));

            routeIdTextView = (TextView)itemView.findViewById(R.id.tvRouteId);
        }
    }

    @Override
    public RouteIdViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new RouteIdViewHolder(inflater, viewGroup);
    }

    @Override
    public void onBindViewHolder(RouteIdViewHolder routeIdViewHolder, int i) {
        String routeId = routeIds.get(i);
        routeIdViewHolder.routeIdTextView.setText(routeId);
    }

    @Override
    public int getItemCount() {
        return routeIds.size();
    }
}
