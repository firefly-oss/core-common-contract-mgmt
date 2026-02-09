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
import com.firefly.core.contracts.core.services.ContractDocumentService;
import com.firefly.core.contracts.interfaces.dtos.ContractDocumentDTO;
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
@RequestMapping("/api/v1/contracts/{contractId}/documents")
@Tag(name = "Contract Documents", description = "API for managing contract documents")
@RequiredArgsConstructor
public class ContractDocumentController {

    private final ContractDocumentService contractDocumentService;

    @Operation(summary = "Filter contract documents", description = "Returns a paginated list of contract documents based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract documents",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<ContractDocumentDTO>>> filterContractDocuments(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Valid @RequestBody FilterRequest<ContractDocumentDTO> filterRequest) {
        return ResponseEntity.ok(contractDocumentService.filterContractDocuments(filterRequest));
    }

    @Operation(summary = "Create a new contract document", description = "Creates a new contract document with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contract document successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractDocumentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract document data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractDocumentDTO>> createContractDocument(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Valid @RequestBody ContractDocumentDTO contractDocumentDTO) {
        // Ensure the contractId in the path is used
        contractDocumentDTO.setContractId(contractId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contractDocumentService.createContractDocument(contractDocumentDTO));
    }

    @Operation(summary = "Get contract document by ID", description = "Returns a contract document based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract document",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractDocumentDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contract document not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{contractDocumentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractDocumentDTO>> getContractDocumentById(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract document to retrieve", required = true)
            @PathVariable UUID contractDocumentId) {
        return ResponseEntity.ok(contractDocumentService.getContractDocumentById(contractDocumentId)
                .filter(document -> document.getContractId().equals(contractId)));
    }

    @Operation(summary = "Update contract document", description = "Updates an existing contract document with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contract document successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractDocumentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract document data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract document not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{contractDocumentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractDocumentDTO>> updateContractDocument(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract document to update", required = true)
            @PathVariable UUID contractDocumentId,
            @Valid @RequestBody ContractDocumentDTO contractDocumentDTO) {
        // Ensure the contractId in the path is used
        contractDocumentDTO.setContractId(contractId);
        return ResponseEntity.ok(contractDocumentService.updateContractDocument(contractDocumentId, contractDocumentDTO));
    }

    @Operation(summary = "Delete contract document", description = "Deletes a contract document based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contract document successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract document not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{contractDocumentId}")
    public Mono<ResponseEntity<Void>> deleteContractDocument(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract document to delete", required = true)
            @PathVariable UUID contractDocumentId) {
        return contractDocumentService.getContractDocumentById(contractDocumentId)
                .filter(document -> document.getContractId().equals(contractId))
                .flatMap(document -> contractDocumentService.deleteContractDocument(contractDocumentId))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
