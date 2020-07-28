package sonique.intellij.recentproject.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.PopupChooserBuilder
import com.intellij.ui.components.JBList
import sonique.intellij.recentproject.service.ProjectHolderService
import javax.swing.DefaultListModel
import javax.swing.JList

class RecentProjectsAction : AnAction() {
    private val projects = service<ProjectHolderService>()

    override fun actionPerformed(e: AnActionEvent) {
        val currentProject: Project = e.project ?: return

        val listModel = DefaultListModel<Project>()
        listModel.addElement(currentProject);
        projects.allOpen.stream()
                .filter { p -> p != currentProject }
                .forEach { p -> listModel.addElement(p) }

        val projectList: JList<Project> = JBList<Project>(listModel)
        projectList.cellRenderer = RecentProjectsRenderer(projects.longestProjectNameLength())
        projectList.selectedIndex = if (listModel.size() > 1) 1 else 0

        val popup = PopupChooserBuilder<Project>(projectList)
                .setTitle("Recent Projects")
                .setItemChoosenCallback(SelectProjectRunnable(projectList))
                .createPopup()

        popup.showCenteredInCurrentWindow(currentProject)
    }
}

