package edu.h2.layoutdemo.login.tracking;

import edu.h2.layoutdemo.login.domain.services.EventService;
import edu.h2.layoutdemo.login.models.Events;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Implement Events tracking after sending request
 */

public class EventTracking {
    private EventService eventService;
    private CompositeDisposable disposables;

    public EventTracking(EventService eventService) {
        this.eventService = eventService;
        disposables = new CompositeDisposable();
    }

    /**
     * Build events Observable
     * @param events
     * @return
     */
    public Observable<Boolean> buildEventObservable(Events events) {
        return eventService.sendEvent(events);
    }

    /**
     * Executing the use case.
     * @param observer
     * @param events
     */
    public void execute(DisposableObserver<Boolean> observer, Events events) {
        final Observable<Boolean> observable = this.buildEventObservable(events);
        observable.subscribeWith(observer);

        addDisposable(observer);
    }

    /**
     * Dispose from current CompositeDisposable
     * @param disposable
     */
    private void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

}
