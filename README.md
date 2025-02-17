# 🥘 GrandmasFoodProduct - Microservicio de Gestión de Productos

## 📌 Descripción
Este microservicio forma parte de un sistema de *Pedidos* y se encarga de gestionar los productos de la app de GrandmasFoodProduct. Implementa *arquitectura hexagonal*, facilitando la escalabilidad y mantenibilidad.

🔹 *Tecnologías utilizadas*:
- 🖥️ *Spring Boot 3.4.2*
- 📦 *Spring Data JPA* (Persistencia)
- 🐘 *PostgreSQL*
- 🎭 *Mockito + JUnit 5* (Pruebas Unitarias)
- 🔍 *Swagger (OpenAPI 3)* (Documentación de API)
- 🔬 *JaCoCo* (Cobertura de código)

## 📁 Arquitectura Hexagonal
El microservicio sigue el patrón de *arquitectura hexagonal*, con las siguientes capas:
📦 GrandmasFoodProduct ┣ 📂 application # Capa de aplicación (Casos de uso) ┣ 📂 domain # Capa de dominio (Modelos y lógica de negocio) ┃ ┣ 📂 models # Entidades del dominio ┃ ┣ 📂 exceptions # Excepciones de negocio ┃ ┗ 📂 spi # Interfaces de persistencia ┣ 📂 infrastructure # Capa de infraestructura (Adaptadores) ┃ ┣ 📂 rest # Controladores REST ┃ ┣ 📂 jpa # Adaptador de persistencia con JPA ┃ ┣ 📂 configuration # Configuraciones de Spring Boot ┃ ┗ 📂 exception # Manejadores globales de excepciones
