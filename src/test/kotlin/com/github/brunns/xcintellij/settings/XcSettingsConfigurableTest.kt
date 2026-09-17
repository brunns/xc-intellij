package com.github.brunns.xcintellij.settings

import com.github.brunns.xcintellij.services.XcSettingsConfigurable
import com.github.brunns.xcintellij.services.XcSettingsState
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class XcSettingsConfigurableTest : BasePlatformTestCase() {

    private lateinit var configurable: XcSettingsConfigurable

    override fun setUp() {
        super.setUp()
        configurable = XcSettingsConfigurable()
        configurable.createComponent() // Instantiates Swing components
    }

    fun testIsModifiedWhenPathChanges() {
        val settings = XcSettingsState.instance
        settings.xcExecutablePath = "xc"
        configurable.reset()

        assertFalse(configurable.isModified)

        configurable.pathField.text = "/custom/path/to/xc"
        assertTrue(configurable.isModified)
    }

    fun testApplyUpdatesSettingsState() {
        configurable.pathField.text = "/usr/bin/xc"
        configurable.apply()

        assertEquals("/usr/bin/xc", XcSettingsState.instance.xcExecutablePath)
    }

    fun testResetRestoresOriginalValue() {
        XcSettingsState.instance.xcExecutablePath = "/original/path/xc"
        configurable.reset()

        assertEquals("/original/path/xc", configurable.pathField.text)

        configurable.pathField.text = "/changed/path/xc"
        configurable.reset()

        assertEquals("/original/path/xc", configurable.pathField.text)
    }
}