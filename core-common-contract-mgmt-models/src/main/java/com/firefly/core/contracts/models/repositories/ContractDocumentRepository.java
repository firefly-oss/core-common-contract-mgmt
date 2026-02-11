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

import com.firefly.core.contracts.models.entities.ContractDocument;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Repository interface for ContractDocument entity operations
 */
@Repository
public interface ContractDocumentRepository extends BaseRepository<ContractDocument, UUID> {

    /**
     * Find documents by contract ID
     */
    Flux<ContractDocument> findByContractId(UUID contractId);

    /**
     * Find documents by document type ID
     */
    Flux<ContractDocument> findByDocumentTypeId(UUID documentTypeId);

    /**
     * Find documents by contract ID and document type ID
     */
    Flux<ContractDocument> findByContractIdAndDocumentTypeId(UUID contractId, UUID documentTypeId);

    /**
     * Find document by document ID
     */
    Mono<ContractDocument> findByDocumentId(UUID documentId);

    /**
     * Find documents added after a specific date
     */
    Flux<ContractDocument> findByDateAddedAfter(LocalDateTime dateAdded);

    /**
     * Find documents added within a date range
     */
    @Query("SELECT * FROM contract_document WHERE date_added BETWEEN :fromDate AND :toDate")
    Flux<ContractDocument> findByDateAddedBetween(@Param("fromDate") LocalDateTime fromDate, 
                                                  @Param("toDate") LocalDateTime toDate);

    /**
     * Count documents by contract ID
     */
    Mono<Long> countByContractId(UUID contractId);

    /**
     * Count documents by document type ID
     */
    Mono<Long> countByDocumentTypeId(UUID documentTypeId);

    /**
     * Find latest documents for a contract
     */
    @Query("SELECT * FROM contract_document WHERE contract_id = :contractId ORDER BY date_added DESC LIMIT :limit")
    Flux<ContractDocument> findLatestByContractId(@Param("contractId") UUID contractId, 
                                                  @Param("limit") Integer limit);
}
