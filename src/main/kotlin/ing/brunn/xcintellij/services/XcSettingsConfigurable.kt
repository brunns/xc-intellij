package ing.brunn.xcintellij.services

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class XcSettingsConfigurable : Configurable {
    private val settings = XcSettingsState.instance
    internal val pathField = TextFieldWithBrowseButton()

    override fun getDisplayName(): String = "xc Task Runner"

    override fun createComponent(): JComponent {
        val descriptor =
            FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()
                .withTitle("Select xc Executable")
                .withDescription("Choose the path to the xc binary")

        pathField.addBrowseFolderListener(TextBrowseFolderListener(descriptor))
        pathField.text = settings.xcExecutablePath

        return panel {
            row("xc Executable Path:") {
                cell(pathField)
                    .comment("Default is 'xc' if available in system PATH.")
            }
        }
    }

    override fun isModified(): Boolean = pathField.text != settings.xcExecutablePath

    override fun apply() {
        settings.xcExecutablePath = pathField.text
    }

    override fun reset() {
        pathField.text = settings.xcExecutablePath
    }
}
