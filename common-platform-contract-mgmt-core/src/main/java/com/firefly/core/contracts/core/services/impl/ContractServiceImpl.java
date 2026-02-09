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
import com.firefly.core.contracts.core.mappers.ContractMapper;
import com.firefly.core.contracts.core.services.ContractService;
import com.firefly.core.contracts.interfaces.dtos.ContractDTO;
import com.firefly.core.contracts.models.entities.Contract;
import com.firefly.core.contracts.models.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository repository;

    @Autowired
    private ContractMapper mapper;

    @Override
    public Mono<PaginationResponse<ContractDTO>> filterContracts(FilterRequest<ContractDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        Contract.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ContractDTO> createContract(ContractDTO contractDTO) {
        return Mono.just(contractDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractDTO> updateContract(UUID contractId, ContractDTO contractDTO) {
        return repository.findById(contractId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract not found with ID: " + contractId)))
                .flatMap(existingContract -> {
                    Contract updatedContract = mapper.toEntity(contractDTO);
                    updatedContract.setContractId(contractId);
                    return repository.save(updatedContract);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContract(UUID contractId) {
        return repository.findById(contractId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract not found with ID: " + contractId)))
                .flatMap(contract -> repository.deleteById(contractId));
    }

    @Override
    public Mono<ContractDTO> getContractById(UUID contractId) {
        return repository.findById(contractId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract not found with ID: " + contractId)))
                .map(mapper::toDTO);
    }
}
