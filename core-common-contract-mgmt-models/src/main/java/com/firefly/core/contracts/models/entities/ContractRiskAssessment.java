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

import com.firefly.core.contracts.interfaces.enums.RiskLevelEnum;
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
 * Contract risk assessment entity representing risk evaluations for contracts
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("contract_risk_assessment")
public class ContractRiskAssessment {

    @Id
    @Column("contract_risk_assessment_id")
    private UUID contractRiskAssessmentId;

    @Column("contract_id")
    private UUID contractId;

    @Column("risk_score")
    private BigDecimal riskScore;

    @Column("risk_level")
    private RiskLevelEnum riskLevel;

    @Column("assessment_date")
    private LocalDateTime assessmentDate;

    @Column("assessor")
    private String assessor;

    @Column("notes")
    private String notes;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
