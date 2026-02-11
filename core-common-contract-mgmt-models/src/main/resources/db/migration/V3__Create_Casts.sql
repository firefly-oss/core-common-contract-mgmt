-- =========================
-- CONTRACT MANAGEMENT ENUM CASTS
-- =========================

-- Create implicit casts from VARCHAR to enum types
-- This allows automatic conversion from string values to enum types

-- Contract status enum cast
CREATE CAST (VARCHAR AS contract_status_enum) WITH INOUT AS IMPLICIT;

-- Status code enum cast
CREATE CAST (VARCHAR AS status_code_enum) WITH INOUT AS IMPLICIT;

-- Event type enum cast
CREATE CAST (VARCHAR AS event_type_enum) WITH INOUT AS IMPLICIT;

-- Risk level enum cast
CREATE CAST (VARCHAR AS risk_level_enum) WITH INOUT AS IMPLICIT;

-- Term category enum cast
CREATE CAST (VARCHAR AS term_category_enum) WITH INOUT AS IMPLICIT;

-- Term data type enum cast
CREATE CAST (VARCHAR AS term_data_type_enum) WITH INOUT AS IMPLICIT;

-- Term validation type enum cast
CREATE CAST (VARCHAR AS term_validation_type_enum) WITH INOUT AS IMPLICIT;
