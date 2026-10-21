package io.fireflysoftware.core.contracts.core.services.impl;

import io.fireflysoftware.core.contracts.core.mappers.ContractTermDynamicMapper;
import io.fireflysoftware.core.contracts.interfaces.dtos.ContractTermDynamicDTO;
import io.fireflysoftware.core.contracts.models.entities.ContractTermDynamic;
import io.fireflysoftware.core.contracts.models.repositories.ContractTermDynamicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContractTermDynamicServiceImplTest {

    @Mock
    private ContractTermDynamicRepository repository;

    @Mock
    private ContractTermDynamicMapper mapper;

    @InjectMocks
    private ContractTermDynamicServiceImpl service;

    private final UUID contractId = UUID.fromString("123e4567-e89b-12d3-a456-426614174020");
    private final UUID templateId = UUID.fromString("123e4567-e89b-12d3-a456-426614174021");

    @Test
    void create_rejectsAnOverlappingActiveTerm() {
        ContractTermDynamic incoming = ContractTermDynamic.builder()
                .contractId(contractId)
                .termTemplateId(templateId)
                .isActive(true)
                .effectiveDate(LocalDateTime.of(2026, 1, 1, 0, 0))
                .build();
        ContractTermDynamic current = ContractTermDynamic.builder()
                .termId(UUID.randomUUID())
                .contractId(contractId)
                .termTemplateId(templateId)
                .isActive(true)
                .effectiveDate(LocalDateTime.of(2025, 1, 1, 0, 0))
                .build();
        when(mapper.toEntity(org.mockito.ArgumentMatchers.any(ContractTermDynamicDTO.class))).thenReturn(incoming);
        when(repository.findByContractIdAndTermTemplateIdAndIsActive(contractId, templateId, true))
                .thenReturn(Flux.just(current));

        StepVerifier.create(service.createContractTermDynamic(new ContractTermDynamicDTO()))
                .expectErrorMatches(error -> error.getMessage().contains("expire the current term"))
                .verify();

        verify(repository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void create_allowsATermThatStartsWhenThePreviousOneExpires() {
        LocalDateTime handoff = LocalDateTime.of(2026, 6, 1, 0, 0);
        ContractTermDynamic incoming = ContractTermDynamic.builder()
                .contractId(contractId)
                .termTemplateId(templateId)
                .isActive(true)
                .effectiveDate(handoff)
                .build();
        ContractTermDynamic current = ContractTermDynamic.builder()
                .termId(UUID.randomUUID())
                .contractId(contractId)
                .termTemplateId(templateId)
                .isActive(true)
                .effectiveDate(LocalDateTime.of(2025, 1, 1, 0, 0))
                .expirationDate(handoff)
                .build();
        ContractTermDynamicDTO response = new ContractTermDynamicDTO();
        when(mapper.toEntity(org.mockito.ArgumentMatchers.any(ContractTermDynamicDTO.class))).thenReturn(incoming);
        when(repository.findByContractIdAndTermTemplateIdAndIsActive(contractId, templateId, true))
                .thenReturn(Flux.just(current));
        when(repository.save(incoming)).thenReturn(Mono.just(incoming));
        when(mapper.toDTO(incoming)).thenReturn(response);

        StepVerifier.create(service.createContractTermDynamic(new ContractTermDynamicDTO()))
                .expectNext(response)
                .verifyComplete();
    }
}
