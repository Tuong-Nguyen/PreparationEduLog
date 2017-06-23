package com.edulog.driverportal.routeselection.presentation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edulog.driverportal.R;
import com.edulog.driverportal.routeselection.model.RouteModel;

import java.util.List;

public class RouteHistoryAdapter extends RecyclerView.Adapter<RouteHistoryAdapter.ViewHolder> {
    private List<RouteModel> routeModels;

    public RouteHistoryAdapter(List<RouteModel> routeModels) {
        this.routeModels = routeModels;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView routeId;
        public TextView routeName;
        public TextView stopCount;

        public ViewHolder(View itemView) {
            super(itemView);

            routeId = (TextView)itemView.findViewById(R.id.tvRouteId);
            routeName = (TextView)itemView.findViewById(R.id.tvRouteName);
            stopCount = (TextView)itemView.findViewById(R.id.tvStopCount);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ViewHolder(inflater.inflate(R.layout.item_search_routes_result, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        RouteModel routeModel = routeModels.get(i);

        viewHolder.routeId.setText(routeModel.getId());
        viewHolder.routeName.setText(routeModel.getName());
        viewHolder.stopCount.setText(String.valueOf(routeModel.getStopCount()));
    }

    @Override
    public int getItemCount() {
        return routeModels.size();
    }
}
