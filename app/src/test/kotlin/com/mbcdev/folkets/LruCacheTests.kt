package com.mbcdev.folkets


import com.google.common.truth.Truth.*
import org.junit.Test

/**
 * Tests for [LruCache]
 *
 * Created by barry on 14/07/2017.
 */
class LruCacheTests {

    @Test
    fun `cache should not use default size when a zero size is specified`() {
        val cache = LruCache<String, String>(0)
        assertThat(cache.maximumSize).isEqualTo(10)
    }

    @Test
    fun `cache should not use default size when a negative size is specified`() {
        val cache = LruCache<String, String>(-1)
        assertThat(cache.maximumSize).isEqualTo(10)
    }

    @Test
    fun `cache should have the correct number of elements`() {

        val cache = LruCache<String, String>(3)

        cache["key1"] = "value1"
        cache["key2"] = "value2"
        cache["key3"] = "value3"

        assertThat(cache).hasSize(3)
    }

    @Test
    fun `elements in the cache should be in the correct order`() {

        val cache = LruCache<String, String>(3)

        cache["key1"] = "value1"
        cache["key2"] = "value2"
        cache["key3"] = "value3"

        assertThat(cache).hasSize(3)

        assertThat(cache.keys)
                .containsExactly("key1", "key2", "key3")
                .inOrder()

        assertThat(cache.values)
                .containsExactly("value1", "value2", "value3")
                .inOrder()
    }

    @Test
    fun `the eldest element should be evicted when the cache size limit is reached`() {

        val cache = LruCache<String, String>(3)

        cache["key1"] = "value1"
        cache["key2"] = "value2"
        cache["key3"] = "value3"
        cache["key4"] = "value4"

        assertThat(cache).hasSize(3)

        assertThat(cache.keys)
                .containsExactly("key2", "key3", "key4")
                .inOrder()

        assertThat(cache.values)
                .containsExactly("value2", "value3", "value4")
                .inOrder()
    }

    @Test
    fun `the cache should respect access order when evicting elements`() {

        val cache = LruCache<String, String>(3)

        cache["key1"] = "value1"
        cache["key2"] = "value2"
        cache["key3"] = "value3"

        val firstElement = cache["key1"]
        assertThat(firstElement).isEqualTo("value1")

        // 1 is promoted, so we have 2, 3, 1

        cache["key4"] = "value4"

        // 2 is evicted, so we have 3, 1 4

        assertThat(cache).hasSize(3)

        assertThat(cache.keys)
                .containsExactly("key3", "key1", "key4")
                .inOrder()

        assertThat(cache.values)
                .containsExactly("value3", "value1", "value4")
                .inOrder()
    }
}