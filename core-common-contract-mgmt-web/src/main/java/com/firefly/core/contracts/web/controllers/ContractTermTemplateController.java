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
import com.firefly.core.contracts.core.services.ContractTermTemplateService;
import com.firefly.core.contracts.interfaces.dtos.ContractTermTemplateDTO;
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
@RequestMapping("/api/v1/contract-term-templates")
@Tag(name = "Contract Term Templates", description = "API for managing contract term templates")
@RequiredArgsConstructor
public class ContractTermTemplateController {

    private final ContractTermTemplateService contractTermTemplateService;

    @Operation(summary = "Filter contract term templates", description = "Returns a paginated list of contract term templates based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract term templates",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<ContractTermTemplateDTO>>> filterContractTermTemplates(
            @Valid @RequestBody FilterRequest<ContractTermTemplateDTO> filterRequest) {
        return ResponseEntity.ok(contractTermTemplateService.filterContractTermTemplates(filterRequest));
    }

    @Operation(summary = "Create a new contract term template", description = "Creates a new contract term template with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contract term template successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractTermTemplateDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract term template data provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractTermTemplateDTO>> createContractTermTemplate(
            @Valid @RequestBody ContractTermTemplateDTO contractTermTemplateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contractTermTemplateService.createContractTermTemplate(contractTermTemplateDTO));
    }

    @Operation(summary = "Get contract term template by ID", description = "Returns a contract term template based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract term template",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractTermTemplateDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contract term template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{termTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractTermTemplateDTO>> getContractTermTemplateById(
            @Parameter(description = "ID of the contract term template to retrieve", required = true)
            @PathVariable UUID termTemplateId) {
        return ResponseEntity.ok(contractTermTemplateService.getContractTermTemplateById(termTemplateId));
    }

    @Operation(summary = "Update contract term template", description = "Updates an existing contract term template with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contract term template successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractTermTemplateDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract term template data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract term template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{termTemplateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractTermTemplateDTO>> updateContractTermTemplate(
            @Parameter(description = "ID of the contract term template to update", required = true)
            @PathVariable UUID termTemplateId,
            @Valid @RequestBody ContractTermTemplateDTO contractTermTemplateDTO) {
        return ResponseEntity.ok(contractTermTemplateService.updateContractTermTemplate(termTemplateId, contractTermTemplateDTO));
    }

    @Operation(summary = "Delete contract term template", description = "Deletes a contract term template based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contract term template successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract term template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{termTemplateId}")
    public Mono<ResponseEntity<Void>> deleteContractTermTemplate(
            @Parameter(description = "ID of the contract term template to delete", required = true)
            @PathVariable UUID termTemplateId) {
        return contractTermTemplateService.deleteContractTermTemplate(termTemplateId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
