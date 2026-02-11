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
import com.firefly.core.contracts.interfaces.enums.TermCategoryEnum;
import com.firefly.core.contracts.interfaces.enums.TermDataTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contract term template entity representing templates for dynamic contract terms
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("contract_term_template")
public class ContractTermTemplate {

    @Id
    @Column("term_template_id")
    private UUID termTemplateId;

    @Column("code")
    private String code;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("term_category")
    private TermCategoryEnum termCategory;

    @Column("data_type")
    private TermDataTypeEnum dataType;

    @Column("is_required")
    private Boolean isRequired;

    @Column("is_active")
    private Boolean isActive;

    @Column("default_value")
    private String defaultValue;

    @Column("validation_rules")
    private JsonNode validationRules;

    @Column("metadata")
    private JsonNode metadata;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
