# ClinicSoft — Sistema de Gestión para Clínica Médica

<div align="center">

![Java 21](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-blue?style=for-the-badge&logo=springsecurity&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3-darkgreen?style=for-the-badge&logo=thymeleaf&logoColor=white)
![Bootstrap 5.3](https://img.shields.io/badge/Bootstrap-5.3-purple?style=for-the-badge&logo=bootstrap&logoColor=white)
![Database H2](https://img.shields.io/badge/Database-H2-blueviolet?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**ClinicSoft** es una solución de software web desarrollada con Spring Boot orientada a la gestión integrada de médicos, pacientes y agendas en centros de especialidades clínicas. El sistema optimiza la asignación de recursos, previene errores de agenda e implementa un riguroso control de acceso basado en roles para asegurar la privacidad de los datos médicos.

</div>

---

## 📌 Índice de Contenidos
* [🚀 Funcionalidades Principales](#-funcionalidades-principales)
* [🛠️ Tecnologías Empleadas](#️-tecnologías-empleadas)
* [⚙️ Reglas de Negocio Implementadas](#️-reglas-de-negocio-implementadas)
* [🔐 Matriz de Seguridad y Roles](#-matriz-de-seguridad-y-roles)
* [📊 Consultas Complejas (Dashboard)](#-consultas-complejas-dashboard)
* [📁 Estructura del Proyecto](#-estructura-del-proyecto)
* [📖 Guía de Ejecución y Pruebas](#-guía-de-ejecución-y-pruebas)
* [🔧 Configuración de Base de Datos](#-configuración-de-base-de-datos)

---

## 🚀 Funcionalidades Principales

* **Dashboard Analítico (Administrador)**: Estadísticas en tiempo real de ingresos acumulados, número global de médicos, pacientes y citas activas.
* **Autenticación Dinámica**: Panel de Login personalizado que separa a administradores y personal médico mediante roles.
* **Proceso de Reserva en Dos Pasos**:
  1. *Paso 1*: Selección de médico especialista y fecha en calendario.
  2. *Paso 2*: Presentación exclusiva de horas disponibles calculando los huecos libres y asignación de paciente.
* **Gestión de Estados de Consulta**: Selección instantánea en lista del estado de la cita (`PENDIENTE`, `REALIZADA`, `NO PRESENTADO`).
* **Edición Autónoma de Perfil**: Los médicos pueden consultar su ficha y actualizar sus tarifas por minuto o duración estimada de consulta de forma independiente.

---

## 🛠️ Tecnologías Empleadas

* **Backend**: Java 21, Spring Boot 3.x, Spring MVC.
* **Persistencia & ORM**: Spring Data JPA, Hibernate, motor H2 en memoria.
* **Seguridad**: Spring Security 6.
* **Frontend**: HTML5, Bootstrap 5.3, Thymeleaf.
* **Validación**: JSR-380 (Jakarta Bean Validation) con anotaciones `@Valid`.
* **Herramientas de Desarrollo**: Lombok, Maven, Git.

---

## ⚙️ Reglas de Negocio Implementadas

Para garantizar la consistencia y automatización de la clínica, el backend implementa las siguientes reglas:

1. **Evitar Solapamientos Horarios (`estaLibre`)**: Se calculan los rangos de tiempo de cada cita (`fechaInicio` y `fechaInicio + duracionCitaMinutos`). Antes de guardar una cita nueva, se comprueba que ningún intervalo de consulta choque con otro asignado a ese mismo médico.
2. **Cálculo Automático de Tarifas (`calcularPrecio`)**: El coste total de cada cita se determina multiplicando el precio por minuto establecido por el médico por el tiempo asignado a su consulta.
3. **Control de Duplicados en Agenda (`tieneCitaMismoDia`)**: Lanza un error descriptivo (`CitaDuplicadaException`) si se intenta agendar una segunda cita para un mismo paciente y un mismo médico en el mismo día, previniendo errores de duplicidad.
4. **Borrado Seguro (Safe Deletion)**: Se impide la eliminación de médicos o pacientes que tengan citas futuras planificadas en estado `PENDIENTE`, lanzando excepciones personalizadas (`MedicoConCitasActivasException` y `PacienteConCitasActivasException`).
5. **Autoprovisionamiento de Cuentas de Médicos**: Si se da de alta un médico sin credenciales explícitas, el sistema normaliza su nombre (eliminando acentos, espacios y prefijos como "Dr.") para crear un usuario único y le asigna la contraseña por defecto `medico` encriptada.

---

## 🔐 Matriz de Seguridad y Roles

| Mapeo / URL | Descripción | ADMIN | MÉDICO | ANÓNIMO |
| :--- | :--- | :---: | :---: | :---: |
| `/`, `/login`, `/css/**`, `/js/**` | Landing page, Login y Recursos estáticos | Permmitido | Permitido | Permitido |
| `/medicos/perfil` | Ver y actualizar perfil propio | Permitido | Permitido | Denegado |
| `/pacientes` , `/pacientes/nuevo` | Listar y añadir nuevos pacientes | Permitido | Permitido | Denegado |
| `/citas/**` | Ver, crear y listar citas | Permitido | Permitido | Denegado |
| `/citas/editar/**`, `/citas/eliminar/**` | Modificación física de registros de citas | Permitido | Denegado | Denegado |
| `/medicos`, `/medicos/nuevo` | Altas, bajas y edición de médicos | Permitido | Denegado | Denegado |
| `/estadisticas` | Dashboard de rendimiento clínico | Permitido | Denegado | Denegado |
| `/h2-console/**` | Consola de administración H2 | Permitido | Denegado | Denegado |

---

## 📊 Consultas Complejas (Dashboard)
El sistema emplea consultas optimizadas en JPQL para la toma de decisiones:
* **Médicos más activos**: Agrupa las citas por especialista, ordenando de mayor a menor volumen (límite 5) para estimar carga laboral.
* **Pacientes frecuentes**: Filtra pacientes con mayor recurrencia de visitas clínicas asignadas.
* **Planificación por día**: Cuenta las citas pendientes programadas para los próximos 7 días, facilitando la gestión de personal de enfermería y recepción.

---

## 📁 Estructura del Proyecto

```text
src/main/java/com/salesianos/dam
├── controller       # Controladores MVC de Spring para endpoints web
├── service          # Capa de servicios con la lógica de negocio y algoritmos
├── repository       # Repositorios Spring Data JPA para acceso a datos
├── security         # Configuraciones de seguridad, contraseñas y roles
├── exception        # Excepciones propias del dominio y ExceptionControllerAdvice
├── enums            # Enumerados comunes (ej. EstadosCita)
├── Cita.java        # Entidad JPA: Cita Médica
├── Medico.java      # Entidad JPA: Médico especialista
├── Paciente.java    # Entidad JPA: Paciente clínico
└── DataLoader.java  # Semilla de datos aleatoria y balanceada
```

Las vistas de usuario se localizan en `src/main/resources/templates` organizadas por carpetas: `/citas`, `/medicos`, `/pacientes` y `/fragments`.

---

## 📖 Guía de Ejecución y Pruebas

### Ejecución Local

1. Clona el repositorio e introduce el directorio raíz:
   ```bash
   git clone https://github.com/ignaciopmon/Proyecto1DAMIgnacio.git
   cd Proyecto1DAMIgnacio
   ```
2. Ejecuta la aplicación mediante Maven wrapper:
   * **En Linux/Mac**:
     ```bash
     ./mvnw spring-boot:run
     ```
   * **En Windows (CMD/PowerShell)**:
     ```bash
     mvnw.cmd spring-boot:run
     ```
3. Accede al sistema desde tu navegador en la URL: [http://localhost:9000](http://localhost:9000)

### Cuentas de Acceso por Defecto
* **Administrador**: `admin` / `admin`
* **Médicos de prueba**: `carlos` / `carlos`, `ana` / `ana`, `luis` / `luis`, `sofia` / `sofia`, `miguel` / `miguel`, `user` / `user`


---

## 🔧 Configuración de Base de Datos

El archivo `src/main/resources/application.properties` configura H2 en memoria:
* **Consola H2**: Disponible localmente en [http://localhost:9000/h2-console](http://localhost:9000/h2-console)
* **JDBC URL**: `jdbc:h2:mem:clinicsoft_db`
* **Credenciales**: Usuario `sa`, contraseña vacía.