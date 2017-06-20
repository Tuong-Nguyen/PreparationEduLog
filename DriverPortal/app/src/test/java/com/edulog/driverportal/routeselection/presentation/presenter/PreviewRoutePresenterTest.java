package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.PreviewRouteUseCase;
import com.edulog.driverportal.routeselection.presentation.view.PreviewRouteView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PreviewRoutePresenterTest {
    private PreviewRoutePresenter previewRoutePresenter;

    @Mock
    private PreviewRouteUseCase mockPreviewRouteUseCase;
    @Mock
    private PreviewRouteView mockPreviewRouteView;

    @Before
    public void setUp() throws Exception {
        previewRoutePresenter = new PreviewRoutePresenterImpl(mockPreviewRouteUseCase);
        previewRoutePresenter.attach(mockPreviewRouteView);
    }

    @Test
    public void previewRoute_useCaseExecute() throws Exception {
        previewRoutePresenter.previewRoute("");

        // TODO: we can assert the query instead of anyString
        verify(mockPreviewRouteUseCase).execute(any(Observer.class), anyString());
    }

    @Test
    public void previewRoute_viewShowProgress() throws Exception {
        previewRoutePresenter.previewRoute("");

        verify(mockPreviewRouteView).showProgress();
    }
}