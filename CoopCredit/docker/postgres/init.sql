-- Script de inicialización de PostgreSQL

-- Crear extensiones útiles
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";  -- Para generar UUIDs
CREATE EXTENSION IF NOT EXISTS "pgcrypto";   -- Para encriptación
--mensaje de confirmacion
SELECT 'Base de datos coopcredit_db inicializada correctamente' AS status;
SELECT
    current_database() as base_datos,
    current_user as usuario,
    version() as version_postgresql;