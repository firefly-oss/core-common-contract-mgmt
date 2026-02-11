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

import com.fasterxml.jackson.databind.JsonNode;
import com.firefly.core.contracts.interfaces.enums.TermValidationTypeEnum;
import com.firefly.core.contracts.models.entities.ContractTermValidationRule;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository interface for ContractTermValidationRule entity operations
 */
@Repository
public interface ContractTermValidationRuleRepository extends BaseRepository<ContractTermValidationRule, UUID> {

    /**
     * Find validation rules by term template ID
     */
    Flux<ContractTermValidationRule> findByTermTemplateId(UUID termTemplateId);

    /**
     * Find validation rules by validation type
     */
    Flux<ContractTermValidationRule> findByValidationType(TermValidationTypeEnum validationType);

    /**
     * Find validation rules by term template ID and validation type
     */
    Flux<ContractTermValidationRule> findByTermTemplateIdAndValidationType(UUID termTemplateId, 
                                                                           TermValidationTypeEnum validationType);

    /**
     * Find validation rules by validation value (JSON comparison)
     */
    @Query("SELECT * FROM contract_term_validation_rule WHERE validation_value = :validationValue::jsonb")
    Flux<ContractTermValidationRule> findByValidationValue(@Param("validationValue") JsonNode validationValue);

    /**
     * Count validation rules for a term template
     */
    Mono<Long> countByTermTemplateId(Long termTemplateId);

    /**
     * Count validation rules by validation type
     */
    Mono<Long> countByValidationType(TermValidationTypeEnum validationType);

    /**
     * Find required validation rules
     */
    @Query("SELECT * FROM contract_term_validation_rule WHERE validation_type = 'REQUIRED'")
    Flux<ContractTermValidationRule> findRequiredValidationRules();

    /**
     * Find validation rules with custom error messages
     */
    @Query("SELECT * FROM contract_term_validation_rule WHERE error_message IS NOT NULL AND error_message != ''")
    Flux<ContractTermValidationRule> findRulesWithCustomErrorMessages();

    /**
     * Find validation rules by term template ID ordered by validation type
     */
    Flux<ContractTermValidationRule> findByTermTemplateIdOrderByValidationType(UUID termTemplateId);

    /**
     * Delete validation rules by term template ID
     */
    @Query("DELETE FROM contract_term_validation_rule WHERE term_template_id = :termTemplateId")
    Mono<Void> deleteByTermTemplateId(@Param("termTemplateId") UUID termTemplateId);

    /**
     * Find validation rules for multiple term templates
     */
    @Query("SELECT * FROM contract_term_validation_rule WHERE term_template_id IN (:termTemplateIds)")
    Flux<ContractTermValidationRule> findByTermTemplateIdIn(@Param("termTemplateIds") Iterable<Long> termTemplateIds);

    /**
     * Check if term template has validation rules
     */
    @Query("SELECT COUNT(*) > 0 FROM contract_term_validation_rule WHERE term_template_id = :termTemplateId")
    Mono<Boolean> existsByTermTemplateId(@Param("termTemplateId") UUID termTemplateId);

    /**
     * Find validation rules that contain a specific JSON key
     */
    @Query("SELECT * FROM contract_term_validation_rule WHERE validation_value ? :key")
    Flux<ContractTermValidationRule> findByValidationValueContainingKey(@Param("key") String key);

    /**
     * Find validation rules with non-null validation values
     */
    @Query("SELECT * FROM contract_term_validation_rule WHERE validation_value IS NOT NULL")
    Flux<ContractTermValidationRule> findRulesWithValidationValue();
}
