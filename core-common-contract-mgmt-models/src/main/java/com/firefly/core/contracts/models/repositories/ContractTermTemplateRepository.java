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


package com.firefly.core.contracts.models.repositories;

import com.firefly.core.contracts.interfaces.enums.TermCategoryEnum;
import com.firefly.core.contracts.interfaces.enums.TermDataTypeEnum;
import com.firefly.core.contracts.models.entities.ContractTermTemplate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository interface for ContractTermTemplate entity operations
 */
@Repository
public interface ContractTermTemplateRepository extends BaseRepository<ContractTermTemplate, UUID> {

    /**
     * Find template by code
     */
    Mono<ContractTermTemplate> findByCode(String code);

    /**
     * Find templates by category
     */
    Flux<ContractTermTemplate> findByTermCategory(TermCategoryEnum termCategory);

    /**
     * Find templates by data type
     */
    Flux<ContractTermTemplate> findByDataType(TermDataTypeEnum dataType);

    /**
     * Find active templates
     */
    Flux<ContractTermTemplate> findByIsActive(Boolean isActive);

    /**
     * Find required templates
     */
    Flux<ContractTermTemplate> findByIsRequired(Boolean isRequired);

    /**
     * Find active templates by category
     */
    Flux<ContractTermTemplate> findByTermCategoryAndIsActive(TermCategoryEnum termCategory, Boolean isActive);

    /**
     * Find active required templates
     */
    Flux<ContractTermTemplate> findByIsActiveAndIsRequired(Boolean isActive, Boolean isRequired);

    /**
     * Find templates by name containing text (case-insensitive)
     */
    @Query("SELECT * FROM contract_term_template WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Flux<ContractTermTemplate> findByNameContainingIgnoreCase(@Param("name") String name);

    /**
     * Find templates by category and data type
     */
    Flux<ContractTermTemplate> findByTermCategoryAndDataType(TermCategoryEnum termCategory, TermDataTypeEnum dataType);

    /**
     * Count active templates
     */
    Mono<Long> countByIsActive(Boolean isActive);

    /**
     * Count templates by category
     */
    Mono<Long> countByTermCategory(TermCategoryEnum termCategory);

    /**
     * Find templates with validation rules
     */
    @Query("SELECT * FROM contract_term_template WHERE validation_rules IS NOT NULL")
    Flux<ContractTermTemplate> findTemplatesWithValidationRules();

    /**
     * Find templates with metadata
     */
    @Query("SELECT * FROM contract_term_template WHERE metadata IS NOT NULL")
    Flux<ContractTermTemplate> findTemplatesWithMetadata();

    /**
     * Find templates by code pattern
     */
    @Query("SELECT * FROM contract_term_template WHERE code LIKE :pattern")
    Flux<ContractTermTemplate> findByCodeLike(@Param("pattern") String pattern);
}
