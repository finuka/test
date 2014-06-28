package com.github.pedrovgs.effectiveandroidui.ui.presenter;

import com.github.pedrovgs.effectiveandroidui.domain.GetTvShows;
import com.github.pedrovgs.effectiveandroidui.ui.fragment.TvShowCatalogFragment;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

/**
 * Created by fina on 28/06/14.
 */
public class TvShowCatalogPresenterIT {

    @Inject
    GetTvShows getTvShows;

    @Mock
    TvShowCatalogPresenter.View view;

    private TvShowCatalogPresenter tvShowCatalogPresenter;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        tvShowCatalogPresenter = new TvShowCatalogPresenter(getTvShows);

    }



}
