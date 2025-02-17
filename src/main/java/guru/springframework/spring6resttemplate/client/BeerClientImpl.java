package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;

    //private static final String BASE_URL = "http://localhost:8080"; //Rimosso perchè definito nella classe di configurazione del RestTemplate
    public static final String GET_BEER_PATH = "/api/v1/beer";
    public static final String GET_BEER_BY_ID_PATH = "/api/v1/beer/{beerId}";

    @Override
    public BeerDTO updateBeer(BeerDTO beerDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        restTemplate.put(GET_BEER_BY_ID_PATH, beerDTO, beerDTO.getId());

        return getBeerById(beerDTO.getId());
    }

    @Override
    public void deleteBeer(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        restTemplate.delete(GET_BEER_BY_ID_PATH, beerId);
    }

    @Override
    public BeerDTO createBeer(BeerDTO newDTO) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //ResponseEntity<BeerDTO> response = restTemplate.postForEntity(GET_BEER_PATH, newDTO, BeerDTO.class); // Questo funziona perfettamente se l'API restituisce l'oggetto
        URI uri = restTemplate.postForLocation(GET_BEER_PATH, newDTO);
        assert uri != null;
        return restTemplate.getForObject(uri.getPath(), BeerDTO.class);
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        return restTemplate.getForObject(GET_BEER_BY_ID_PATH, BeerDTO.class, beerId);
    }

    @Override
    public Page<BeerDTO> listBeers() {
        return this.listBeers(null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Integer pageNumber, Integer pageSize) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        //Ci permette di costruire il path includendo i query parameters
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(GET_BEER_PATH);

        if(beerName != null) {
            builder.queryParam("beerName", beerName);
        }

        if(beerStyle != null) {
            builder.queryParam("beerStyle", beerStyle);
        }

        if(pageNumber != null) {
            builder.queryParam("pageNumber", pageNumber);
        }

        if(pageSize != null) {
            builder.queryParam("pageSize", pageSize);
        }

        ResponseEntity<BeerDTOPageImpl> pageResponse = restTemplate.getForEntity(builder.toUriString(), BeerDTOPageImpl.class);

        return pageResponse.getBody();
    }

}
