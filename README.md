# Proyecto Ignacio 1DAM

Aplicacion web de gestion para una clinica, desarrollada con Spring Boot. Permite administrar medicos, pacientes y citas, con control de acceso por roles y una base de datos H2 en memoria para pruebas.

## Funcionalidades principales

- Pagina de inicio con resumen de medicos, pacientes y citas.
- Login con dos tipos de usuario: administrador y medico.
- Gestion de medicos: listado, alta, edicion, eliminacion y perfil.
- Gestion de pacientes: listado, alta, edicion y eliminacion.
- Gestion de citas:
  - Creacion en dos pasos: seleccion de medico y fecha, y despues seleccion de hora y paciente.
  - Calculo automatico de duracion y precio segun el medico.
  - Control de citas duplicadas y solapadas.
  - Estados de cita: pendiente, realizada o no presentado.
  - Los medicos solo ven y gestionan sus propias citas.
- Pagina de estadisticas para el administrador.
- Carga automatica de datos de ejemplo al iniciar la aplicacion.

## Tecnologias

- Java 21
- Spring Boot 4
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- H2 Database
- Lombok
- Maven

## Estructura del proyecto

```text
src/main/java/com/salesianos/dam
+-- controller      Controladores MVC
+-- service         Logica de negocio
+-- repository      Repositorios JPA
+-- security        Configuracion de seguridad y usuarios
+-- exception       Excepciones propias de la aplicacion
+-- enums           Enumerados del dominio
+-- Cita.java       Entidad de cita medica
+-- Medico.java     Entidad de medico
+-- Paciente.java   Entidad de paciente
+-- DataLoader.java Datos iniciales de prueba
```

Las plantillas Thymeleaf estan en:

```text
src/main/resources/templates
```

## Como ejecutar

Desde la raiz del proyecto:

```bash
./mvnw spring-boot:run
```

En Windows tambien se puede ejecutar:

```bash
mvnw.cmd spring-boot:run
```

La aplicacion queda disponible en:

```text
http://localhost:9000
```

## Base de datos

El proyecto usa H2 en memoria, configurado en `src/main/resources/application.properties`.

- URL JDBC: `jdbc:h2:mem:clinicsoft_db`
- Usuario: `sa`
- Password: vacio
- Consola H2: `http://localhost:9000/h2-console`

La base de datos se crea de nuevo en cada arranque porque esta configurada con:

```properties
spring.jpa.hibernate.ddl-auto=create-drop
```

## Usuarios de prueba

Administrador:

```text
Usuario: admin
Password: admin
```

Medicos cargados por defecto:

```text
Usuario: carlos   Password: carlos
Usuario: ana      Password: ana
Usuario: luis     Password: luis
Usuario: sofia    Password: sofia
Usuario: miguel   Password: miguel
Usuario: user    Password: user
```

Si se crea un medico nuevo sin indicar usuario o password, la aplicacion genera un usuario a partir del nombre y asigna la password por defecto `medico`.

## Rutas principales

- `/` - Inicio
- `/login` - Inicio de sesion
- `/logout` - Cierre de sesion
- `/medicos` - Gestion de medicos
- `/medicos/perfil` - Perfil del medico
- `/pacientes` - Gestion de pacientes
- `/citas` - Gestion de citas
- `/citas/nueva` - Crear nueva cita
- `/estadisticas` - Estadisticas de la clinica
- `/h2-console` - Consola de base de datos H2

## Permisos

- El administrador puede gestionar medicos, pacientes, citas y estadisticas.
- Los medicos pueden ver su perfil, gestionar pacientes y trabajar con sus propias citas.
- La edicion y eliminacion avanzada queda reservada al administrador.

## Pruebas

Para ejecutar los tests:

```bash
./mvnw test
```

En Windows:

```bash
mvnw.cmd test
```
