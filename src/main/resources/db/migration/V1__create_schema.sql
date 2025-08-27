-- Tabela Account
CREATE TABLE account (
    account_number NUMERIC(8, 0) NOT NULL,
    agency_number  NUMERIC(4, 0) NOT NULL,
    account_type   VARCHAR(10) NOT NULL,
    name           VARCHAR(30) NOT NULL,
    last_name      VARCHAR(45) NULL,
    document_number VARCHAR(14) NOT NULL,
    PRIMARY KEY (account_number, agency_number)
);

CREATE TABLE pix (
    id                VARCHAR(36) PRIMARY KEY,
    pix_type          VARCHAR(9) NOT NULL,
    pix_value         VARCHAR(77) NOT NULL,
    account_number    NUMERIC(8, 0) NOT NULL,
    agency_number     NUMERIC(4, 0) NOT NULL,
    inclusion_date    TIMESTAMP NOT NULL,
    inactivation_date TIMESTAMP NULL,
    is_active         BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_pix_account FOREIGN KEY (account_number, agency_number)
        REFERENCES account (account_number, agency_number),
    CONSTRAINT uq_pix_value UNIQUE (pix_value)
);

-- Contas
INSERT INTO account (account_number, agency_number, account_type, name, last_name, document_number)
VALUES
(00000001, 1234, 'CORRENTE', 'Carlos', 'Carmo', '12345678901'), -- CPF
(00000002, 5678, 'POUPANCA', 'Maria', 'Julia', '98765432100'); -- CPF

-- Pix (para conta de Carlos, com mais de 5 PIX)
INSERT INTO pix (id, pix_type, pix_value, account_number, agency_number, inclusion_date, inactivation_date, is_active)
VALUES
('11111111-0000-0000-0000-000000000001', 'CPF', '12345678901', 1, 1234, '2025-08-26 00:00:00', NULL, TRUE),
('11111111-0000-0000-0000-000000000002', 'EMAIL', 'carlos@email.com', 1, 1234, '2025-08-26 00:00:00', NULL, TRUE),
('11111111-0000-0000-0000-000000000003', 'CELULAR', '+5511999999999', 1, 1234, '2025-08-26 00:00:00', NULL, TRUE),
('11111111-0000-0000-0000-000000000004', 'ALEATORIO', 'a1b2c3d4e5f6g7h8i9j0', 1, 1234, '2025-08-26 00:00:00', NULL, TRUE),
('11111111-0000-0000-0000-000000000005', 'CNPJ', '12345678000199', 1, 1234, '2025-08-26 00:00:00', NULL, TRUE),
('11111111-0000-0000-0000-000000000006', 'EMAIL', 'outra-chave@email.com', 1, 1234, '2025-08-26 00:00:00', NULL, TRUE);

-- Pix (para conta da Maria, apenas 1 exemplo)
INSERT INTO pix (id, pix_type, pix_value, account_number, agency_number, inclusion_date, inactivation_date, is_active)
VALUES
('22222222-0000-0000-0000-000000000001', 'EMAIL', 'maria@email.com', 2, 5678, '2025-08-26 00:00:00', NULL, TRUE);

