package com.github.pedrovgs.effectiveandroidui.domain;

import com.github.pedrovgs.effectiveandroidui.domain.GetTvShows;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.Catalog;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.TvShow;
import com.github.pedrovgs.effectiveandroidui.executor.Executor;
import com.github.pedrovgs.effectiveandroidui.executor.Interactor;
import com.github.pedrovgs.effectiveandroidui.executor.MainThread;
import com.github.pedrovgs.effectiveandroidui.util.RandomUtilsRefactored;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Created by fina on 26/06/14.
 */
class GetTvShowsInteractorRefactored implements Interactor, GetTvShows {

    private static final int PERCENTAGE_OF_FAILS = 50;
    public static final int WAIT_TIME = 1500;

    private final Catalog catalog;
    private final Executor executor;
    private final MainThread mainThread;
    private final RandomUtilsRefactored randomUtils;

    private Callback callback;

    @Inject GetTvShowsInteractorRefactored(Catalog catalog, Executor executor, MainThread mainThread,
                                   RandomUtilsRefactored randomUtils) {
        this.catalog = catalog;
        this.executor = executor;
        this.mainThread = mainThread;
        this.randomUtils = randomUtils;
    }

    @Override public void execute(final Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException(
                    "Callback can't be null, the client of this interactor needs to get the response in the callback");
        }
        this.callback = callback;
        this.executor.run(this);
    }

    @Override public void run() {
        waitToDoThisSampleMoreInteresting();

        if (haveToShowError()) {
            notifyError();
        } else {
            Collection<TvShow> tvShows = catalog.getTvShows();
            nofityTvShowsLoaded(tvShows);
        }
    }

    /**
     * To simulate a we are getting the TvShows data from internet we are going to force a 1.5
     * seconds
     * delay using Thread.sleep.
     */
    private void waitToDoThisSampleMoreInteresting() {
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            //Empty
        }
    }

    private boolean haveToShowError() {
        return randomUtils.percent(PERCENTAGE_OF_FAILS);
    }

    private void notifyError() {
        mainThread.post(new Runnable() {
            @Override public void run() {
                callback.onConnectionError();
            }
        });
    }

    private void nofityTvShowsLoaded(final Collection<TvShow> tvShows) {
        mainThread.post(new Runnable() {
            @Override public void run() {
                callback.onTvShowsLoaded(tvShows);
            }
        });
    }
}
