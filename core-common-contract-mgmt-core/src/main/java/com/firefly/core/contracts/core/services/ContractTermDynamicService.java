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
import com.firefly.core.contracts.interfaces.dtos.ContractTermDynamicDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing contract term dynamics.
 */
public interface ContractTermDynamicService {
    /**
     * Filters the contract term dynamics based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for ContractTermDynamicDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of contract term dynamics
     */
    Mono<PaginationResponse<ContractTermDynamicDTO>> filterContractTermDynamics(FilterRequest<ContractTermDynamicDTO> filterRequest);
    
    /**
     * Creates a new contract term dynamic based on the provided information.
     *
     * @param contractTermDynamicDTO the DTO object containing details of the contract term dynamic to be created
     * @return a Mono that emits the created ContractTermDynamicDTO object
     */
    Mono<ContractTermDynamicDTO> createContractTermDynamic(ContractTermDynamicDTO contractTermDynamicDTO);
    
    /**
     * Updates an existing contract term dynamic with updated information.
     *
     * @param termId the unique identifier of the contract term dynamic to be updated
     * @param contractTermDynamicDTO the data transfer object containing the updated details of the contract term dynamic
     * @return a reactive Mono containing the updated ContractTermDynamicDTO
     */
    Mono<ContractTermDynamicDTO> updateContractTermDynamic(UUID termId, ContractTermDynamicDTO contractTermDynamicDTO);
    
    /**
     * Deletes a contract term dynamic identified by its unique ID.
     *
     * @param termId the unique identifier of the contract term dynamic to be deleted
     * @return a Mono that completes when the contract term dynamic is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteContractTermDynamic(UUID termId);
    
    /**
     * Retrieves a contract term dynamic by its unique identifier.
     *
     * @param termId the unique identifier of the contract term dynamic to retrieve
     * @return a Mono emitting the {@link ContractTermDynamicDTO} representing the contract term dynamic if found,
     *         or an empty Mono if the contract term dynamic does not exist
     */
    Mono<ContractTermDynamicDTO> getContractTermDynamicById(UUID termId);
}
