package com.mbcdev.folkets;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
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
    SaldoLinks(@NonNull Context context, @NonNull String rawValue) {

        String[] rawLinks = rawValue.split(Utils.ASTERISK_SEPARATOR);
        links = new ArrayList<>();

        for (String rawLink : rawLinks) {
            links.add(new SaldoLink(context, rawLink));
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

    @Override
    public String toString() {
        return "SaldoLinks{" +
                "links=" + links +
                '}';
    }
}