# Use Case Diagram

This diagram represents the system's actors and their interactions with the key functionalities (Use Cases).
**Note:** Displayed using Flowchart syntax for maximum compatibility.

```mermaid
graph LR
    %% Styles
    classDef actor fill:#f9f,stroke:#333,stroke-width:2px;
    classDef usecase fill:#fff,stroke:#333,stroke-width:2px,rx:20,ry:20;
    classDef boundary fill:none,stroke:#333,stroke-width:1px,stroke-dasharray: 5 5;

    %% Actors
    Admin((Administrador)):::actor
    Advogado((Advogado)):::actor
    Correspondente((Correspondente)):::actor

    %% Group: Solicitacoes
    subgraph Solicitacoes [Gerenciamento de Solicitações]
        direction TB
        UC1([Criar Solicitação]):::usecase
        UC2([Editar Solicitação]):::usecase
        UC3([Visualizar Solicitação]):::usecase
        UC4([Anexar Arquivos]):::usecase
        UC5([Avaliar Solicitação]):::usecase
        UC6([Download de Arquivos]):::usecase
        UC7([Rejeitar Solicitação]):::usecase
    end

    %% Group: Usuarios
    subgraph Usuarios [Gerenciamento de Usuários]
        direction TB
        UC8([Cadastrar Usuário]):::usecase
        UC9([Inativar Usuário]):::usecase
        UC10([Alterar Senha]):::usecase
    end

    %% Group: Correspondentes
    subgraph Correspondentes [Gerenciamento de Correspondentes]
        direction TB
        UC11([Cadastrar Correspondente]):::usecase
        UC12([Listar Correspondentes]):::usecase
        UC13([Gerenciar Pagamentos]):::usecase
        UC14([Visualizar Volumetria]):::usecase
    end

    %% Group: Comarcas
    subgraph Comarcas [Gerenciamento de Comarcas]
        direction TB
        UC15([Cadastrar Comarca]):::usecase
        UC16([Pesquisar Comarca]):::usecase
    end

    %% Administrator Relationships
    Admin --> UC1
    Admin --> UC2
    Admin --> UC3
    Admin --> UC4
    Admin --> UC5
    Admin --> UC6
    Admin --> UC7
    Admin --> UC8
    Admin --> UC9
    Admin --> UC10
    Admin --> UC11
    Admin --> UC12
    Admin --> UC13
    Admin --> UC14
    Admin --> UC15
    Admin --> UC16

    %% Lawyer Relationships
    Advogado --> UC1
    Advogado --> UC2
    Advogado --> UC3
    Advogado --> UC4
    Advogado --> UC6
    Advogado --> UC16

    %% Correspondent Relationships
    Correspondente --> UC3
    Correspondente --> UC4
    Correspondente --> UC6
    Correspondente --> UC13
```
