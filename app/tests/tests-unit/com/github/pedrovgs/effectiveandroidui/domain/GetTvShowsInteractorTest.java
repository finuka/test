package com.github.pedrovgs.effectiveandroidui.domain;

import com.github.pedrovgs.effectiveandroidui.domain.tvshow.Catalog;
import com.github.pedrovgs.effectiveandroidui.executor.Executor;
import com.github.pedrovgs.effectiveandroidui.test.doubles.SynchronousExecutor;
import com.github.pedrovgs.effectiveandroidui.test.doubles.SynchronousMainThread;
import com.github.pedrovgs.effectiveandroidui.util.RandomUtilsRefactored;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by fina on 26/06/14.
 */
public class GetTvShowsInteractorTest {

    @Mock
    Catalog catalogMock;

    @Mock
    GetTvShows.Callback callbackMock;

    @Mock
    RandomUtilsRefactored randomUtilsMock;

    private GetTvShowsInteractor getTvShowsInteractor;
    private GetTvShowsInteractorRefactored getTvShowsInteractorRefactored;

    private SynchronousMainThread synchronousMainThread;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        synchronousMainThread = new SynchronousMainThread();

        getTvShowsInteractor = new GetTvShowsInteractor(catalogMock,
                new SynchronousExecutor(),
                synchronousMainThread);

        getTvShowsInteractorRefactored = new GetTvShowsInteractorRefactored(catalogMock, new SynchronousExecutor(),
                synchronousMainThread, randomUtilsMock);
    }

    /*
      @Override public void execute(final Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException(
            "Callback can't be null, the client of this interactor needs to get the response in the callback");
        }
        this.callback = callback;
        this.executor.run(this);
    }
    Tenemos dos casos:
        - cuando callback es null, debemos comprobar el contrato y ver que efectivamente se lanza la excepción
        - si el callback no es null, el executor debe llamar al método run.
            Para verificar esto, debemos tener control sobre dicho executor. Usando mockito, se puede hacer un
            verify sobre él.
     */

    @Test(expected = IllegalArgumentException.class)
    public void executeShouldThrowsAnExceptionWhenCallbackIsNull() {
        //given
        getTvShowsInteractor.execute(null);
    }

    @Test
    public void executeShouldRunExecutor() { //TODO, BETTER NAME!!!
        //given
        Executor executorMock = Mockito.mock(Executor.class);
        GetTvShowsInteractor interactor =  new GetTvShowsInteractor(catalogMock, executorMock, synchronousMainThread);

        //when
        interactor.execute(callbackMock);

        //then
        verify(executorMock).run(interactor);
    }

    /*
     @Override public void run() {
        waitToDoThisSampleMoreInteresting();

        if (haveToShowError()) {
            notifyError();
        } else {
            Collection<TvShow> tvShows = catalog.getTvShows();
        nofityTvShowsLoaded(tvShows);
        }
     }

     Problema:
        No tenemos control sobre ese "haveToShowError"
     Solución:
        1) Usar powermock
        2) Refactorizar la clase, wrappeando esa llamada y pasandola como colaborador.
        RandomUtils es estático. Hay que refactorizarla para evitar eso

     */

    @Test
    public void runShouldShowError() {
        //given
        when(randomUtilsMock.percent(anyInt())).thenReturn(true);

        //when
        getTvShowsInteractorRefactored.run();

        //then
        verify(callbackMock).onConnectionError();
    }

    @Test
    public void runShouldLoadShows() {
        //given
        when(randomUtilsMock.percent(anyInt())).thenReturn(false);

        //when
        getTvShowsInteractorRefactored.run();

        //then
        verify(callbackMock).onTvShowsLoaded(catalogMock.getTvShows());
    }

}
