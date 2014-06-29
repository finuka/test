package com.github.pedrovgs.effectiveandroidui.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.github.pedrovgs.effectiveandroidui.R;
import com.github.pedrovgs.effectiveandroidui.domain.TvShowsModule;
import com.github.pedrovgs.effectiveandroidui.domain.tvshow.Catalog;
import com.github.pedrovgs.effectiveandroidui.executor.Executor;
import com.github.pedrovgs.effectiveandroidui.executor.ExecutorModule;
import com.github.pedrovgs.effectiveandroidui.executor.MainThread;
import com.github.pedrovgs.effectiveandroidui.test.doubles.SynchronousExecutor;
import com.github.pedrovgs.effectiveandroidui.test.doubles.SynchronousMainThread;
import com.github.pedrovgs.effectiveandroidui.ui.activity.TvShowActivity;
import com.github.pedrovgs.effectiveandroidui.ui.renderer.chapterviewmodel.ChapterViewModelRendererAdapter;
import com.github.pedrovgs.effectiveandroidui.ui.viewmodel.TvShowViewModel;
import com.github.pedrovgs.effectiveandroidui.util.RandomUtils;
import dagger.Module;
import dagger.Provides;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by fina on 29/06/14.
 */
@Config(manifest = "app/src/main/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class TvShowFragmentIT {

    private static final String ANY_TV_SHOW = "any_tv_show_id";

    @Inject
    TvShowViewModel tvShowViewModel;

    private TvShowFragment tvShowFragment;
    private TvShowActivity activity;

    @Module(
            includes = {
                    ExecutorModule.class, TvShowsModule.class,
            },
            injects = TvShowFragmentIT.class,
            overrides = true
    )
    static class TestModule {

        @Provides
        @Singleton
        TvShowViewModel provideTvShowViewModel() {
            return mock(TvShowViewModel.class);
        }
    }

    @Before
    public void setUp() {

        ActivityController<TvShowActivity> activityController = Robolectric.buildActivity(TvShowActivity.class);
        activity = activityController.create().resume().get();

        tvShowFragment = (TvShowFragment) activity.getSupportFragmentManager().findFragmentById(R.id.f_tv_show);
    }

    @Test
    public void onViewCreatedShouldInitializeTheView() {
        View view = new View(activity);

        tvShowFragment.onViewCreated(view, new Bundle());

        verifyViewsInitialization();
        verifyViewModelBinding();

    }

    private void verifyViewsInitialization() {
        ListView listView = (ListView) activity.findViewById(R.id.lv_chapters);

        assertThat(listView.getHeaderViewsCount(), greaterThan(0));
        assertThat(listView.getAdapter(), is(instanceOf(ChapterViewModelRendererAdapter.class)));

    }

    private void verifyViewModelBinding() {
        verify(tvShowViewModel).setListener(tvShowFragment);
        verify(tvShowViewModel).initialize();
    }

    @Test
    public void showTvShowShouldLoadShow() {

        tvShowFragment.showTvShow(ANY_TV_SHOW);

        verify(tvShowViewModel).loadTvShow(ANY_TV_SHOW);

    }


}
