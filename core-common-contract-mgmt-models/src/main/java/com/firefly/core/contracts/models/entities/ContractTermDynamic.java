/*
 * Copyright 2025 Firefly Software Solutions Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.firefly.core.contracts.models.entities;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contract term dynamic entity representing dynamic/parameterized terms for contracts
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("contract_term_dynamic")
public class ContractTermDynamic {

    @Id
    @Column("term_id")
    private UUID termId;

    @Column("contract_id")
    private UUID contractId;

    @Column("term_template_id")
    private UUID termTemplateId;

    @Column("term_value_text")
    private String termValueText;

    @Column("term_value_numeric")
    private BigDecimal termValueNumeric;

    @Column("term_value_json")
    private JsonNode termValueJson;

    @Column("effective_date")
    private LocalDateTime effectiveDate;

    @Column("expiration_date")
    private LocalDateTime expirationDate;

    @Column("is_active")
    private Boolean isActive;

    @Column("notes")
    private String notes;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
