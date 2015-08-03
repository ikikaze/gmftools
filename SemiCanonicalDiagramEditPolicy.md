This page describes what has to be done. The necessary changes will be cast into templates shortly.

# What is a Semi-Canonical Diagram Edit Policy #
A canonical container in GMF is a `View` that is reflecting all of the semantic children in the diagram.  The `XXXCanonicalEditPolicy` is responsible for implementing this behavior.

For many applications, we want to reflect only a selected subset of these children. Consider an UML diagram showing only some of the classifiers in a package. Nevertheless, we don't want to show views for deleted semantic elements or want to automatically delete connections with one or two deleted endpoints. We call this behavior semi-canonical.

Unfortunately, semi-canonical containers are not available in GMF by default. The following is a description of the manual changes necessary to implement semi-canonical diagrams.

## Delete from Diagram vs Delete from Model ##
The two different acions are provided only, if a diagram does not register an `EditPolicy` as the canonical role. An easy way to enable both is to register the generated `DiagramCanonicalEditPolicy` with as another role in the generated `DiagramEditPart`.

## Adapt generated DiagramCanonicalEditPolicy ##
  * Modify the method `getSemanticChildrenList()` to filter all semantic children which don't have a view, e.g. by calling

```
ElementViewFilter.filterElementsWithoutViewInParent(result, viewObject);
```

> before returning the list of semantic children.

  * Change superclass to de.itemis.gmf.runtime.edit.policy.SemiCanonicalDiagramEditPolicy

  * Make `collectAllLinks()` and `createConnections()` protected.

  * In `createConnections()`, change the boolean parameter in the constructor call to the `ConnectionViewDescriptor` from `false` to `true` to automatically make created edges persistent.

  * Comment out the last line in `refreshConnections()` and return an empty list instead, to avoid automatically adding of links. This makes the second half of the loop body above obsolete.

  * Implement the missing abstract methods. This step is necessary, as the generated XXXLinkDescriptors don't have a generic superclass. So you must cast the `linkDescriptor` to the generated class and investigate the conditions yourself.

## Enable Dropping of Semantic Elements onto the Diagram ##

Register the `GenericDiagramDropEditPolicy` as `DRAG_DROP_ROLE` in the generated `DiagramEditPart`.

```
installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new GenericDiagramDropEditPolicy());
```

TODO: This EP collides with the one generated if Shortcuts are enabled. We have to modify the template that generates the DragAndDropEditPolicy for shortcuts.

TODO: Allow dropping elemens that are mapped to connections, not just nodes.

## Add an action that adds all connections that can be created from the semantic model for the selected node ##

As this action is generic, it has been implemented in `de.itemis.gmf.runtime.extensions.actions.AddConnectionsAction`.

TODO: multiselection does not work