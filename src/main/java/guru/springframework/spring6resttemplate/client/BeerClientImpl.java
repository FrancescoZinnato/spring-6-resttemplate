package guru.springframework.spring6resttemplate.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;

    //private static final String BASE_URL = "http://localhost:8080"; //Rimosso perchè definito nella classe di configurazione del RestTemplate
    private static final String GET_BEER_PATH = "/api/v1/beer";

    @Override
    public Page<BeerDTO> listBeers() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> response = restTemplate.getForEntity(GET_BEER_PATH, String.class);

        //Restituisce una LinkedHashMap con altre LinkedHashMap al suo interno
        ResponseEntity<Map> mapResponse = restTemplate.getForEntity(GET_BEER_PATH, Map.class);

        //Restituisce un JsonNode della libreria Jackson
        ResponseEntity<JsonNode> jsonResponse = restTemplate.getForEntity(GET_BEER_PATH, JsonNode.class);

        //Restituisce un nodo mancante come confermato con il system out sottostante, non funziona
        jsonResponse.getBody().findPath("content")
                .elements().forEachRemaining(node -> System.out.println(node.get("beerName").asText()));
        //Conferma che il nodo è mancante
        System.out.println(jsonResponse.getBody().findPath("content").isMissingNode());

        //SystemOut per vedere la struttura del body della jsonResponse
        //System.out.println(jsonResponse.getBody());

        //Stampo tutti i nomi delle birre accedendo ai nodi basandomi sulla struttura del body ottenuta dal print sopra, funziona
        //jsonResponse.getBody().findPath("_embedded").findPath("beers").elements().forEachRemaining(node -> System.out.println(node.get("beerName").asText()));

        ResponseEntity<BeerDTOPageImpl> pageResponse = restTemplate.getForEntity(GET_BEER_PATH, BeerDTOPageImpl.class);

        //System.out.println(response.getBody());

        return null;
    }

}
