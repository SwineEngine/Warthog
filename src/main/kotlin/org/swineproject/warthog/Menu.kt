package org.swineproject.warthog

import jep.Jep
import jep.JepConfig
import jep.python.PyObject
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.*
import org.eclipse.swt.widgets.Menu
import org.swineproject.warthog.filetypes.ProjectFile
import java.io.File

class Menu(decorations: Decorations) {
    val menuBar = Menu(decorations, SWT.BAR)

    val fileMenu = MenuItem(menuBar, SWT.CASCADE).apply {
        text = "File"

        this.menu = Menu(this).apply {
            MenuItem(this, SWT.PUSH).apply {
                text = "New Project"
                addListener(SWT.Selection) {
                    val directoryDialog = DirectoryDialog(decorations as Shell)
                    directoryDialog.filterPath = Globals.workingDirectory
                    val directory = directoryDialog.open()

                    if (directory != null) {
                        ProjectFile.new(directory)

                        // TODO: Use a templating engine instead like Velocity or StringTemplate
                        val game = this::class.java.getResource("/scripts/game.py").readText()
                                .replace(">title", "\"Title\"")
                                .replace(">size", "(800, 600)")
                                .replace(">vsync", "True")

                        val scene = this::class.java.getResource("/scripts/scene.py").readText()

                        File("$directory/game.py").apply {
                            writeText(game)
                        }
                        File("$directory/assets/scenes/").mkdirs()
                        File("$directory/assets/scenes/main_scene.py").apply {
                            writeText(scene)
                        }
                        File("$directory/assets/scripts").mkdirs()
                        File("$directory/assets/sprites").mkdirs()

                        Globals.workingDirectory = directory
                        Globals.project!!.refreshFiles()

                        if (Globals.python != null) {
                            Globals.python!!.close()
                        }

                        Globals.python = Jep()
                        Globals.python!!.runScript("$directory/assets/scenes/main_scene.py")

                        Globals.hierarchy!!.list.removeAll()
                        Globals.syncAllObjects()
                        // TODO: Check if the object doesn't have a parent, if it does, append it as a child item
                        Globals.objectList.forEach { Globals.hierarchy!!.list.add(Globals.python!!.getValue("$it.name").toString()) }
                    }
                }
            }

            // MenuItem(this, SWT.PUSH).apply { text = "Open Project" }
        }
    }
}