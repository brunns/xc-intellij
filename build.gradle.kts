import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("org.jetbrains.kotlin.jvm") version "2.4.20"
    id("org.jetbrains.intellij.platform")
    id("org.jetbrains.changelog") version "2.5.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea("2026.2.0.1")
        testFramework(TestFrameworkType.Platform)
    }
}

tasks.named("compileKotlin") {
    dependsOn("ktlintFormat")
}
