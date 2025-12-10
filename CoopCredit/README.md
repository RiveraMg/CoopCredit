# CoopCredit

Sistema de gestión de créditos para cooperativas, construido con arquitectura hexagonal (puertos y adaptadores).

## Descripción del Proyecto

CoopCredit es una aplicación backend que proporciona funcionalidades completas para la gestión de solicitudes de crédito en una cooperativa. Incluye:

- **Gestión de Miembros**: Registro y actualización de miembros de la cooperativa
- **Solicitudes de Crédito**: Crear y evaluar solicitudes de crédito
- **Autenticación**: Sistema JWT para seguridad
- **Evaluación de Riesgo**: Integración con central de riesgos externa
- **Base de Datos**: PostgreSQL con migraciones Flyway

## Tecnologías

- **Java 21** LTS
- **Spring Boot 3.x**
- **Spring Security**
- **JPA/Hibernate**
- **PostgreSQL**
- **Maven**
- **JWT (JSON Web Tokens)**
- **Flyway** (migraciones de base de datos)
- **Docker & Docker Compose**

## Requisitos Previos

- Java 21 JDK
- Maven 3.8+
- Docker y Docker Compose (para ambiente con BD)
- PostgreSQL 12+ (si ejecutas localmente sin Docker)

## Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone <repository-url>
cd CoopCredit
```

### 2. Configurar la Base de Datos

#### Opción A: Usando Docker Compose (Recomendado)

```bash
# Ambiente de desarrollo
docker-compose -f docker/docker-compose.dev.yml up -d

# Ambiente de producción
docker-compose -f docker/docker-compose.prod.yml up -d
```

#### Opción B: PostgreSQL Local

Crear una base de datos y usuario:

```sql
CREATE DATABASE coopcredit;
CREATE USER coopcredit_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE coopcredit TO coopcredit_user;
```

### 3. Configurar Variables de Entorno

Editar `src/main/resources/application.properties` o `application.yml`:

```properties
# Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/coopcredit
spring.datasource.username=coopcredit_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQL10Dialect
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# JWT
app.jwt.secret=your_jwt_secret_key_here
app.jwt.expiration=86400000

# Servidor
server.port=8080
server.servlet.context-path=/api
```

### 4. Compilar el Proyecto

```bash
./mvnw clean package
```

### 5. Ejecutar la Aplicación

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080/api`

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/codeup/CoopCredit/
│   │   ├── application/       # Casos de uso
│   │   ├── config/            # Configuraciones (Security, App)
│   │   ├── domain/            # Lógica de negocio
│   │   │   ├── exception/     # Excepciones de dominio
│   │   │   ├── model/         # Modelos de dominio
│   │   │   └── ports/         # Puertos (interfaces)
│   │   ├── exception/         # Manejo global de excepciones
│   │   ├── infrastructure/    # Adaptadores e implementaciones
│   │   │   ├── adapters/
│   │   │   │   ├── in/        # Adaptadores de entrada (controllers)
│   │   │   │   └── out/       # Adaptadores de salida (BD, APIs externas)
│   │   │   └── ...
│   │   └── security/          # JWT y seguridad
│   └── resources/
│       ├── application.yml
│       └── db/migration/      # Scripts SQL de Flyway
└── test/
    └── java/com/codeup/CoopCredit/  # Tests
```

## Endpoints Principales

### Autenticación

- **POST** `/api/auth/register` - Registrar nuevo usuario
- **POST** `/api/auth/login` - Iniciar sesión y obtener JWT

### Miembros

- **POST** `/api/members` - Registrar nuevo miembro
- **GET** `/api/members/{id}` - Obtener miembro por ID
- **GET** `/api/members/document/{document}` - Obtener miembro por documento
- **PUT** `/api/members/{id}` - Actualizar miembro

### Solicitudes de Crédito

- **POST** `/api/applications` - Crear solicitud de crédito
- **GET** `/api/applications/{id}` - Obtener solicitud por ID
- **GET** `/api/applications/member/{memberId}` - Obtener solicitudes de un miembro
- **GET** `/api/applications/pending` - Obtener todas las solicitudes pendientes
- **PUT** `/api/applications/{id}/evaluate` - Evaluar solicitud de crédito

## Testing

### Ejecutar Tests

```bash
./mvnw test
```

### Con Coverage

```bash
./mvnw clean test jacoco:report
# Reporte en: target/site/jacoco/index.html
```

## Construcción de la Imagen Docker

```bash
# Construir imagen
docker build -t coopcredit:latest .

# Ejecutar contenedor
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/coopcredit \
  -e SPRING_DATASOURCE_USERNAME=coopcredit_user \
  -e SPRING_DATASOURCE_PASSWORD=password \
  coopcredit:latest
```

## Migraciones de Base de Datos

Las migraciones se ejecutan automáticamente con Flyway al iniciar la aplicación.

Ubicación: `src/main/resources/db/migration/`

Scripts:
- `V1__create_schema.sql` - Crear tablas principales
- `V2__create_relationships.sql` - Crear relaciones y constraints
- `V3__insert_initial_data.sql` - Insertar datos iniciales

## Seguridad

- **JWT**: Se utiliza para autenticación stateless
- **Spring Security**: Configuración de permisos y roles
- **CORS**: Configurado en `SecurityConfig.java`
- **HTTPS**: Recomendado para producción

## Arquitectura

El proyecto sigue el patrón de arquitectura hexagonal (puertos y adaptadores):

- **Domain**: Contiene la lógica de negocio pura (sin dependencias externas)
- **Application**: Casos de uso que orquestan la lógica de dominio
- **Infrastructure**: Implementaciones concretas (bases de datos, APIs externas)
- **Ports**: Interfaces que definen contratos entre capas

## Contribución

1. Crear una rama para la nueva funcionalidad: `git checkout -b feature/nueva-funcionalidad`
2. Hacer commits descriptivos
3. Hacer push a la rama
4. Crear un Pull Request

## Licencia

Este proyecto está bajo licencia MIT.

## Contacto

Para preguntas o soporte, contactar al equipo de desarrollo.

---

**Última actualización**: Diciembre 2025
