# Custom Figures #
All figures described here are meant to be referenced in the graph model by means of `CustomFigure` nodes in the figure gallery.

# Custom Layouts #

All layouts described here are meant to be referenced in the graph model by means of `CustomLayout` nodes  in the figure gallery.

## de.itemis.gmf.runtime.layout.ResizableFlowLayout ##

A layout for nodes with compartments that fills the parent figure if it is resized. The `FlowLayout` created by GMF does not fill the parent figure, resulting in strange looking nodes, especially with empty compartments.

Before:
![http://gmftools.googlecode.com/svn/wiki/images/without_fill.png](http://gmftools.googlecode.com/svn/wiki/images/without_fill.png)
After:
![http://gmftools.googlecode.com/svn/wiki/images/with_fill.png](http://gmftools.googlecode.com/svn/wiki/images/with_fill.png)

## de.itemis.gmf.runtime.layout.ScaleInnerFigureLayout ##

A layout for figures whose bounding box is bigger than the area to raw its children in, e.g. ellipses or diamond figures. These the child area is usually the outer bounds scaled down by a divisor, e.g. sqrt(2) for ellipses and 2 for diamond figures. This number can be provided using the a `CustomLayoutAttribute` named `scaleFactor` in the graph model.

Before:
![http://gmftools.googlecode.com/svn/wiki/images/without_scaling.png](http://gmftools.googlecode.com/svn/wiki/images/without_scaling.png)
After:![http://gmftools.googlecode.com/svn/wiki/images/with_scaling.png](http://gmftools.googlecode.com/svn/wiki/images/with_scaling.png)