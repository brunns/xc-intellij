package ing.brunn.xcintellij.services

import com.intellij.openapi.components.*
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "com.example.xc.settings.XcSettingsState",
    storages = [Storage("XcSettings.xml")]
)
@Service(Service.Level.APP)
class XcSettingsState : PersistentStateComponent<XcSettingsState> {
    var xcExecutablePath: String = "xc" // Default to assuming 'xc' is on system PATH

    override fun getState(): XcSettingsState = this

    override fun loadState(state: XcSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: XcSettingsState
            get() = service()
    }
}