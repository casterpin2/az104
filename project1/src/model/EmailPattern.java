package model;

public enum EmailPattern {
    EMAIL_REGEX("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,3}$");

    private final String pattern;


    EmailPattern(String pattern) {
        this.pattern = pattern;
    }


    public String getPattern() {
        return this.pattern;
    }

    public boolean isValid(String email) {
        return email.matches(this.pattern);
    }
}