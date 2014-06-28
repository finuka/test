package com.github.pedrovgs.effectiveandroidui.domain;

import com.github.pedrovgs.effectiveandroidui.domain.exception.TvShowNotFoundException;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.Catalog;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.TvShow;
import com.github.pedrovgs.effectiveandroidui.executor.Executor;
import com.github.pedrovgs.effectiveandroidui.test.doubles.SynchronousExecutor;
import com.github.pedrovgs.effectiveandroidui.test.doubles.SynchronousMainThread;
import com.github.pedrovgs.effectiveandroidui.util.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by fina on 28/06/14.
 */
public class GetTvShowByIdInteractorTest {

    private static final String ANY_TVSHOWID = "any_tvShowID";


    @Mock
    Catalog catalogMock;

    @Mock
    GetTvShowById.Callback callbackMock;

    @Mock
    RandomUtils randomUtilsMock;

    @Mock
    TvShow tvShowMock;

    private GetTvShowByIdInteractor getTvShowByIdInteractor;

    private SynchronousMainThread synchronousMainThread;
    private SynchronousExecutor synchronousExecutor;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        synchronousMainThread = new SynchronousMainThread();
        synchronousExecutor = new SynchronousExecutor();

        getTvShowByIdInteractor = new GetTvShowByIdInteractor(synchronousExecutor, synchronousMainThread,
                catalogMock, randomUtilsMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeShouldThrowsAnExceptionWhenCallbackIsNull() {
        //given
        getTvShowByIdInteractor.execute(ANY_TVSHOWID, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeShouldThrowsAnExceptionWhenTvShowIdIsNull() {
        //given
        getTvShowByIdInteractor.execute(null, callbackMock);
    }

    @Test
    public void executeShouldRun() {
        //given
        Executor executorMock = Mockito.mock(Executor.class);

        GetTvShowByIdInteractor interactor = new GetTvShowByIdInteractor(executorMock, synchronousMainThread,
                catalogMock, randomUtilsMock);

        //when
        interactor.execute(ANY_TVSHOWID, callbackMock);

        //then
        verify(executorMock).run(interactor);

    }

    @Test
    public void runShouldShowError() {
        //given
        when(randomUtilsMock.expectedPercent(anyInt())).thenReturn(true);
        getTvShowByIdInteractor.setCallback(callbackMock);

        //when
        getTvShowByIdInteractor.run();

        //then
        verify(callbackMock).onConnectionError();
    }

    @Test
    public void runShouldShowTv() throws TvShowNotFoundException {
        //given
        when(catalogMock.getTvShowById(anyString())).thenReturn(tvShowMock);
        when(randomUtilsMock.expectedPercent(anyInt())).thenReturn(false);
        getTvShowByIdInteractor.setCallback(callbackMock);

        //when
        getTvShowByIdInteractor.run();

        //then
        verify(callbackMock).onTvShowLoaded(tvShowMock);
    }

    @Test
    public void runShouldNotifyTvShowNotFound() throws TvShowNotFoundException {
        //given
        when(catalogMock.getTvShowById(anyString())).thenThrow(new TvShowNotFoundException());
        when(randomUtilsMock.expectedPercent(anyInt())).thenReturn(false);
        getTvShowByIdInteractor.setCallback(callbackMock);

        //when
        getTvShowByIdInteractor.run();

        //then
        verify(callbackMock).onTvShowNotFound();
    }

}
