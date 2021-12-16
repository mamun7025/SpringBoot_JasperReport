package com.mrgroup.springjasper.controller;

import com.mrgroup.springjasper.service.JsrReportRenderService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;


@Controller
@RequestMapping("/reports")
public class JsrReportViewController {


    @Autowired
    JsrReportRenderService jsrReportRenderService;


    @GetMapping("/showReport")
    public HttpEntity<byte[]> getReport(HttpServletRequest request, HttpServletResponse response){

        String rptFileName = request.getParameter("rptFileName");
        String outputFileName = request.getParameter("outputFileName");
        if(outputFileName == null || outputFileName.equals("")) outputFileName = rptFileName;
        String reportFormat = request.getParameter("reportFormat");
        if(reportFormat == null || reportFormat.equals("")) reportFormat = "PDF";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        response.setHeader("Content-Disposition", "filename="+outputFileName+"."+reportFormat);

        try {

            JasperPrint jasperPrint = this.jsrReportRenderService.generateReport(request);
            byte [] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            return new HttpEntity<>(pdfBytes, headers);

        } catch (Exception e) {
            System.out.println("***********------------");
            e.printStackTrace();
        }

        return null;

    }



    @ResponseBody
    @GetMapping("/showReport_2ndWay")
    public void getReport2(HttpServletRequest request, HttpServletResponse response){

        String rptFileName = request.getParameter("rptFileName");
        String outputFileName = request.getParameter("outputFileName");
        if(outputFileName == null || outputFileName.equals("")) outputFileName = rptFileName;
        String reportFormat = request.getParameter("reportFormat");
        if(reportFormat == null || reportFormat.equals("")) reportFormat = "PDF";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "filename="+outputFileName+"."+reportFormat);

        OutputStream out = null;
        try {
            out = response.getOutputStream();
            JasperPrint jasperPrint = this.jsrReportRenderService.generateReport(request);
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return out;

    }




}
