package com.github.pedrovgs.effectiveandroidui.ui.viewmodel.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.github.pedrovgs.effectiveandroidui.ui.activity.MainActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.util.ActivityController;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.robolectric.Robolectric.buildActivity;
import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by fina on 29/06/14.
 */
@RunWith(RobolectricTestRunner.class)
public class ShowTvShowOnBrowserActionCommandIT {

    private final static String TV_SHOW_URL = "http://any_url.com";

    private Activity context;
    private ShowTvShowOnBrowserActionCommand actionCommand;

    @Before
    public void setUp() {

        ActivityController<Activity> controller = buildActivity(Activity.class);
        context = controller.create().resume().get();

        actionCommand = new ShowTvShowOnBrowserActionCommand(context);

    }

    @Test
    public void shouldDoNothingIfURLIsNull() {
        actionCommand.execute();

        Intent intent = getNextStartedIntent();

        assertThat(intent, is(nullValue()));
    }

    @Test
    public void shouldLaunchIntentWhenURLIsNotNull() {
        actionCommand.setTvShowUrl(TV_SHOW_URL);
        actionCommand.execute();

        Intent intent = getNextStartedIntent();

        assertThat(intent.getData(), is(Uri.parse(TV_SHOW_URL)));

    }

    private Intent getNextStartedIntent() {
        return shadowOf(context).getShadowApplication().getNextStartedActivity();
    }

}
