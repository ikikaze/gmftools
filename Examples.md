# Using the examples #

Try out the [GMFTools](GMFTools.md) development extensions:
  * Follow [setup steps](Prerequistes.md).
  * Import the plug-in `de.itemis.gmf.example` into your workspace.
  * Open the file `model/Entity.genmodel` and choose "Generate Model Code" from the context menu of the root node.
  * Either import `de.itemis.gmf.example.entity.edit` or also generate its code by choosing "Generate Edit Code" from the context menu of the root node in the `model/Entity.genmodel`. In the first case, you'll get nicer icons snatched from the Ecore-UML project.
  * Select `model/Entity.gmfmap` and push the "GMF" button in the toolbar.
  * Select `model/Datatype.gmfmap` and push the "GMF" button in the toolbar.
  * Spawn a new Eclipse application (Run->Run Configurations...).

The runtime extensions can be observed in the new Eclipse workbench:
  * Create a new project.
  * Create a new "Entity Diagram", add some elements.
  * Select the semantic model, i.e. the file with the extension ".entity" and choose "Initialize new datatype\_diagram diagram file".

The example demonstrates the following GMFTools features
  * [Shared editing domain](SharedEditingDomain.md)
    * If you add a `Namespace` in either of the diagrams, it should also appear in the other.
    * Diagrams are saved synchronously.
    * Resources are only unloaded if they are no longer referenced by any editor.
  * [Custom layout](CustomFiguresAndLayouts.md) for `Namespace` ellipse figures.