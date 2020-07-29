package sonique.intellij.openprojects.action

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import java.awt.Frame
import javax.swing.JList

internal class SelectProjectRunnable(private val list: JList<Project>) : Runnable {

    override fun run() {
        val projectFrame = WindowManager.getInstance().getFrame(list.selectedValue)
        val frameState = projectFrame!!.extendedState
        if ((frameState and Frame.ICONIFIED) == Frame.ICONIFIED) {
            // restore the frame if it is minimized
            projectFrame.extendedState = (frameState xor Frame.ICONIFIED)
        }
        projectFrame.toFront()
        projectFrame.requestFocus()
    }
}
