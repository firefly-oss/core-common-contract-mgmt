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


package com.firefly.core.contracts.interfaces.enums;

/**
 * Event type enumeration matching the database enum event_type_enum
 */
public enum EventTypeEnum {
    CONTRACT_CREATED,
    CONTRACT_SIGNED,
    CONTRACT_ACTIVATED,
    CONTRACT_AMENDED,
    CONTRACT_RENEWED,
    CONTRACT_SUSPENDED,
    CONTRACT_TERMINATED,
    CONTRACT_EXPIRED,
    MILESTONE_REACHED,
    PAYMENT_DUE,
    PAYMENT_RECEIVED,
    BREACH_REPORTED,
    DISPUTE_RAISED,
    AUDIT_COMPLETED
}
