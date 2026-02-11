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


package com.firefly.core.contracts.models.repositories;

import com.firefly.core.contracts.interfaces.enums.EventTypeEnum;
import com.firefly.core.contracts.models.entities.ContractEvent;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository interface for ContractEvent entity operations
 */
@Repository
public interface ContractEventRepository extends BaseRepository<ContractEvent, UUID> {

    /**
     * Find events by contract ID
     */
    Flux<ContractEvent> findByContractId(UUID contractId);

    /**
     * Find events by contract ID ordered by event date
     */
    Flux<ContractEvent> findByContractIdOrderByEventDateDesc(UUID contractId);

    /**
     * Find events by event type
     */
    Flux<ContractEvent> findByEventType(EventTypeEnum eventType);

    /**
     * Find events by contract ID and event type
     */
    Flux<ContractEvent> findByContractIdAndEventType(UUID contractId, EventTypeEnum eventType);

    /**
     * Find events within a date range
     */
    Flux<ContractEvent> findByEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate);

    /**
     * Find events by contract ID within a date range
     */
    @Query("SELECT * FROM contract_event WHERE contract_id = :contractId AND event_date BETWEEN :fromDate AND :toDate ORDER BY event_date DESC")
    Flux<ContractEvent> findByContractIdAndEventDateBetween(@Param("contractId") UUID contractId,
                                                            @Param("fromDate") LocalDateTime fromDate,
                                                            @Param("toDate") LocalDateTime toDate);

    /**
     * Find recent events for a contract
     */
    @Query("SELECT * FROM contract_event WHERE contract_id = :contractId ORDER BY event_date DESC LIMIT :limit")
    Flux<ContractEvent> findRecentEventsByContractId(@Param("contractId") UUID contractId, 
                                                     @Param("limit") Integer limit);

    /**
     * Find events after a specific date
     */
    Flux<ContractEvent> findByEventDateAfter(LocalDateTime eventDate);

    /**
     * Count events by contract ID
     */
    Mono<Long> countByContractId(Long contractId);

    /**
     * Count events by event type
     */
    Mono<Long> countByEventType(EventTypeEnum eventType);

    /**
     * Find latest event for a contract
     */
    @Query("SELECT * FROM contract_event WHERE contract_id = :contractId ORDER BY event_date DESC LIMIT 1")
    Mono<ContractEvent> findLatestEventByContractId(@Param("contractId") UUID contractId);
}
