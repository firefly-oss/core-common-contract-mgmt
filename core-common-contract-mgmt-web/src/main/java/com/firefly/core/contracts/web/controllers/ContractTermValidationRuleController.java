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
import com.firefly.core.contracts.core.services.ContractTermValidationRuleService;
import com.firefly.core.contracts.interfaces.dtos.ContractTermValidationRuleDTO;
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
@RequestMapping("/api/v1/contract-term-templates/{termTemplateId}/validation-rules")
@Tag(name = "Contract Term Validation Rules", description = "API for managing contract term validation rules")
@RequiredArgsConstructor
public class ContractTermValidationRuleController {

    private final ContractTermValidationRuleService contractTermValidationRuleService;

    @Operation(summary = "Filter contract term validation rules", description = "Returns a paginated list of contract term validation rules based on filter criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract term validation rules",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = PaginationResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid filter criteria provided", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<PaginationResponse<ContractTermValidationRuleDTO>>> filterContractTermValidationRules(
            @Parameter(description = "ID of the term template", required = true)
            @PathVariable UUID termTemplateId,
            @Valid @RequestBody FilterRequest<ContractTermValidationRuleDTO> filterRequest) {
        return ResponseEntity.ok(contractTermValidationRuleService.filterContractTermValidationRules(filterRequest));
    }

    @Operation(summary = "Create a new contract term validation rule", description = "Creates a new contract term validation rule with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Contract term validation rule successfully created",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractTermValidationRuleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract term validation rule data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Term template not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractTermValidationRuleDTO>> createContractTermValidationRule(
            @Parameter(description = "ID of the term template", required = true)
            @PathVariable UUID termTemplateId,
            @Valid @RequestBody ContractTermValidationRuleDTO contractTermValidationRuleDTO) {
        // Ensure the termTemplateId in the path is used
        contractTermValidationRuleDTO.setTermTemplateId(termTemplateId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contractTermValidationRuleService.createContractTermValidationRule(contractTermValidationRuleDTO));
    }

    @Operation(summary = "Get contract term validation rule by ID", description = "Returns a contract term validation rule based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved contract term validation rule",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractTermValidationRuleDTO.class))),
        @ApiResponse(responseCode = "404", description = "Contract term validation rule not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @GetMapping(value = "/{validationRuleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractTermValidationRuleDTO>> getContractTermValidationRuleById(
            @Parameter(description = "ID of the term template", required = true)
            @PathVariable UUID termTemplateId,
            @Parameter(description = "ID of the contract term validation rule to retrieve", required = true)
            @PathVariable UUID validationRuleId) {
        return ResponseEntity.ok(contractTermValidationRuleService.getContractTermValidationRuleById(validationRuleId)
                .filter(rule -> rule.getTermTemplateId().equals(termTemplateId)));
    }

    @Operation(summary = "Update contract term validation rule", description = "Updates an existing contract term validation rule with the provided information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Contract term validation rule successfully updated",
                content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ContractTermValidationRuleDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid contract term validation rule data provided", 
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract term validation rule not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @PutMapping(value = "/{validationRuleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mono<ContractTermValidationRuleDTO>> updateContractTermValidationRule(
            @Parameter(description = "ID of the term template", required = true)
            @PathVariable UUID termTemplateId,
            @Parameter(description = "ID of the contract term validation rule to update", required = true)
            @PathVariable UUID validationRuleId,
            @Valid @RequestBody ContractTermValidationRuleDTO contractTermValidationRuleDTO) {
        // Ensure the termTemplateId in the path is used
        contractTermValidationRuleDTO.setTermTemplateId(termTemplateId);
        return ResponseEntity.ok(contractTermValidationRuleService.updateContractTermValidationRule(validationRuleId, contractTermValidationRuleDTO));
    }

    @Operation(summary = "Delete contract term validation rule", description = "Deletes a contract term validation rule based on its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Contract term validation rule successfully deleted",
                content = @Content),
        @ApiResponse(responseCode = "404", description = "Contract term validation rule not found", 
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                content = @Content)
    })
    @DeleteMapping("/{validationRuleId}")
    public Mono<ResponseEntity<Void>> deleteContractTermValidationRule(
            @Parameter(description = "ID of the term template", required = true)
            @PathVariable UUID termTemplateId,
            @Parameter(description = "ID of the contract term validation rule to delete", required = true)
            @PathVariable UUID validationRuleId) {
        return contractTermValidationRuleService.getContractTermValidationRuleById(validationRuleId)
                .filter(rule -> rule.getTermTemplateId().equals(termTemplateId))
                .flatMap(rule -> contractTermValidationRuleService.deleteContractTermValidationRule(validationRuleId))
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
