package community.fides.bluepages.backend.service.crawler.documentfetcher;


import community.fides.bluepages.backend.domain.Did;

public interface DidDocumentFetcher {

    boolean canFetchRawData(Did did);

    byte[] fetchRawData(Did did);

}
