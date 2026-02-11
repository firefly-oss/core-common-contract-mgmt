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
import com.firefly.core.contracts.core.services.ContractRiskAssessmentService;
import com.firefly.core.contracts.interfaces.dtos.ContractRiskAssessmentDTO;
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
@RequestMapping("/api/v1/contracts/{contractId}/risk-assessments")
@Tag(name = "Contract Risk Assessments", description = "API for managing contract risk assessments")
@RequiredArgsConstructor
public class ContractRiskAssessmentController {

    private final ContractRiskAssessmentService contractRiskAssessmentService;

    @Operation(summary = "Filter contract risk assessments", description = "Returns a paginated list of contract risk assessments based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract risk assessments",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<ContractRiskAssessmentDTO>>> filterContractRiskAssessments(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Valid @RequestBody FilterRequest<ContractRiskAssessmentDTO> filterRequest) {
        return ResponseEntity.ok(contractRiskAssessmentService.filterContractRiskAssessments(filterRequest));
    }

    @Operation(summary = "Create a new contract risk assessment", description = "Creates a new contract risk assessment with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contract risk assessment successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractRiskAssessmentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract risk assessment data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractRiskAssessmentDTO>> createContractRiskAssessment(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Valid @RequestBody ContractRiskAssessmentDTO contractRiskAssessmentDTO) {
        // Ensure the contractId in the path is used
        contractRiskAssessmentDTO.setContractId(contractId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contractRiskAssessmentService.createContractRiskAssessment(contractRiskAssessmentDTO));
    }

    @Operation(summary = "Get contract risk assessment by ID", description = "Returns a contract risk assessment based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract risk assessment",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractRiskAssessmentDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contract risk assessment not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{contractRiskAssessmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractRiskAssessmentDTO>> getContractRiskAssessmentById(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract risk assessment to retrieve", required = true)
            @PathVariable UUID contractRiskAssessmentId) {
        return ResponseEntity.ok(contractRiskAssessmentService.getContractRiskAssessmentById(contractRiskAssessmentId)
                .filter(assessment -> assessment.getContractId().equals(contractId)));
    }

    @Operation(summary = "Update contract risk assessment", description = "Updates an existing contract risk assessment with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contract risk assessment successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractRiskAssessmentDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract risk assessment data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract risk assessment not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{contractRiskAssessmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractRiskAssessmentDTO>> updateContractRiskAssessment(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract risk assessment to update", required = true)
            @PathVariable UUID contractRiskAssessmentId,
            @Valid @RequestBody ContractRiskAssessmentDTO contractRiskAssessmentDTO) {
        // Ensure the contractId in the path is used
        contractRiskAssessmentDTO.setContractId(contractId);
        return ResponseEntity.ok(contractRiskAssessmentService.updateContractRiskAssessment(contractRiskAssessmentId, contractRiskAssessmentDTO));
    }

    @Operation(summary = "Delete contract risk assessment", description = "Deletes a contract risk assessment based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contract risk assessment successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract risk assessment not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{contractRiskAssessmentId}")
    public Mono<ResponseEntity<Void>> deleteContractRiskAssessment(
            @Parameter(description = "ID of the contract", required = true)
            @PathVariable UUID contractId,
            @Parameter(description = "ID of the contract risk assessment to delete", required = true)
            @PathVariable UUID contractRiskAssessmentId) {
        return contractRiskAssessmentService.getContractRiskAssessmentById(contractRiskAssessmentId)
                .filter(assessment -> assessment.getContractId().equals(contractId))
                .flatMap(assessment -> contractRiskAssessmentService.deleteContractRiskAssessment(contractRiskAssessmentId))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
