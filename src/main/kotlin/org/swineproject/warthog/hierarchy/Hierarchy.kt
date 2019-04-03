package org.swineproject.warthog.hierarchy

import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.List

class Hierarchy(parent: Composite) : Composite(parent, SWT.BORDER) {
    val list: List = List(this, SWT.BORDER).apply { layoutData = GridData(GridData.FILL_BOTH) }
}