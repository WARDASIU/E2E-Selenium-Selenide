package org.example.tests.pos.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OwnerFormData {
    String firstName;
    String lastName;
    String address;
    String city;
    String telephone;
}
