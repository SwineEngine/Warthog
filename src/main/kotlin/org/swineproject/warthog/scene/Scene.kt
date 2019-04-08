package org.swineproject.warthog.scene

import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.opengl.GLCanvas
import org.eclipse.swt.opengl.GLData
import org.eclipse.swt.widgets.*
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.swineproject.warthog.Globals
import javax.swing.Spring.height


class Scene(parent: Composite) : Composite(parent, SWT.BORDER) {
    val scene = GLCanvas(this, SWT.NO_BACKGROUND, GLData().apply { doubleBuffer = true }).apply {
        layoutData = GridData(GridData.FILL_BOTH)
        // background = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY)

        this.setCurrent()
        GL.createCapabilities()

        GL11.glClearColor(145f / 255, 145f / 255, 145f / 255, 1f)

        val rectangleShader = GL20.glCreateProgram()

        val vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER)
        GL20.glShaderSource(vertexShader, this::class.java.getResource("/shaders/rectangle.vert").readText())
        GL20.glCompileShader(vertexShader)
        GL20.glAttachShader(rectangleShader, vertexShader)

        val fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER)
        GL20.glShaderSource(fragmentShader, this::class.java.getResource("/shaders/rectangle.frag").readText())
        GL20.glCompileShader(fragmentShader)
        GL20.glAttachShader(rectangleShader, fragmentShader)

        GL20.glBindAttribLocation(rectangleShader, 0, "position")

        GL20.glLinkProgram(rectangleShader)
        GL20.glValidateProgram(rectangleShader)
        GL20.glUseProgram(rectangleShader)

        addListener(SWT.Resize) {
            val area = this@apply.clientArea

            GL11.glViewport(0, 0, area.width, area.height)
            GL11.glMatrixMode(GL11.GL_PROJECTION)
            GL11.glLoadIdentity()
        }

        display.asyncExec(object : Runnable {
            override fun run() {
                if (!this@apply.isDisposed) {
                    this@apply.setCurrent()
                    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT)

                    for (obj in Globals.objectList) {
                        for (comp in 0 until (Globals.python!!.getValue("len($obj.get_components())") as Long)) {
                            val drawDict = Globals.python!!.getValue("$obj.get_components()[$comp].draw()") as HashMap<String, *>

                            val vertexList = mutableListOf<Float>()
                            for (i in drawDict["vertices"]!! as ArrayList<*>) {
                                for (j in i as List<Float>) {
                                    vertexList.add(j)
                                }
                            }

                            val indexList = (drawDict["indices"] as ArrayList<Int>).toIntArray()

                            val vertexBuffer = GL20.glGenBuffers()
                            val indexBuffer = GL20.glGenBuffers()

                            GL20.glBindBuffer(GL20.GL_ARRAY_BUFFER, vertexBuffer)
                            GL20.glBufferData(GL20.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertexList.size).put(vertexList.toFloatArray()).flip(), GL20.GL_STATIC_DRAW)
                            GL20.glEnableVertexAttribArray(0)

                            GL20.glBindBuffer(GL20.GL_ELEMENT_ARRAY_BUFFER, indexBuffer)
                            GL20.glBufferData(GL20.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indexList.size).put(indexList).flip(), GL20.GL_STATIC_DRAW)
                            GL20.glVertexAttribPointer(0, 4, GL20.GL_FLOAT, false, 0, 0L)

                            GL20.glDrawElements(GL11.GL_TRIANGLE_FAN, vertexList.size, GL11.GL_UNSIGNED_INT, 0L)
                        }
                    }

                    this@apply.swapBuffers()
                    display.asyncExec(this)
                }
            }
        })
    }

    val game = GLCanvas(this, SWT.NO_BACKGROUND, GLData().apply { doubleBuffer = true }).apply {
        layoutData = GridData(GridData.FILL_BOTH)
        // background = Display.getDefault().getSystemColor(SWT.COLOR_DARK_BLUE)
        // GL11.glClearColor(26f, 100f, 219f, 1f)
    }

    val toolbar = ToolBar(this, SWT.HORIZONTAL or SWT.WRAP or SWT.RIGHT).apply {
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.END).grab(true, false).span(2, 0).applyTo(this)

        val play = ToolItem(this, SWT.RADIO).apply { text = " > " }
        val pause = ToolItem(this, SWT.RADIO).apply { text = "| |" }
    }
}