# Introduction #

The mapping-model allows to define different kinds of constraints. By using OCL or Rexep it is not necessary to touch the generated code. Unfortunately there is no tooling to prove if the constraint works. In some cases OCL-statements will become to complicated. For this cases you may have the need to implement your constraint in Java. GMF will generate a sceleton for you with an emty body. The next step would be to touch the generated code, because there is no hook to handle in your implementation in another way.

The following page will show how to use a hook which is part of GMFTools to implement Constraints for Nodes and Links without touching the generated code.


# Details #

There are two extensionpoints to register Java-Constraints. One for Node- and one for LinkConstraints.

`de.itemis.gmf.runtime.extensions.linkConstraintHook`

`de.itemis.gmf.runtime.extensions.nodeConstraintHook`

The constraints are defined for one single nsURI.
The constraint itself must implement the corresponding Interface `de.itemis.gmf.runtime.constrainthook.INodeConstraintDefinition` / `de.itemis.gmf.runtime.constrainthook.ILinkConstraintDefinition`.

For example: If you want to implement a constraint to avoid the creation of a special Node you have the information which visualID the node has an what the container would be. Out of this informations you can return a boolean if the creation should be avoided or not.

```
public interface INodeConstraintDefinition {

	
	public boolean canCreateNode(int childVisualID, EObject container);
}
```

For a LinkConstaint you will have the information about source, target and what the container would be.

```
public interface ILinkConstraintDefinition {

	
	public boolean canCreateLink(int childVisualID, EObject container, EObject source, EObject target);
}
```

By using the custom templates `de.itemis.gmf.runtime.extensions/templates-3.4/constraints` for Ganymede or `de.itemis.gmf.runtime.extensions/templates-3.5/constraints` for Galileo the hook will be generated to the right place and your constraints will be executed.