package springapp.jokefactory.utils;

import java.util.Collections;
import java.util.Set;

public class Ids {

    static public int generateNewId(Set<Integer> keysSoFar) {
        if (keysSoFar.isEmpty()) {
            return 0;
        }
        else{
            return Collections.max(keysSoFar) + 1;
        }
    }
}
