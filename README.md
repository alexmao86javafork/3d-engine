# 3d-engine
Simple Pure Java 3D Engine

For educational purposes and for fun, I made this simple yet interesting project to try first hand the beauty of 3d graphics.

As the description suggests, this project does not depend on any third party library, nor it uses hardware acceleration, except maybe when the image buffer is painted on screen.

Left Mouse Button drag to rotate
Right Mouse Button drag to resize

ATTENTION: Because the off screen triangles are not filtered, and because of the perspective projection, when resized too much the off screen triangles become too big thereby slowing down the rendering process. Try to keep the objects within the visible screen. Hopefully will work on this and make it safer :)
