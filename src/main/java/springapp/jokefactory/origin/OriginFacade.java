package springapp.jokefactory.origin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springapp.jokefactory.origin.dto.OriginItemDto;

import java.util.Optional;

@Service
public class OriginFacade {

    @Autowired
    private OriginRepository originRepository;

    public Optional<Origin> tryToGetOriginByOriginItem(OriginItemDto originItemDto) {
        if (originItemDto != null && originItemDto.getId() != null) {
            return originRepository.findById(originItemDto.getId());
        }
        return Optional.empty();
    }
}
