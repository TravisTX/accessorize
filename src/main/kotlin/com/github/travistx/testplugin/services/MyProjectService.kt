package com.github.travistx.testplugin.services

import com.intellij.openapi.project.Project
import com.github.travistx.testplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
