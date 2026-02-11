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


package com.firefly.core.contracts.interfaces.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.firefly.core.contracts.interfaces.enums.RiskLevelEnum;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contract risk assessment DTO for API operations - compatible with R2DBC ContractRiskAssessment entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractRiskAssessmentDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID contractRiskAssessmentId;

    @NotNull(message = "Contract ID is required")
    private UUID contractId;

    @DecimalMin(value = "0.00", message = "Risk score must be greater than or equal to 0")
    @DecimalMax(value = "100.00", message = "Risk score must be less than or equal to 100")
    private BigDecimal riskScore;

    @NotNull(message = "Risk level is required")
    private RiskLevelEnum riskLevel;

    private LocalDateTime assessmentDate;

    @Size(max = 255, message = "Assessor must not exceed 255 characters")
    private String assessor;

    private String notes;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
