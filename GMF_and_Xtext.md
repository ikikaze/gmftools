# Introduction #

# How to get the Xtext / GMF example running #

  * Prerequisites:
    * JRE 6 or higher
    * Eclipse Galileo distribution including Xtext 0.7.1, GMF 2.2, SVN support  + Dependencies

  * Checkout the source code (four plug-ins) from
```
http://gmftools.googlecode.com/svn/trunk/xtext-gmf-example
```
  * (Re-)generate the Xtext code by running the workflow
```
org.xtext.cg2009.exercise1.solution/src/org/xtext/cg2009/GenerateEntities.mwe
```
> as an MWE Workflow to make sure you're compatible with the latest version of Xtext.
  * Generate the EMF Edit plug-in by opening
```
org.xtext.cg2009.exercise1.solution/src-gen/org/xtext/cg2009/Entities.genmodel
```
> And choosing "Generate Edit Code" from the context menu of the root element.
  * Generate the GMF code by choosing "Generate diagram code" on
```
org.xtext.cg2009.exercise1.gmf/model/Entities.transformed.gmfgen
```
  * Even though I used GMFTools for creating the example, it is neither necessary to generate the code nor to run the example.
  * You should now have at least six plug-in projects in your workspace
|org.xtext.cg2009.exercise1.glue|Glue code to bridge Xtext and GMF editor|
|:------------------------------|:---------------------------------------|
|org.xtext.cg2009.exercise1.gmf |The GMF models to generate the editor from|
|org.xtext.cg2009.exercise1.solution|The Xtext runtime plug-in containing the language infrastructure|
|org.xtext.cg2009.exercise1.solution.diagram|The generated GMF diagram               |
|org.xtext.cg2009.exercise1.solution.ui|The Xtext UI plug-in containing the Xtext editor|
|org.xtext.cg2009.exercise1.solution.edit|The EMF edit plug-in generated from the EMF genmodel|

  * Spawn a new Eclipse instance
    * Create a project with an new .entity file
    * Define some entity and save
    * Right-click on the file in the navigator and choose "Initialize new Entities diagram..."
    * Have fun

## Known Limitations ##
  * Due to GMF bug
```
http://bugs.eclipse.org/bugs/show_bug.cgi?id=281014
```
> the diagram lacks canonical elements after restart
  * Due to bug
```
https://bugs.eclipse.org/bugs/show_bug.cgi?id=284850
```
> you cannot start from an empty diagram. This is partly fixed in the CVS HEAD version.

## Known Pitfalls ##
  * Confusing XtextSerializationExceptions: When serializing a model with Xtext (this happens when you save a GMF diagram) all constraints that are implied by the grammar must be fulfilled. For example, if an assignment in the grammar is mandatory, the model can only be serialized when the corresponding value in the model is actually set. Xtext's ParseTreeConstructur may throw SerializationExceptions with confusing messages. It doesn't so so because it hates you, it just doesn't know any better due to its backtracking algorithm. In this case, Xtext's ConcreteSyntaxValidator is your friend. This is an EValidator that can be run from within GMF or configured to always run before a resource is serialized. The ConcrteSyntaxValidator produces understandable error messages.

  * Performance problems during loading due to GMF's CrossReferenceAdapter. GMF uses the CrossReferenceAdapter to maintain target-to-origin maps for all cross references. However, per default the CrossReferenceAdapter is configured to resolve all cross references in an eager way. This practically disables Xtext's lazy linking. The CrossReferenceAdapter can be configured not to resolve cross references on its own:
```
public class CustomDiagramEditingDomainFactory extends DiagramEditingDomainFactory {

	private static CustomDiagramEditingDomainFactory instance = new CustomDiagramEditingDomainFactory();

	public static WorkspaceEditingDomainFactory getInstance() {
		return instance;
	}

	protected void configure(TransactionalEditingDomain domain) {
		final ResourceSet rset = domain.getResourceSet();
		// Create a CrossReferenceAdapter with resolve == false.
		// This ensures that cross references are resolved lazy (on demand) 
		// and not by the CrossReferenceAdapter itself. Resolving them
		// eagerly can be fatal for Xtext's performance.
		// Usually, super.configure() sets the Adapter. 
		// However if we do it before super.() does it, it won't be set again.
		if (CrossReferenceAdapter.getExistingCrossReferenceAdapter(rset) == null) {
			rset.eAdapters().add(new CrossReferenceAdapter(false));
		}
		super.configure(domain);
	}

}
```