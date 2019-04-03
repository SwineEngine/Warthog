package org.swineproject.warthog.project

import org.eclipse.swt.SWT
import org.eclipse.swt.custom.SashForm
import org.eclipse.swt.custom.ScrolledComposite
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.widgets.*
import org.swineproject.warthog.Globals
import java.io.File

class Project(parent: Composite) : Composite(parent, SWT.BORDER) {
    val horizontalForm = SashForm(this, SWT.HORIZONTAL or SWT.SMOOTH).apply { layoutData = GridData(GridData.FILL_BOTH) }

    val tree: Tree = Tree(horizontalForm, SWT.BORDER or SWT.FULL_SELECTION or SWT.VIRTUAL).apply {
        layoutData = GridData(GridData.FILL_BOTH)
    }

    val scrollComposite = ScrolledComposite(horizontalForm, SWT.BORDER or SWT.H_SCROLL or SWT.V_SCROLL).apply {
        expandHorizontal = true
        expandVertical = true
    }

    val fileComposite = ToolBar(scrollComposite, SWT.WRAP).apply {
        // background = Display.getDefault().getSystemColor(SWT.COLOR_WHITE)
    }

    init {
        scrollComposite.content = fileComposite

        horizontalForm.weights = intArrayOf(1, 3)

        tree.addListener(SWT.SetData) {
            val item = it.item as TreeItem

            val directory: File
            if (item.parentItem == null) {
                directory = (tree.data as Array<File>)[it.index]

                item.setText(arrayOf(directory.name))
            }
            else {
                directory = (item.parentItem.data as Array<File>)[it.index]

                item.setText(arrayOf(directory.name))
            }

            if (directory.isDirectory) {
                val directoryList = directory.listFiles { file ->
                    file.isDirectory && Globals.directoryBlacklist.all { !file.name.startsWith(it) }
                }

                if (directoryList.isNotEmpty()) {
                    item.data = directoryList
                    item.itemCount = directoryList.size
                }
                else {
                    item.data = directory.absoluteFile
                }
            }
        }

        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                if (e.item != null) {
                    for (i in fileComposite.items) {
                        i.dispose()
                    }

                    val testFile = (e.item.data as? File)
                    if (testFile != null) {
                        for (i in testFile.listFiles { file -> !file.isDirectory}) {
                            ToolItem(fileComposite, SWT.RADIO).apply {
                                text = i.name
                            }
                        }

                        scrollComposite.setMinSize(fileComposite.computeSize(SWT.DEFAULT, SWT.DEFAULT))
                    }
                }
            }
        })
    }

    fun refreshFiles() {
        val directoryList = File(Globals.workingDirectory).listFiles { file ->
            file.isDirectory && Globals.directoryBlacklist.all { !file.name.startsWith(it) }
        }
        tree.itemCount = directoryList.size
        tree.data = directoryList
    }
}