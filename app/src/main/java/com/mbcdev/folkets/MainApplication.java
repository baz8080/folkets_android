package com.mbcdev.folkets;

import android.app.Application;

import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.access.AnonymousIdentity;
import com.zendesk.sdk.network.impl.ZendeskConfig;

import timber.log.Timber;
import zendesk.connect.ConnectSDK;
import zendesk.core.Identity;
import zendesk.core.Zendesk;

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
        initialiseSupportSdk();
        initialiseConnectSdk();
    }

    private void initialiseSupportSdk() {
        ZendeskConfig.INSTANCE.init(
                this,
                "https://z3nfolkets.zendesk.com",
                getString(R.string.FOLKETS_ZEN_APP_ID),
                getString(R.string.FOLKETS_ZEN_OAUTH_CLIENT_ID));

        ZendeskConfig.INSTANCE.setIdentity(new AnonymousIdentity.Builder().build());
    }

    private void initialiseConnectSdk() {

        Identity coreIdentity = new zendesk.core.AnonymousIdentity.Builder().build();

        Zendesk.INSTANCE.init(
                this,
                "https://z3nfolkets.zendesk.com",
                getString(R.string.FOLKETS_CONNECT_APP_ID),
                getString(R.string.FOLKETS_CONNECT_OAUTH_CLIENT_ID),
                coreIdentity);

        ConnectSDK.INSTANCE.init(Zendesk.INSTANCE);
    }
}
