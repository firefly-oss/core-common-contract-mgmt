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

import com.firefly.core.contracts.interfaces.enums.RiskLevelEnum;
import com.firefly.core.contracts.models.entities.ContractRiskAssessment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository interface for ContractRiskAssessment entity operations
 */
@Repository
public interface ContractRiskAssessmentRepository extends BaseRepository<ContractRiskAssessment, UUID> {

    /**
     * Find risk assessments by contract ID
     */
    Flux<ContractRiskAssessment> findByContractId(UUID contractId);

    /**
     * Find risk assessments by contract ID ordered by assessment date
     */
    Flux<ContractRiskAssessment> findByContractIdOrderByAssessmentDateDesc(UUID contractId);

    /**
     * Find risk assessments by risk level
     */
    Flux<ContractRiskAssessment> findByRiskLevel(RiskLevelEnum riskLevel);

    /**
     * Find risk assessments by assessor
     */
    Flux<ContractRiskAssessment> findByAssessor(String assessor);

    /**
     * Find latest risk assessment for a contract
     */
    @Query("SELECT * FROM contract_risk_assessment WHERE contract_id = :contractId ORDER BY assessment_date DESC LIMIT 1")
    Mono<ContractRiskAssessment> findLatestByContractId(@Param("contractId") UUID contractId);

    /**
     * Find risk assessments within a date range
     */
    Flux<ContractRiskAssessment> findByAssessmentDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    /**
     * Find risk assessments with score above threshold
     */
    @Query("SELECT * FROM contract_risk_assessment WHERE risk_score >= :threshold")
    Flux<ContractRiskAssessment> findByRiskScoreGreaterThanEqual(@Param("threshold") BigDecimal threshold);

    /**
     * Find risk assessments with score below threshold
     */
    @Query("SELECT * FROM contract_risk_assessment WHERE risk_score <= :threshold")
    Flux<ContractRiskAssessment> findByRiskScoreLessThanEqual(@Param("threshold") BigDecimal threshold);

    /**
     * Find high-risk contracts (CRITICAL or HIGH risk level)
     */
    @Query("SELECT * FROM contract_risk_assessment WHERE risk_level IN ('CRITICAL', 'HIGH')")
    Flux<ContractRiskAssessment> findHighRiskAssessments();

    /**
     * Count assessments by risk level
     */
    Mono<Long> countByRiskLevel(RiskLevelEnum riskLevel);

    /**
     * Count assessments for a contract
     */
    Mono<Long> countByContractId(Long contractId);

    /**
     * Find assessments by contract and risk level
     */
    Flux<ContractRiskAssessment> findByContractIdAndRiskLevel(UUID contractId, RiskLevelEnum riskLevel);

    /**
     * Find recent assessments across all contracts
     */
    @Query("SELECT * FROM contract_risk_assessment ORDER BY assessment_date DESC LIMIT :limit")
    Flux<ContractRiskAssessment> findRecentAssessments(@Param("limit") Integer limit);
}
