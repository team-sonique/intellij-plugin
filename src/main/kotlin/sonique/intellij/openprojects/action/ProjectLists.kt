package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.ui.components.JBList
import sonique.intellij.openprojects.action.ProjectListUtil.longestProjectName
import java.util.*
import javax.swing.DefaultListModel
import javax.swing.JList

internal class ProjectLists(private val pm: ProjectManager) {
    fun build(currentProject: Project): JList<Project> {
        println(pm.openProjects.map { it.name })
        println(currentProject.name)
        val openProjects = LinkedList(pm.openProjects.toList())
                .also { it.remove(currentProject); it.addFirst(currentProject) }
                .fold(DefaultListModel<Project>()) { model, project ->
                    model.apply { addElement(project) }
                }

        return JBList(openProjects).apply {
            cellRenderer = OpenProjectsRenderer(longestProjectName(openProjects))
            selectedIndex = if (openProjects.size() > 1) 1 else 0
        }
    }
}
