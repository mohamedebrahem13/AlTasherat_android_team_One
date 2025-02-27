package com.solutionplus.altasherat.android.helpers.logging.writers

import com.solutionplus.altasherat.android.helpers.logging.LogWriter

/**
 * Do not writes anything
 */
class DummyWriter : LogWriter {
    override val isDebugEnabled: Boolean
        get() = false

    override fun debug(clazz: Class<*>, message: String?) {}

    override fun info(clazz: Class<*>, message: String?) {}

    override fun warning(clazz: Class<*>, message: String?) {}

    override fun error(clazz: Class<*>, message: String?, throwable: Throwable?) {}
}