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
import com.firefly.core.contracts.interfaces.dtos.ContractEventDTO;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Service interface for managing contract events.
 */
public interface ContractEventService {
    /**
     * Filters the contract events based on the given criteria.
     *
     * @param filterRequest the request object containing filtering criteria for ContractEventDTO
     * @return a reactive {@code Mono} emitting a {@code PaginationResponse} containing the filtered list of contract events
     */
    Mono<PaginationResponse<ContractEventDTO>> filterContractEvents(FilterRequest<ContractEventDTO> filterRequest);
    
    /**
     * Creates a new contract event based on the provided information.
     *
     * @param contractEventDTO the DTO object containing details of the contract event to be created
     * @return a Mono that emits the created ContractEventDTO object
     */
    Mono<ContractEventDTO> createContractEvent(ContractEventDTO contractEventDTO);
    
    /**
     * Updates an existing contract event with updated information.
     *
     * @param contractEventId the unique identifier of the contract event to be updated
     * @param contractEventDTO the data transfer object containing the updated details of the contract event
     * @return a reactive Mono containing the updated ContractEventDTO
     */
    Mono<ContractEventDTO> updateContractEvent(UUID contractEventId, ContractEventDTO contractEventDTO);
    
    /**
     * Deletes a contract event identified by its unique ID.
     *
     * @param contractEventId the unique identifier of the contract event to be deleted
     * @return a Mono that completes when the contract event is successfully deleted or errors if the deletion fails
     */
    Mono<Void> deleteContractEvent(UUID contractEventId);
    
    /**
     * Retrieves a contract event by its unique identifier.
     *
     * @param contractEventId the unique identifier of the contract event to retrieve
     * @return a Mono emitting the {@link ContractEventDTO} representing the contract event if found,
     *         or an empty Mono if the contract event does not exist
     */
    Mono<ContractEventDTO> getContractEventById(UUID contractEventId);
}
