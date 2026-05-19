# Software Reuse UNIR

Repositorio de trabajo para la actividad de la asignatura **Reutilización de Software** sobre patrones de creación.

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

