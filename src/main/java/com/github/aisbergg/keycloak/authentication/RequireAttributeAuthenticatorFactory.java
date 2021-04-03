package com.github.aisbergg.keycloak.authentication;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Arrays;
import java.util.List;

public class RequireAttributeAuthenticatorFactory implements AuthenticatorFactory {

    private static final String PROVIDER_ID = "require-attribute";
    public static final String ATTRIBUTE_NAME = PROVIDER_ID + "-name";
    public static final String ATTRIBUTE_VALUE = PROVIDER_ID + "-value";
    public static final String SOURCE_USER = PROVIDER_ID + "-source-user";
    public static final String SOURCE_GROUP = PROVIDER_ID + "-source-group";
    public static final String SOURCE_ROLE = PROVIDER_ID + "-source-role";

    public static final RequireAttributeAuthenticator ROLE_AUTHENTICATOR = new RequireAttributeAuthenticator();

    @Override
    public String getDisplayType() {
        return "Require Attribute";
    }

    @Override
    public String getReferenceCategory() {
        return null;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    public static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED, AuthenticationExecutionModel.Requirement.DISABLED
    };

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return "Requires the user to have an attribute with a specified value.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {

        ProviderConfigProperty name = new ProviderConfigProperty();
        name.setType(ProviderConfigProperty.STRING_TYPE);
        name.setDefaultValue("{clientId}:login");
        name.setName(ATTRIBUTE_NAME);
        name.setLabel("Attribute Name");
        name.setHelpText("Required attribute name that a user needs to have to proceed with the authentication. " +
            "This can be an attribute defined in the user, group or role context. " +
            "The placeholder '{clientId}' can be used to incorporate the client name. " +
            "If the user doesn't have the attribute, the authenticator will fail.");

        ProviderConfigProperty value = new ProviderConfigProperty();
        value.setType(ProviderConfigProperty.STRING_TYPE);
        value.setDefaultValue("yes");
        value.setName(ATTRIBUTE_VALUE);
        value.setLabel("Attribute Value");
        value.setHelpText("A value that the attribute must have.");

        ProviderConfigProperty sourceUser = new ProviderConfigProperty();
        sourceUser.setType(ProviderConfigProperty.BOOLEAN_TYPE);
        sourceUser.setDefaultValue(Boolean.TRUE);
        sourceUser.setName(SOURCE_USER);
        sourceUser.setLabel("User Source");
        sourceUser.setHelpText("Use the user itself as a source of the attribute.");

        ProviderConfigProperty sourceRole = new ProviderConfigProperty();
        sourceRole.setType(ProviderConfigProperty.BOOLEAN_TYPE);
        sourceRole.setDefaultValue(Boolean.TRUE);
        sourceRole.setName(SOURCE_ROLE);
        sourceRole.setLabel("Role Source");
        sourceRole.setHelpText("Use the assigned roles as a source of the attribute.");

        ProviderConfigProperty sourceGroup = new ProviderConfigProperty();
        sourceGroup.setType(ProviderConfigProperty.BOOLEAN_TYPE);
        sourceGroup.setDefaultValue(Boolean.TRUE);
        sourceGroup.setName(SOURCE_GROUP);
        sourceGroup.setLabel("Group Source");
        sourceGroup.setHelpText("Use the assigned groups as a source of the attribute.");

        return Arrays.asList(name, value, sourceUser, sourceRole, sourceGroup);
    }

    @Override
    public void close() {}

    @Override
    public Authenticator create(KeycloakSession session) {
        return ROLE_AUTHENTICATOR;
    }

    @Override
    public void init(Config.Scope config) {}

    @Override
    public void postInit(KeycloakSessionFactory factory) {}

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
