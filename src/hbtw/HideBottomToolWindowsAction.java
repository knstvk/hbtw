package hbtw;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;

public class HideBottomToolWindowsAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        String[] toolWindowIds = toolWindowManager.getToolWindowIds();
        for (String toolWindowId : toolWindowIds) {
            ToolWindow toolWindow = toolWindowManager.getToolWindow(toolWindowId);
            ToolWindowAnchor anchor = toolWindow.getAnchor();
            if (anchor == ToolWindowAnchor.BOTTOM) {
                toolWindow.hide(null);
            }
        }
    }
}
