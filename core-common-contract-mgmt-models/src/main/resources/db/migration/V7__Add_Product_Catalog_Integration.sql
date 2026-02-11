-- =========================
-- ADD PRODUCT CATALOG INTEGRATION
-- =========================

-- Add product catalog integration fields to contract table
ALTER TABLE contract 
    ADD COLUMN product_catalog_id UUID,
    ADD COLUMN product_id UUID;

-- Add comments for clarity
COMMENT ON COLUMN contract.product_catalog_id IS 'Reference UUID to product catalog entry in Product Management System';
COMMENT ON COLUMN contract.product_id IS 'Reference UUID to specific product instance in Product Management System';

-- Add indexes for better query performance on product catalog fields
CREATE INDEX idx_contract_product_catalog_id ON contract(product_catalog_id);
CREATE INDEX idx_contract_product_id ON contract(product_id);
CREATE INDEX idx_contract_product_catalog_product ON contract(product_catalog_id, product_id);
