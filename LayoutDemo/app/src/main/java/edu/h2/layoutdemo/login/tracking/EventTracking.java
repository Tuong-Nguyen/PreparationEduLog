package edu.h2.layoutdemo.login.tracking;

import edu.h2.layoutdemo.login.domain.services.EventService;
import edu.h2.layoutdemo.login.models.Event;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by ntmhanh on 6/15/2017.
 */

public class EventTracking {
    private EventService eventService;
    private CompositeDisposable disposables;

    public EventTracking(EventService eventService) {
        this.eventService = eventService;
        disposables = new CompositeDisposable();
    }
    public Observable<Boolean> buildEventObservable(Event event) {
        return eventService.sendEvent(event);
    }

    public void execute(DisposableObserver<Boolean> observer, Event event) {
        final Observable<Boolean> observable = this.buildEventObservable(event);
        observable.subscribeWith(observer);

        addDisposable(observer);
    }

    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

}
