package com.mbcdev.folkets;

import android.support.annotation.NonNull;

import com.zendesk.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Models a collection of {@link SaldoLink}
 *
 * Created by barry on 21/08/2016.
 */
class SaldoLinks implements Serializable {

    private final List<SaldoLink> links;

    /**
     * Creates an instance from the raw database value
     *
     * @param rawValue the raw database value
     */
    SaldoLinks(String rawValue) {

        if (StringUtils.isEmpty(rawValue)) {
            links = Collections.emptyList();
            return;
        }

        String[] rawLinks = rawValue.split(Utils.ASTERISK_SEPARATOR);
        links = new ArrayList<>();

        for (String rawLink : rawLinks) {
            if (StringUtils.hasLength(rawLink)) {
                SaldoLink saldoLink = new SaldoLink(rawLink);
                if (saldoLink.hasValidLinks()) {
                    links.add(saldoLink);
                }
            }
        }
    }

    /**
     * Gets the saldo links
     *
     * @return the saldo links
     */
    @NonNull List<SaldoLink> getLinks() {
        return links;
    }
}