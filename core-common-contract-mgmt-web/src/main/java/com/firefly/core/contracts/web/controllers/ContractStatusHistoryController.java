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
import com.firefly.core.contracts.core.services.ContractStatusHistoryService;
import com.firefly.core.contracts.interfaces.dtos.ContractStatusHistoryDTO;
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
@RequestMapping("/api/v1/contracts/{contractId}/status-history")
@Tag(name = "Contract Status History", description = "API for managing contract status history")
@RequiredArgsConstructor
public class ContractStatusHistoryController {

    private final ContractStatusHistoryService contractStatusHistoryService;

    @Operation(summary = "Filter contract status history", description = "Returns a paginated list of contract status history based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract status history",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<ContractStatusHistoryDTO>>> filterContractStatusHistory(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Valid @RequestBody FilterRequest<ContractStatusHistoryDTO> filterRequest) {
        return ResponseEntity.ok(contractStatusHistoryService.filterContractStatusHistory(filterRequest));
    }

    @Operation(summary = "Create a new contract status history", description = "Creates a new contract status history with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contract status history successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractStatusHistoryDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract status history data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractStatusHistoryDTO>> createContractStatusHistory(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Valid @RequestBody ContractStatusHistoryDTO contractStatusHistoryDTO) {
        // Ensure the contractId in the path is used
        contractStatusHistoryDTO.setContractId(contractId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contractStatusHistoryService.createContractStatusHistory(contractStatusHistoryDTO));
    }

    @Operation(summary = "Get contract status history by ID", description = "Returns a contract status history based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract status history",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractStatusHistoryDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contract status history not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{contractStatusHistoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractStatusHistoryDTO>> getContractStatusHistoryById(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract status history to retrieve", required = true)
            @PathVariable UUID contractStatusHistoryId) {
        return ResponseEntity.ok(contractStatusHistoryService.getContractStatusHistoryById(contractStatusHistoryId)
                .filter(history -> history.getContractId().equals(contractId)));
    }

    @Operation(summary = "Update contract status history", description = "Updates an existing contract status history with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contract status history successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractStatusHistoryDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract status history data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract status history not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{contractStatusHistoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractStatusHistoryDTO>> updateContractStatusHistory(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract status history to update", required = true)
            @PathVariable UUID contractStatusHistoryId,
            @Valid @RequestBody ContractStatusHistoryDTO contractStatusHistoryDTO) {
        // Ensure the contractId in the path is used
        contractStatusHistoryDTO.setContractId(contractId);
        return ResponseEntity.ok(contractStatusHistoryService.updateContractStatusHistory(contractStatusHistoryId, contractStatusHistoryDTO));
    }

    @Operation(summary = "Delete contract status history", description = "Deletes a contract status history based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contract status history successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract status history not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{contractStatusHistoryId}")
    public Mono<ResponseEntity<Void>> deleteContractStatusHistory(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract status history to delete", required = true)
            @PathVariable UUID contractStatusHistoryId) {
        return contractStatusHistoryService.getContractStatusHistoryById(contractStatusHistoryId)
                .filter(history -> history.getContractId().equals(contractId))
                .flatMap(history -> contractStatusHistoryService.deleteContractStatusHistory(contractStatusHistoryId))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
