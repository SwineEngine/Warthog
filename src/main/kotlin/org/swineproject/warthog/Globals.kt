package org.swineproject.warthog

import jep.Jep
import org.swineproject.warthog.hierarchy.Hierarchy
import org.swineproject.warthog.inspector.Inspector
import org.swineproject.warthog.project.Project
import org.swineproject.warthog.scene.Scene

object Globals {
    val directoryBlacklist = arrayOf(".git", ".idea", ".gradle", "gradle", "out")

    var workingDirectory: String = System.getProperty("user.dir")

    var python: Jep? = null

    // Widgets
    var hierarchy: Hierarchy? = null
    var scene: Scene? = null
    var inspector: Inspector? = null
    var project: Project? = null

    fun getAllObjects(): List<Any> {
        val objectList = mutableListOf<Any>()

        for (i in python!!.getValue("locals()") as HashMap<String, Any>) {
            // TODO: Check if the object is a swine.GameObject before adding it
            objectList.add(i)
        }

        return objectList
    }
}