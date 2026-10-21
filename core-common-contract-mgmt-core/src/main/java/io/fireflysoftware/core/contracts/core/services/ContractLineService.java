/*
 * Copyright 2025 Firefly Software Foundation
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


package io.fireflysoftware.core.contracts.core.services;

import io.fireflysoftware.core.contracts.interfaces.dtos.ContractLineDTO;
import org.fireflyframework.core.filters.FilterRequest;
import org.fireflyframework.core.queries.PaginationResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ContractLineService {

    Mono<PaginationResponse<ContractLineDTO>> filterContractLines(FilterRequest<ContractLineDTO> filterRequest);

    Mono<ContractLineDTO> createContractLine(ContractLineDTO dto);

    Mono<ContractLineDTO> updateContractLine(UUID contractLineId, ContractLineDTO dto);

    Mono<Void> deleteContractLine(UUID contractLineId);

    Mono<ContractLineDTO> getContractLineById(UUID contractLineId);
}
