package com.edulog.driverportal.routedetails.presentation;

import com.edulog.driverportal.common.device.Session;
import com.edulog.driverportal.routedetails.domain.GetRouteUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observer;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MotionPresenterTest {
    private MotionContract.MotionPresenter motionPresenter;

    @Mock
    private GetRouteUseCase mockGetRouteUseCase;
    @Mock
    private Session mockSession;
    @Mock
    private MotionContract.MotionView mockMotionView;

    @Before
    public void setUp() {
        motionPresenter = new MotionPresenterImpl(mockGetRouteUseCase, mockSession);
        motionPresenter.attach(mockMotionView);
    }

    @Test
    public void getActiveRouteTest() throws Exception {
        when(mockSession.getRouteId()).thenReturn("123");

        motionPresenter.getActiveRoute();

        verify(mockSession).getRouteId();
        verify(mockMotionView).showProgress();
        verify(mockGetRouteUseCase).execute(any(Observer.class), anyString());
    }
}