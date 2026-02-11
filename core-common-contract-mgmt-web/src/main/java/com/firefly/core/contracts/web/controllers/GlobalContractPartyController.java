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


package com.firefly.core.contracts.web.controllers;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.contracts.core.services.ContractPartyService;
import com.firefly.core.contracts.interfaces.dtos.ContractPartyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Global controller for party-centric contract party queries.
 * <p>
 * This controller provides endpoints for querying contract parties by partyId,
 * which is useful for aggregating all contracts associated with a specific party
 * (e.g., for session context enrichment in the Security Center).
 */
@RestController
@RequestMapping("/api/v1/contract-parties")
@Tag(name = "Global Contract Parties", description = "API for party-centric contract party queries")
@RequiredArgsConstructor
public class GlobalContractPartyController {

    private final ContractPartyService contractPartyService;

    @Operation(
        summary = "Get contract parties by party ID",
        description = "Returns a paginated list of all contract parties for a specific party. " +
                      "This endpoint is useful for finding all contracts a party is involved in."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved contract parties"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid party ID provided",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ContractPartyDTO>>> getContractPartiesByPartyId(
            @Parameter(description = "ID of the party to retrieve contract parties for", required = true)
            @RequestParam UUID partyId,
            @Parameter(description = "Filter for active contracts only", required = false)
            @RequestParam(required = false, defaultValue = "true") Boolean isActive) {
        
        // Build filter request for partyId and isActive
        ContractPartyDTO criteria = ContractPartyDTO.builder()
                .partyId(partyId)
                .isActive(isActive)
                .build();
        
        FilterRequest<ContractPartyDTO> filterRequest = new FilterRequest<>();
        filterRequest.setFilters(criteria);
        
        return contractPartyService.filterContractParties(filterRequest).map(ResponseEntity::ok);
    }

    @Operation(
        summary = "Filter contract parties globally",
        description = "Returns a paginated list of contract parties based on filter criteria across all contracts"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved contract parties"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid filter criteria provided",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content
        )
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<PaginationResponse<ContractPartyDTO>>> filterContractPartiesGlobally(
            @Valid @RequestBody FilterRequest<ContractPartyDTO> filterRequest) {
        return contractPartyService.filterContractParties(filterRequest).map(ResponseEntity::ok);
    }
}
