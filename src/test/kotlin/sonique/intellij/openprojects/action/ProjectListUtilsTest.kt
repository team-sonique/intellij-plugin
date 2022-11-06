package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import sonique.intellij.openprojects.action.ProjectListUtil.longestProjectName
import javax.swing.DefaultListModel

class ProjectListUtilsTest {
    private val project1 = mock<Project> { on {name} doReturn "abcdef"}
    private val project2 = mock<Project> { on {name} doReturn "abcdefgh" }
    private val project3 = mock<Project> { on {name} doReturn "abcd"}

    @Test
    fun calculatesMaxLength() {
        assertThat(longestProjectName(listModelOf(project1, project2, project3)), CoreMatchers.equalTo(8))
        assertThat(longestProjectName(listModelOf(project1, project3)), CoreMatchers.equalTo(6))
        assertThat(longestProjectName(listModelOf(project3)), CoreMatchers.equalTo(4))
    }

    @Test
    fun statsWithZeroLength() {
        assertThat(longestProjectName(DefaultListModel()), CoreMatchers.equalTo(0))
    }

    private fun listModelOf(vararg projects: Project) =
        projects.fold(DefaultListModel<Project>()) { a, p -> a.apply { addElement(p) } }
}
