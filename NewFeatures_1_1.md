# 1.1.7 #
  * Tools: Button now runs last build
  * Runtime: Complete redesign of shared editing domain. Restored `WorkspaceSynchronizer` as in original generated code but share it among `ResourceSetInfo`s of the same editing domain. Should now work with multiple diagams sharing editing domain and other editors, e.g. Xtext, that are synchronizing on file changes only.

# 1.1.6 #
  * Converted GMF button into a pulldown button, accessing all registered GMFTools configurations.

# 1.1.5 #
  * Configuration set is stored in preferences when starting .def file. You can then use the button afterwards.
  * Warning instead of empty error message when project containing trafo file does not have oAW/XtendXpand nature.
  * Removed template aspects for ElementChooser, as it works with proxies anyway.

# 1.1.4 #
  * Definition file (.def), being used to generate a GMF editor via the context menu entry (see changes for 1.1.2) may now also specify options to override those settings in the preferences store. A sample entry within a .def file may look as follows:

```
  # Properties for GMF Tools Engine
  deleteGmfGenModel = true
  transformMapping2GmfGenModel = true
  transformGmfGenModels = true
  fixRegisteredTypes = true
  deletegeGeneratedDiagramPlugin = true
  generateDiagramPlugin = true
```

# 1.1.3 #

  * You can now either use oAW/Xtend or M2T/Xpand for the transformation (thanks to Earl Waldin from paranor). You have to choose the appropriate plug-in `de.itemis.gmf.tools.oaw` or `de.itemis.gmf.tools.m2t`.
  * The templates have been migrated to GMF 2.2RC3. The 2.1 templates are still there and can still be used. Make sure to point your `Tempate Directory` to the right location.
  * The example has been migrated to GMF2.2RC3. It will no longer work with GMF 2.1.
  * You should not put the `gmfgen.ecore` in the directory where your models reside. This will cause an error with GMF 2.2. Rather than that, add a dependency from your model plug-in to `de.itemis.gmf.runtime.extensions`.

# 1.1.2 #
  * Added context menu action to generate a GMF editor based on the GMF model settings specified in a .def file (and not based on GMF model entries in the preferences). The .def file is a simply property file of the following form:
```
  # Definition file read by GMF Tools Engine
  gmfmap = /org.vires.diagram.functions.def/model/Vires_Functions.gmfmap
  genmodel = /org.vires.model/model/Vires_Functions.genmodel
  ext = /org.vires.diagram.functions.def/model/Vires_Functions.ext
  gmfgen = /org.vires.diagram.functions.def/model/Vires_Functions.gmfgen
  transformedgmfgen = /org.vires.diagram.functions.def/model/Vires_Functions.transformed.gmfgen
```

# 1.1.0 #

In version 1.1
  * Added templates for sharing an editing domain in GMF 2.2 M5
  * ResourceUnloadingStrategy now looks for open diagram editors before unloading unneeded resources. This is more consistent with combined semantic/graphical model files. It also eliminates the need for the extension point.
  * You can now specify in the preferences where the model and transformation files for an editor are located, so models and transformation are no longer associated via naming conventions. This makes it easier to build GMF editors for genmodels in another plug-in.
  * The transformation can now include other extensions on the project's classpath, the same way as it would work in oAW.

# Migrating from 1.0 #

  * Make sure you register the GMF gen models that share element types in the new preference section.
  * Regenerate your editors to eliminate the contributions to the deleted extension point.
  * **IMPORTANT:** You must create a GMF-File set in the GMFTools' preferences in order to make the GMF button work!