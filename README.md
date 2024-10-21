# killdb
El objetivo del proyecto killdb es recorrer una serie de base de datos almacenadas en un archivo txt de configuración y hacer un Kill de las conexiones a las mismas que estén en estado Sleep hace más de 5400 segundos (es decir 1 hora y media). Esto es para que el servidor de las bases de datos no tire el error TOO MANY CONNECTIONS. 
