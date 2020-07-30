package sonique.intellij.openprojects.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.PopupChooserBuilder
import sonique.intellij.MyBundle

class OpenProjectsAction : AnAction() {
    private val lists = ProjectLists(service())

    override fun actionPerformed(e: AnActionEvent) {
        val currentProject: Project = e.project ?: return

        val projectList = lists.build(currentProject)

        val popup = PopupChooserBuilder<Project>(projectList)
                .setTitle(MyBundle.message("open.projects"))
                .setItemChoosenCallback(SelectProjectRunnable(projectList))
                .createPopup()

        popup.showCenteredInCurrentWindow(currentProject)
    }
}
