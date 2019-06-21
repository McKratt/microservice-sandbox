package net.bakaar.sandbox.domain.shared;

import lombok.EqualsAndHashCode;
import net.bakaar.sandbox.shared.domain.vo.BusinessNumber;

import java.util.regex.Pattern;

@EqualsAndHashCode
public class AddressNumber extends BusinessNumber {
    private AddressNumber(long value) {
        super(value);
    }

    public static AddressNumber of(String addressNumber) {
        if (addressNumber == null) {
            throw new IllegalArgumentException("Input should not be null");
        }
        Pattern pattern = Pattern.compile("A[0-9]{9}");
        if (!pattern.matcher(addressNumber).matches()) {
            throw new IllegalArgumentException("The input should follow the pattern A[0-9]{9}");
        }
        return new AddressNumber(Integer.parseInt(addressNumber.substring(1)));
    }

    public static AddressNumber of(long id) {
        return new AddressNumber(id);
    }

    @Override
    public String format() {
        return "A" + getValue();
    }
}
