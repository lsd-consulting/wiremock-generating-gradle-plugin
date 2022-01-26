package {{stubPackageName}};

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.lang.String.format;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("com.lsdconsulting.stub.plugin.ControllerProcessor")
public class {{stubClassName}} extends StubBase {

    private static final int OK = 200;
    public static final int ONCE = 1;

    private ObjectMapper objectMapper;

    public {{stubClassName}}(ObjectMapper objectMapper) {
        super(objectMapper);
        this.objectMapper = objectMapper;
    }

    private static final String {{methodName.toUpperCase()}}_URL = "{{rootUrl}}{{url}}";

    public void get{{methodName}}({{responseType}} response) {
        buildGet(format({{methodName.toUpperCase()}}_URL), OK, buildBody(response));
    }

    public void get{{methodName}}(int status, {{responseType}} response) {
        buildGet(format({{methodName.toUpperCase()}}_URL), status, buildBody(response));
    }

    public void verifyGet{{methodName}}() {
        verifyGet{{methodName}}(ONCE);
    }

    public void verifyGet{{methodName}}(final int times) {
        verify(times, getRequestedFor(urlEqualTo(format({{methodName.toUpperCase()}}_URL))));
    }
}