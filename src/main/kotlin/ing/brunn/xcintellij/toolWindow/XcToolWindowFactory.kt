package ing.brunn.xcintellij.toolWindow

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import ing.brunn.xcintellij.services.XcTaskService
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.DefaultListModel
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.SwingUtilities

class XcToolWindowFactory : ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = XcToolWindowPanel(project)
        val content = ContentFactory.getInstance().createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}

class XcToolWindowPanel(private val project: Project) : JPanel(BorderLayout()) {
    private val listModel = DefaultListModel<String>()
    internal val taskList = JBList(listModel)

    init {
        val scrollPane = JBScrollPane(taskList)
        add(createToolbar(), BorderLayout.NORTH)
        add(scrollPane, BorderLayout.CENTER)

        // Setup double-click action listener
        taskList.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) {
                    val selectedTask = taskList.selectedValue
                    if (selectedTask != null) {
                        onTaskDoubleClicked(selectedTask)
                    }
                }
            }
        })

        refreshTasks()
    }

    fun refreshTasks() {
        listModel.clear()

        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                val taskService = project.getService(XcTaskService::class.java)
                val tasks = taskService?.fetchTasks() ?: emptyList()

                // Update Swing UI elements back on the EDT
                SwingUtilities.invokeLater {
                    tasks.forEach { listModel.addElement(it) }
                }
            } catch (e: Exception) {
                SwingUtilities.invokeLater {
                    listModel.addElement("Error loading tasks: ${e.message}")
                }
            }
        }
    }

    private fun onTaskDoubleClicked(taskName: String) {
        // Step 3 (Task Execution) will connect here
        println("Triggered task execution: $taskName")
    }

    private fun createToolbar(): JComponent {
        val actionGroup = DefaultActionGroup().apply {
            add(object : AnAction("Refresh Tasks", "Reload tasks from README", AllIcons.Actions.Refresh) {
                override fun actionPerformed(e: AnActionEvent) {
                    refreshTasks()
                }
            })
        }
        val toolbar = ActionManager.getInstance().createActionToolbar("XcToolWindow", actionGroup, true)
        toolbar.targetComponent = this
        return toolbar.component
    }
}