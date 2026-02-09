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
import com.firefly.core.contracts.interfaces.dtos.ContractDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing contracts.
 */
public interface ContractService {
    /**
     * Filters the contracts based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for ContractDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of contracts
     */
    Mono<PaginationResponse<ContractDTO>> filterContracts(FilterRequest<ContractDTO> filterRequest);
    
    /**
     * Creates a new contract based on the provided information.
     *
     * @param contractDTO the DTO object containing details of the contract to be created
     * @return a Mono that emits the created ContractDTO object
     */
    Mono<ContractDTO> createContract(ContractDTO contractDTO);
    
    /**
     * Updates an existing contract with updated information.
     *
     * @param contractId the unique identifier of the contract to be updated
     * @param contractDTO the data transfer object containing the updated details of the contract
     * @return a reactive Mono containing the updated ContractDTO
     */
    Mono<ContractDTO> updateContract(UUID contractId, ContractDTO contractDTO);
    
    /**
     * Deletes a contract identified by its unique ID.
     *
     * @param contractId the unique identifier of the contract to be deleted
     * @return a Mono that completes when the contract is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteContract(UUID contractId);
    
    /**
     * Retrieves a contract by its unique identifier.
     *
     * @param contractId the unique identifier of the contract to retrieve
     * @return a Mono emitting the {@link ContractDTO} representing the contract if found,
     *         or an empty Mono if the contract does not exist
     */
    Mono<ContractDTO> getContractById(UUID contractId);
}
