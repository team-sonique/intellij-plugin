package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import javax.swing.JList

internal class ProjectListsTest {
    private var project1 = mock<Project>().apply { whenever(name).thenReturn("abcdef") }
    private var project2 = mock<Project>().apply { whenever(name).thenReturn("abcdefgh") }
    private var project3 = mock<Project>().apply { whenever(name).thenReturn("abcd") }

    private val pm = mock<ProjectManager>().apply { whenever(openProjects).thenReturn(arrayOf(project1, project2, project3)) }

    @Test
    internal fun removesProjectsAndUpdateMaxNameLength() {
        val projectLists = ProjectLists(pm)

        val list: JList<Project> = projectLists.build(project2)

        assertThat(list.selectedIndex, equalTo(1))
        assertThat(list.model.getElementAt(0), equalTo(project2))
        assertThat(list.model.getElementAt(1), equalTo(project1))
        assertThat(list.model.getElementAt(2), equalTo(project3))
    }
}