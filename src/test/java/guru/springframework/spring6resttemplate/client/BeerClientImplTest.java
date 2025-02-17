package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

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

    @Test
    void getBeerById() {
        Page<BeerDTO> beerDTOPage = beerClient.listBeers();
        BeerDTO beerDTO = beerDTOPage.getContent().getFirst();
        BeerDTO dtoById = beerClient.getBeerById(beerDTO.getId());
        assertNotNull(dtoById);
        assertEquals(beerDTO.getId(), dtoById.getId());
    }

}