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
import com.firefly.core.contracts.core.services.ContractEventService;
import com.firefly.core.contracts.interfaces.dtos.ContractEventDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contracts/{contractId}/events")
@Tag(name = "Contract Events", description = "API for managing contract events")
@RequiredArgsConstructor
public class ContractEventController {

    private final ContractEventService contractEventService;

    @Operation(summary = "Filter contract events", description = "Returns a paginated list of contract events based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract events",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<ContractEventDTO>>> filterContractEvents(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Valid @RequestBody FilterRequest<ContractEventDTO> filterRequest) {
        return ResponseEntity.ok(contractEventService.filterContractEvents(filterRequest));
    }

    @Operation(summary = "Create a new contract event", description = "Creates a new contract event with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contract event successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractEventDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract event data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractEventDTO>> createContractEvent(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Valid @RequestBody ContractEventDTO contractEventDTO) {
        // Ensure the contractId in the path is used
        contractEventDTO.setContractId(contractId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contractEventService.createContractEvent(contractEventDTO));
    }

    @Operation(summary = "Get contract event by ID", description = "Returns a contract event based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract event",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractEventDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contract event not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{contractEventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractEventDTO>> getContractEventById(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract event to retrieve", required = true)
            @PathVariable UUID contractEventId) {
        return ResponseEntity.ok(contractEventService.getContractEventById(contractEventId)
                .filter(event -> event.getContractId().equals(contractId)));
    }

    @Operation(summary = "Update contract event", description = "Updates an existing contract event with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contract event successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractEventDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract event data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract event not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{contractEventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractEventDTO>> updateContractEvent(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract event to update", required = true)
            @PathVariable UUID contractEventId,
            @Valid @RequestBody ContractEventDTO contractEventDTO) {
        // Ensure the contractId in the path is used
        contractEventDTO.setContractId(contractId);
        return ResponseEntity.ok(contractEventService.updateContractEvent(contractEventId, contractEventDTO));
    }

    @Operation(summary = "Delete contract event", description = "Deletes a contract event based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contract event successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract event not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{contractEventId}")
    public Mono<ResponseEntity<Void>> deleteContractEvent(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract event to delete", required = true)
            @PathVariable UUID contractEventId) {
        return contractEventService.getContractEventById(contractEventId)
                .filter(event -> event.getContractId().equals(contractId))
                .flatMap(event -> contractEventService.deleteContractEvent(contractEventId))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
