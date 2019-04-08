package org.swineproject.warthog.hierarchy

import org.eclipse.swt.SWT
import org.eclipse.swt.custom.ScrolledComposite
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import org.eclipse.swt.widgets.List
import org.swineproject.warthog.Globals

class Hierarchy(parent: Composite) : Composite(parent, SWT.BORDER) {
    val list: List = List(this, SWT.BORDER or SWT.SINGLE or SWT.H_SCROLL or SWT.V_SCROLL).apply {
        layoutData = GridData(GridData.FILL_BOTH)

        addListener(SWT.Selection) {
            Globals.inspector!!.composite.children.forEach { dispose() }

            val obj = Globals.objectList[this.selectionIndices[0]]

            Label(Globals.inspector!!.composite, SWT.NONE).apply { text = "Name: " + Globals.python!!.getValue("$obj.name") as String }

            val scrolledComposite = ScrolledComposite(Globals.inspector!!.composite, SWT.V_SCROLL)
            val scrolledContent = Composite(scrolledComposite, SWT.NONE).apply {
                layout = GridLayout()
                layoutData = GridData(SWT.FILL, SWT.FILL, true, true)
            }

            // TODO: Cache the list instead of calling it each time
            for (i in 0 until (Globals.python!!.getValue("len($obj.get_components())") as Long)) {
                val name = Globals.python!!.getValue("$obj.get_components()[$i].__class__.__name__")
                Group(scrolledContent, SWT.NONE).apply {
                    text = name as String
                    layoutData = GridData(GridData.FILL_BOTH)
                }
            }

            Globals.inspector!!.composite.pack()

            scrolledComposite.content = scrolledContent
            scrolledComposite.setMinSize(Globals.inspector!!.computeSize(SWT.DEFAULT, SWT.DEFAULT))

            scrolledContent.pack()

            // Globals.python!!.getValue("${Globals.objectList[this.selectionIndices[0]]}.component_list")
        }
    }
}