package guru.springframework.spring6resttemplate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true, value = "pageable")
//Provato refactor da generic a BeerDTO per ottenere i BeerDTO invece di LinkedHashMap nel content della pageResponse ma non funziona perchè vuole la completa qualificazione guru.springframework.spring6resttemplate.model.BeerDTO
public class BeerDTOPageImpl<BeerDTO> extends PageImpl<BeerDTO> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerDTOPageImpl(@JsonProperty("_embedded") Map<String, List<BeerDTO>> embedded, // Per estrarre "beers" che si trova dentro "_embedded"
                           @JsonProperty("number") int page,
                           @JsonProperty("size") Integer size,
                           @JsonProperty("totalElements") long total) {

        super(embedded != null && embedded.containsKey("beers") ? embedded.get("beers") : Collections.emptyList(),
                PageRequest.of(page, (size != null && size > 0) ? size : 10),
                total);
    }

    //Non funziona, "content" non esiste, ho una risposta diversa dalle lezioni don't ask me why dc
    /*
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RestPageImpl(@JsonProperty("content") List<T> content,
                        @JsonProperty("number") int page,
                        @JsonProperty("size") Integer size,
                        @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, (size != null && size > 0) ? size : 11), total);
    }
    */

    public BeerDTOPageImpl(List<BeerDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerDTOPageImpl(List<BeerDTO> content) {
        super(content);
    }
}
