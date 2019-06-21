package net.bakaar.sandbox.infra.data.rest;

import net.bakaar.sandbox.domain.number.BusinessNumberRepository;
import net.bakaar.sandbox.domain.shared.AddressNumber;
import net.bakaar.sandbox.shared.domain.vo.PNumber;

import java.util.Random;

public class BusinessNumberFakeAdapater implements BusinessNumberRepository {

    private final Random r = new Random();

    @Override
    public PNumber fetchNextPNumber() {
        // Don't do that at home...
        int highest = 99999999;
        int lowest = 10000000;
        int generatedId = r.nextInt(highest - lowest) + lowest;
        return PNumber.of(generatedId);
    }

    @Override
    public AddressNumber fetchNextAddressNumber() {
        // Don't do that at home...
        int highest = 999999999;
        int lowest = 100000000;
        int generatedId = r.nextInt(highest - lowest) + lowest;
        return AddressNumber.of(generatedId);
    }
}
