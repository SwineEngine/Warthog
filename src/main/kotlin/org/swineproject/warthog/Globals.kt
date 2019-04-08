package org.swineproject.warthog

import jep.Jep
import jep.python.PyObject
import org.swineproject.warthog.hierarchy.Hierarchy
import org.swineproject.warthog.inspector.Inspector
import org.swineproject.warthog.project.Project
import org.swineproject.warthog.scene.Scene

object Globals {
    val directoryBlacklist = arrayOf(".git", ".idea", ".gradle", "gradle", "out")

    var workingDirectory: String = System.getProperty("user.dir")

    var python: Jep? = null

    val objectList = mutableListOf<String>()

    // Widgets
    var hierarchy: Hierarchy? = null
    var scene: Scene? = null
    var inspector: Inspector? = null
    var project: Project? = null

    fun syncAllObjects() {
        objectList.clear()

        // It's just easier to compare the objects from Python
        python!!.eval("import swine")
        python!!.eval("objs = []")
        python!!.eval("""|for k, v in list(locals().items()):
                         |    if type(v) == swine.GameObject:
                         |        objs.append(k)
        """.trimMargin())

        for (i in python!!.getValue("objs") as ArrayList<String>) {
            objectList.add(i)
        }
    }
}