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
import com.fasterxml.jackson.databind.JsonNode;
import com.firefly.core.contracts.interfaces.enums.TermValidationTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contract term validation rule DTO for API operations - compatible with R2DBC ContractTermValidationRule entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractTermValidationRuleDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID validationRuleId;

    @NotNull(message = "Term template ID is required")
    private UUID termTemplateId;

    @NotNull(message = "Validation type is required")
    private TermValidationTypeEnum validationType;

    /**
     * Validation criteria as JSON. Examples:
     * - For REGEX: {"pattern": "^[A-Z]{2,10}$", "flags": "i"}
     * - For RANGE: {"min": 0, "max": 100}
     * - For LIST: {"values": ["OPTION1", "OPTION2", "OPTION3"]}
     * - For REQUIRED: {"required": true}
     * - For LENGTH: {"minLength": 5, "maxLength": 50}
     */
    private JsonNode validationValue;

    @Size(max = 500, message = "Error message must not exceed 500 characters")
    private String errorMessage;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
