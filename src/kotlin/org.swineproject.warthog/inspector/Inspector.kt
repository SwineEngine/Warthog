package org.swineproject.hog.inspector

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Composite

class Inspector(parent: Composite) : Composite(parent, SWT.BORDER) {
    val composite: Composite = Composite(this, SWT.NONE)

    init {
        val compositeLayoutData = GridData(GridData.FILL_BOTH)
        composite.layoutData = compositeLayoutData
    }
}