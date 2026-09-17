package com.github.brunns.xcintellij.settings

import com.github.brunns.xcintellij.services.XcSettingsState
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class XcSettingsStateTest : BasePlatformTestCase() {

    fun testDefaultExecutablePathIsXc() {
        val settings = XcSettingsState.instance
        assertEquals("xc", settings.xcExecutablePath)
    }

    fun testStateModificationAndLoading() {
        val settings = XcSettingsState.instance
        settings.xcExecutablePath = "/usr/local/bin/xc"

        val newState = XcSettingsState()
        newState.loadState(settings.state)

        assertEquals("/usr/local/bin/xc", newState.xcExecutablePath)
    }
}