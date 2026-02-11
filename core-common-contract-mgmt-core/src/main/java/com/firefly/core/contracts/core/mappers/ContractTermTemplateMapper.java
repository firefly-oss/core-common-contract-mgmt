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


package com.firefly.core.contracts.core.mappers;

import com.firefly.core.contracts.interfaces.dtos.ContractTermTemplateDTO;
import com.firefly.core.contracts.models.entities.ContractTermTemplate;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for ContractTermTemplate entity and ContractTermTemplateDTO
 */
@Mapper(componentModel = "spring")
public interface ContractTermTemplateMapper {

    /**
     * Convert ContractTermTemplate entity to ContractTermTemplateDTO
     *
     * @param contractTermTemplate the ContractTermTemplate entity
     * @return the ContractTermTemplateDTO
     */
    ContractTermTemplateDTO toDTO(ContractTermTemplate contractTermTemplate);

    /**
     * Convert ContractTermTemplateDTO to ContractTermTemplate entity
     *
     * @param contractTermTemplateDTO the ContractTermTemplateDTO
     * @return the ContractTermTemplate entity
     */
    ContractTermTemplate toEntity(ContractTermTemplateDTO contractTermTemplateDTO);
}
