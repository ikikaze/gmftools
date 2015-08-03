# Introduction #

Some version control tools like Clearcase sets checked in artefakts to read only. This  could cause problems, because GMF internaly validates if all loaded resources are writeable. If there is at least one that is read only, the whole editor would be read only.
Normally you could say that this behaviour is correct, but when you use library-model-elements (e.g. Xtext-models) in your model and you do not want to modify them, you would have a need to leave the editor writeable even when there are read-only resources referenced.

This page discribes where the validators of GMF are and how you could deal with this problem.


# Details #
GMF internaly uses a _org.eclipse.gmf.runtime.common.core.command.FileModificationValidator_ which has a _org.eclipse.gmf.runtime.common.core.command.IModificationValidator_ to prove if a list of IFiles is writeable.

```
	/**
	 * Checks that the <code>files</code> may be modified.
	 * 
	 * @return the approval status
	 */
	public static IStatus approveFileModification(IFile[] files) {
		return getValidator().validateEdit(files);
	}
```

This delegates to the _org.eclipse.gmf.runtime.common.core.command.IModificationValidator_. The standard implementation is _org.eclipse.gmf.runtime.common.ui.util.UIModificationValidator_.

```
    /**
     * This is the where the real call to validate the files takes place.
     * 
     * @param files list of files to validate.
     * @param shell the shell to use when displaying error messages.
     * @return the status indicating whether the validate succeeded or not.
     */
    protected IStatus doValidateEdit(IFile[] files, Shell shell) {

		boolean ok = FileModificationValidator.getInstance().okToEdit(files,
				CommonUIMessages.UIModificationValidator_ModificationMessage,
				shell);
		return ok ? Status.OK_STATUS : ERROR_STATUS;
	}

```

This delegates back to the FileModificationValidator which delgates to the _org.eclipse.core.internal.resources.Workspace_.

```
/* (non-javadoc)
	 * @see IWorkspace#validateEdit(IFile[], Object)
	 */
	public IStatus validateEdit(final IFile[] files, final Object context) {
		// if validation is turned off then just return
		if (!shouldValidate) {
			String message = Messages.resources_readOnly2;
			MultiStatus result = new MultiStatus(ResourcesPlugin.PI_RESOURCES, IResourceStatus.READ_ONLY_LOCAL, message, null);
			for (int i = 0; i < files.length; i++) {
				if (files[i].isReadOnly()) {
					IPath filePath = files[i].getFullPath();
					message = NLS.bind(Messages.resources_readOnly, filePath);
					result.add(new ResourceStatus(IResourceStatus.READ_ONLY_LOCAL, filePath, message));
				}
			}
			return result.getChildren().length == 0 ? Status.OK_STATUS : (IStatus) result;
		}
		// first time through the validator hasn't been initialized so try and create it
		if (validator == null)
			initializeValidator();
		// we were unable to initialize the validator. Validation has been turned off and 
		// a warning has already been logged so just return.
		if (validator == null)
			return Status.OK_STATUS;
		// otherwise call the API and throw an exception if appropriate
		final IStatus[] status = new IStatus[1];
		ISafeRunnable body = new ISafeRunnable() {
			public void handleException(Throwable exception) {
				status[0] = new ResourceStatus(IStatus.ERROR, null, Messages.resources_errorValidator, exception);
			}

			public void run() throws Exception {
				Object c = context;
				//must null any reference to FileModificationValidationContext for backwards compatibility
				if (!(validator instanceof FileModificationValidator))
					if (c instanceof FileModificationValidationContext)
						c = null;
				status[0] = validator.validateEdit(files, c);
			}
		};
		SafeRunner.run(body);
		return status[0];
	}
```

From here the Workspace delegates to a _org.eclipse.core.resources.team.FileModificationValidator_ which proves if the files are read only or not.

This dependency from the FileModificationValidator to the UIModificationValidator could be set by the following method in the FileModificationValidator:
```
	/**
	 * Set the modification validator to be used to check that files can be
	 * modified.
	 * <P>
	 * This method may only be called once, and must be called before any
	 * request to validate files. Attempts to set the validator will be ignored
	 * after it has been already set, or after a default one has been created.
	 * 
	 * @param validator
	 *            the modification validator
	 */
	public static void setModificationValidator(IModificationValidator v) {
		if (validator == null) {
			validator = v;
		}
	}
```

As you can imagine the FileModificationValidator is a singleton and the IModificationValidator could be set only ones.
The problem is that the _org.eclipse.gmf.runtime.common.ui.internal.CommonUIPlugin_ sets the default validator at startup-time.
So you have no chance to handle in your custom implementation.

```
	/**
	 * Override to initialize the modification validator.
	 */
	public void start(BundleContext context)
		throws Exception {
		super.start(context);
		
		// Make sure validateEdit for resources affected by GMF operations is
		// done with UI context.
		uiValidator = new UIModificationValidator();
		FileModificationValidator.setModificationValidator(uiValidator);
	}
```

There are two ways of solving this problem. The first is to modify the CorePlugin and nobody wants this. The second one is to set the validator of the FileModificationValidator by reflection at statuptime of your custom plugin.