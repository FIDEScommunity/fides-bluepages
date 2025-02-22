package community.fides.bluepages.backend.configuration;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "display-config")
@Getter
@Setter
public class DisplayConfig {

    Map<String, GenericAttribute> genericAttributes;
    Map<String, ServiceConfig> services;
    Map<String, CredentialConfig> credentials;

    public String getServiceTypeLabel(final String serviceType) {
        final ServiceConfig serviceConfig = services.get(serviceType);
        if (serviceConfig == null) {
            return "Service Type";
        }
        return serviceConfig.serviceTypeLabel();
    }

    public String getServiceEndpointLabel(final String serviceType) {
        final ServiceConfig serviceConfig = services.get(serviceType);
        if (serviceConfig == null) {
            return "Endpoint";
        }
        return serviceConfig.serviceEndpointLabel();
    }

    public String getServiceTitle(final String serviceType) {
        final ServiceConfig serviceConfig = services.get(serviceType);
        if (serviceConfig == null) {
            return serviceType;
        }
        return serviceConfig.title();
    }

    public String getServiceIcon(final String serviceType) {
        final ServiceConfig serviceConfig = services.get(serviceType);
        if (serviceConfig == null) {
            return "pi-book";
        }
        return serviceConfig.icon();
    }

    public Integer getServiceDisplayOrder(final String serviceType) {
        final ServiceConfig serviceConfig = services.get(serviceType);
        if (serviceConfig == null) {
            return 1;
        }
        return serviceConfig.displayOrder();
    }

    public String getCredentialIcon(final String credentialType) {
        final CredentialConfig credentialConfig = credentials.get(credentialType);
        if (credentialConfig == null) {
            return "pi-book";
        }
        return credentialConfig.icon();
    }

    public Integer getCredentialDisplayOrder(final String credentialType) {
        final CredentialConfig credentialConfig = credentials.get(credentialType);
        if (credentialConfig == null) {
            return 1;
        }
        return credentialConfig.displayOrder();
    }

    public CredentialAttribute getCredentialAttribute(final String credentialType, final String attribute) {
        final CredentialConfig credentialConfig = credentials.get(credentialType);
        if (credentialConfig == null) {
            return null;
        }
        return credentialConfig.attributesToShow().get(getNormalizedAttributeName(attribute));
    }

    private static String getNormalizedAttributeName(final String attribute) {
        return attribute.replace(".", "_");
    }

    public String getCredentialAttributeType(final String credentialType, final String attribute) {
        final CredentialAttribute credentialAttribute = getCredentialAttribute(credentialType, attribute);
        return (credentialAttribute == null) ? "Text" : credentialAttribute.type();
    }


    public record ServiceConfig(
            Integer displayOrder,
            String title,
            String icon,
            String serviceTypeLabel,
            String serviceEndpointLabel
    ) {}

    public record CredentialConfig(
            String issuanceUrl,
            Integer displayOrder,
            String icon,
            Map<String, CredentialAttribute> attributesToShow
    ) {

        public String getCredentialAttributeType(final String attributeKey) {
            if (attributeKey == null) {
                return "text";
            }
            final CredentialAttribute credentialAttribute = attributesToShow.get(getNormalizedAttributeName(attributeKey));
            if (credentialAttribute == null) {
                return "text";
            }
            return credentialAttribute.type();
        }

        public String getCredentialAttributeLabel(final String attributeKey) {
            if (attributeKey == null) {
                return "";
            }
            final CredentialAttribute credentialAttribute = attributesToShow.get(getNormalizedAttributeName(attributeKey));
            if ((credentialAttribute == null) || (credentialAttribute.label() == null)) {
                return attributeKey;
            }
            return credentialAttribute.label();
        }

        public boolean mustShowAttribute(final community.fides.bluepages.backend.domain.CredentialAttribute att) {
            if (att == null) {
                return false;
            }
            return attributesToShow.containsKey(getNormalizedAttributeName(att.getKey()));
        }
    }

    public record CredentialAttribute(
            String type,
            String label
    ) {}

    public record GenericAttribute(
            String credentialType,
            String attribute,
            FallbackAttribute fallback
    ) {}

    public record FallbackAttribute(
            String credentialType,
            String attribute

    ) {}
}
