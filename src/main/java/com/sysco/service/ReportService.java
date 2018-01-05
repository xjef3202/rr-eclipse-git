package com.sysco.service;


import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.constants.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysco.model.Customer;
import com.sysco.model.NutritionResponse;
import com.sysco.model.Product;
import com.sysco.model.ProductCardLayoutManager;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@Slf4j
public class ReportService {

    private NutritionService nutritionService;

    private ProductService productService;

    private ImageService imageService;

    private CustomerService customerService;

    public ReportService(NutritionService nutritionService, ProductService productService, ImageService imageService, CustomerService customerService) {
        this.nutritionService = nutritionService;
        this.productService = productService;
        this.imageService = imageService;
        this.customerService = customerService;
    }

    public byte [] generateReport(String supc, String opCo , String accountNo){
        Map<String, String> params = getParams(supc, opCo, accountNo);

        byte [] response = generatePdfReport(params);

        return  response;
    }

    public Map<String, String> getParams(String supc, String opCo, String accountNo) {
        Map<String, String> params = new HashMap<>();
        try {
            params = getNutritionInfo(supc);
        }catch (Exception e){
            params.put("nutritionInfoFlag","flag");
            log.info("getParams() :  get Params Exception",e);
        }
        Map<String, String> product = getProductInfo(supc, opCo, accountNo);
        params.putAll(product);
        params.put("imagePath", getImageSvcResponse(supc));
        params.put("customerNumber",accountNo);
        params.put("customerName",getCustomerName(opCo,accountNo));
        return params;
    }

    public Map<String, String> getProductInfo(String supc, String opCo, String customerId) {
        Product product = productService.getProduct(supc, opCo, customerId);
        return new ObjectMapper().convertValue(product,Map.class);
    }

    public Map<String, String> getNutritionInfo(String supc) {
        NutritionResponse nutritionResponse = nutritionService.getNutritionObject(supc);
        return new ObjectMapper().convertValue(nutritionResponse, Map.class);
    }

    public  byte [] generatePdfReport(Map<String,String> params){
        byte [] content = null;
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;

        DynamicReport dynamicReport = getDynamicReport();
        JRDataSource emptyDS = new JREmptyDataSource();

        Map<String,Object> data = new HashMap<>();
        try {
            jasperReport = getJasperReport(params, dynamicReport, data);
            jasperPrint = getJasperPrint(emptyDS, jasperReport, data);
            content =  JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            log.error("generatedPdfReport() :  Jasper Exception",e);
        }

        return content;
    }

    public  JasperReport getJasperReport(Map<String, String> params, DynamicReport dynamicReport, Map<String, Object> data) throws JRException {
        JasperReportsContext jasperReportsContext = new SimpleJasperReportsContext();
        JasperReport jasperReport = DynamicJasperHelper.
                generateJasperReport(dynamicReport, new ProductCardLayoutManager(params,jasperReportsContext), data);
        return jasperReport;
    }

    public  JasperPrint getJasperPrint(JRDataSource emptyDS, JasperReport jasperReport, Map<String, Object> data) throws JRException {
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, data, emptyDS);
        removeBlankPages(jasperPrint);
        return jasperPrint;
    }

    private  void removeBlankPages(JasperPrint jasperPrint) {
        Iterator<JRPrintPage> iterator = jasperPrint.getPages().iterator();
        while(iterator.hasNext()){
            JRPrintPage p = iterator.next();
            if(p.getElements().size()<=3){
                iterator.remove();
            }
        }
    }

    public  DynamicReport getDynamicReport() {
        DynamicReportBuilder drb = new DynamicReportBuilder();
        drb.setUseFullPageWidth(true).setTopMargin(5).setBottomMargin(10).setLeftMargin(40).setRightMargin(40);
        drb.setPageSizeAndOrientation(Page.Page_Legal_Portrait());
        return drb.build();
    }

    public String getImageSvcResponse(String supc){
        String imagePath=null;

        try {
            String response = imageService.getImageInfo(supc);
            if (null != response) {
                JSONObject jsonObject = new JSONObject(response);
                imagePath = jsonObject.getJSONObject("Product").getJSONObject("images").getJSONObject("web").getJSONArray("urls").getString(0);
            }
        } catch(HttpStatusCodeException e){
            log.error("getImageSvcResponse() : Could Not Retrieve Image URL",e);
        }

        catch (JSONException e) {
            log.error("getImageSvcResponse() : JSON Exception "+e);
        }
        return imagePath;
    }

    public String getCustomerName(String opCo,String accountNo) {
            Customer customer = customerService.getCustomer(opCo,accountNo);
            return customer.getCustomerName();
    }
}
