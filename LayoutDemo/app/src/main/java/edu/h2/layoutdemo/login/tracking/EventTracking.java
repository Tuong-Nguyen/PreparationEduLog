package edu.h2.layoutdemo.login.tracking;

import edu.h2.layoutdemo.login.domain.services.EventService;
import edu.h2.layoutdemo.login.models.Event;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Implement Event tracking after sending request
 */

public class EventTracking {
    private EventService eventService;
    private CompositeDisposable disposables;

    public EventTracking(EventService eventService) {
        this.eventService = eventService;
        disposables = new CompositeDisposable();
    }

    /**
     * Build event Observable
     * @param event
     * @return
     */
    public Observable<Boolean> buildEventObservable(Event event) {
        return eventService.sendEvent(event);
    }

    /**
     * Executing the use case.
     * @param observer
     * @param event
     */
    public void execute(DisposableObserver<Boolean> observer, Event event) {
        final Observable<Boolean> observable = this.buildEventObservable(event);
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
