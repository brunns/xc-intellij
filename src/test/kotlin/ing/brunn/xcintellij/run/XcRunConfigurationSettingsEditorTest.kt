package ing.brunn.xcintellij.run

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class XcRunConfigurationSettingsEditorTest : BasePlatformTestCase() {
    private lateinit var type: XcConfigurationType
    private lateinit var config: XcRunConfiguration
    private lateinit var editor: XcRunConfigurationSettingsEditor

    override fun setUp() {
        super.setUp()
        type = XcConfigurationType()
        config = XcRunConfiguration(project, type.configurationFactories.first(), "xc test")
        editor = XcRunConfigurationSettingsEditor()
        editor.component
    }

    fun testResetEditorFromPopulatesTaskName() {
        config.taskName = "lint"
        editor.resetFrom(config)

        assertEquals("lint", editor.taskNameField.text)
    }

    fun testApplyEditorToUpdatesConfiguration() {
        editor.taskNameField.text = "docker-build"
        editor.applyTo(config)

        assertEquals("docker-build", config.taskName)
    }
}
