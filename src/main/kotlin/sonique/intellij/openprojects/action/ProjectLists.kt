package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.ui.components.JBList
import sonique.intellij.openprojects.action.ProjectListUtil.longestProjectName
import javax.swing.DefaultListModel
import javax.swing.JList

internal class ProjectLists(pm: ProjectManager) : ProjectManagerListener {
    private val openProjects = DefaultListModel<Project>()

    init {
        pm.openProjects.forEach { openProjects.addElement(it) }
    }

    override fun projectOpened(project: Project) {
        openProjects.addElement(project)
    }

    override fun projectClosed(project: Project) {
        openProjects.removeElement(project)
    }

    fun build(currentProject: Project): JList<Project> {
        openProjects.removeElement(currentProject)
        openProjects.add(0, currentProject)

        return JBList(openProjects).apply {
            cellRenderer = OpenProjectsRenderer(longestProjectName(openProjects))
            selectedIndex = if (openProjects.size() > 1) 1 else 0
        }
    }
}
