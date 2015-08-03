# GMF Generator Model Transformation #

## The GMF Generator Model Reconciler ##
Having defined your mapping model, GMF requires generating an intermediate gmfgen model, which serves as the input of the code generator. In most scenarios, the developer has to apply changes to the gmfgen model to customize the generated plug-in, e.g. enable printing.

GMF uses an internal, transparent transformation from the map model to the gmfgen model. This transformation ends with a reconciling step, trying to merge manual changes to the gmfgen model with the newly generated version.

This reconciliation step does not always work as expected, so manual changes might be lost. Even though the transformation has been documented in the meantime ([GMF Genmodel](http://wiki.eclipse.org/GMF_GenModel_Hints)), some customizations are simply always lost, i.e. changing the visual ID of the diagram or the name ID of the editing domain. Being forced to apply the same changes manually on every change of the mapping model is quite annoying, so we have started to look for a better solution.

## Our Solution: Use M2M-Trafo Instead of Reconciling ##

Our solution is to define a m2m-transformation to change the gmfgen model rather than directly editing it. Code-generation is then started form the transformed version. That way, the reconciler is bypassed and the gmfgen model becomes an temporary artifact. It does not even have to be version controlled, as it can be reconstructed from the map model at any time. The customized workflow is sketched in the following diagram. Dashed arrows depict information flow, while solid arrows represent model references.
![http://gmftools.googlecode.com/svn/wiki/images/GMF_Genmodel_Trafo.png](http://gmftools.googlecode.com/svn/wiki/images/GMF_Genmodel_Trafo.png)

Note that we follow the "Never mix manually written code with generated code" best practice, which causes some people to call us "purists".

We use openArchitectureWare's Xtend to actually perform the transformation. Note that the transformation is subject to version control as it describes your customizations.

The transformation must reside in a file with the same name as the mapping model but `.ext` as the extension. It must define an extension function

```
List[emf::EObject] transform(List[emf::EObject] gmfGenModelContents)
```

that is called by GMFTools UI actions. The example shows how to delegate from that extension to other type specific ones. You can also create an example transformation by choosing `GMF Tools->Create GMF genmodel transformation` from the context menu of an existing gmfgen file. You can apply that transformation choosing `GMF Tools->Transform GMF genmodel`. This will result in a new gmfgen model from which you can generate the diagram code as usual.

The new generation workflow (and more stuff) is wrapped in a single UI action, that can be triggered by clicking on the GMF button in the action bar. See [GMFTools](GMFTools.md) for details

### Considerations Using Xtend ###

Unfortunately, GMF ships with its own stripped down version of Xtend that is not suitable for M2M trafos, because it  lacks the following features
  * Code completion in the editor, which is enormously useful when writing transformations for a metamodel you know little about.
  * An API for EMF model manipulation, e.g. setters don't work.
  * The generic reflective API in the typesystem, e.g. `eAllContents` is unavailable.

The backside of using oAW's Xtend are
  * GMF models, imported metamodels and the transformation have to reside on the classpath of the model project, e.g. be in a source folder, be visible to the model plug-in.
  * Imports statements work different. You don't specify the `nsURI` of a metamodel, but its `nsPrefix`.
    * We have to disable GMF's oAW builder on the project holding the GMF models, as it creates error markers for the import statements.
    * You cannot have GMF Xtend files in the same project.
    * If you use dynamic templates and rely on syntax error feedback from the IDE, the templates have to be in another project.