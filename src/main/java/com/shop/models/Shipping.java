package com.shop.models;

import lombok.Data;

@Data
public final class Shipping {
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public Shipping(ShippingBuilder shippingBuilder) {
        this.address = shippingBuilder.address;
        this.city = shippingBuilder.city;
        this.state = shippingBuilder.state;
        this.zipCode = shippingBuilder.zipCode;
        this.country = shippingBuilder.country;
    }

    public static class ShippingBuilder {
        private String address;
        private String city;
        private String country;
        private String state;
        private String zipCode;

        public ShippingBuilder address(String address) {
            this.address = address;
            return this;
        }

        public ShippingBuilder city(String city) {
            this.city = city;
            return this;
        }

        public ShippingBuilder state(String state) {
            this.state = state;
            return this;
        }

        public ShippingBuilder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public ShippingBuilder country(String country) {
            this.country = country;
            return this;
        }

        public Shipping build() {
            return new Shipping(this);
        }

    }
}
