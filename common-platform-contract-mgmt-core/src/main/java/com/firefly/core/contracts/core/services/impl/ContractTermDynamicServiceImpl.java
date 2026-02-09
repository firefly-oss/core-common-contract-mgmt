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
import com.firefly.core.contracts.core.mappers.ContractTermDynamicMapper;
import com.firefly.core.contracts.core.services.ContractTermDynamicService;
import com.firefly.core.contracts.interfaces.dtos.ContractTermDynamicDTO;
import com.firefly.core.contracts.models.entities.ContractTermDynamic;
import com.firefly.core.contracts.models.repositories.ContractTermDynamicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ContractTermDynamicServiceImpl implements ContractTermDynamicService {

    @Autowired
    private ContractTermDynamicRepository repository;

    @Autowired
    private ContractTermDynamicMapper mapper;

    @Override
    public Mono<PaginationResponse<ContractTermDynamicDTO>> filterContractTermDynamics(FilterRequest<ContractTermDynamicDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        ContractTermDynamic.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ContractTermDynamicDTO> createContractTermDynamic(ContractTermDynamicDTO contractTermDynamicDTO) {
        return Mono.just(contractTermDynamicDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractTermDynamicDTO> updateContractTermDynamic(UUID termId, ContractTermDynamicDTO contractTermDynamicDTO) {
        return repository.findById(termId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term dynamic not found with ID: " + termId)))
                .flatMap(existingTerm -> {
                    ContractTermDynamic updatedTerm = mapper.toEntity(contractTermDynamicDTO);
                    updatedTerm.setTermId(termId);
                    return repository.save(updatedTerm);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContractTermDynamic(UUID termId) {
        return repository.findById(termId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term dynamic not found with ID: " + termId)))
                .flatMap(term -> repository.deleteById(termId));
    }

    @Override
    public Mono<ContractTermDynamicDTO> getContractTermDynamicById(UUID termId) {
        return repository.findById(termId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term dynamic not found with ID: " + termId)))
                .map(mapper::toDTO);
    }
}
