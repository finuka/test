package com.github.pedrovgs.effectiveandroidui.ui.presenter;

import com.github.pedrovgs.effectiveandroidui.domain.GetTvShows;
import com.github.pedrovgs.effectiveandroidui.domain.TvShowsModule;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.Catalog;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.Chapter;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.TvShow;
import com.github.pedrovgs.effectiveandroidui.executor.Executor;
import com.github.pedrovgs.effectiveandroidui.executor.ExecutorModule;
import com.github.pedrovgs.effectiveandroidui.executor.MainThread;
import com.github.pedrovgs.effectiveandroidui.test.doubles.SynchronousExecutor;
import com.github.pedrovgs.effectiveandroidui.test.doubles.SynchronousMainThread;
import com.github.pedrovgs.effectiveandroidui.ui.renderer.tvshow.TvShowCollection;
import com.github.pedrovgs.effectiveandroidui.util.RandomUtils;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * Created by fina on 28/06/14.
 */
public class TvShowCatalogPresenterIT {

    @Inject
    GetTvShows getTvShows;

    @Inject
    Catalog catalog;

    @Inject
    RandomUtils randomUtils;

    @Mock
    TvShowCatalogPresenter.View view;


    private TvShowCatalogPresenter tvShowCatalogPresenter;

    private Collection<TvShow> expectedTvShowCollection;

    @Before public void setUp() {
        ObjectGraph.create(new TestModule()).inject(this);

        MockitoAnnotations.initMocks(this);

        tvShowCatalogPresenter = new TvShowCatalogPresenter(getTvShows);
        tvShowCatalogPresenter.setView(view);

        initTvShowsMock();

    }

    private void initTvShowsMock() {
        TvShow  tvShow = new TvShow("House of Cards", "http://thetvdb.com/banners/_cache/posters/79861-1.jpg",
                "http://thetvdb.com/banners/_cache/fanart/original/79861-3.jpg", 3);
        tvShow.addEpisode(new Chapter("House of Cards Episode 1", "1990-11-18"));
        tvShow.addEpisode(new Chapter("House of Cards Episode 2", "1990-11-25"));
        tvShow.addEpisode(new Chapter("House of Cards Episode 3", "1990-12-02"));
        tvShow.addEpisode(new Chapter("House of Cards Episode 4", "1990-12-09"));

        expectedTvShowCollection = new LinkedHashSet<TvShow>();
        expectedTvShowCollection.add(tvShow);
    }

    @Module(
            includes = {
                    ExecutorModule.class, TvShowsModule.class,
            },
            injects = TvShowCatalogPresenterIT.class,
            overrides = true
    )
    static class TestModule {

        @Provides @Singleton Catalog provideCatalog() {
            return mock(Catalog.class);
        }

        @Provides MainThread provideMainThread() {
            return new SynchronousMainThread();
        }

        @Provides Executor provideExecutor() {
            return new SynchronousExecutor();
        }

        @Provides @Singleton
        RandomUtils provideRandomUtils() {
            return mock(RandomUtils.class);
        }
    }


    @Test
    public void initializeShouldLoadTvShows() {
        when(randomUtils.expectedPercent(anyInt())).thenReturn(false);
        when(catalog.getTvShows()).thenReturn(expectedTvShowCollection);

        tvShowCatalogPresenter.initialize();

        TvShowCollection actualTvShowCollection = tvShowCatalogPresenter.getCurrentTvShows();

        assertTrue(actualTvShowCollection.getAsList().containsAll(expectedTvShowCollection));

    }

    @Test
    public void initializeShouldShowError() {
        when(randomUtils.expectedPercent(anyInt())).thenReturn(true);
        when(view.isReady()).thenReturn(true);

        tvShowCatalogPresenter.initialize();

        verify(view).isAlreadyLoaded();

    }

}
