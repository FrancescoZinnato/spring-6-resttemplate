package guru.springframework.spring6resttemplate.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient beerClient;

    @Test
    void listBeersName() {
        beerClient.listBeers("johan", null, null, null); // Se c'è uno spazio nel nome c'è un problema, devo vedere perché prkddii
    }

    @Test
    void listAllBeers() {
        beerClient.listBeers();
    }

}