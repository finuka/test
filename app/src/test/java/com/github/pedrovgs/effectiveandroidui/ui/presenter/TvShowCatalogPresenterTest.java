package com.github.pedrovgs.effectiveandroidui.ui.presenter;

import com.github.pedrovgs.effectiveandroidui.domain.GetTvShows;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.TvShow;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by fina on 28/06/14.
 */
public class TvShowCatalogPresenterTest {

    @Mock
    GetTvShows getTvShowsMock;

    @Mock
    TvShowCatalogPresenter.View viewMock;

    @Mock
    TvShow tvShowMock;

    private TvShowCatalogPresenter tvShowCatalogPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        tvShowCatalogPresenter = new TvShowCatalogPresenter(getTvShowsMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setViewWithNullViewShouldThrowsAnException() {

        tvShowCatalogPresenter.setView(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void initializeShouldThrowsAnExceptionWhenViewIsNotSet() {

        tvShowCatalogPresenter.initialize();

    }

    @Test
    public void loadTvShowsShouldShowLoadingWhenViewIsReady() {
        when(viewMock.isReady()).thenReturn(true);

        tvShowCatalogPresenter.setView(viewMock);
        tvShowCatalogPresenter.initialize();

        verify(viewMock).showLoading();
    }

    @Test
    public void initializeShouldLoadsVideos() {

        tvShowCatalogPresenter.setView(viewMock);
        tvShowCatalogPresenter.initialize();

        verify(getTvShowsMock).execute(any(GetTvShows.Callback.class));

    }

    @Test(expected = IllegalArgumentException.class)
    public void resumeShouldThrowsAnExceptionWhenViewIsNotSet() {
        tvShowCatalogPresenter.resume();
    }

    @Test
    public void onTvShowThumbnailClickedShouldShowTvShow() {

        tvShowCatalogPresenter.setView(viewMock);

        tvShowCatalogPresenter.onTvShowThumbnailClicked(tvShowMock);

        verify(viewMock).showTvShow(tvShowMock);

    }

    @Test
    public void onTvShowClickedShouldShowTvShowInfo() {

        tvShowCatalogPresenter.setView(viewMock);

        tvShowCatalogPresenter.onTvShowClicked(tvShowMock);

        verify(viewMock).showTvShowInfo(tvShowMock);
    }

}
