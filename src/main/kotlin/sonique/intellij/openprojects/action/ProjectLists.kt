package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.ui.components.JBList
import com.intellij.util.containers.stream
import javax.swing.DefaultListModel
import javax.swing.JList

internal class ProjectLists(private val pm: ProjectManager) {

    fun build(currentProject: Project): JList<Project> {
        val openProjects = pm.openProjects

        val listModel = DefaultListModel<Project>()
        listModel.addElement(currentProject)
        openProjects.stream()
                .filter { p -> p != currentProject }
                .forEach { p -> listModel.addElement(p) }

        val projectList: JList<Project> = JBList<Project>(listModel)
        projectList.cellRenderer = OpenProjectsRenderer(longestProjectName(openProjects))
        projectList.selectedIndex = if (listModel.size() > 1) 1 else 0

        return projectList
    }

    fun longestProjectName(openProjects: Array<Project>): Int {
        return openProjects.stream()
                .map { p -> p!!.name.length }
                .max(Int::compareTo)
                .orElse(0)
    }
}
