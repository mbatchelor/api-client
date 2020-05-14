package com.overops.report.service.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.overops.report.service.model.QualityGateTestResults.TestType;

public class QualityReport {

    public enum ReportStatus {
        PASSED,
        FAILED,
        WARNING
    }

    ReportStatus statusCode = ReportStatus.FAILED;
    String statusMsg = "";
    boolean showEventsForPassedTest;

    QualityGateTestResults newErrorsTestResults;
    QualityGateTestResults resurfacedErrorsTestResults;
    QualityGateTestResults criticalErrorsTestResults;
    QualityGateTestResults regressionErrorsTestResults;
    QualityGateTestResults totalErrorsTestResults;
    QualityGateTestResults uniqueErrorsTestResults;

    List<QualityGateEvent> topEvents = Collections.emptyList();

    QualityReportExceptionDetails exceptionDetails;

    public QualityReport() {
    }

    public ReportStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(ReportStatus statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public QualityGateTestResults getNewErrorsTestResults() {
        return newErrorsTestResults;
    }

    public void setNewErrorsTestResults(QualityGateTestResults newErrorsTestResults) {
        this.newErrorsTestResults = newErrorsTestResults;
    }

    public QualityGateTestResults getResurfacedErrorsTestResults() {
        return resurfacedErrorsTestResults;
    }

    public void setResurfacedErrorsTestResults(QualityGateTestResults resurfacedErrorsTestResults) {
        this.resurfacedErrorsTestResults = resurfacedErrorsTestResults;
    }

    public QualityGateTestResults getCriticalErrorsTestResults() {
        return criticalErrorsTestResults;
    }

    public void setCriticalErrorsTestResults(QualityGateTestResults criticalErrorsTestResults) {
        this.criticalErrorsTestResults = criticalErrorsTestResults;
    }

    public QualityGateTestResults getRegressionErrorsTestResults() {
        return regressionErrorsTestResults;
    }

    public void setRegressionErrorsTestResults(QualityGateTestResults regressionErrorsTestResults) {
        this.regressionErrorsTestResults = regressionErrorsTestResults;
    }

    public QualityGateTestResults getTotalErrorsTestResults() {
        return totalErrorsTestResults;
    }

    public void setTotalErrorsTestResults(QualityGateTestResults totalErrorsTestResults) {
        this.totalErrorsTestResults = totalErrorsTestResults;
    }

    public QualityGateTestResults getUniqueErrorsTestResults() {
        return uniqueErrorsTestResults;
    }

    public void setUniqueErrorsTestResults(QualityGateTestResults uniqueErrorsTestResults) {
        this.uniqueErrorsTestResults = uniqueErrorsTestResults;
    }

    public List<QualityGateEvent> getTopEvents() {
        return topEvents;
    }

    public void setTopEvents(List<QualityGateEvent> topEvents) {
        this.topEvents = topEvents;
    }

    public QualityReportExceptionDetails getExceptionDetails() {
        return exceptionDetails;
    }

    public void setExceptionDetails(QualityReportExceptionDetails exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public HtmlParts getHtmlParts() {
        return this.getHtmlParts(false);
    }

    public HtmlParts getHtmlParts(boolean showEventsForPassedGates) {
        HtmlParts htmlParts = new HtmlParts();
        if (exceptionDetails != null) {
            htmlParts.setHtml(getExceptionHtml());
        } else {
            htmlParts.setHtml(getReportHtml(showEventsForPassedGates));
        }
        htmlParts.setCss(getStyleHtml());
        return htmlParts;
    }

    public String toHtml() {
        return toHtml(false);
    }

    public String toHtml(boolean showEventsForPassedGates) {
        QualityReportGenerator generator = new QualityReportGenerator();
        QualityReportTemplate template = new QualityReportTemplate(this);
        template.setShowEventsForPassedGates(showEventsForPassedGates);
        return generator.generate(template, "page");
    }

    private String getExceptionHtml() {
        QualityReportGenerator generator = new QualityReportGenerator();
        QualityReportTemplate template = new QualityReportTemplate(this);
        return generator.generate(template, "exception");
    }

    public String getStyleHtml(){
        QualityReportGenerator generator = new QualityReportGenerator();
        QualityReportTemplate template = new QualityReportTemplate(this);
        return generator.generate(template, "style");
    }

    private String getReportHtml(boolean showEventsForPassedGates) {
        QualityReportGenerator generator = new QualityReportGenerator();
        QualityReportTemplate template = new QualityReportTemplate(this);
        template.setShowEventsForPassedGates(showEventsForPassedGates);
        String reportHtml = generator.generate(template, "report");
       return reportHtml;
    }

}
