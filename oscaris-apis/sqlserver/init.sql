-- 1. Create the database if not exists
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'oscaris_db')
BEGIN
    CREATE DATABASE oscaris_db;
END
GO

-- 2. Change context to the new database
USE oscaris_db;
GO
