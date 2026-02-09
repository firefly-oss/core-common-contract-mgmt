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
import com.firefly.core.contracts.interfaces.dtos.ContractStatusHistoryDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing contract status history.
 */
public interface ContractStatusHistoryService {
    /**
     * Filters the contract status history based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for ContractStatusHistoryDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of contract status history
     */
    Mono<PaginationResponse<ContractStatusHistoryDTO>> filterContractStatusHistory(FilterRequest<ContractStatusHistoryDTO> filterRequest);
    
    /**
     * Creates a new contract status history based on the provided information.
     *
     * @param contractStatusHistoryDTO the DTO object containing details of the contract status history to be created
     * @return a Mono that emits the created ContractStatusHistoryDTO object
     */
    Mono<ContractStatusHistoryDTO> createContractStatusHistory(ContractStatusHistoryDTO contractStatusHistoryDTO);
    
    /**
     * Updates an existing contract status history with updated information.
     *
     * @param contractStatusHistoryId the unique identifier of the contract status history to be updated
     * @param contractStatusHistoryDTO the data transfer object containing the updated details of the contract status history
     * @return a reactive Mono containing the updated ContractStatusHistoryDTO
     */
    Mono<ContractStatusHistoryDTO> updateContractStatusHistory(UUID contractStatusHistoryId, ContractStatusHistoryDTO contractStatusHistoryDTO);
    
    /**
     * Deletes a contract status history identified by its unique ID.
     *
     * @param contractStatusHistoryId the unique identifier of the contract status history to be deleted
     * @return a Mono that completes when the contract status history is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteContractStatusHistory(UUID contractStatusHistoryId);
    
    /**
     * Retrieves a contract status history by its unique identifier.
     *
     * @param contractStatusHistoryId the unique identifier of the contract status history to retrieve
     * @return a Mono emitting the {@link ContractStatusHistoryDTO} representing the contract status history if found,
     *         or an empty Mono if the contract status history does not exist
     */
    Mono<ContractStatusHistoryDTO> getContractStatusHistoryById(UUID contractStatusHistoryId);
}
