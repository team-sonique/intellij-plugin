package sonique.intellij.openprojects.service

import com.intellij.openapi.project.Project
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class ProjectHolderServiceTest {
    private val project1 = Mockito.mock(Project::class.java)
    private val project2 = Mockito.mock(Project::class.java)
    private val project3 = Mockito.mock(Project::class.java)

    val p = ProjectHolderService()

    @BeforeEach
    internal fun setupMocks() {
        Mockito.`when`(project1.name).thenReturn("abcdef")
        Mockito.`when`(project2.name).thenReturn("abcdefgh")
        Mockito.`when`(project3.name).thenReturn("abcd")
    }

    @Test
    internal fun addProjectsAndUpdateMaxNameLength() {
        p.addProject(project1)
        p.addProject(project2)
        p.addProject(project3)

        assertThat(p.allOpen, hasItems(project1, project2, project3))
        assertThat(p.longestProjectNameLength(), equalTo(8))
    }

    @Test
    internal fun removesProjectsAndUpdateMaxNameLength() {
        p.addProject(project1)
        p.addProject(project2)
        p.addProject(project3)

        assertThat(p.allOpen, hasItems(project1, project2, project3))

        p.removeProject(project2)

        assertThat(p.allOpen, hasItems(project1, project3))
        assertThat(p.longestProjectNameLength(), equalTo(6))
    }

    @Test
    internal fun statsWithZeroLength() {
        assertThat(p.longestProjectNameLength(), equalTo(0))
    }
}
