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

import com.firefly.core.contracts.models.entities.ContractParty;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;

/**
 * Repository interface for ContractParty entity operations
 */
@Repository
public interface ContractPartyRepository extends BaseRepository<ContractParty, UUID> {

    /**
     * Find contract parties by contract ID
     */
    Flux<ContractParty> findByContractId(UUID contractId);

    /**
     * Find contract parties by party ID
     */
    Flux<ContractParty> findByPartyId(UUID partyId);

    /**
     * Find active contract parties by contract ID
     */
    Flux<ContractParty> findByContractIdAndIsActive(UUID contractId, Boolean isActive);

    /**
     * Find active contract parties by party ID
     */
    Flux<ContractParty> findByPartyIdAndIsActive(UUID partyId, Boolean isActive);

    /**
     * Find contract parties by role
     */
    Flux<ContractParty> findByRoleInContractId(UUID roleInContractId);

    /**
     * Find contract party by contract and party ID
     */
    Mono<ContractParty> findByContractIdAndPartyId(UUID contractId, UUID partyId);

    /**
     * Count active parties for a contract
     */
    @Query("SELECT COUNT(*) FROM contract_party WHERE contract_id = :contractId AND is_active = true")
    Mono<Long> countActivePartiesByContractId(@Param("contractId") Long contractId);

    /**
     * Count contracts for a party
     */
    Mono<Long> countByPartyIdAndIsActive(Long partyId, Boolean isActive);
}
