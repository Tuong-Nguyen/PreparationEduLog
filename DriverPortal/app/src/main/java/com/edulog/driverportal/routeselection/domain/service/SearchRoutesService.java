package com.edulog.driverportal.routeselection.domain.service;

import com.edulog.driverportal.routeselection.data.entity.RouteEntity;

import java.util.List;

import io.reactivex.Observable;

public interface SearchRoutesService {
    Observable<List<RouteEntity>> findRoutesById(String routeId);
}
