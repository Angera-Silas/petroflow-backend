package com.angerasilas.petroflow_backend.config;

import com.angerasilas.petroflow_backend.common.constants.HeaderNames;
import com.angerasilas.petroflow_backend.common.graphql.GraphQlContextKeys;
import com.angerasilas.petroflow_backend.infrastructure.observability.RequestCorrelationFilter;
import com.angerasilas.petroflow_backend.tenancy.context.TenantContextHolder;
import graphql.scalars.ExtendedScalars;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.util.StringUtils;

@Configuration
public class GraphQlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.GraphQLLong);
    }

    @Bean
    public WebGraphQlInterceptor graphQlContextInterceptor() {
        return (request, chain) -> {
            String tenantId = TenantContextHolder.hasContext()
                    ? TenantContextHolder.getCurrentTenant()
                    : request.getHeaders().getFirst(HeaderNames.TENANT_ID);

            String requestId = request.getHeaders().getFirst(HeaderNames.REQUEST_ID);
            Object requestAttributeId = request.getAttributes().get(RequestCorrelationFilter.REQUEST_ID_ATTRIBUTE);
            if (!StringUtils.hasText(requestId) && requestAttributeId != null) {
                requestId = requestAttributeId.toString();
            }

            Map<String, Object> contextValues = new LinkedHashMap<>();
            if (StringUtils.hasText(tenantId)) {
                contextValues.put(GraphQlContextKeys.TENANT_ID, tenantId);
            }
            if (StringUtils.hasText(requestId)) {
                contextValues.put(GraphQlContextKeys.REQUEST_ID, requestId);
            }

            request.configureExecutionInput((executionInput, builder) -> builder
                    .graphQLContext(contextValues)
                    .build());

            return chain.next(request);
        };
    }
}
