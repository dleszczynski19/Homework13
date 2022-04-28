package com.shop.models;

import lombok.Data;

@Data
public final class User {
    private String socialTitle;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String month;
    private String day;
    private String year;
    private boolean receiveOffers;
    private boolean dataPrivacy;
    private boolean singUpNewsletter;
    private boolean acceptPolicy;

    public User(UserBuilder userBuilder) {
        this.socialTitle = userBuilder.socialTitle;
        this.firstName = userBuilder.firstName;
        this.lastName = userBuilder.lastName;
        this.email = userBuilder.email;
        this.password = userBuilder.password;
        this.month = userBuilder.month;
        this.day = userBuilder.day;
        this.year = userBuilder.year;
        this.receiveOffers = userBuilder.receiveOffers;
        this.dataPrivacy = userBuilder.dataPrivacy;
        this.singUpNewsletter = userBuilder.singUpNewsletter;
        this.acceptPolicy = userBuilder.acceptPolicy;
    }

    @Override
    public String toString() {
        return socialTitle + "\n" + firstName + "\n" + lastName;
    }

    public static class UserBuilder {
        private String socialTitle;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String month;
        private String day;
        private String year;
        private boolean receiveOffers;
        private boolean dataPrivacy;
        private boolean singUpNewsletter;
        private boolean acceptPolicy;

        public UserBuilder socialTitle(String socialTitle) {
            this.socialTitle = socialTitle;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder month(String month) {
            this.month = month;
            return this;
        }

        public UserBuilder day(String day) {
            this.day = day;
            return this;
        }

        public UserBuilder year(String year) {
            this.year = year;
            return this;
        }

        public UserBuilder receiveOffers(boolean receiveOffers) {
            this.receiveOffers = receiveOffers;
            return this;
        }

        public UserBuilder dataPrivacy(boolean dataPrivacy) {
            this.dataPrivacy = dataPrivacy;
            return this;
        }

        public UserBuilder singUpNewsletter(boolean singUpNewsletter) {
            this.singUpNewsletter = singUpNewsletter;
            return this;
        }

        public UserBuilder acceptPolicy(boolean acceptPolicy) {
            this.acceptPolicy = acceptPolicy;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}