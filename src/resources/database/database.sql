CREATE TABLE agent (
                       id_agent INT AUTO_INCREMENT PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       email VARCHAR(100),
                       password VARCHAR(100),
                       agent_type ENUM('OUVRIER', 'RESPONSABLE_DEPARTEMENT', 'DIRECTEUR', 'STAGIAIRE'),
                       id_department INT NULL --after
);

CREATE TABLE department (
                            id_department INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            id_manager INT NULL,
                            CONSTRAINT fk_manager FOREIGN KEY (id_manager)
                                REFERENCES agent(id_agent)
                                ON DELETE SET NULL
);

-- adding departement to agent table as foreign key
ALTER TABLE agent
    ADD CONSTRAINT fk_department
        FOREIGN KEY (id_department) REFERENCES department(id_department)
            ON DELETE SET NULL;

CREATE TABLE payment (
                         id_payment INT AUTO_INCREMENT PRIMARY KEY,
                         payment_type ENUM('SALARY', 'PRIME', 'BONUS', 'INDEMNITE') NOT NULL,
                         amount DECIMAL(10,2) NOT NULL,
                         payment_date DATE NOT NULL,
                         condition_validated BOOLEAN DEFAULT FALSE,  -- Only for BONUS/INDEMNITE
                         id_agent INT NOT NULL,             -- FK to agent
                         CONSTRAINT fk_payment_agent FOREIGN KEY (id_agent)
                             REFERENCES agent(id_agent)
                             ON DELETE CASCADE
);


