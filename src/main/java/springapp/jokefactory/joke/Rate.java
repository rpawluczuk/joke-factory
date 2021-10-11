package springapp.jokefactory.joke;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
@Builder
@AllArgsConstructor
class Rate {

    private Float value;
    private Short count;

    public Rate() {
        this.value = (float) 0;
        this.count = 0;
    }
}
