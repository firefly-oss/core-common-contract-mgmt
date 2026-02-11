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

import com.firefly.core.contracts.interfaces.enums.ContractStatusEnum;
import com.firefly.core.contracts.models.entities.Contract;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository interface for Contract entity operations
 */
@Repository
public interface ContractRepository extends BaseRepository<Contract, UUID> {

    /**
     * Find contracts by status
     */
    Flux<Contract> findByContractStatus(ContractStatusEnum contractStatus);

    /**
     * Find contracts by contract number
     */
    Mono<Contract> findByContractNumber(String contractNumber);

    /**
     * Find contracts by date range
     */
    @Query("SELECT * FROM contract WHERE start_date >= :startDate AND end_date <= :endDate")
    Flux<Contract> findByDateRange(@Param("startDate") LocalDateTime startDate, 
                                   @Param("endDate") LocalDateTime endDate);

    /**
     * Find active contracts (status = ACTIVE)
     */
    @Query("SELECT * FROM contract WHERE contract_status = 'ACTIVE'")
    Flux<Contract> findActiveContracts();

    /**
     * Find contracts expiring within a date range
     */
    @Query("SELECT * FROM contract WHERE end_date BETWEEN :fromDate AND :toDate")
    Flux<Contract> findContractsExpiringBetween(@Param("fromDate") LocalDateTime fromDate, 
                                                @Param("toDate") LocalDateTime toDate);

    /**
     * Count contracts by status
     */
    Mono<Long> countByContractStatus(ContractStatusEnum contractStatus);
}
