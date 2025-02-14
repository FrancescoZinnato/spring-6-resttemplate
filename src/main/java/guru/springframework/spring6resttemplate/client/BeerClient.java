package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import org.springframework.data.domain.Page;

public interface BeerClient {

    Page<BeerDTOPageImpl> listBeers();

}
