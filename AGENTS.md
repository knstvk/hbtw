# AGENTS.md

IntelliJ Platform plugin with one action, `hbtw.HideBottomToolWindowsAction`, and one startup activity, `hbtw.HideAiChatStartupActivity`, which hides the AI Assistant tool window (ID `AIAssistant`, title "AI Chat") when a project opens. The build uses Gradle with the IntelliJ Platform Gradle Plugin 2.x. See `README.md` for what the plugin does and the build commands.

## Platform version and Java version

- The code compiles against the **oldest supported IDE**: `intellijIdea("2025.2.6.3")` with a Java 21 toolchain, and `sinceBuild = "252"`. This keeps the plugin compatible with 2025.2 and newer.
- IntelliJ 2026.2 jars are Java 25 bytecode (class version 69). Compiling against 2026.2 with Java 21 fails with `class file has wrong version 69.0, should be 65.0`. To move the minimum version up, change all three settings together: the platform version, the toolchain version, and `sinceBuild`.
- For versions before 2025.3, `intellijIdea(...)` resolves to IntelliJ IDEA Ultimate (`ideaIU`). The single `idea` artifact starts at 2025.3.

## Plugin descriptor

`src/main/resources/META-INF/plugin.xml` has no `<version>` or `<idea-version>`. The `patchPluginXml` task adds them from `version` and `pluginConfiguration.ideaVersion` in `build.gradle.kts`, so change them there.

## Actions

Every `AnAction` overrides `getActionUpdateThread()`; the platform logs a warning for actions that do not. Get the project with `e.getProject()` and handle `null`.

## Verification

There are no automated tests. A change is ready when both of these pass:

1. `./gradlew buildPlugin`
2. `./gradlew verifyPlugin` reports `Compatible` for every IDE listed.

`verifyPlugin` lists the IDEs explicitly in `pluginVerification.ides`: the lowest supported version and the current one. `recommended()` would download many IDE versions, because the plugin has no `untilBuild`. When you change the supported range, update this list.

To check behavior, use `./gradlew runIde`. It needs a person to click the action in the started IDE.
