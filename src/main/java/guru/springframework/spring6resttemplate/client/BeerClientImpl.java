package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;

    //private static final String BASE_URL = "http://localhost:8080"; //Rimosso perchè definito nella classe di configurazione del RestTemplate
    private static final String GET_BEER_PATH = "/api/v1/beer";

    @Override
    public Page<BeerDTO> listBeers(String beerName) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //Ci permette di costruire il path includendo i query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(GET_BEER_PATH);

        if(beerName != null) {
            builder.queryParam("beerName", beerName);
        }

        ResponseEntity<BeerDTOPageImpl> pageResponse = restTemplate.getForEntity(builder.toUriString(), BeerDTOPageImpl.class);

        return pageResponse.getBody();
    }

}
