package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import javax.swing.JList

internal class ProjectListsTest {
    private val project1 = mock(Project::class.java)
    private val project2 = mock(Project::class.java)
    private val project3 = mock(Project::class.java)

    val pm = mock(ProjectManager::class.java)

    private val projectLists = ProjectLists(pm)

    @BeforeEach
    internal fun setupMocks() {
        Mockito.`when`(project1.name).thenReturn("abcdef")
        Mockito.`when`(project2.name).thenReturn("abcdefgh")
        Mockito.`when`(project3.name).thenReturn("abcd")
    }

    @Test
    internal fun calculatesMaxLength() {
        assertThat(projectLists.longestProjectName(arrayOf(project1, project2, project3)), CoreMatchers.equalTo(8))
        assertThat(projectLists.longestProjectName(arrayOf(project1, project3)), CoreMatchers.equalTo(6))
        assertThat(projectLists.longestProjectName(arrayOf(project3)), CoreMatchers.equalTo(4))
    }

    @Test
    internal fun removesProjectsAndUpdateMaxNameLength() {
        Mockito.`when`(pm.openProjects).thenReturn(arrayOf(project1, project2, project3))

        val list: JList<Project> = projectLists.build(project2)

        assertThat(list.selectedIndex, CoreMatchers.equalTo(1))
        assertThat(list.model.getElementAt(0), CoreMatchers.equalTo(project2))
        assertThat(list.model.getElementAt(1), CoreMatchers.equalTo(project1))
        assertThat(list.model.getElementAt(2), CoreMatchers.equalTo(project3))
    }

    @Test
    internal fun statsWithZeroLength() {
        assertThat(projectLists.longestProjectName(emptyArray()), CoreMatchers.equalTo(0))
    }
}