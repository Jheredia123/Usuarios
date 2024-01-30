Pruebas para ingresar al proyecto. 
descargar el repositorio.
compilar proyecto abrir postman y llamar al primer servicio.
http://localhost:8080/api/v1/login
usuario: user password: (tomar el token desde la consola: Using generated security password: f7b098fc-2c9c-4faf-b5b1-16953e8d09ff ) 
entrar al swagger http://localhost:8080/swagger-ui/index.html 
ya reconocido el token puedes acceder a los servicios
http://localhost:8080/api/v1/getAll agregar este json para el servicio
/saveUsuario agrega el token en la zona de autorizacion del servicio y agregar este json para la creacion del usuario

{ "name": "Juan Rodriguez", "email": "juan@rodriguez.org", "password": "hunter2", "phones": [ { "number": "1234567", "citycode": "1", "contrycode": "57" } ] }
