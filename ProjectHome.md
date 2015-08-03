This project offers a collection of development tools, extensions to the runtime library and extensions to the code generator of the Graphical Modeling Framework ([GMF](http://www.eclipse.org/modeling/gmf/)).

GMF is an generative framework to design graphical model editors based on the [Eclipse](http://www.eclipse.org) platform. To create an editor, the user designs couple of [EMF](http://www.eclipse.org/modeling/emf/?project=emf) based models, which are fed into GMF's code generator which produces an Eclipse plug-in that implements the diagram editor. While being quite productive in the development of the first 80% of an application it is rather hard to finish the remaining 20% manually, as the underlying frameworks are quite complex.

Nevertheless, some reoccurring tasks can be cast into templates and extensions of the runtime library, encapsulating the nifty details and pushing productivity even further.

This project is all about making development with GMF easier and more productive. It contains
  * Development tools
  * M2M transformations to customize the existing/generated GMF models
  * Template extensions for reoccurring tasks
  * Runtime library extensions

If you want to stay uptodate with GMFTools, subscribe to http://groups.google.de/group/gmftools.