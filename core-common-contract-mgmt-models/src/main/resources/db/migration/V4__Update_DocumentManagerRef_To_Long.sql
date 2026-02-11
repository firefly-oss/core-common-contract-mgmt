-- =========================
-- UPDATE DOCUMENT MANAGER REFERENCE TO LONG
-- =========================

-- Update contract table: change document_manager_ref from VARCHAR to UUID and rename to document_manager_ref_id
ALTER TABLE contract 
    DROP COLUMN IF EXISTS document_manager_ref,
    ADD COLUMN document_manager_ref_id UUID;

-- Update contract_document table: change document_manager_ref from VARCHAR to UUID and rename to document_manager_ref_id
ALTER TABLE contract_document
    DROP COLUMN IF EXISTS document_manager_ref,
    ADD COLUMN document_manager_ref_id UUID;

-- Update contract_event table: change document_manager_ref from VARCHAR to UUID and rename to document_manager_ref_id
ALTER TABLE contract_event 
    DROP COLUMN IF EXISTS document_manager_ref,
    ADD COLUMN document_manager_ref_id UUID;

-- Add comments for clarity
COMMENT ON COLUMN contract.document_manager_ref_id IS 'Reference ID to document in document management platform';
COMMENT ON COLUMN contract_document.document_manager_ref_id IS 'Reference ID to document in document management platform';
COMMENT ON COLUMN contract_event.document_manager_ref_id IS 'Reference ID to document in document management platform';
