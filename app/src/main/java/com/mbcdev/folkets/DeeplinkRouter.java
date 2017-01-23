package com.mbcdev.folkets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zendesk.sdk.model.helpcenter.SimpleArticle;
import com.zendesk.sdk.support.ViewArticleActivity;
import com.zendesk.util.CollectionUtils;
import com.zendesk.util.StringUtils;

import java.util.List;

import timber.log.Timber;

/**
 * Activity to route deep-links.
 * <p>
 *     HC V1
 *     folkets://hc/v1/{articleId}
 *     folkets://hc/v1/212412429
 * </p>
 *
 *
 * Created by barry on 09/01/2017.
 */
public class DeeplinkRouter extends AppCompatActivity {

    private static final String FOLKETS_SCHEME = "folkets";
    private static final String URI_HELP_CENTER_HOST = "hc";
    private static final String URI_VERSION_1 = "v1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!intentHasValidData()) {
            finish();
            return;
        }

        routeIntent(getIntent().getData());
        finish();
    }

    private boolean intentHasValidData() {
        Intent intent = getIntent();
        Uri dataUri = intent.getData();

        boolean isFolketsScheme = dataUri != null
                && intent.getScheme() != null
                && FOLKETS_SCHEME.equals(intent.getScheme());

        boolean hasPathSegments = dataUri != null
                && CollectionUtils.isNotEmpty(dataUri.getPathSegments());

        boolean hasHost = dataUri != null
                && StringUtils.hasLength(dataUri.getHost());

        Timber.d("Has correct scheme: %b, has path segments: %b, has host: %b",
                isFolketsScheme, hasPathSegments, hasHost);

        return isFolketsScheme && hasPathSegments;
    }

    private void routeIntent(Uri data) {

        String host = data.getHost();
        List<String> pathSegments = data.getPathSegments();
        Timber.d("routing intent for host %s", host);

        if (URI_HELP_CENTER_HOST.equals(host)) {

            if (pathSegments.size() < 2) {
                Timber.d("hc deep-links require at least 2 path segments");
                return;
            }

            String version = pathSegments.get(0);
            Timber.d("hc url version %s has been requested", version);
            Timber.d("got %d path segments", pathSegments.size());

            if (URI_VERSION_1.equals(version) && pathSegments.size() == 2) {

                Long articleId = valueOf(pathSegments.get(1));
                Timber.d("hc article %d has been requested", articleId);

                if (articleId != null) {
                    SimpleArticle article = new SimpleArticle(articleId, null);
                    ViewArticleActivity.startActivity(this, article);
                }
            }
        }
    }

    /**
     * Gets the value of a long, or null.
     *
     * @param string the string to parse
     * @return The value of the long, or null if there was an issue with parsing
     */
    private Long valueOf(String string) {

        Long number = null;

        try {
            number = Long.valueOf(string);
        } catch (NumberFormatException e) {
            Timber.e(e);
        }

        return number;
    }
}
