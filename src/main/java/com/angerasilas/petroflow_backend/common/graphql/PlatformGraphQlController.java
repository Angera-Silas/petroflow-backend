package com.angerasilas.petroflow_backend.common.graphql;

import graphql.schema.DataFetchingEnvironment;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PlatformGraphQlController {

    private final Environment environment;

    @QueryMapping
    public PlatformInfo platformInfo(DataFetchingEnvironment dataFetchingEnvironment) {
        return new PlatformInfo(
                environment.getProperty("spring.application.name", "petroflow-backend"),
                asString(dataFetchingEnvironment.getGraphQlContext().get(GraphQlContextKeys.TENANT_ID)),
                asString(dataFetchingEnvironment.getGraphQlContext().get(GraphQlContextKeys.REQUEST_ID)),
                Instant.now().toString());
    }

    private String asString(Object value) {
        return value == null ? null : value.toString();
    }
}
