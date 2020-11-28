package io.swagger.commons;

public final class Constant {

    public static final String ROLE_SERVICE = "SERVICE";
    public static final String ROLE_ADMIN = "ADMIN";

    public static final String ASTERISKS = "**";
    public static final String FWD_SLASH = "/";
    public static final String REQ_MAPPING_PEOPLE = FWD_SLASH + "people";
    public static final String REQ_MAPPING_ASTERISKS = FWD_SLASH + ASTERISKS;
    public static final String SOUT_USERNAME_PASSWORD = "Username: {}, Password: {}";
    public static final String INSTANTIATION_NOW_PERMITTED = "Instantiation now Permitted.";

    private Constant() {
        throw new UnsupportedOperationException(Constant.INSTANTIATION_NOW_PERMITTED);
    }
}
