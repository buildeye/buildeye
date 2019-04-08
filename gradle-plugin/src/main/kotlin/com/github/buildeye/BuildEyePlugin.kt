package com.github.buildeye

import com.github.buildeye.listeners.BuildListener
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle

class BuildEyePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        GlobalState.init(target.gradle)
    }
}

private object GlobalState {
    @Volatile
    private var initialized: Boolean = false

    @Synchronized
    fun init(gradle: Gradle) {
        if (initialized) return

        gradle.addBuildListener(BuildListener(gradle))

        initialized = true
        gradle.rootProject.logger.info("Initialized BuildEye plugin")
    }
}
