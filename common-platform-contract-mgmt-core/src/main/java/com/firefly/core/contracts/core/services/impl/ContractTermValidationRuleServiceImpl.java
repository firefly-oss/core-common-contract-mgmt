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
import com.firefly.core.contracts.core.mappers.ContractTermValidationRuleMapper;
import com.firefly.core.contracts.core.services.ContractTermValidationRuleService;
import com.firefly.core.contracts.interfaces.dtos.ContractTermValidationRuleDTO;
import com.firefly.core.contracts.models.entities.ContractTermValidationRule;
import com.firefly.core.contracts.models.repositories.ContractTermValidationRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import java.util.UUID;

@Service
@Transactional
public class ContractTermValidationRuleServiceImpl implements ContractTermValidationRuleService {

    @Autowired
    private ContractTermValidationRuleRepository repository;

    @Autowired
    private ContractTermValidationRuleMapper mapper;

    @Override
    public Mono<PaginationResponse<ContractTermValidationRuleDTO>> filterContractTermValidationRules(FilterRequest<ContractTermValidationRuleDTO> filterRequest) {
        return FilterUtils
                .createFilter(
                        ContractTermValidationRule.class,
                        mapper::toDTO
                )
                .filter(filterRequest);
    }

    @Override
    public Mono<ContractTermValidationRuleDTO> createContractTermValidationRule(ContractTermValidationRuleDTO contractTermValidationRuleDTO) {
        return Mono.just(contractTermValidationRuleDTO)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDTO);
    }

    @Override
    public Mono<ContractTermValidationRuleDTO> updateContractTermValidationRule(UUID validationRuleId, ContractTermValidationRuleDTO contractTermValidationRuleDTO) {
        return repository.findById(validationRuleId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term validation rule not found with ID: " + validationRuleId)))
                .flatMap(existingRule -> {
                    ContractTermValidationRule updatedRule = mapper.toEntity(contractTermValidationRuleDTO);
                    updatedRule.setValidationRuleId(validationRuleId);
                    return repository.save(updatedRule);
                })
                .map(mapper::toDTO);
    }

    @Override
    public Mono<Void> deleteContractTermValidationRule(UUID validationRuleId) {
        return repository.findById(validationRuleId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term validation rule not found with ID: " + validationRuleId)))
                .flatMap(rule -> repository.deleteById(validationRuleId));
    }

    @Override
    public Mono<ContractTermValidationRuleDTO> getContractTermValidationRuleById(UUID validationRuleId) {
        return repository.findById(validationRuleId)
                .switchIfEmpty(Mono.error(new RuntimeException("Contract term validation rule not found with ID: " + validationRuleId)))
                .map(mapper::toDTO);
    }
}
