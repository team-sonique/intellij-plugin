package sonique.intellij.recentproject.service

import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import kotlin.math.max

@Service
class ProjectHolderService {
    val allOpen = mutableSetOf<Project>()

    fun addProject(project: Project) = allOpen.add(project)

    fun removeProject(project: Project) = allOpen.remove(project)

    fun longestProjectNameLength(): Int = allOpen.stream()
            .map { p -> p.name.length }
            .reduce(0) { a, b -> max(a, b) }
}
