package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import javax.swing.ListModel

internal object ProjectListUtil {
    fun longestProjectName(openProjects: ListModel<Project>) =
        (0 until openProjects.size)
            .map { openProjects.getElementAt(it) }
            .map { p -> p!!.name.length }
            .max() ?: 0
}
