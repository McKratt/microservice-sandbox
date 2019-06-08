package net.bakaar.sandbox.cas.domain.entity;


import net.bakaar.sandbox.shared.domain.vo.PNumber;

public class Case {
    private final PNumber injured;
    private final String id;

    private Case(String id, PNumber injured) {
        this.injured = injured;
        this.id = id;
    }

    public PNumber getInjured() {
        return injured;
    }

    public String getId() {
        return id;
    }

    public static BusinessIdBuilder builder() {
        return id -> injured -> new Case(id, injured);
    }

    @FunctionalInterface
    public interface InjuredBuilder {
        Case withInjured(PNumber injured);
    }

    @FunctionalInterface
    public interface BusinessIdBuilder {
        InjuredBuilder withBusinnessId(String id);
    }
}
