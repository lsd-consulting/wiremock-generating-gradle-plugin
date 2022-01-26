package {{stubPackageName}};

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import lombok.SneakyThrows;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

class StubBase {

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/json; charset=utf-8";

    private final ObjectMapper objectMapper;

    StubBase(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    void buildGet(final String requestUrl, final int status, final String response) {
        stub(status, response, get(urlEqualTo(requestUrl)));
    }

    private void stub(final int status, final String response, final MappingBuilder mappingBuilder) {
        stubFor(mappingBuilder
                .willReturn(
                        aResponse()
                                .withStatus(status)
                                .withBody(response)
                                .withHeader(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
                )
        );
    }

    @SneakyThrows
    String buildBody(final Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
