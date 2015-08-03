# Prerequistes #

The GMFTools can be run in two configurations:

  * Eclipse Galileo 3.5 http://www.eclipse.org/downloads/
  * GMF 2.2 http://www.eclipse.org/modeling/gmf/
  * M2T/Xpand 0.7 http://wiki.eclipse.org/Xpand
  * make sure to install `de.itemis.gmf.tools.m2t`

or

  * Eclipse Ganymede 3.4 http://www.eclipse.org/downloads/
  * GMF 2.1.0 http://www.eclipse.org/modeling/gmf/
  * openArchitectureWare 4.3 http://wiki.eclipse.org/OAW
  * make sure to install `de.itemis.gmf.tools.oaw`

You can download a large bundle containing everything but GMF and its prerequisites in the download section of
http://oaw.itemis.de/

Furthermore, if you plan to use shared editing domains, we recommend to install the plugin `org.eclipse.gmf.bridge.trace` to persist the `visualIDs` of element types between generator runs. It is part of GMF's experimental SDK but you can also check it out individually from CVS at

```
:pserver:anonymous@dev.eclipse.org/cvsroot/modeling
org.eclipse.gmf/plugins/org.eclipse.gmf.bridge.trace
```

# Setup #

  * Download the sources. This can also be done from within Eclipse using an appropriate SVN plugin.
  * Start Eclipse and import the plug-in sources.
  * Export `de.itemis.gmf.tools` into as a deployable plug-in into your Eclipse installation.
  * Restart Eclipse.
  * Import the source of the `de.itemis.gmf.runtime.extensions` project.