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


package com.firefly.core.contracts.core.services;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.contracts.interfaces.dtos.ContractDocumentDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing contract documents.
 */
public interface ContractDocumentService {
    /**
     * Filters the contract documents based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for ContractDocumentDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of contract documents
     */
    Mono<PaginationResponse<ContractDocumentDTO>> filterContractDocuments(FilterRequest<ContractDocumentDTO> filterRequest);
    
    /**
     * Creates a new contract document based on the provided information.
     *
     * @param contractDocumentDTO the DTO object containing details of the contract document to be created
     * @return a Mono that emits the created ContractDocumentDTO object
     */
    Mono<ContractDocumentDTO> createContractDocument(ContractDocumentDTO contractDocumentDTO);
    
    /**
     * Updates an existing contract document with updated information.
     *
     * @param contractDocumentId the unique identifier of the contract document to be updated
     * @param contractDocumentDTO the data transfer object containing the updated details of the contract document
     * @return a reactive Mono containing the updated ContractDocumentDTO
     */
    Mono<ContractDocumentDTO> updateContractDocument(UUID contractDocumentId, ContractDocumentDTO contractDocumentDTO);
    
    /**
     * Deletes a contract document identified by its unique ID.
     *
     * @param contractDocumentId the unique identifier of the contract document to be deleted
     * @return a Mono that completes when the contract document is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteContractDocument(UUID contractDocumentId);
    
    /**
     * Retrieves a contract document by its unique identifier.
     *
     * @param contractDocumentId the unique identifier of the contract document to retrieve
     * @return a Mono emitting the {@link ContractDocumentDTO} representing the contract document if found,
     *         or an empty Mono if the contract document does not exist
     */
    Mono<ContractDocumentDTO> getContractDocumentById(UUID contractDocumentId);
}
