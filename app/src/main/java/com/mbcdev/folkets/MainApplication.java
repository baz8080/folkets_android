package com.mbcdev.folkets;

import android.app.Application;

import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.access.AnonymousIdentity;
import com.zendesk.sdk.network.impl.ZendeskConfig;

import timber.log.Timber;

/**
 * Main application class. Installs a debug timber tree
 *
 * Created by barry on 20/08/2016.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        Logger.setLoggable(BuildConfig.DEBUG);

        ZendeskConfig.INSTANCE.init(
                this,
                "https://z3nfolkets.zendesk.com",
                getString(R.string.FOLKETS_ZEN_APP_ID),
                getString(R.string.FOLKETS_ZEN_OAUTH_CLIENT_ID));

        ZendeskConfig.INSTANCE.setIdentity(new AnonymousIdentity.Builder().build());
    }
}
