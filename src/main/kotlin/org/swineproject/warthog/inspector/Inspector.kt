package org.swineproject.warthog.inspector

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Canvas
import org.eclipse.swt.widgets.Composite

class Inspector(parent: Composite) : Composite(parent, SWT.BORDER) {
    val composite: Composite = Composite(this, SWT.NONE).apply { layoutData = GridData(GridData.FILL_BOTH) }
}