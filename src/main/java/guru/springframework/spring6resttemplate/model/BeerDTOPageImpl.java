package guru.springframework.spring6resttemplate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true, value = "pageable")
//Provato refactor da generic a BeerDTO per ottenere i BeerDTO invece di LinkedHashMap nel content della pageResponse ma non funziona perchè vuole la completa qualificazione guru.springframework.spring6resttemplate.model.BeerDTO
public class BeerDTOPageImpl<BeerDTO> extends PageImpl<guru.springframework.spring6resttemplate.model.BeerDTO> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BeerDTOPageImpl(@JsonProperty("content") List<guru.springframework.spring6resttemplate.model.BeerDTO> content,
                           @JsonProperty("number") int page,
                           @JsonProperty("size") int size,
                           @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, size), total);
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

    public BeerDTOPageImpl(List<guru.springframework.spring6resttemplate.model.BeerDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public BeerDTOPageImpl(List<guru.springframework.spring6resttemplate.model.BeerDTO> content) {
        super(content);
    }
}
