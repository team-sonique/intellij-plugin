package sonique.intellij.recentproject.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.SimpleTextAttributes
import java.awt.Font
import javax.swing.JList

internal class RecentProjectsRenderer(private val longestProjectName: Int) : ColoredListCellRenderer<Project>() {
    override fun customizeCellRenderer(list: JList<out Project>, project: Project, index: Int, selected: Boolean, hasFocus: Boolean) {
        icon = IconLoader.findIcon("/nodes/ideaProject.png")
        font = Font("Menlo", list.font.style, list.font.size)

        append(project.name, SimpleTextAttributes.DARK_TEXT, true)
        append(String.format("%" + (longestProjectName + 1 - project.name.length) + "s[%s]", " ", project.presentableUrl))
    }
}