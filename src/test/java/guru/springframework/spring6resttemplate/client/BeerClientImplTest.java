package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

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

    @Test
    @Rollback
    void testCreateBeer() {
        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123245")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDto);
        assertNotNull(savedDto);
    }

}