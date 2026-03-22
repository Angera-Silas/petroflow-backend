package com.angerasilas.petroflow_backend.modules.reporting.graphql;

import com.angerasilas.petroflow_backend.modules.reporting.dto.response.ReportingOverviewResponse;
import com.angerasilas.petroflow_backend.modules.reporting.service.ReportingService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReportingGraphQlController {

    private final ReportingService reportingService;

    @QueryMapping
    public ReportingOverviewResponse reportingOverview() {
        return reportingService.getReportingOverview();
    }
}
