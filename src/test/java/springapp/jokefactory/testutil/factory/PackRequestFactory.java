package springapp.jokefactory.testutil.factory;


import springapp.jokefactory.topic.panel.PackRequest;

import java.util.function.Consumer;

public class PackRequestFactory {

    public static PackRequest createInitialRequest() {
        return createInitialRequest(overrides -> {});
    }

    public static PackRequest createInitialRequest(Consumer<PackRequest> overrides) {
        PackRequest request = new PackRequest();
        request.setParentId(null);
        request.setPageNumber(0);
        request.setPageSize(23);
        request.setTopicPackIndex(null);
        overrides.accept(request);
        return request;
    }

    public static PackRequest createStandardRequest() {
        return createStandardRequest(overrides -> {});
    }

    public static PackRequest createStandardRequest(Consumer<PackRequest> overrides) {
        PackRequest request = new PackRequest();
        request.setParentId(1L);
        request.setPageNumber(0);
        request.setPageSize(23);
        request.setTopicPackIndex(0);
        overrides.accept(request);
        return request;
    }
}
