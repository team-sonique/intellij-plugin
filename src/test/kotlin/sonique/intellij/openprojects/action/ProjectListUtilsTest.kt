package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import sonique.intellij.openprojects.action.ProjectListUtil.longestProjectName
import javax.swing.DefaultListModel

internal class ProjectListUtilsTest {
    private val project1 = mock(Project::class.java)
    private val project2 = mock(Project::class.java)
    private val project3 = mock(Project::class.java)

    @BeforeEach
    internal fun setupMocks() {
        Mockito.`when`(project1.name).thenReturn("abcdef")
        Mockito.`when`(project2.name).thenReturn("abcdefgh")
        Mockito.`when`(project3.name).thenReturn("abcd")
    }

    @Test
    internal fun calculatesMaxLength() {
        assertThat(longestProjectName(listModelOf(project1, project2, project3)), CoreMatchers.equalTo(8))
        assertThat(longestProjectName(listModelOf(project1, project3)), CoreMatchers.equalTo(6))
        assertThat(longestProjectName(listModelOf(project3)), CoreMatchers.equalTo(4))
    }

    @Test
    internal fun statsWithZeroLength() {
        assertThat(longestProjectName(DefaultListModel()), CoreMatchers.equalTo(0))
    }

    private fun listModelOf(vararg projects: Project) =
            projects.fold(DefaultListModel<Project>()) { a, p -> a.apply { addElement(p) } }
}