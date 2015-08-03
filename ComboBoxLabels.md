# Introduction #

In standard GMF, the labels in a diagram usually refer to one or more EAttributes of an element. In some cases, you want a label to reflect the value of an EReference.

E.g., consider a transition in a state diagram: The trigger of such a transition can be an event that is already defined and contained in another model. Nevertheless, you want the name of the trigger to be shown as in the label of the transition.

There are two challenges in implementing that with GMF
  1. GMF only allows to link labels to attibutes of the underlying semantic element.
  1. Rather than entering the name of the referenced element, you my want to choose it from a list of candidates.

GMFTools provides two classes and an interface to help you in this context

  * `de.itemis.gmf.runtime.combolabel.ComboBoxDirectEditManager` replaces the `TextDirectEditManager` in the generated label edit part. It uses a `ComboBoxCellEditor` to let the user choose the value of the reference from a list of candidates.
  * `de.itemis.gmf.runtime.combolabel.ComboBoxDirectEditPolicy` replaces the `LabelDirectEditPolicy` to circumvent the generated parser and directly set the selected value using the `ISemanticRedirectingEditPart` interface.
  * `de.itemis.gmf.runtime.combolabel.ISemanticRedirectingEditPart` is an additional interface the generated label edit part should implement to provide the list of candidates, a getter and a setter for the reference's value.


# Implementation #

Sorry, but this is only a rough sketch of the steps involved. The modifications require a deeper knowledge of GMF and are considered for power users. We are also aware that many steps can be achieved in a different way, too.
  * Generate your editor with a label mapping to a dummy attribute in your parent class. This is the easiest way to produce the VISUAL\_ID and type/view registration for the label edit part.
  * Create a customized label edit part. The recommended way to do that is via the editPartProvider service of GMF, to keep generated code separate from manual extensions.
  * In that customized edit part
    * Override `addSemanticListeners()`, `removeSemanticListeners()`, `handleNotificationEvent(Notification event)` to additionally make the edit part listen to the changes of the reference and the attributes for the referenced object's label.
    * Override `getManager()` to return a `ComboBoxDirectEditManager`.
    * Override `createDefaultEditPolicies()` and replace install a new `ComboBoxDirectEditPolicy` with the `EditPolicy.DIRECT_EDIT_ROLE` role.
    * Override `getLabelText()` to compose the label's text from the referenced element.
    * Let the edit part implement `ISemanticRedirectingEditPart`.

The result can look like this

![http://gmftools.googlecode.com/svn/wiki/images/comboLabel_expanded.png](http://gmftools.googlecode.com/svn/wiki/images/comboLabel_expanded.png)