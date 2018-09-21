package org.swineproject.hog.project

import org.eclipse.swt.SWT
import org.eclipse.swt.custom.SashForm
import org.eclipse.swt.custom.ScrolledComposite
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Tree
import org.eclipse.swt.widgets.TreeColumn
import org.eclipse.swt.widgets.TreeItem

class Project(parent: Composite) : Composite(parent, SWT.BORDER) {
    val horizontalForm = SashForm(this, SWT.HORIZONTAL or SWT.SMOOTH)

    val tree: Tree = Tree(horizontalForm, SWT.BORDER or SWT.FULL_SELECTION)
    val scrollComposite = ScrolledComposite(horizontalForm, SWT.BORDER)

    init {
        val compositeLayoutData = GridData(GridData.FILL_BOTH)
        horizontalForm.layoutData = compositeLayoutData

        tree.layoutData = GridData(GridData.FILL_VERTICAL)
        scrollComposite.layoutData = GridData(GridData.FILL_BOTH)

        horizontalForm.weights = intArrayOf(1, 4)

        tree.headerVisible = true

        val nameColumn = TreeColumn(tree, SWT.NONE)
        nameColumn.text = "Name"
        nameColumn.width = 100

        val extensionColumn = TreeColumn(tree, SWT.NONE)
        extensionColumn.text = "Extension"
        extensionColumn.width = 70

        // val item = TreeItem(tree, SWT.NONE)
        // item.setText(arrayOf("Hello", "dffff"))
        
        // TreeItem(item, SWT.NONE).text = "fdfff"
    }
}