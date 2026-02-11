-- =========================
-- CONTRACT MANAGEMENT ENUMS
-- =========================

-- Contract status enumeration
CREATE TYPE contract_status_enum AS ENUM (
    'DRAFT',
    'PENDING_APPROVAL',
    'ACTIVE',
    'SUSPENDED',
    'TERMINATED',
    'EXPIRED',
    'CANCELLED'
);

-- Status code enumeration for contract status history
CREATE TYPE status_code_enum AS ENUM (
    'CREATED',
    'SUBMITTED_FOR_APPROVAL',
    'APPROVED',
    'REJECTED',
    'ACTIVATED',
    'SUSPENDED',
    'TERMINATED',
    'EXPIRED',
    'CANCELLED',
    'AMENDED'
);

-- Event type enumeration for contract events
CREATE TYPE event_type_enum AS ENUM (
    'CONTRACT_CREATED',
    'CONTRACT_SIGNED',
    'CONTRACT_ACTIVATED',
    'CONTRACT_AMENDED',
    'CONTRACT_RENEWED',
    'CONTRACT_SUSPENDED',
    'CONTRACT_TERMINATED',
    'CONTRACT_EXPIRED',
    'MILESTONE_REACHED',
    'PAYMENT_DUE',
    'PAYMENT_RECEIVED',
    'BREACH_REPORTED',
    'DISPUTE_RAISED',
    'AUDIT_COMPLETED'
);

-- Risk level enumeration for risk assessments
CREATE TYPE risk_level_enum AS ENUM (
    'LOW',
    'MEDIUM',
    'HIGH',
    'CRITICAL'
);

-- Term category enumeration for contract term templates
CREATE TYPE term_category_enum AS ENUM (
    'FINANCIAL',
    'LEGAL',
    'OPERATIONAL',
    'COMPLIANCE',
    'PERFORMANCE',
    'DELIVERY',
    'PAYMENT',
    'LIABILITY',
    'TERMINATION',
    'RENEWAL',
    'CUSTOM'
);

-- Term data type enumeration for contract term templates
CREATE TYPE term_data_type_enum AS ENUM (
    'STRING',
    'NUMBER',
    'DECIMAL',
    'BOOLEAN',
    'DATE',
    'DATETIME',
    'ENUM',
    'JSON',
    'MONEY'
);

-- Term validation type enumeration for validation rules
CREATE TYPE term_validation_type_enum AS ENUM (
    'REQUIRED',
    'MIN_LENGTH',
    'MAX_LENGTH',
    'MIN_VALUE',
    'MAX_VALUE',
    'REGEX_PATTERN',
    'ENUM_VALUES',
    'DATE_RANGE',
    'CUSTOM_FUNCTION'
);
