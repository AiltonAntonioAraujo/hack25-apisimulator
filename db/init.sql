
use master;
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'hack')
BEGIN
    CREATE DATABASE [hack];
    PRINT 'Database [hack] created successfully.';
END
ELSE
BEGIN
    PRINT 'Database [hack] already exists.';
END
GO

-- Verifica se o login "hack" existe e cria se não existir
IF NOT EXISTS (SELECT * FROM sys.sql_logins WHERE name = 'hack')
BEGIN
    CREATE LOGIN [hack] WITH PASSWORD = 'Password23', CHECK_POLICY = OFF;
    PRINT 'Login [hack] created successfully.';
    
    -- Adiciona o login à role sysadmin
    ALTER SERVER ROLE [sysadmin] ADD MEMBER [hack];
    PRINT 'Login [hack] added to sysadmin role.';
END
ELSE
BEGIN
    PRINT 'Login [hack] already exists.';
END
GO

-- Usa o banco de dados hack para criar o usuário
USE hack;
GO

-- Cria o usuário do banco de dados para o login se não existir
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'hack')
BEGIN
    CREATE USER [hack] FOR LOGIN [hack];
    PRINT 'Database user [hack] created successfully.';
    
    -- Adiciona o usuário à role db_owner do banco de dados
    ALTER ROLE [db_owner] ADD MEMBER [hack];
    PRINT 'User [hack] added to db_owner role.';
END
ELSE
BEGIN
    PRINT 'Database user [hack] already exists.';
END
GO

CREATE TABLE dbo.PRODUTO (
    CO_PRODUTO INT NOT NULL PRIMARY KEY,
    NO_PRODUTO VARCHAR(200) NOT NULL,
    PC_TAXA_JUROS NUMERIC(10,9) NOT NULL,
    NU_MINIMO_MESES SMALLINT NOT NULL,
    NU_MAXIMO_MESES SMALLINT NULL,  -- Corrigido: era "NOT" sem NULL
    VLR_MINIMO NUMERIC(18,2) NOT NULL,
    VLR_MAXIMO NUMERIC(18,2) NULL
);
GO

INSERT INTO dbo.PRODUTO (CO_PRODUTO, NO_PRODUTO, PC_TAXA_JUROS, NU_MINIMO_MESES, NU_MAXIMO_MESES, VLR_MINIMO, VLR_MAXIMO)
VALUES 
(1, 'Produto 1', 0.0179000000, 0, 24, 200.00, 10000.00),
(2, 'Produto 2', 0.0175000000, 25, 48, 10001.00, 100000.00),
(3, 'Produto 3', 0.0182000000, 49, 96, 100000.01, 1000000.00),
(4, 'Produto 4', 0.0151000000, 96, NULL, 1000000.01, NULL);
GO

