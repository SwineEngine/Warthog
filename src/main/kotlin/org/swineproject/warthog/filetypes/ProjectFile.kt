package org.swineproject.warthog.filetypes

import com.electronwill.nightconfig.core.Config
import com.electronwill.nightconfig.core.InMemoryFormat
import com.electronwill.nightconfig.core.io.ParsingMode
import com.electronwill.nightconfig.core.io.WritingMode
import com.electronwill.nightconfig.toml.TomlFormat
import com.electronwill.nightconfig.toml.TomlWriter
import org.swineproject.warthog.Globals
import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.Path

/**
 * A helper class for loading and storing project files
 */
object ProjectFile {
    /**
     * Stores a new project and then loads it
     *
     * @param file The absolute file location to store in and load from
     */
    fun new(file: String) {
        store(file, WritingMode.REPLACE)
        load(file)
    }

    /**
     * Loads a project file
     *
     * @param file The absolute file location to load from
     */
    fun load(file: String) {

    }

    /**
     * Stores a project file
     *
     * @param file The absolute file location to store in
     */
    fun store(file: String, mode: WritingMode) {
        val tomlFormat = TomlFormat.instance()
        val tomlWriter = tomlFormat.createWriter()

        val config = InMemoryFormat.defaultInstance().createConfig()

        config.set<String>("tools.toml_version", "0.5.0")
        config.set<String>("tools.python_version", "3.7.2")
        config.set<String>("tools.swine_version", "3.0.0")

        config.set<String>("project.name", "Untitled Project")
        config.set<String>("project.icon", "")
        config.set<String>("project.main_scene", "${Globals.workingDirectory}/assets/scenes/main_scene.py")

        config.set<String>("export.location", "${Globals.workingDirectory}/build")

        config.set<String>("quick_build.implementation", "CPython")
        config.set<String>("quick_build.build_tool", "PyInstaller")

        // val toml = tomlWriter.writeToString(config)
        // println(toml)

        tomlWriter.write(config, File("$file/project.toml"), mode)
    }
}