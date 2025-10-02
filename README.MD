# 💼 Agent Payment Management Application (Java Console App)

## 📌 Project Context
This project was developed as part of an individual assignment with the goal of **digitalizing the management of agent payments** and the monitoring of departments.  
The application allows **agents** and **department managers** to consult and manage information related to payments, departments, and statistics, while respecting defined business rules.

---

## 🚀 Main Features

### 👤 For Agents
- View their personal information and assigned department.
- Display their payment history (salary, bonus, reward, allowance).
- Filter and sort payments by **type, amount, or date** (using Streams and Lambdas).
- Calculate the **total** and **average** of their payments.
- Identify the highest and lowest payment.

### 🏢 For Department Managers
- Create, update, or delete a department and assign a manager.
- Add, update, or delete an agent and assign them to a department.
- Add a payment to an agent:
    - **Salary and bonus**: can be freely added.
    - **Reward and allowance**: only available if the agent is **eligible** (Manager or Director) and if the **condition** is validated.
- Consult and filter payments of a specific agent or all agents in the department.
- Automatically calculate the **total** and **average** of payments per agent and per department.
- Identify unusual or incorrect payments.

### 📊 Global Statistics
- Annual salary total per agent.
- Number of bonuses, rewards, and allowances received.
- Total payments made within a department.
- Average salary of agents per department.
- Ranking of agents by total amount received.
- Distribution of payments by type (Salary, Bonus, Reward, Allowance).
- Agent with the highest total received.

---

## ⚙️ Technical Architecture

The project follows the **MVC (Model - View - Controller)** architecture:

- **Model**:
    - `Agent`, `Department`, `Payment`
    - Enums: `AgentType`, `PaymentType`
- **Service**: business logic (payment rules, eligibility for rewards/allowances).
- **Controller**: handles interactions between the view and services.
- **View**: console interface (menus for agents and managers).

---

### 📂 Package & File Structure

```
Directory structure:
└── mustapha-moutaki-agent-payment-management-system/
    ├── Agent-Payment-Management-System.iml
    ├── docker-compose.yml
    └── src/
        ├── Main.java
        ├── controller/
        │   └── MainController.java
        ├── dao/
        │   ├── AgentDAO.java
        │   ├── DBConnection.java
        │   ├── DepartmentDAO.java
        │   ├── PaymentDAO.java
        │   └── implments/
        │       ├── AgentDAOImpl.java
        │       ├── DepartmentDAOImpl.java
        │       └── PaymentDAOImpl.java
        ├── exception/
        │   └── InvalidPaymentException.java
        ├── model/
        │   ├── Agent.java
        │   ├── Department.java
        │   ├── Payment.java
        │   ├── Person.java
        │   └── enums/
        │       ├── AgentType.java
        │       └── PaymentType.java
        ├── resources/
        │   └── database/
        │       ├── config.properties
        │       └── database.sql
        ├── security/
        │   └── AuthentificationService.java
        ├── service/
        │   ├── AgentService.java
        │   ├── DepartmentService.java
        │   ├── PaymentService.java
        │   └── implement/
        │       ├── AgentServiceImpl.java
        │       ├── DepartmentServiceImpl.java
        │       └── PaymentServiceImpl.java
        ├── Utils/
        │   ├── DateUtils.java
        │   ├── InputUtils.java
        │   └── PasswordUtils.java
        └── view/
            ├── AgentUi.java
            ├── DepartmentUi.java
            ├── DirectorUi.java
            ├── MainMenu.java
            ├── ManagerUi.java
            └── PaymentUi.java
```

---

## 🛠️ Technologies Used
- **Java SE** (Collections, Streams, Optional, Java Time API)
- **Advanced OOP** (Encapsulation, Inheritance, Polymorphism)
- **JDBC** (data persistence)
- **PlantUML** (UML diagrams)

---

## 📐 UML
- **Class Diagram**: represents main entities and their relationships.
- **Sequence Diagram**: adding a payment by a manager.
- **Use Case Diagram**: actions possible for agents and managers.

---

## 🎮 Test Scenarios
0. log as Director -> create Manager and Departments .
1. Log in as an agent → view personal information → display payment history.
2. Log in as a manager → create a department → add an agent → add a payment.
3. Validate business rules:
    - Impossible to add a negative amount.
    - Impossible to add reward/allowance without eligibility and validation.
4. Statistics: display total and average of payments per agent and per department.

---

## 📦 Deliverables
- Complete and functional source code.
- Executable JAR file--st-inPro.
- UML diagrams .
- Documentation via this README.

---