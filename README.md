# Hide Bottom Tool Windows

An IntelliJ Platform plugin that adds one action: **Hide Bottom Tool Windows**. It hides every open tool window docked at the bottom of the IDE window (Terminal, Run, Debug, Problems, and so on).

## Using the action

- The action is in **Window → Active Tool Window**, at the top of the submenu.
- You can also run it with **Find Action** (`Cmd+Shift+A` / `Ctrl+Shift+A`) by typing "Hide Bottom Tool Windows".
- To assign a keyboard shortcut, go to **Settings → Keymap** and search for the action name.

## Hiding AI Chat on project opening

The AI Assistant plugin opens its **AI Chat** tool window each time a project opens. This plugin hides that tool window right after the project opens. If you open AI Chat yourself later, it stays open.

## Compatibility

The plugin depends only on the base platform module, so it works in all JetBrains IDEs, version 2025.2 (build 252) or newer.

## Building

You need a JDK 17 or newer to run Gradle. If JDK 21 is not installed, the build downloads it automatically.

```bash
./gradlew buildPlugin    # builds build/distributions/hbtw-<version>.zip
./gradlew runIde         # starts a separate IDE with the plugin installed
./gradlew verifyPlugin   # checks compatibility with the IDE versions listed in build.gradle.kts
```

The first build downloads Gradle and an IntelliJ IDEA distribution, so it takes a few minutes.

## Installing a local build

In the IDE, open **Settings → Plugins**, click **⚙ → Install Plugin from Disk…**, and select the zip file from `build/distributions/`.
