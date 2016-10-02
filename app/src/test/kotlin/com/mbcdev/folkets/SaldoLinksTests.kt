package com.mbcdev.folkets

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Tests for [SaldoLinks]
 *
 * Created by barry on 02/10/2016.
 */
class SaldoLinksTests {

    @Test
    fun constructorShouldBeNullSafeForRawValue() {
        SaldoLinks(null)
    }

    @Test
    fun linksShouldBeEmptyWhenRawValueIsNull() {
        val saldoLinks = SaldoLinks(null)
        assertThat(saldoLinks.links).isEmpty()
    }

    @Test
    fun linksShouldBeEmptyWhenRawValueIsEmpty() {
        val saldoLinks = SaldoLinks("")
        assertThat(saldoLinks.links).isEmpty()
    }

    @Test
    fun linksShouldBeEmptyWhenRawValueIsUntrimmed() {
        val saldoLinks = SaldoLinks(" \t\n")
        assertThat(saldoLinks.links).isEmpty()
    }

    @Test
    fun linksShouldBeEmptyWhenRawValueIsSeparator() {
        val saldoLinks = SaldoLinks("**")
        assertThat(saldoLinks.links).isEmpty()
    }

    @Test
    fun linksShouldBeEmptyWhenRawValueIsSeparatorUntrimmed() {
        val saldoLinks = SaldoLinks(" ** ")
        assertThat(saldoLinks.links).isEmpty()
    }

    @Test
    fun singleLinkShouldBeParsed() {
        val saldoLinks = SaldoLinks("ackord||ackord..1||ackord..nn.1")
        assertThat(saldoLinks.links).hasSize(1)
    }

    @Test
    fun singleLinkShouldBeParsedWithTrailingSpace() {
        val saldoLinks = SaldoLinks("ackord||ackord..1||ackord..nn.1 ")
        assertThat(saldoLinks.links).hasSize(1)
    }

    @Test
    fun singleLinkShouldBeParsedWithTrailingDelimiter() {
        val saldoLinks = SaldoLinks("ackord||ackord..1||ackord..nn.1" +
                "**")
        assertThat(saldoLinks.links).hasSize(1)
    }

    @Test
    fun twoLinksShouldBeParsed() {
        val saldoLinks = SaldoLinks("ackord||ackord..1||ackord..nn.1" +
                "**" +
                "tack||tack..1||tack..nn.1"
        )
        assertThat(saldoLinks.links).hasSize(2)
    }

    @Test
    fun twoLinksShouldBeParsedWithTrailingDelimiter() {
        val saldoLinks = SaldoLinks("ackord||ackord..1||ackord..nn.1" +
                "**" +
                "tack||tack..1||tack..nn.1" +
                "**"
        )
        assertThat(saldoLinks.links).hasSize(2)
    }

    @Test
    fun twoLinksShouldBeParsedWithLeadingWhitespace() {
        val saldoLinks = SaldoLinks("ackord||ackord..1||ackord..nn.1" +
                "** " +
                "tack||tack..1||tack..nn.1")
        assertThat(saldoLinks.links).hasSize(2)
    }
}