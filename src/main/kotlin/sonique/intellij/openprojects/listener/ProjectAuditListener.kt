package sonique.intellij.openprojects.listener

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.project.ProjectManagerListener
import sonique.intellij.openprojects.service.ProjectHolderService


class ProjectAuditListener : ProjectManagerListener {
    private val projects = service<ProjectHolderService>()

    init {
        val pm = service<ProjectManager>()
        pm.openProjects.forEach { p -> projects.addProject(p) }
    }

    override fun projectOpened(project: Project) {
        projects.addProject(project)
    }

    override fun projectClosed(project: Project) {
        projects.removeProject(project)
    }
}

