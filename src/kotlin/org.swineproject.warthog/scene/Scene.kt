package org.swineproject.hog.scene

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Display

class Scene(parent: Composite) : Composite(parent, SWT.BORDER) {
    val canvas: Canvas = Canvas(this, SWT.NONE)

    init {
        val canvasLayoutData = GridData(GridData.FILL_BOTH)
        canvas.layoutData = canvasLayoutData

        canvas.background = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY)
    }
}