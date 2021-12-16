package com.mrgroup.springjasper.service;

import com.zaxxer.hikari.HikariDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.FileSystems;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Service
public class JsrReportRenderService {


    private final HikariDataSource dataSource;

    public JsrReportRenderService(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void validation(HttpServletRequest request) throws Exception {

        String rptFileName = request.getParameter("rptFileName");
        if(rptFileName == null || rptFileName.equals("")){
            throw new Exception("Please provide jasper report name");
        }

    }

    public Map<String, Object> setReportParameters(Map<String, Object>parameters, HttpServletRequest request){

        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        parameters.put("start_date", startDate);
        parameters.put("end_date", endDate);

        return parameters;

    }


    public JasperPrint generateReport( HttpServletRequest request ) throws Exception {

        this.validation(request);
        String rptFileName = request.getParameter("rptFileName");
        String rptSubFolderName = request.getParameter("rptSubFolderName");

        try {

            File file = ResourceUtils.getFile("classpath:jsreports");
            /*Get absolute path*/
            String baseReportFolderPath = file.getAbsolutePath();
            System.out.println("Base report folder path : "+baseReportFolderPath);

            String fileSeparator = FileSystems.getDefault().getSeparator();
            String targetReportPath = baseReportFolderPath + fileSeparator + rptFileName;
            if(rptSubFolderName != null && !rptSubFolderName.equals("")) targetReportPath = baseReportFolderPath + fileSeparator + rptSubFolderName + fileSeparator + rptFileName;
            System.out.println("Target report path is : "+targetReportPath);

            JasperReport jasperReport = JasperCompileManager.compileReport( targetReportPath + ".jrxml" );

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters = this.setReportParameters(parameters, request);
            parameters.put("SUBREPORT_DIR", baseReportFolderPath + fileSeparator);

            Connection dbConn = dataSource.getConnection();
            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dbConn);
            dbConn.close();

            return jasperPrint;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }




}
