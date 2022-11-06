package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import javax.swing.JList

internal class ProjectListsTest {
    private var project1 = mock<Project> { on { name } doReturn "abcdef" }
    private var project2 = mock<Project> { on { name } doReturn "abcdefgh" }
    private var project3 = mock<Project> { on { name } doReturn "abcd" }

    private val pm = mock<ProjectManager> { on {openProjects} doReturn arrayOf(project1, project2, project3) }

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
