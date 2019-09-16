# Angular Spring Boot Data Encryptation
Este proyecto genera una comunicación cifrada entre un api gateway y sus consumidores.

### Request:
![request-canal-web](https://user-images.githubusercontent.com/18618236/64930915-2a741000-d80b-11e9-818e-50e783eb7612.png)

### Response
![api-gateway-response](https://user-images.githubusercontent.com/18618236/64930987-ae2dfc80-d80b-11e9-8d58-f826b6dba4a2.png)

# Componentes
* Microservicios: Microservicio Hello (Spring Boot 2.1.7.RELEASE)
* Api gateway: Spring cloud netflix (Zuul Proxy & Spring Cloud Greenwich.SR2)
* Canales: Canal web (Angular 7.x)

# Canales
Un canal representa un consumidor del api, por ejemplo; una aplicación web, una app mobile u otros sistemas.
Para integrar un nuevo canal a la lista de canales de confianza, se debe agregar su configuración en el archivo application.properties del microservicio api-gateway:

```
# canales registrados para el envio y recepción cifrado
app.security.aes.trusted-channel[0].id=web
app.security.aes.trusted-channel[0].key=secret_key_base64
app.security.aes.trusted-channel[0].iv=iv_base64

#app.security.aes.trusted-channel[1].id=mobile-android
#app.security.aes.trusted-channel[1].key=secret
#app.security.aes.trusted-channel[1].iv=secret
#
#app.security.aes.trusted-channel[2].id=mobile-ios
#app.security.aes.trusted-channel[2].key=secret
#app.security.aes.trusted-channel[2].iv=secret

```

**El canal debe enviar en cada peticíón su id utilizando la cabecera 'channel-id'**

Para el demo usando el canal web, en el archivo environment.ts se definen la configuración de seguridad.


## Cifrado
Para el cifrado se utiliza [Advanced Encryptation Standard (AES)]( 
https://es.wikipedia.org/wiki/Advanced_Encryption_Standard)

Cada request enviado por el canal va cifrado y cada response enviado por el api viene crifrado.

Los microservicios que estan "bajo o detrás" del api gateway no trabajan con la data cifrada, es el api gateway el que se encarga de cifrar o decifrar las peticiones.

## Demo
Con docker-compose se deben crear las imagenes y los contenedores utilizando en la consola el comando:
```
docker-compose up -d
```
![container-list](https://user-images.githubusercontent.com/18618236/64930903-0d3f4180-d80b-11e9-8ac9-788db357e8fe.png)

* Canal web angular: Configurado en el puerto 80 [http://localhost](http://localhost)
* Api gateway: Configurado en el puerto 5001 [http://localhost:5001]
