# Shared Editing Domain #

The editing domain of an GMF editor holds all model resources, manages transactions, etc. In the normal case, the GMF generated code creates a new editing domain for each diagram editor that is opened. This can lead to a couple of problems

  * Multiple instances of the same model elements are held in memory at the same time, resulting in large memory overhead.
  * Editors synchronize their contents only on file changes. If a file resource change triggers a reload in a dirty editor, the user has the choice to keep either the changed version in the editor or the currently saved one. This results in information loss and nasty synchroniztion loops.

Typical usecases for sharing an editing domain are:

  * Several GMF editors show different metamodel partitions of the same model.
  * Several GMF editors show different partitions of the same model.

Sharing an editing domain between several editors has a number of consequences:

  * Editors also share their command stack. Undo/Redo might affect other editors than the active one.
  * Editors sharing the same EditingDomain are either all dirty or all clean.
  * You no longer dispose the EditingDomain when you close an editor, so you need an unloading strategy to get rid of unneded resources to avoid a memory leak.
  * You might still have references to unloaded models on the command stack. We haven't developed an cleanup strategy yet. Help is welcome.

## Helpful Resources ##

The GMF Wiki already offers a section on that topic ([GMF Tips](http://wiki.eclipse.org/index.php/GMF_Tips#Sharing_single_EditingDomain_instance_across_several_diagrams)). Even though marked as new, it is a couple of months old and does not describe the whole story.

Other useful resources on that topic are in the GMF newsgroup, especially [1](news://news.eclipse.org:119/ftvt82$os5$1@build.eclipse.org) and [2](news://news.eclipse.org:119/fpjnn4$2i5$1@build.eclipse.org).

## Solution with GMFTools ##

This section describe how to share an editing domain using GMFTools.

### Eclipse ###

  * Install the [GMFTools](Prerequistes.md) and its prerequisites.

### Workspace ###

The following changes are necessary in the workspace containing the GMF models
  * Import the `de.itemis.gmf.runtime.extensions` plug-in
  * Create a plug-in with a model folder. The latter will contain all models.
  * Create your domain model and its genmodel and generate the model and edit plug-ins.
  * Create your GMF models.
  * If you want to use GMF Tools make sure
    * The `gmfgen.ecore` is on the classpath of the model plug-in
    * All models relating to one diagram type are called the same (apart from the extension). If you use the same domain model for different diagram editors, you need a copy of the dnmain genmodel for each diagram type.
    * Enable the `openArchitectureWare Nature` on the project holding the models to have code completion etc.
    * Disable GMF's oAW nature as it will report errors in your transformation file! You have to do that using a text editor in the hidden file `.project` in the root directory of your model plug-in.

### GMF Generator Model ###

You should apply some changes to the GMF generator model. I recommend to use the M2M-transformation provided by the de.itemis.gmf.tools plug-in as a toolbar button or as popup menus.

#### GenEditorGenerator ####

  * Use distinct model IDs
  * Use distinct diagram file extensions
  * Set dynamic templates to true
  * Set template directory to `de.itemis.gmf.runtime.extensions/templates/sharedEditingDomain`
  * It's nice to use distinct package name prefixes, too.

#### GenPlugin ####

  * Use distinct plug-in IDs

#### GenDiagram ####

  * Manually set distinct visual IDs. `org.eclipse.gmf.bridge.trace` unfortunately won't help you.

#### GenNodes/GenLinks ####

  * Manually set distinct `visualIDs` or use `org.eclipse.gmf.bridge.trace` plug-in

#### MetamodelTypes ####

Only one ElementType per EClass is allowed in the ElementTypeRegistry. If you have multiple representations of the same EClass, you must use one metamodelType and replace the others with specializationTypes referring to the first. There are several ways to do that:
  * Modify the GMF genmodels manually.
  * Use the `GMF Tools->Harmonize type registrations` popup menu action from [GMFTools](GMFTools.md).
  * Push the big GMF button from [GMFTools](GMFTools.md).
Note there is a TODO item here: It would be much nicer if GMF's extensible type registry handled such cases better. Another maybe simpler alternative would be to generate unique metamodelTypes for all types in the metamodel and use only specializationTypes in the diagrams.

## Changes in the templates ##

The following list describes the changes to the templates, applied using Xpand aspects. It might be useful when migrating to the next version of GMF:

  * Added a dependency to the plug-in `de.itemis.gmf.runtime.extensions`
  * All relevant instantiations of an editing domain refer to `de.itemis.gmf.runtime.editingdomain.SharedEditingDomainUtil.getSharedEditingDomain(sharedEditingDomainID)`.
  * All calls to `EditingDomain.dispose()` have been eliminated.
  * `ResourceSetInfo` has been refactored. Common things, e.g. the `WorkspaceSynchronizer` has been extracted to `SharedResourceSetInfoDelegate` which is connected to the editing domain by means of a `SharedResourceSetInfoAdapter`
  * The `ResourceInfo.dispose()` calls `de.itemis.gmf.runtime.editingdomain.ResourceUnloadingTool` to selectively unload unneeded resources.
  * The `ResourceInfo` is set dirty on creation if the resource set is dirty.
  * All occurrences of `Resource.unload()` have been revised.
  * An extension to `de.itemis.gmf.runtime.extensions.diagramEditorInfo` is registered for each diagram editor, describing the file extension the editor uses for diagrams. It is needed to distinguish between diagram and semantic resources in the `ResourceUnloadingTool`.
  * In the `plugin.xml`, the types are registered for the shared editing domain ID and a common client context.