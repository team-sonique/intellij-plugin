package sonique.intellij.openprojects.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.ui.popup.PopupChooserBuilder
import com.intellij.openapi.wm.WindowManager
import com.intellij.util.messages.MessageBus
import com.intellij.util.messages.MessageBusConnection
import sonique.intellij.MyBundle
import java.awt.Frame

object OpenProjectsAction : AnAction() {
    private val lists = ProjectLists(service())
    private val windowManager = WindowManager.getInstance()

    init {
        val messageBus = service<MessageBus>()
        val connection: MessageBusConnection = messageBus.connect()
        connection.subscribe(ProjectManager.TOPIC, lists)
    }

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let {
            PopupChooserBuilder<Project>(lists.build(it))
                .setTitle(MyBundle.message("open.projects"))
                .setItemChosenCallback(this::callbackFor)
                .createPopup().apply {
                    showCenteredInCurrentWindow(it)
                }
        }
    }

    private fun callbackFor(project: Project) {
        windowManager.getFrame(project)?.apply {
            if ((extendedState and Frame.ICONIFIED) == Frame.ICONIFIED) {
                extendedState = (extendedState xor Frame.ICONIFIED)
            }
            toFront()
            requestFocus()
        }
    }
}
