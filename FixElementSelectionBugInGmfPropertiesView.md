# Introduction #
Selecting references in properties views can be done using the mouse or by an incremental search using your keyboard. The keyboard selection usually works for a small list of choices. With bigger models the list quickly becomes large and the key selection mechanism will break the ExtendedComboBoxCellEditor in GMF properties view until you restart Eclipse. Since browsing through large combobox lists with your mouse is no option, this [bug](https://bugs.eclipse.org/bugs/show_bug.cgi?id=193757) needs to be fixed. As long as it's not fixed in the GMF code base you need to do it yourself.

# Details #

To fix the issue:
  1. import the plugin **org.eclipse.gmf.runtime.emf.ui.properties** as source project into your workspace
  1. open the class **UndoableModelPropertySheetEntry**
  1. navigate to the method **applyEditorValue()**
  1. replace the first **if**-block with:
```
if (editor == null || !editor.isActivated()) {
  return;
}
```
  1. make sure, your launch configuration is using the workspace version of the **org.eclipse.gmf.runtime.emf.ui.properties** plugin