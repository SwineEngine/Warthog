package org.swineproject.warthog.scene

import org.eclipse.jface.layout.GridDataFactory
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.*

class Scene(parent: Composite) : Composite(parent, SWT.BORDER) {
    val scene: Canvas = Canvas(this, SWT.NONE).apply {
        layoutData = GridData(GridData.FILL_BOTH)
        background = Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY)
    }

    val game: Canvas = Canvas(this, SWT.NONE).apply {
        layoutData = GridData(GridData.FILL_BOTH)
        background = Display.getDefault().getSystemColor(SWT.COLOR_DARK_BLUE)
    }

    val toolbar = ToolBar(this, SWT.HORIZONTAL or SWT.WRAP or SWT.RIGHT).apply {
        GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.END).grab(true, false).span(2, 0).applyTo(this)

        val play = ToolItem(this, SWT.RADIO).apply { text = " > " }
        val pause = ToolItem(this, SWT.RADIO).apply { text = "| |" }
    }
}