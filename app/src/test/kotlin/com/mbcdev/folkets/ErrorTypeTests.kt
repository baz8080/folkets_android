package com.mbcdev.folkets

import org.junit.Test
import com.google.common.truth.Truth.assertThat

/**
 * Tests for [ErrorType]
 *
 * Created by barry on 26/09/2016.
 */
class ErrorTypeTests {

    @Test
    fun nullDatabaseErrorHasCorrectMessage() {
        assertThat(ErrorType.DATABASE_NULL.stringResourceId).isEqualTo(R.string.error_database_null)
    }
}
