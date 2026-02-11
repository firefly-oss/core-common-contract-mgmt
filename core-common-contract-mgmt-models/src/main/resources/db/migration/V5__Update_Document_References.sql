-- =========================
-- UPDATE DOCUMENT REFERENCES
-- =========================

-- Remove document_manager_ref_id from contract table
ALTER TABLE contract 
    DROP COLUMN IF EXISTS document_manager_ref_id;

-- Update contract_document table: rename document_manager_ref_id to document_id and change document_type to document_type_id
ALTER TABLE contract_document
    DROP COLUMN IF EXISTS document_manager_ref_id,
    ADD COLUMN document_id UUID,
    DROP COLUMN IF EXISTS document_type,
    ADD COLUMN document_type_id UUID;

-- Update contract_event table: remove document_manager_ref_id (no longer needed)
ALTER TABLE contract_event
    DROP COLUMN IF EXISTS document_manager_ref_id;

-- Add comments for clarity
COMMENT ON COLUMN contract_document.document_id IS 'Reference ID to document in document management platform';
COMMENT ON COLUMN contract_document.document_type_id IS 'Reference ID to ContractDocumentType master data entity';
