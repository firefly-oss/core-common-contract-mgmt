-- =========================
-- CONTRACT MANAGEMENT TABLES
-- =========================

-- Main contract table
CREATE TABLE contract (
    contract_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_number VARCHAR(255) UNIQUE,
    contract_status contract_status_enum NOT NULL DEFAULT 'DRAFT',
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    document_manager_ref VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Contract parties table
CREATE TABLE contract_party (
    contract_party_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_id UUID NOT NULL,
    party_id UUID NOT NULL,
    role_in_contract_id UUID NOT NULL,
    date_joined TIMESTAMP,
    date_left TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_party_contract FOREIGN KEY (contract_id) REFERENCES contract(contract_id) ON DELETE CASCADE
);

-- Contract documents table
CREATE TABLE contract_document (
    contract_document_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_id UUID NOT NULL,
    document_type VARCHAR(100) NOT NULL,
    document_manager_ref VARCHAR(500) NOT NULL,
    date_added TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_document_contract FOREIGN KEY (contract_id) REFERENCES contract(contract_id) ON DELETE CASCADE
);

-- Contract status history table
CREATE TABLE contract_status_history (
    contract_status_history_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_id UUID NOT NULL,
    status_code status_code_enum NOT NULL,
    status_start_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status_end_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_status_history_contract FOREIGN KEY (contract_id) REFERENCES contract(contract_id) ON DELETE CASCADE
);

-- Contract events table
CREATE TABLE contract_event (
    contract_event_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_id UUID NOT NULL,
    event_type event_type_enum NOT NULL,
    event_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    event_description TEXT,
    document_manager_ref VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_event_contract FOREIGN KEY (contract_id) REFERENCES contract(contract_id) ON DELETE CASCADE
);

-- Contract risk assessment table
CREATE TABLE contract_risk_assessment (
    contract_risk_assessment_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_id UUID NOT NULL,
    risk_score DECIMAL(5,2),
    risk_level risk_level_enum NOT NULL,
    assessment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    assessor VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_risk_assessment_contract FOREIGN KEY (contract_id) REFERENCES contract(contract_id) ON DELETE CASCADE
);

-- Contract term template table
CREATE TABLE contract_term_template (
    term_template_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    term_category term_category_enum NOT NULL,
    data_type term_data_type_enum NOT NULL,
    is_required BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    default_value TEXT,
    validation_rules JSONB,
    metadata JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Contract term validation rules table
CREATE TABLE contract_term_validation_rule (
    validation_rule_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    term_template_id UUID NOT NULL,
    validation_type term_validation_type_enum NOT NULL,
    validation_value VARCHAR(1000),
    error_message VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_term_validation_rule_template FOREIGN KEY (term_template_id) REFERENCES contract_term_template(term_template_id) ON DELETE CASCADE
);

-- Dynamic contract terms table
CREATE TABLE contract_term_dynamic (
    term_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contract_id UUID NOT NULL,
    term_template_id UUID NOT NULL,
    term_value_text TEXT,
    term_value_numeric DECIMAL(20,6),
    term_value_json JSONB,
    effective_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expiration_date TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contract_term_dynamic_contract FOREIGN KEY (contract_id) REFERENCES contract(contract_id) ON DELETE CASCADE,
    CONSTRAINT fk_contract_term_dynamic_template FOREIGN KEY (term_template_id) REFERENCES contract_term_template(term_template_id) ON DELETE RESTRICT
);

-- =========================
-- INDEXES FOR PERFORMANCE
-- =========================

-- Contract indexes
CREATE INDEX idx_contract_status ON contract(contract_status);
CREATE INDEX idx_contract_dates ON contract(start_date, end_date);
CREATE INDEX idx_contract_number ON contract(contract_number) WHERE contract_number IS NOT NULL;

-- Contract party indexes
CREATE INDEX idx_contract_party_contract_id ON contract_party(contract_id);
CREATE INDEX idx_contract_party_party_id ON contract_party(party_id);
CREATE INDEX idx_contract_party_active ON contract_party(is_active);

-- Contract document indexes
CREATE INDEX idx_contract_document_contract_id ON contract_document(contract_id);
CREATE INDEX idx_contract_document_type ON contract_document(document_type);

-- Contract status history indexes
CREATE INDEX idx_contract_status_history_contract_id ON contract_status_history(contract_id);
CREATE INDEX idx_contract_status_history_dates ON contract_status_history(status_start_date, status_end_date);

-- Contract event indexes
CREATE INDEX idx_contract_event_contract_id ON contract_event(contract_id);
CREATE INDEX idx_contract_event_type ON contract_event(event_type);
CREATE INDEX idx_contract_event_date ON contract_event(event_date);

-- Contract risk assessment indexes
CREATE INDEX idx_contract_risk_assessment_contract_id ON contract_risk_assessment(contract_id);
CREATE INDEX idx_contract_risk_assessment_level ON contract_risk_assessment(risk_level);
CREATE INDEX idx_contract_risk_assessment_date ON contract_risk_assessment(assessment_date);

-- Contract term template indexes
CREATE INDEX idx_contract_term_template_code ON contract_term_template(code);
CREATE INDEX idx_contract_term_template_category ON contract_term_template(term_category);
CREATE INDEX idx_contract_term_template_active ON contract_term_template(is_active);

-- Contract term validation rule indexes
CREATE INDEX idx_contract_term_validation_rule_template_id ON contract_term_validation_rule(term_template_id);

-- Dynamic contract term indexes
CREATE INDEX idx_contract_term_dynamic_contract_id ON contract_term_dynamic(contract_id);
CREATE INDEX idx_contract_term_dynamic_template_id ON contract_term_dynamic(term_template_id);
CREATE INDEX idx_contract_term_dynamic_active ON contract_term_dynamic(is_active);
CREATE INDEX idx_contract_term_dynamic_dates ON contract_term_dynamic(effective_date, expiration_date);
