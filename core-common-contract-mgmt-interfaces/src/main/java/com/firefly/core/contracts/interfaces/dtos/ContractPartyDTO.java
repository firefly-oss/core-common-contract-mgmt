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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contract party DTO for API operations - compatible with R2DBC ContractParty entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractPartyDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID contractPartyId;

    @NotNull(message = "Contract ID is required")
    private UUID contractId;

    @NotNull(message = "Party ID is required")
    private UUID partyId;

    @NotNull(message = "Role in contract ID is required")
    private UUID roleInContractId;

    private LocalDateTime dateJoined;

    private LocalDateTime dateLeft;

    private Boolean isActive;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
