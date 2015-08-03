# GMFTools #

We have written an action that can be executed via a toolbar button. You must select a GMF model file the first time you trigger it. All subsequent executions will use the selected file or the last one, if the selection is not a suitable GMF file. The following subactions are executed in the given order

  1. Delete existing gmfgen model to avoid reconciliation (see GenModelTransformation)
  1. Generate a new gmfgen model from the mapping model
  1. Create a new gmfgen transformation if it does not already exist (see GenModelTransformation)
  1. Transform the generated gmfgen model into a customized version (see GenModelTransformation)
  1. Harmonize type registrations in all active customized gmfgen models. This list is automatically filled when the action is called on a new GMF model. It can be maintined/changed in the properties section. (see SharedEditingDomain)
  1. Delete the existing diagram editor plug-in
  1. Generate the diagram code.

The subactions can be enabled/disabled individually on a preference page. Alternatively, the subactions 3, 4, and 5 are available as popup menu actions.