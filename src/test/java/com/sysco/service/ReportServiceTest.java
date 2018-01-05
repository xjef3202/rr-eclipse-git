package com.sysco.service;

import ar.com.fdvs.dj.domain.DynamicReport;
import com.sysco.TestUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.JRTemplatePrintFrame;
import net.sf.jasperreports.engine.fill.JRTemplatePrintImage;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.sysco.ReportServiceTestData.*;

public class ReportServiceTest {
    private ReportService reportService;

    @Before
    public void setup() {
        RestTemplate restTemplate = new RestTemplate();

        String dbUrl = TestUtils.getProp("db.url");
        String user = TestUtils.getProp("db.username");
        String password = TestUtils.getProp("db.password");
        String db2Driver = TestUtils.getProp("db.drivername");

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setDriverClassName(db2Driver);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

//        NutritionDao nutritionDao = new NutritionDao(dbUrl, dataSource);
//        NutritionServiceImplDB nutritionService = new NutritionServiceImplDB(nutritionDao);
        NutritionService nutritionService = new NutritionService(TestUtils.PRODUCT_SVC_URL, restTemplate);

        ProductService productService = new ProductService(TestUtils.PRODUCT_SVC_URL, restTemplate);
        ImageService imageService = new ImageService(TestUtils.IMAGE_SVC_URL, restTemplate);
        CustomerService customerService = new CustomerService(TestUtils.ADMIN_SVC_URL, restTemplate);

        reportService = new ReportService(nutritionService, productService, imageService, customerService);
    }

    @Test
    public void getImageSvcResponseTest()  {
        Assert.assertEquals(IMG_URL,reportService.getImageSvcResponse(SUPC_WITH_IMAGE));
    }

    @Test
    public void getCustomerNameTest() {
        Assert.assertEquals("MILES, WAYNE",reportService.getCustomerName("010","403030"));
    }

    @Test
    public void generateReportTest(){
        byte [] data = reportService.generateReport(SUPC,OPCO,ACCOUNT_NO);
        Assert.assertEquals(true,data.length>0);
    }

    @Test
    public void getJasperPrintTest() throws JRException {
        JasperReport jasperReport = getJasperReport();
        Map<String,Object> data = new HashMap<>();
        JRDataSource emptyDS = new JREmptyDataSource();

        JasperPrint jasperPrint = reportService.getJasperPrint(emptyDS,jasperReport,data);
        Assert.assertTrue(jasperPrint.getPages().size()>0);

        JRTemplatePrintImage jrTemplatePrintImage = (JRTemplatePrintImage) jasperPrint.getPages().get(0).getElements().get(0);
        Assert.assertEquals(IMG_HEIGHT,jrTemplatePrintImage.getHeight());

        JRTemplatePrintFrame printFrame = (JRTemplatePrintFrame) jasperPrint.getPages().get(0).getElements().get(5);
        JRTemplatePrintText jrTemplatePrintText = (JRTemplatePrintText) printFrame.getElements().get(0);
        Assert.assertEquals(DISCLAIMIR_TEXT,jrTemplatePrintText.getFullText());

    }
    private  JasperReport getJasperReport() throws JRException {
        Map<String,String> params = reportService.getParams(SUPC,OPCO,ACCOUNT_NO);
        DynamicReport dynamicReport = reportService.getDynamicReport();
        Map<String,Object> data = new HashMap<>();
        JasperReportsContext jasperReportsContext = new SimpleJasperReportsContext();
        JasperReport jasperReport = reportService.getJasperReport(params,dynamicReport,data);
        return jasperReport;
    }
}