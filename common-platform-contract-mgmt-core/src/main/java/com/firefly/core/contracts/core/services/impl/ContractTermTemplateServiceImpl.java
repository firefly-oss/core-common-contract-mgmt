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
import com.firefly.core.contracts.core.mappers.ContractTermTemplateMapper;
import com.firefly.core.contracts.core.services.ContractTermTemplateService;
import com.firefly.core.contracts.interfaces.dtos.ContractTermTemplateDTO;
import com.firefly.core.contracts.models.entities.ContractTermTemplate;
import com.firefly.core.contracts.models.repositories.ContractTermTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ContractTermTemplateServiceImpl implements ContractTermTemplateService {

    @Autowired
    private ContractTermTemplateRepository repository;

    @Autowired
    private ContractTermTemplateMapper mapper;

    @Override
    public Mono<PaginationResponse<ContractTermTemplateDTO>> filterContractTermTemplates(FilterRequest<ContractTermTemplateDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        ContractTermTemplate.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ContractTermTemplateDTO> createContractTermTemplate(ContractTermTemplateDTO contractTermTemplateDTO) {
        return Mono.just(contractTermTemplateDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractTermTemplateDTO> updateContractTermTemplate(UUID termTemplateId, ContractTermTemplateDTO contractTermTemplateDTO) {
        return repository.findById(termTemplateId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term template not found with ID: " + termTemplateId)))
                .flatMap(existingTemplate -> {
                    ContractTermTemplate updatedTemplate = mapper.toEntity(contractTermTemplateDTO);
                    updatedTemplate.setTermTemplateId(termTemplateId);
                    return repository.save(updatedTemplate);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContractTermTemplate(UUID termTemplateId) {
        return repository.findById(termTemplateId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term template not found with ID: " + termTemplateId)))
                .flatMap(template -> repository.deleteById(termTemplateId));
    }

    @Override
    public Mono<ContractTermTemplateDTO> getContractTermTemplateById(UUID termTemplateId) {
        return repository.findById(termTemplateId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term template not found with ID: " + termTemplateId)))
                .map(mapper::toDTO);
    }
}
