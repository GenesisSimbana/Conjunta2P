# Sistema de Manejo de Efectivo en Ventanillas Bancarias

## Descripción

Sistema desarrollado en Java 21 con Spring Boot 3.5.3 para el manejo de efectivo en ventanillas bancarias. Utiliza MongoDB como base de datos documental y sigue las mejores prácticas de desarrollo, incluyendo principios SOLID, DRY y programación orientada a objetos.

## Características

- **Gestión de Turnos de Caja**: Inicio y cierre de turnos con validación de saldos
- **Registro de Transacciones**: Depósitos, ahorros y otras transacciones con denominaciones detalladas
- **Control de Concurrencia**: Implementación de control de concurrencia optimista
- **Validación de Negocio**: Reglas de negocio para impedir múltiples turnos abiertos y validar saldos
- **API REST**: Endpoints documentados con OpenAPI/Swagger
- **Manejo de Excepciones**: Sistema robusto de manejo de errores

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.3**
- **Spring Data MongoDB**
- **MapStruct** (mapeo de objetos)
- **Jakarta Validation** (validaciones)
- **OpenAPI/Swagger** (documentación)
- **MongoDB** (base de datos)

## Estructura del Proyecto

```
src/main/java/com/conjunta/simbana/
├── config/
│   └── CorsConfig.java
├── controller/
│   ├── dto/
│   │   ├── DenominacionTurnoDto.java
│   │   ├── IniciarTurnoDto.java
│   │   └── RegistrarTransaccionDto.java
│   ├── mapper/
│   │   └── DenominacionTurnoMapper.java
│   ├── TurnoCajaController.java
│   └── TransaccionTurnoController.java
├── exception/
│   ├── BusinessException.java
│   ├── NotFoundException.java
│   └── GlobalExceptionHandler.java
├── model/
│   ├── DenominacionTurno.java
│   ├── TurnoCaja.java
│   ├── TurnoCajaId.java
│   ├── TransaccionTurno.java
│   └── TransaccionTurnoId.java
├── repository/
│   ├── TurnoCajaRepository.java
│   └── TransaccionTurnoRepository.java
└── service/
    ├── TurnoCajaService.java
    └── TransaccionTurnoService.java
```

## Modelos de Datos

### TurnoCaja
- **Clave Compuesta**: `TurnoCajaId` (códigoCaja, códigoCajero, fecha)
- **Campos**: fechaInicio, fechaFin, montoInicial, montoFinal, estado, versión
- **Estados**: ABIERTO, CERRADO

### TransaccionTurno
- **Clave Compuesta**: `TransaccionTurnoId` (códigoCaja, códigoCajero, códigoTurno, códigoTransaccion)
- **Campos**: tipoTransaccion, montoTotal, fechaTransaccion, denominaciones
- **Tipos**: INICIO, AHORRO, DEPOSITO, CIERRE

### DenominacionTurno
- **Campos**: denominacion, cantidad, monto
- **Cálculo automático**: monto = denominacion × cantidad

## Endpoints de la API

### Turnos de Caja

#### POST /v1/cashboxes/turnos
Inicia un nuevo turno de caja.

**Body:**
```json
{
  "codigoCaja": "CAJA001",
  "codigoCajero": "CAJERO001",
  "fecha": "20241201",
  "montoInicial": 1000.00,
  "denominacionesIniciales": [
    {
      "denominacion": 100.00,
      "cantidad": 10
    }
  ]
}
```

#### PATCH /v1/cashboxes/turnos/{turnoId}/cerrar
Cierra un turno de caja.

**Parámetros:**
- `turnoId`: formato "codigoCaja-codigoCajero-fecha"
- `montoFinal`: monto final del turno

#### GET /v1/cashboxes/turnos/{turnoId}
Busca un turno por su ID.

#### GET /v1/cashboxes/turnos/abiertos
Busca turnos abiertos por caja y cajero.

### Transacciones

#### POST /v1/cashboxes/transacciones
Registra una nueva transacción.

**Body:**
```json
{
  "codigoCaja": "CAJA001",
  "codigoCajero": "CAJERO001",
  "codigoTurno": "20241201",
  "tipoTransaccion": "DEPOSITO",
  "montoTotal": 500.00,
  "denominaciones": [
    {
      "denominacion": 50.00,
      "cantidad": 10
    }
  ]
}
```

#### GET /v1/cashboxes/transacciones
Busca transacciones por caja, cajero y turno.

#### GET /v1/cashboxes/transacciones/por-tipo
Busca transacciones por tipo.

## Reglas de Negocio

1. **Un turno abierto por caja y cajero**: No se puede abrir múltiples turnos para el mismo cajero y caja.
2. **Validación de saldo**: Al cerrar un turno, el monto final debe coincidir con el calculado basado en las transacciones.
3. **Validación de denominaciones**: El monto total debe coincidir con la suma de las denominaciones.
4. **Control de concurrencia**: Uso de versión para evitar conflictos de concurrencia.

## Configuración

### application.properties
```properties
# MongoDB
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=simbana_db

# OpenAPI/Swagger
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

## Instalación y Ejecución

### Prerrequisitos
- Java 21
- Maven 3.6+
- MongoDB 4.4+

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone <repository-url>
   cd simbana
   ```

2. **Instalar dependencias**
   ```bash
   mvn clean install
   ```

3. **Configurar MongoDB**
   - Asegúrate de que MongoDB esté ejecutándose en localhost:27017
   - La base de datos `simbana_db` se creará automáticamente

4. **Ejecutar la aplicación**
   ```bash
   mvn spring-boot:run
   ```

5. **Acceder a la documentación**
   - Swagger UI: http://localhost:8080/swagger-ui.html
   - API Docs: http://localhost:8080/api-docs

## Ejemplos de Uso

### 1. Iniciar un Turno
```bash
curl -X POST http://localhost:8080/v1/cashboxes/turnos \
  -H "Content-Type: application/json" \
  -d '{
    "codigoCaja": "CAJA001",
    "codigoCajero": "CAJERO001",
    "fecha": "20241201",
    "montoInicial": 1000.00,
    "denominacionesIniciales": [
      {"denominacion": 100.00, "cantidad": 10}
    ]
  }'
```

### 2. Registrar una Transacción
```bash
curl -X POST http://localhost:8080/v1/cashboxes/transacciones \
  -H "Content-Type: application/json" \
  -d '{
    "codigoCaja": "CAJA001",
    "codigoCajero": "CAJERO001",
    "codigoTurno": "20241201",
    "tipoTransaccion": "DEPOSITO",
    "montoTotal": 500.00,
    "denominaciones": [
      {"denominacion": 50.00, "cantidad": 10}
    ]
  }'
```

### 3. Cerrar un Turno
```bash
curl -X PATCH "http://localhost:8080/v1/cashboxes/turnos/CAJA001-CAJERO001-20241201/cerrar?montoFinal=1500.00"
```

## Características Técnicas

- **Principios SOLID**: Aplicación de principios de diseño orientado a objetos
- **DRY**: Evita duplicación de código
- **Inyección de Dependencias**: Uso de Spring IoC container
- **Transacciones**: Manejo de transacciones con @Transactional
- **Validaciones**: Validación de entrada con Jakarta Validation
- **Mapeo de Objetos**: Uso de MapStruct para conversiones
- **Documentación**: API documentada con OpenAPI/Swagger
- **Manejo de Errores**: Sistema robusto de excepciones

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles. 