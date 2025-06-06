# 🎮 Máquina Arcade - Juegos de Lógica (Java + Swing)

Este proyecto es una aplicación arcade educativa desarrollada en **Java** con interfaz gráfica en **Swing**, persistencia con **Hibernate** y pruebas con **JUnit 5**. Incluye tres juegos clásicos de lógica:

- ♛ N Reinas
- ♞ Recorrido del Caballo
- 🗼 Torres de Hanoi

---
## Link al repositorio

[https://github.com/dxn1l/Arcade](https://github.com/dxn1l/Arcade)

---

## 📦 Características

- 🎨 Interfaz gráfica intuitiva estilo videojuego.
- 📚 Historial persistente de partidas con filtros y eliminación por sección.
- 💾 Persistencia con Hibernate y base de datos local.
- 🔁 Resolución automática en todos los juegos.
- 🔄 Reinicio con registro en historial.
- 🧠 Lógica separada de la UI mediante el patrón MVC.
- 🏗️ Factories y Abstract Factory para construcción flexible de componentes.

---

## 🚀 Requisitos

- Java 17+
- Maven 3.6+
- (Opcional) Base de datos H2, SQLite o cualquier otra compatible con Hibernate.

---

## 📂 Estructura del proyecto

El proyecto se estructura de la siguiente manera:

```
src/
├── main/
│   └── java/
│       ├── fabrica/
│       │   ├── FabricaJuegosCompleta.java
│       │   ├── FactoryCaballoCompleta.java
│       │   ├── FactoryNReinasCompleta.java
│       │   ├── FactoryHanoiCompleta.java
│       │   └── JuegoAbstractFactory.java
│       │
│       ├── juegos/
│       │   ├── Caballo/
│       │   │       ├── CaballoJuego.java
│       │   │       ├── ControladorCaballo.java
│       │   │       └── ResultadoCaballo.java
│       │   ├── Hanoi/
│       │   │       ├── ControladorHanoi.java
│       │   │       ├── HanoiJuego.java
│       │   │       └── ResultadoHanoi.java
│       │   └── NReinas/
│       │           ├── ControladorNReinas.java
│       │           ├── NReinasJuego.java
│       │           └── ResultadoNReinas.java
│       │
│       ├── persistencia/
│       │   ├── HibernateUtil.java
│       │   └── Partida.java
│       │
│       └── Vista/
│           ├── MenuPrincipal.java
│           ├── PanelCaballo.java
│           ├── PanelHanoi.java
│           ├── PanelNReinas.java
│           └── VentanaHistorial.java
│       
│       
│
├── test/
│   └── java/
│       ├── fabrica/
│       │   └── FabricaJuegosCompletaTest.java
│       │
│       ├── juegos/
│       │   ├── Caballo/
│       │   │       └── CaballoJuegoTest.java
│       │   ├── Hanoi/
│       │   │       └── HanoiJuegoTest.java
│       │   └── NReinas/
│       │           └── NReinaJuegoTest.java
│       │
│       └── persistencia/
│           └── PartidaTest.java
│
└── Docs/
    └── UMLs/
        ├── Actividades.puml
        ├── Clases.puml
        └── Objeto.puml
```

---

## 🛠️ Compilación y ejecución

### 👉 Desde consola:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="arcade.vista.MenuPrincipal"
```

### 👉 Desde IDE:

- Abrir el proyecto en IntelliJ IDEA.
- Ejecutar el main de la clase `arcade.vista.MenuPrincipal` en la clase `arcade.vista.Main`.
- Ajustar la configuración de ejecución si es necesario.
- Ejecutar el programa.

---
## 🧪 Pruebas

Para ejecutar las pruebas unitarias, ejecute el comando `mvn test` en la consola.

---
## 📝 Corbetura de pruebas

Se incluyen pruebas para:

- ✔️ Lógica de N Reinas, Caballo y Hanoi

- ✔️ Persistencia con Partida

- ✔️ Factories de componentes

- ✔️ Validaciones lógicas (e.g. reinas no se atacan)