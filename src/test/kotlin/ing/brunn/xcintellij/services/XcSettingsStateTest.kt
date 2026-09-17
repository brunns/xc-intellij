package ing.brunn.xcintellij.services

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class XcSettingsStateTest : BasePlatformTestCase() {
    override fun tearDown() {
        try {
            XcSettingsState.instance.xcExecutablePath = "xc"
        } finally {
            super.tearDown()
        }
    }

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
