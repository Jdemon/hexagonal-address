package th.hexagonal.address;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import th.hexagonal.address.domain.port.ILoadAddressPort;
import th.hexagonal.address.domain.port.IRetrieveAddressPort;
import th.hexagonal.address.external.client.IAddressClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private ILoadAddressPort loadAddressPort;

    @Autowired
    private IRetrieveAddressPort retrieveAddressPort;

    @Autowired
    private IAddressClient addressClient;

    @Test
    void application() {
        Application.main(new String[]{});
    }

    @Test
    void contextLoads() {
        assertThat(loadAddressPort).isNotNull();
        assertThat(retrieveAddressPort).isNotNull();
        assertThat(addressClient).isNotNull();
    }

}
