package org.example.tests.pos.page_objects.owner.add_owner.dataprovider;

import org.example.tests.pos.model.OwnerFormData;
import org.testng.annotations.DataProvider;

public class AddOwnerDataprovider {
    @DataProvider(name = "ownerFormData")
    public static Object[][] ownerFormData() {
        return new Object[][] {
                { OwnerFormData.builder()
                        .firstName("Jan")
                        .lastName("Kowalski")
                        .address("21 Jump Street")
                        .city("Zawoja")
                        .telephone("1231231231")
                        .build() },
                { OwnerFormData.builder()
                        .firstName("Anna")
                        .lastName("Nowak")
                        .address("10 Test Avenue")
                        .city("Krakow")
                        .telephone("3213213213")
                        .build() }
        };
    }
}
