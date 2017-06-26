package com.edulog.driverportal.routeselection.presentation.presenter;

import com.edulog.driverportal.routeselection.domain.interactor.GetRouteUseCase;
import com.edulog.driverportal.routeselection.model.LoadMode;

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
    private PreviewRouteContract.PreviewRoutePresenter previewRoutePresenter;

    @Mock
    private GetRouteUseCase mockGetRouteUseCase;
    @Mock
    private PreviewRouteContract.PreviewRouteView mockPreviewRouteView;

    @Before
    public void setUp() throws Exception {
        previewRoutePresenter = new PreviewRoutePresenterImpl(mockGetRouteUseCase);
        previewRoutePresenter.attach(mockPreviewRouteView);
    }

    @Test
    public void previewRoute_useCaseExecute() throws Exception {
        previewRoutePresenter.previewRoute("", LoadMode.REMOTE);

        verify(mockGetRouteUseCase).execute(any(Observer.class), any(GetRouteUseCase.Params.class));
    }

    @Test
    public void previewRoute_viewShowProgress() throws Exception {
        previewRoutePresenter.previewRoute("", LoadMode.REMOTE);

        verify(mockPreviewRouteView).showProgress();
    }
}