/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.contracts.web.controllers;

import io.fireflysoftware.core.contracts.core.services.ContractLineService;
import io.fireflysoftware.core.contracts.interfaces.dtos.ContractLineDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contracts/{contractId}/lines")
@Tag(name = "Contract lines", description = "Products on an agreement. The contract product id follows the primary line.")
@RequiredArgsConstructor
public class ContractLineController {

    private final ContractLineService contractLineService;

    @PostMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Filter contract lines")
    public ResponseEntity<Mono<PaginationResponse<ContractLineDTO>>> filter(
            @PathVariable UUID contractId,
            @Valid @RequestBody FilterRequest<ContractLineDTO> filterRequest) {
        if (filterRequest.getFilters() == null) {
            filterRequest.setFilters(new ContractLineDTO());
        }
        filterRequest.getFilters().setContractId(contractId);
        return ResponseEntity.ok(contractLineService.filterContractLines(filterRequest));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a product line")
    public ResponseEntity<Mono<ContractLineDTO>> create(
            @PathVariable UUID contractId,
            @Valid @RequestBody ContractLineDTO dto) {
        dto.setContractId(contractId);
        return ResponseEntity.status(HttpStatus.CREATED).body(contractLineService.createContractLine(dto));
    }

    @GetMapping(value = "/{contractLineId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a contract line")
    public ResponseEntity<Mono<ContractLineDTO>> get(
            @PathVariable UUID contractId,
            @PathVariable UUID contractLineId) {
        return ResponseEntity.ok(contractLineService.getContractLineById(contractLineId)
                .filter(line -> contractId.equals(line.getContractId()))
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Contract line with ID " + contractLineId + " does not belong to contract " + contractId))));
    }

    @PutMapping(value = "/{contractLineId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Replace a contract line")
    public ResponseEntity<Mono<ContractLineDTO>> update(
            @PathVariable UUID contractId,
            @PathVariable UUID contractLineId,
            @Valid @RequestBody ContractLineDTO dto) {
        dto.setContractId(contractId);
        return ResponseEntity.ok(contractLineService.updateContractLine(contractLineId, dto));
    }

    @DeleteMapping("/{contractLineId}")
    @Operation(summary = "Remove a contract line")
    public ResponseEntity<Mono<Void>> delete(
            @PathVariable UUID contractId,
            @PathVariable UUID contractLineId) {
        return ResponseEntity.ok(contractLineService.getContractLineById(contractLineId)
                .filter(line -> contractId.equals(line.getContractId()))
                .switchIfEmpty(Mono.error(new RuntimeException(
                        "Contract line with ID " + contractLineId + " does not belong to contract " + contractId)))
                .then(contractLineService.deleteContractLine(contractLineId)));
    }
}
