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


package com.firefly.core.contracts.core.services.impl;

import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.filters.FilterUtils;
import org.fireflyframework.core.queries.PaginationResponse;
import com.firefly.core.contracts.core.mappers.ContractStatusHistoryMapper;
import com.firefly.core.contracts.core.services.ContractStatusHistoryService;
import com.firefly.core.contracts.interfaces.dtos.ContractStatusHistoryDTO;
import com.firefly.core.contracts.models.entities.ContractStatusHistory;
import com.firefly.core.contracts.models.repositories.ContractStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ContractStatusHistoryServiceImpl implements ContractStatusHistoryService {

    @Autowired
    private ContractStatusHistoryRepository repository;

    @Autowired
    private ContractStatusHistoryMapper mapper;

    @Override
    public Mono<PaginationResponse<ContractStatusHistoryDTO>> filterContractStatusHistory(FilterRequest<ContractStatusHistoryDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        ContractStatusHistory.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ContractStatusHistoryDTO> createContractStatusHistory(ContractStatusHistoryDTO contractStatusHistoryDTO) {
        return Mono.just(contractStatusHistoryDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractStatusHistoryDTO> updateContractStatusHistory(UUID contractStatusHistoryId, ContractStatusHistoryDTO contractStatusHistoryDTO) {
        return repository.findById(contractStatusHistoryId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract status history not found with ID: " + contractStatusHistoryId)))
                .flatMap(existingHistory -> {
                    ContractStatusHistory updatedHistory = mapper.toEntity(contractStatusHistoryDTO);
                    updatedHistory.setContractStatusHistoryId(contractStatusHistoryId);
                    return repository.save(updatedHistory);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContractStatusHistory(UUID contractStatusHistoryId) {
        return repository.findById(contractStatusHistoryId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract status history not found with ID: " + contractStatusHistoryId)))
                .flatMap(history -> repository.deleteById(contractStatusHistoryId));
    }

    @Override
    public Mono<ContractStatusHistoryDTO> getContractStatusHistoryById(UUID contractStatusHistoryId) {
        return repository.findById(contractStatusHistoryId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract status history not found with ID: " + contractStatusHistoryId)))
                .map(mapper::toDTO);
    }
}
