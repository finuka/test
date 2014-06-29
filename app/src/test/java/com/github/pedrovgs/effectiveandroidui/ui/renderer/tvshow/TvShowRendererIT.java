package com.github.pedrovgs.effectiveandroidui.ui.renderer.tvshow;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.github.pedrovgs.effectiveandroidui.R;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.TvShow;
import com.github.pedrovgs.effectiveandroidui.ui.presenter.TvShowCatalogPresenter;
import com.github.pedrovgs.effectiveandroidui.ui.renderer.tvshow.TvShowRenderer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;

/**
 * Created by fina on 29/06/14.
 */
@Config(manifest = "app/src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class TvShowRendererIT {

    private static final String ANY_TITLE = "Any title";
    private static final String ANY_POSTER = "Any poster";
    private static final String ANY_FANART = "Any fan art";
    private static final int ANY_NUMBER_OF_SEASONS = 2;
    @Mock
    private TvShowCatalogPresenter tvShowCatalogPresenterMock;

    private TvShowRenderer tvShowRenderer;



    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        Context context = Robolectric.application.getApplicationContext();

        tvShowRenderer = new TvShowRenderer(context, tvShowCatalogPresenterMock);

    }

    @Test
    public void onThumbnailClickedShouldDelegateInPresenter() {
        tvShowRenderer.onThumbnailClicked();

        verify(tvShowCatalogPresenterMock).onTvShowThumbnailClicked(any(TvShow.class));
    }

    @Test
    public void onBackgroundClickedShouldDelegateInPresenter() {
        tvShowRenderer.onBackgroundClicked();

        verify(tvShowCatalogPresenterMock).onTvShowClicked(any(TvShow.class));
    }

    @Test
    public void renderShouldInitializeTheView() {

        ActivityController<Activity> activityController = Robolectric.buildActivity(Activity.class);
        Activity activity = activityController.create().get();
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        tvShowRenderer.onCreate(initTvShow(), layoutInflater, null);

        View view = tvShowRenderer.getRootView();

        tvShowRenderer.render();

        TextView titleTexView = (TextView) view.findViewById(R.id.tv_title);
        assertThat(titleTexView.getText().toString(), is(equalToIgnoringCase(ANY_TITLE)));

        TextView counterTexView = (TextView) view.findViewById(R.id.tv_seasons_counter);
        String seasonsCounter = activity.getString(R.string.seasons_counter, ANY_NUMBER_OF_SEASONS);
        assertThat(counterTexView.getText().toString(), is(equalToIgnoringCase(seasonsCounter)));

    }

    private TvShow initTvShow() {
        return new TvShow(ANY_TITLE, ANY_POSTER, ANY_FANART, ANY_NUMBER_OF_SEASONS);
    }

}
