# Software Reuse UNIR

Repositorio de trabajo para la actividad de la asignatura **Reutilización de Software** sobre patrones de creación.

## Ejecución rápida para el docente

La implementación que debe evaluarse es la de `Java`.

### Requisitos

- Java 21 o superior
- Maven 3.8 o superior

### Opción 1: ejecutar directamente con Maven

```bash
cd java
mvn clean test
mvn exec:java -Dexec.mainClass=com.unir.reuse.reports.Main
```

### Opción 2: generar el JAR y ejecutarlo

```bash
cd java
mvn clean package
java -jar target/report-processing-1.0.0.jar
```

### Qué debería verse

El programa procesa informes de ejemplo y muestra por consola mensajes como:

```text
Procesando informe 'ENT_FAC_001.txt' de ENTRADA y tipo FACTURA...
Procesando informe 'MIX_COM_006.txt' de MIXTA y tipo COMPRA...
Error procesando 'SAL_PED_008.txt': The category SALIDA does not support reports of type PEDIDO.
```

La combinación `SAL_PED` falla de forma intencionada porque el enunciado solo añade `PEDIDO` a la categoría `ENTRADA`.

## Estructura

- `java/`: implementación principal evaluable en Java con Maven.
- `python/`: implementación equivalente en Python sin usar `Protocol`.
- `csharp/`: implementación equivalente en C# sobre .NET 8.
- `docs/`: memoria en LaTeX, diagramas UML y recursos de apoyo.

## Decisiones de diseño

La solución se apoya en una variante pragmática de **Abstract Factory**:

- cada categoría de informe (`ENTRADA`, `SALIDA`, `MIXTA`) se modela como una familia;
- cada familia expone una factoría concreta;
- la creación de procesadores se resuelve mediante un método genérico por tipo, evitando una interfaz rígida con un método distinto por producto.

Esta decisión permite explicar en la memoria dos ideas importantes:

1. el patrón reduce el acoplamiento frente a una solución con condicionales;
2. una implementación demasiado ortodoxa de `Abstract Factory` penalizaría la evolución asimétrica del sistema, como ocurre al añadir `PEDIDO` solo a `ENTRADA`.

## Comandos útiles

### Java

```bash
cd java
mvn test
mvn exec:java -Dexec.mainClass=com.unir.reuse.reports.Main
mvn package
java -jar target/report-processing-1.0.0.jar
```

### Python

```bash
cd python
PYTHONPATH=src python3 -m report_processing
```

### C#

```bash
cd csharp/src/ReportProcessing
dotnet run
```

### Memoria

```bash
cd docs
make
```
