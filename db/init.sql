
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
