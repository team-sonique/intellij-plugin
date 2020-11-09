package sonique.intellij.openprojects.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.PopupChooserBuilder
import com.intellij.openapi.wm.WindowManager
import sonique.intellij.MyBundle
import java.awt.Frame

object OpenProjectsAction : AnAction() {
    private val lists = ProjectLists(service())
    private val windowManager = WindowManager.getInstance()

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { project ->
            PopupChooserBuilder(lists.build(project))
                    .setTitle(MyBundle.message("open.projects"))
                    .setItemChosenCallback(this::callbackFor)
                    .createPopup().apply {
                        showCenteredInCurrentWindow(project)
                    }
        }
    }

    private fun callbackFor(project: Project) {
        val projectFrame = windowManager.getFrame(project)
        val frameState = projectFrame!!.extendedState
        if ((frameState and Frame.ICONIFIED) == Frame.ICONIFIED) {
            // restore the frame if it is minimized
            projectFrame.extendedState = (frameState xor Frame.ICONIFIED)
        }
        projectFrame.toFront()
        projectFrame.requestFocus()
    }
}
