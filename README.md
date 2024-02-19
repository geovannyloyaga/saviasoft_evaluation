# saviasoft_evaluation
Evaluación de SAVIASOFT

# Creación de BDD

Para crear la estructura de la BDD se requiere ejecutar el scripts adjunto 'saviasoft_script'

# Ejecución para BACKEND

Abrir el proyecto de la carpeta saviasoft_backend desde cualquier IDE

Realizar el cambio del usuario de la base de datos que tenga configurado en el archivo ubicado en: saviasoft_backend/src/main/resources/application.properties la siguiente línea:

spring.datasource.password=XXXXXXX

Yo use Spring Tools de la pagina oficial y correr la Aplicación de Spring Boot

# Ejecución para FRONTEND

Primero debe tener conexión a internet

Abrir el proyecto con un IDE o desde la terminal ubicarse en la carpeta saviasoft_frontend

Ejecutar el comando: yarn install

Una vez concluido la instalación procedemos a ejecutar el proyecto con el comando: yarn start