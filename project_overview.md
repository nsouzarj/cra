# Project Overview: SISGECOL (Sistema de Gerenciamento de Colaboradores)

## 1. Project Identity
- **Name:** SISGECOL / CRA
- **Description:** System for managing diligence and hearings requests (solicitações de audiências e diligências).
- **Authors:** Nelson Seixas de Souza.
- **Inception Date:** 01/06/2012.
- **Last Major Update:** ~2021 (based on README).

## 2. Technology Stack
- **Language:** Java (Legacy, potentially Java 7 or 8).
- **Web Framework:** JSF 2.0 (Mojarra 2.0.4).
- **Component Libraries:**
    - PrimeFaces 3.4.2
    - BootsFaces 1.2.0 (for responsive layout)
- **Persistence:**
    - Hibernate 4.3.11 (ORM)
    - PostgreSQL (Database)
- **Server:** Apache Tomcat (suggested by `.smarttomcat` and structure).
- **Build System:** Manual Dependency Management (Libraries in `WebContent/WEB-INF/lib`). No Maven/Gradle detected.
- **IDE:** Eclipse (Dynamic Web Project structure).

## 3. Architecture
The application follows a standard **MVC** (Model-View-Controller) architecture adapted for JSF:

### View Layer (Frontend)
- **Files:** `.xhtml` files in `WebContent`.
- **Libraries:** Uses `p:` (PrimeFaces) and `b:` (BootsFaces) namespaces.
- **Resources:** CSS/JS in `WebContent/resources` (implied).

### Controller / Business Layer (Managed Beans)
- **Package:** `br.adv.cra.manager`
- **Mechanism:** Classes annotated with `@ManagedBean`.
- **Key Beans:**
    - `ManagerSolicitacao`: Handles requests (solicitações) logic.
    - `ManagerUsuario`: Manages user sessions and operations.
    - `ManagerCorrespondente`: Manages correspondents.
    - `ManagerBancaProcesso`, `ManagerPagamento`, etc.
- **Responsibility:** These classes likely handle both the UI logic (binding to views) and business rules.

### Persistence Layer (DAO)
- **Package:** `br.adv.cra.persistence`
- **Mechanism:** DAO Pattern (Data Access Object) using Hibernate `Session`.
- **Key DAOs:**
    - `SolicitacaoDao`: CRUD operations for requests.
    - `UsuarioDao`, `CorrespondenteDao`, `ProcessoDao`.
- **Configuration:** `src/config/hibernate.cfg.xml`.

### Model Layer (Entities)
- **Package:** `br.adv.cra.entity`
- **Mechanism:** JPA Entities annotated with `@Entity`, `@Table`, `@Id`.
- **Key Entities:**
    - `Solicitacao`: The core entity representing a request.
    - `Usuario`: System users.
    - `Correspondente`: External collaborators.
    - `Processo`: Legal process details.

## 4. Key Configurations
- **Database Connection:** Configured in `hibernate.cfg.xml`.
    - URL: `jdbc:postgresql://192.168.1.104:5432/dbsolic` (Development/Local IP).
    - User: `postgres`.
- **JSF Configuration:** `WEB-INF/faces-config.xml`.
    - Defined navigation rules.
    - Registered phase listeners (`CacheControlPhaseListener`).
- **Web Deployment:** `WEB-INF/web.xml`.
    - Servlet mappings for `FacesServlet`.
    - Session timeout: 60 minutes.
    - PrimeFaces FileUpload filter configured.

## 5. Observations & Recommendations
- **Legacy Dependencies:** Many libraries are quite old (e.g., PrimeFaces 3.4.2 is from ~2012/2013). Upgrading might be challenging due to breaking changes.
- **Manual Lib Management:** All JARs are in `WEB-INF/lib`. Migrating to Maven/Gradle would improve dependency management and reproducibility.
- **Encoding:** Source files allow non-UTF-8 characters (likely ISO-8859-1 or Windows-1252), which can cause encoding issues in modern editors/CI environments.
