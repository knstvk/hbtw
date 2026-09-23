package hbtw;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.messages.MessageBusConnection;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.TimeUnit;

/**
 * Hides the "AI Chat" tool window of the AI Assistant plugin when a project opens.
 * <p>
 * AI Assistant shows this tool window from its own startup activity, which can run before or after this one.
 * So this activity hides the tool window if it is already visible, and also hides it if it is shown
 * during a short period after this activity runs.
 */
public class HideAiChatStartupActivity implements ProjectActivity {

    private static final String AI_CHAT_TOOL_WINDOW_ID = "AIAssistant";
    private static final long LISTEN_SECONDS = 10;

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        MessageBusConnection connection = project.getMessageBus().connect();
        connection.subscribe(ToolWindowManagerListener.TOPIC, new ToolWindowManagerListener() {
            @Override
            public void toolWindowShown(@NotNull ToolWindow toolWindow) {
                if (AI_CHAT_TOOL_WINDOW_ID.equals(toolWindow.getId())) {
                    connection.disconnect();
                    ApplicationManager.getApplication().invokeLater(toolWindow::hide, project.getDisposed());
                }
            }
        });
        AppExecutorUtil.getAppScheduledExecutorService()
                .schedule(connection::disconnect, LISTEN_SECONDS, TimeUnit.SECONDS);

        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        toolWindowManager.invokeLater(() -> {
            ToolWindow toolWindow = toolWindowManager.getToolWindow(AI_CHAT_TOOL_WINDOW_ID);
            if (toolWindow != null && toolWindow.isVisible()) {
                toolWindow.hide();
            }
        });
        return Unit.INSTANCE;
    }
}
