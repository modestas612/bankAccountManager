package lt.inventiCodingChallange.bankAccountManager.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("storage")
@Getter
@Setter
public class StorageProperties {
    private String location = "exports";
}
