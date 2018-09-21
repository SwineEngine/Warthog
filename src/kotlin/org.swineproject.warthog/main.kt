package org.swineproject.hog

import org.eclipse.swt.SWT
import org.eclipse.swt.custom.SashForm
import org.eclipse.swt.graphics.Point
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.swineproject.hog.hierarchy.Hierarchy
import org.swineproject.hog.inspector.Inspector
import org.swineproject.hog.project.Project
import org.swineproject.hog.scene.Scene


fun main(args: Array<String>) {
    val display = Display()
    val shell = Shell(display)
    shell.text = "Hog"

    val layout = FillLayout()
    shell.layout = layout

    // Widgets

    val verticalForm = SashForm(shell, SWT.VERTICAL or SWT.SMOOTH)

    val horizontalForm = SashForm(verticalForm, SWT.HORIZONTAL or SWT.SMOOTH)

        // Hierarchy
    val hierarchy = Hierarchy(horizontalForm)
    hierarchy.layout = GridLayout()

        // Scene
    val scene = Scene(horizontalForm)
    scene.layout = GridLayout()

        // Inspector
    val inspector = Inspector(horizontalForm)
    inspector.layout = GridLayout()

        // Project
    val project = Project(verticalForm)
    project.layout = GridLayout()

    verticalForm.weights = intArrayOf(2, 1)
    horizontalForm.weights = intArrayOf(1, 3, 1)

    verticalForm.pack()
    horizontalForm.pack()

    // End Widgets

    shell.pack()
    shell.open()

    shell.setSize(980, 680)

    while (!shell.isDisposed) {
        if (!display.readAndDispatch()) {
            display.sleep()
        }
    }
    display.dispose()
}