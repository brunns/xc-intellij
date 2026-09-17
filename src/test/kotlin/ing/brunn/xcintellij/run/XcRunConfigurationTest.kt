package ing.brunn.xcintellij.run

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class XcRunConfigurationTest : BasePlatformTestCase() {
    fun testTaskNameOptionPersistence() {
        val type = XcConfigurationType()
        val factory = type.configurationFactories.first()
        val config = XcRunConfiguration(project, factory, "xc build")

        assertEquals("", config.taskName)

        config.taskName = "build"
        assertEquals("build", config.taskName)
    }

    fun testConfigurationEditorCreation() {
        val type = XcConfigurationType()
        val factory = type.configurationFactories.first()
        val config = XcRunConfiguration(project, factory, "xc test")

        val editor = config.configurationEditor
        assertNotNull(editor)
        assertInstanceOf(editor, XcRunConfigurationSettingsEditor::class.java)
    }
}
