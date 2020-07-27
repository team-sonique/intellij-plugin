package sonique.intellij.recentproject.listener

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import sonique.intellij.recentproject.service.ProjectHolderService


class ProjectAuditListener : ProjectManagerListener {
    private val projects = service<ProjectHolderService>()

    override fun projectOpened(project: Project) {
        projects.addProject(project)
    }

    override fun projectClosed(project: Project) {
        projects.removeProject(project)
    }
}

