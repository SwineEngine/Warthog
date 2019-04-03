package org.swineproject.warthog

import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.SWT
import org.eclipse.swt.custom.SashForm
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Control
import org.swineproject.warthog.hierarchy.Hierarchy
import org.swineproject.warthog.inspector.Inspector
import org.swineproject.warthog.project.Project
import org.swineproject.warthog.scene.Scene

fun main(args: Array<String>) {
    val window = object: ApplicationWindow(null) {
        override fun create() {
            super.create()

            shell.text = "Warthog"
            shell.setSize(1400, 700)

            val menu = Menu(shell)
            shell.menuBar = menu.menuBar
        }

        override fun createContents(parent: Composite?): Control {
            val verticalForm = SashForm(parent, SWT.VERTICAL or SWT.SMOOTH)
            val horizontalForm = SashForm(verticalForm, SWT.HORIZONTAL or SWT.SMOOTH)
            // Hierarchy
            Globals.hierarchy = Hierarchy(horizontalForm).apply { layout = GridLayout() }
            // Scene
            Globals.scene = Scene(horizontalForm).apply { layout = GridLayout().apply { numColumns = 2 } }
            // Inspector
            Globals.inspector = Inspector(horizontalForm).apply { layout = GridLayout() }
            // Project
            Globals.project = Project(verticalForm).apply { layout = GridLayout() }

            verticalForm.weights = intArrayOf(2, 1)
            horizontalForm.weights = intArrayOf(1, 3, 1)

            verticalForm.pack()
            horizontalForm.pack()

            return super.createContents(parent)
        }
    }.apply {
        setBlockOnOpen(true)
        open()
    }
}