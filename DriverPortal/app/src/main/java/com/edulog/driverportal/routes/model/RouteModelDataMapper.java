package com.edulog.driverportal.routes.model;

import com.edulog.driverportal.routes.data.entity.RouteEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RouteModelDataMapper {
    public static RouteModel transform(RouteEntity routeEntity) {
        if (routeEntity == null) {
            throw new IllegalArgumentException("Can not transform empty value.");
        }

        RouteModel routeModel = new RouteModel();
        routeModel.setId(routeEntity.getId());
        routeModel.setName(routeEntity.getName());
        routeModel.setStopCount(routeEntity.getStopCount());

        return routeModel;
    }

    public static List<RouteModel> transform(List<RouteEntity> routeEntities) {
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
