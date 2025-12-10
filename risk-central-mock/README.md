# Risk Central Mock

Descripción
- Proyecto mock (simulado) que expone un pequeño servicio REST para evaluar riesgo de crédito.
- Está implementado con Spring Boot y pensado para usarse como servicio de prueba/integración.

¿Para qué sirve?
- Proporciona una API simple que, dado un documento (identificador), monto y plazo, devuelve una evaluación de riesgo (score y nivel de riesgo).
- Ideal para pruebas de integración, demos o como reemplazo temporal cuando el servicio real no está disponible.

Características principales
- Endpoint REST para evaluación de riesgo.
- Endpoint de `health` para chequeos de disponibilidad.
- DTOs con Lombok para facilitar la serialización JSON.

Tecnologías
- Java (11+ recomendado, funciona con Java 17)
- Spring Boot
- Lombok (para getters/setters/builders)

Requisitos
- Java 11 o superior.
- Maven (o el wrapper `mvnw`).
- Habilitar `annotation processing` en el IDE para Lombok.

Estructura del proyecto (resumen)
- `RiskCentralApplication.java`: punto de entrada de la aplicación.
- `controller/`: controladores REST (`RiskEvaluationController.java`).
- `dto/`: modelos de petición y respuesta (`RiskEvaluationRequest`, `RiskEvaluationResponse`).
- `service/`: lógica de negocio (`RiskEvaluationService.java`).

API / Endpoints

- `POST /risk-evaluation`
  - Descripción: evalúa el riesgo de crédito según los datos enviados.
  - Request (JSON):

```json
{
  "document": "1017654311",
  "amount": 5000000,
  "termMonths": 36
}
```

  - Response (JSON):

```json
{
  "document": "1017654311",
  "score": 642,
  "riskLevel": "MEDIUM",
  "details": "Moderate credit risk"
}
```

  - Códigos HTTP comunes:
    - `200 OK`: evaluación completada.
    - `400 Bad Request`: request inválido (p. ej. falta `document`).

- `GET /risk-evaluation/health`
  - Descripción: chequeo de salud del servicio; retorna `200 OK` con un mensaje simple.

Ejemplos (curl)

Health check:

```bash
curl -v http://localhost:8080/risk-evaluation/health
```

Evaluación de riesgo:

```bash
curl -v -X POST http://localhost:8080/risk-evaluation \
  -H "Content-Type: application/json" \
  -d '{"document":"1017654311","amount":5000000,"termMonths":36}'
```

Cómo ejecutar

- Con el wrapper de Maven (si está en el proyecto):

```bash
./mvnw spring-boot:run
```

- Con Maven instalado:

```bash
mvn spring-boot:run
```

- Empaquetar y ejecutar JAR:

```bash
mvn clean package
java -jar target/*.jar
```

Notas para desarrolladores
- Lombok se usa en los DTOs: habilite `annotation processing` en su IDE (IntelliJ/Eclipse) o instale el plugin correspondiente.
- `RiskEvaluationService` contiene la lógica de negocio; al ser un mock, puede devolver valores deterministas o aleatorios según convenga para pruebas.

Opciones de mejora (sugerencias)
- Añadir tests unitarios e integración para el `controller` y `service`.
- Crear un `Dockerfile` para facilitar despliegues locales o en CI.
- Añadir OpenAPI / Swagger para documentar automáticamente la API.

Contribuir
- Fork y pull request: si quieres agregar mejoras, prueba localmente y abre un PR con una descripción clara.

Licencia
- Repositorio sin licencia explícita (agrega una `LICENSE` si deseas explícitamente permitir usos).

Contacto
- Si quieres que añada Docker, CI (GitHub Actions) o ejemplos de integración, dime y lo preparo.


