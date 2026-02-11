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

import com.firefly.core.contracts.interfaces.dtos.ContractTermDynamicDTO;
import com.firefly.core.contracts.models.entities.ContractTermDynamic;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for ContractTermDynamic entity and ContractTermDynamicDTO
 */
@Mapper(componentModel = "spring")
public interface ContractTermDynamicMapper {

    /**
     * Convert ContractTermDynamic entity to ContractTermDynamicDTO
     *
     * @param contractTermDynamic the ContractTermDynamic entity
     * @return the ContractTermDynamicDTO
     */
    ContractTermDynamicDTO toDTO(ContractTermDynamic contractTermDynamic);

    /**
     * Convert ContractTermDynamicDTO to ContractTermDynamic entity
     *
     * @param contractTermDynamicDTO the ContractTermDynamicDTO
     * @return the ContractTermDynamic entity
     */
    ContractTermDynamic toEntity(ContractTermDynamicDTO contractTermDynamicDTO);
}
