# ImgToJsonGUI
Programa para realizar mapas a partir de una imagen.

#Entrada / salida
El programa necesita un png como entrada. Dentro de la carpeta donde está el png buscará los ficheros para construir el mapa.Los fichers que busca son los mismos que los de salida. Este programa devuelve 5 ficheros de salida.

Los ficheros son los siguientes:

intersection.json - Contiene la información de las intersecciones

segments.json - Contiene la información de los segmentos

steps.json - Contiene la información de los steps que forman el segmento

prohibitions.json - Contiene la información de los caminos que no se pueden seguir

ESCENARIO_SIN_NOMBRE.png - Imagen del mapa

#Ejecución

Una vez ejecutas el programa aparecen dos ventanas, una ventana contiene los datos de los segmentos, intersecciones y steps(ISS). La otra ventana contiene una barra de opciones y es la que contendrá la estructura de nuestro mapa. En la barra de opciones tenemos diferentes posibilidades de empezar a utilizar el programa. Podemos utilizar las opciones **Nuevo** o **Importar** para iniciar el proceso.
En cualquiera de estos casos nos abrirá un explorador de archivos donde buscar la imagen de nuestro mapa **Es necesario que en el caso de importar tengamos la imagen en la misma carpeta que los ficheros del mapa**

Una vez seleccionado el mapa podemos ver la imagen de seleccionada y entramos en el modo de añadir intersecciones. Dentro de este modo podemos clickar dentro de la imagen y se nos abrirá una ventana en la que rellenar los datos de las intersecciones. Una vez rellenados los datos podemos ver un punto rojo en la imagen que hemos escogido, en el punto seleccionado. Una vez hayamos puesto todas las intersecciones que quereamos pasamos al **MODO** Segmento.
En el modo Segmento podemos añadir los caminos que van desde una intersección a otra. En principio clicamos sobre la intersección inicial del segmento. Nos aparecerá una ventana informativa. Al cerrarla podemos empezar a modelar el camino para nuestro segmento(steps) que acabará al clickar en la intersección destino.

Una vez clicado aparece una ventana para rellenear los datos. **En esta ventana tenemos datos con valores especificos como dirección que ha de ser un string en el que ponga up o down**.

Una vez rellenada la ventana se podrá observar el segmento en el mapa. Ahora podemos exportarla en una carpeta para poder utilizarla con otro programa.