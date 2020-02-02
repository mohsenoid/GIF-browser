package com.mohsenoid.gifbrowser.util.config

/**
 * Application Config Provider.
 */
interface ConfigProvider {

    /**
     * Checks network connectivity status.
     */
    fun isOnline(): Boolean
}
