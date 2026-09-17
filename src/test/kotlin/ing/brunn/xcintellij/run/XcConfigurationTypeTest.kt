package ing.brunn.xcintellij.run

import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class XcConfigurationTypeTest : BasePlatformTestCase() {
    fun testConfigurationTypeIsRegistered() {
        val type = ConfigurationTypeUtil.findConfigurationType(XcConfigurationType::class.java)
        assertNotNull(type)
        assertEquals("xc Task", type.displayName)
        assertEquals("XcRunConfigurationType", type.id)
        assertEquals(1, type.configurationFactories.size)
    }

    fun testFactoryCreatesTemplateConfiguration() {
        val type = XcConfigurationType()
        val factory = type.configurationFactories.first()
        val config = factory.createTemplateConfiguration(project)

        assertInstanceOf(config, XcRunConfiguration::class.java)
        assertEquals("xc", config.name)
    }
}
