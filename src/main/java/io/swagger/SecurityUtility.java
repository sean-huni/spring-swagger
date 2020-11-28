package io.swagger;

import io.swagger.commons.Constant;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

public class SecurityUtility {

    private SecurityUtility() {
        throw new UnsupportedOperationException(Constant.INSTANTIATION_NOW_PERMITTED);
    }

    public static void createSecurityContext(String username, String password, String... roles) {

        Assert.notNull(username, "Username must not be null!");
        Assert.notNull(password, "Password must not be null!");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList(roles)));
    }

}
