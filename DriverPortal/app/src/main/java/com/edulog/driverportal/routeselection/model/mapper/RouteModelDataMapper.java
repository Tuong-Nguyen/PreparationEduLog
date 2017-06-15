package com.edulog.driverportal.routeselection.model.mapper;

import com.edulog.driverportal.routeselection.data.entity.RouteEntity;
import com.edulog.driverportal.routeselection.model.RouteModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RouteModelDataMapper {
    public RouteModel transform(RouteEntity routeEntity) {
        if (routeEntity == null) {
            throw new IllegalArgumentException("Can not transform empty value.");
        }

        RouteModel routeModel = new RouteModel();

        return routeModel;
    }

    public List<RouteModel> transform(List<RouteEntity> routeEntities) {
        List<RouteModel> routeModels;

        if (routeEntities != null && !routeEntities.isEmpty()) {
            routeModels = new ArrayList<>();
            for (RouteEntity routeEntity : routeEntities) {
                RouteModel routeModel = transform(routeEntity);
                routeModels.add(routeModel);
            }
        } else {
            routeModels = Collections.emptyList();
        }

        return routeModels;
    }
}
