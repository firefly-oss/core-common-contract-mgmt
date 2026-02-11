-- =========================
-- UPDATE VALIDATION VALUE TO JSONB
-- =========================

-- Update contract_term_validation_rule table: change validation_value from VARCHAR to JSONB
ALTER TABLE contract_term_validation_rule 
    ALTER COLUMN validation_value TYPE JSONB USING 
    CASE 
        WHEN validation_value IS NULL THEN NULL
        WHEN validation_value = '' THEN NULL
        WHEN validation_value ~ '^[{[].*[}\]]$' THEN validation_value::JSONB
        ELSE ('{"value": "' || replace(validation_value, '"', '\"') || '"}')::JSONB
    END;

-- Add comment for clarity
COMMENT ON COLUMN contract_term_validation_rule.validation_value IS 'Validation criteria as JSON object (e.g., {"pattern": "^[A-Z]+$"} for regex, {"min": 0, "max": 100} for range)';
