package com.edulog.driverportal.routedetails.domain;

import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.common.domain.UseCase;
import com.edulog.driverportal.routedetails.model.MotionEvent;

import io.reactivex.Observable;

public class SendEventUseCase extends UseCase<MotionEvent, SendEventUseCase.Params> {
    private EventService eventService;
    private Session session;

    public SendEventUseCase(EventService eventService, Session session) {
        this.eventService = eventService;
        this.session = session;
    }

    @Override
    public Observable<MotionEvent> buildUseCaseObservable(Params params) {
        MotionEvent motionEvent = new MotionEvent();
        motionEvent.setLatitude(params.latitude);
        motionEvent.setLongitude(params.longitude);

        double speed = params.speed;
        double speedThreshold = session.getSpeedThreshold();
        if (speed > speedThreshold) {
            return eventService.createEvent(motionEvent.getLatitude(), motionEvent.getLongitude())
                    .flatMap(response -> {
                        if (response.code() == 200)
                            return Observable.just(motionEvent);
                        else
                            return Observable.error(new RuntimeException("aaaa"));
                    });
        } else {
            // placeholder
            return Observable.error(new RuntimeException("Speed"));
            // placeholder
        }
    }

    public static Params buildParams(double latitude, double longitude, double speed) {
        Params params = new Params();

        params.latitude = latitude;
        params.longitude = longitude;
        params.speed = speed;

        return params;
    }

    public static class Params {
        public double latitude;
        public double longitude;
        public double speed;
    }
}
