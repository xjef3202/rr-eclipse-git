package com.sysco.model;

import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import com.sysco.enums.Allergen;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.fill.JRMeasuredText;
import net.sf.jasperreports.engine.fonts.FontUtil;
import net.sf.jasperreports.engine.type.*;
import net.sf.jasperreports.engine.util.JRStyledText;
import net.sf.jasperreports.engine.util.JRStyledTextParser;
import net.sf.jasperreports.engine.util.JRTextMeasurerUtil;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class ProductCardLayoutManager extends ClassicLayoutManager {

    protected static final String FONT_HELVETICA_BOLD = "Helvetica-Bold";
    protected static final String FONT_HELVETICA_OBLIQUE = "Helvetica-Oblique";
    protected static final int DETAIL_MIDDLE_SECTION_WIDTH = 10;
    private static final String TEXT_DESCRIPTION = "Description";
    private static final String TEXT_INFORMATION = "Information";
    private static final String TEXT_NUTRITION_FACTS = "Nutrition Facts";
    private static final String TEXT_K12_EQUIVALENTS = "K-12 Equivalents";
    private static final String TEXT_SERVING_SIZE = "Serving Size ";
    private static final String TEXT_AMOUNT_PER_SERVING = "Amount Per Serving";
    private static final String TEXT_CALORIES = "Calories ";
    private static final String TEXT_DAILY_VALUE = "% Daily Value*";
    private static final String TEXT_TOTAL_FAT = "Total Fat ";
    private static final String TEXT_SATURATED_FAT = "Saturated Fat ";
    private static final String TEXT_TRANS = "Trans";
    private static final String TEXT_FAT = "Fat ";
    private static final String TEXT_PORTION_SIZE = "Portion Size";
    private static final String TEXT_CHOLESTEROL = "Cholesterol ";
    private static final String TEXT_SODIUM = "Sodium ";
    private static final String TEXT_TOTAL_CARBOHYDRATE = "Total Carbohydrate ";
    private static final String TEXT_DIETARY_FIBER = "Dietary Fiber ";
    private static final String TEXT_SUGARS = "Total Sugars ";
    private static final String TEXT_INCLUDED_SUGARS = "Includes {0} Added Sugars ";
    private static final String TEXT_PROTEIN = "Protein ";
    private static final String TEXT_VITAMIN_A = "Vitamin A";
    private static final String TEXT_VITAMIN_C = "Vitamin C";
    private static final String TEXT_VITAMIN_D = "Vitamin D";
    private static final String TEXT_CALCIUM = "Calcium";
    private static final String TEXT_IRON = "Iron";
    private static final String TEXT_POTASSIUM = "Potassium";
    private static final String TEXT_PERCENT_DESC = "The % Daily Value(DV) tells you how much a nutrient in a serving of food contributes to a daily diet. 2,000 calories a day is used for general nutrition advice.";
    private static final String TEXT_LESS_THAN = "Less than";
    protected static final String TEXT_INGREDIENTS = "Ingredients";
    protected static final String TEXT_ALLERGENS = "Allergens & Attributes";
    protected static final String TEXT_INFORMATION_NOT_AVAILABLE = "Information Not Available";
    private static final int INGREDIENT_VALUE_WIDTH = 93;
    private static final String TEXT_DISCLAIMER = new StringBuilder()
            .append("Disclaimer \n 1. The nutritional values indicated may not be complete based on limited information from product manufacturer. ")
            .append("2. This is a representation of the nutritional label. Because the data may change from time to time, this information may not always be identical to the nutritional label information on products sold. ")
            .append("3. If the Nutrition Label, Allergen Information and/or Ingredient List on eNutrition conflicts with these three statements on the product packaging itself, defer to the information on the product packaging. To ensure complete and current information on a product, always contact the product manufacturer. ")
            .append("4. % Daily Values are based on US 1990 NLEA regulations. ")
            .append("5. These items' qualification as 'gluten-free' is dependent on the accuracy of the gluten-free representations of the manufacturers of the items and their ingredients. Therefore, Sysco and its affiliates do not guarantee that any item will be completely gluten-free. Consumers with celiac disease and/or gluten sensitivities should exercise proper caution in the consumption of any food items and should always consider their individual dietary requirements and needs.")
            .toString();
    private static final int NUTRITION_GRID_PADDING_LEFT = 5;
    private static final int NUTRITION_GRID_PADDING_RIGHT = 5;
    public static final String CHILD_NUTRITION = "Child Nutrition";
    public static final String MEAT_MEAT_ALT_OZ_EQ = "Meat/Meat Alt(oz eq)";
    public static final String OZ_EQ = " oz eq";
    public static final String PRODUCT_FORMULATION_STATEMENT = "Product Formulation Statement";
    public static final String FRUIT_CUP = "Fruit(cup)";
    public static final String CUP = " cup";
    public static final String VEGETABLE_RED_ORANGE = "Vegetable - Red/Orange(cup)";
    public static final String VEGETABLE_DARK_GREEN = "Vegetable - Dark Green(cup)";
    public static final String VEGETABLE_STARCHY = "Vegetable - Starchy(cup)";
    public static final String VEGETABLE_BEANS_PEAS = "Vegetable - BeansPeas(cup)";
    public static final String VEGETABLE_OTHER = "Vegetable - Other(cup)";
    public static final String CN_IDENTIFICATION = "CN Identification";
    public static final String NOTES = "Notes:";

    private static final int WIDTH_401 = 401;
    private static final int HEIGHT_217 = 217;
    private static final int NUMBER_2 = 2;
    public static final String IMG_PATH = "src/main/resources/images/";


    private Map<String, String> productInfo;
    private JasperReportsContext jasperReportsContext;


    public ProductCardLayoutManager(Map<String, String> productInfo, JasperReportsContext jasperReportsContext) {
        this.productInfo = productInfo;
        this.jasperReportsContext = jasperReportsContext;
    }

    @Override
    protected void startLayout() {
        super.startLayout();
    }

    @Override
    protected void ensureDJStyles() {
    }

    @Override
    protected void endLayout() {
        super.endLayout();
        generateBackground();
    }

    private void generateBackground() {
        JRDesignBand backgroundBand = (JRDesignBand) getDesign().getBackground();
        if (backgroundBand == null) {
            backgroundBand = new JRDesignBand();
            getDesign().setBackground(backgroundBand);
        }

        backgroundBand.setSplitType(SplitTypeEnum.STRETCH);
        backgroundBand.setHeight(getDesign().getPageHeight() - getDesign().getTopMargin() - getDesign().getBottomMargin());

        // Add Watermark
        String path = "\"" + IMG_PATH + "sysco_logo_bw25.bmp" + "\"";
        JRDesignImage watermark = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
        JRDesignExpression imageExp = new JRDesignExpression();

        imageExp.setText(path);
        imageExp.setValueClass(String.class);
        watermark.setExpression(imageExp);
        watermark.setWidth(WIDTH_401);
        watermark.setHeight(HEIGHT_217);

        int x = (getReport().getOptions().getPrintableWidth() - watermark.getWidth()) / NUMBER_2;
        int y = (backgroundBand.getHeight() - watermark.getHeight()) / NUMBER_2;

        watermark.setX(x);
        watermark.setY(y);
        backgroundBand.addElement(watermark);
    }

    @Override
    protected void setBandsFinalHeight() {
        // Calculate page height
        int pageHeight = 0;
        setMargins();
        pageHeight += getDesign().getTopMargin();
        pageHeight += getDesign().getTitle().getHeight();
        pageHeight += getDesign().getPageHeader().getHeight();
        pageHeight += getDesign().getDetailSection().getBands()[0].getHeight();
        pageHeight += getDesign().getSummary().getHeight();
        pageHeight += getDesign().getBottomMargin();

        getDesign().setPageHeight(pageHeight);
    }

    private void setMargins() {
        getDesign().setTopMargin(30);
        getDesign().setBottomMargin(30);
        getDesign().setLeftMargin(30);
        getDesign().setRightMargin(30);
    }

    @Override
    protected void generateTitleBand() {
        JRDesignBand titleBand = (JRDesignBand) getDesign().getTitle();
        if (titleBand == null) {
            titleBand = new JRDesignBand();
            getDesign().setTitle(titleBand);
        }

        // Add Sysco Logo
        String path = "\"" + IMG_PATH + "omapx_logo.png" + "\"";
        JRDesignImage logoImage = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());
        JRDesignExpression imageExp = new JRDesignExpression();

        imageExp.setText(path);
        imageExp.setValueClass(String.class);

        logoImage.setExpression(imageExp);
        logoImage.setWidth(118);
        logoImage.setHeight(30);
        logoImage.setX(0);
        logoImage.setY(0);

        titleBand.setSplitType(SplitTypeEnum.STRETCH);
        titleBand.setHeight(logoImage.getHeight());
        titleBand.addElement(logoImage);
    }

    @Override
    protected void generateHeaderBand() {
        JRDesignBand headerBand = (JRDesignBand) getDesign().getPageHeader();

        if (headerBand == null) {
            headerBand = new JRDesignBand();
            getDesign().setPageHeader(headerBand);
        }

        //Add Customer Name & Account
        String customerNameTextValue = "Customer Name : " + emptyIfNull(productInfo.get("customerName")) + "\n";
        String customerNumberTextValue = "Account Number : " + emptyIfNull(productInfo.get("customerNumber"));
        customerNameTextValue += customerNumberTextValue;
        int xPos = getReport().getOptions().getPrintableWidth() - (getReport().getOptions().getPrintableWidth() / 4);
        int yPos = 0;
        JRDesignStaticText customerNameText = getJrDesignStaticText(xPos, yPos, customerNameTextValue, FONT_HELVETICA_BOLD, 10, true, 160);
        int mHeight = getMeasuredHeight(customerNameText);
        customerNameText.setHeight(mHeight);
        customerNameText.setHorizontalAlignment(HorizontalAlignEnum.LEFT);

        headerBand.setHeight(customerNameText.getHeight());
        headerBand.addElement(customerNameText);
    }

    @Override
    protected void transformDetailBand() {
        String nutritionInfoFlag = productInfo.get("nutritionInfoFlag");
        JRDesignFrame leftFrame = generateDetailLeftSection();
        JRDesignFrame rightFrame = new JRDesignFrame();
        if (!StringUtils.isEmpty(nutritionInfoFlag)){
            rightFrame.setHeight(0);
        }else {
            rightFrame = generateDetailRightSection();
        }
        rightFrame.setX(leftFrame.getWidth() + 15);
        int max = Math.max(leftFrame.getHeight(), rightFrame.getHeight());
        JRDesignFrame summaryFrame = generateSummarySection(max);

        JRDesignBand detailBand = (JRDesignBand) ((JRDesignSection) getDesign().getDetailSection()).getBandsList().get(0);

        detailBand.setSplitType(SplitTypeEnum.STRETCH);
        detailBand.setHeight(Math.max(leftFrame.getHeight(), rightFrame.getHeight()) + summaryFrame.getHeight());

        detailBand.addElement(leftFrame);
        detailBand.addElement(rightFrame);
        detailBand.addElement(summaryFrame);
    }

    private JRDesignFrame generateDetailLeftSection() {
        String path;
        JRDesignFrame leftFrame = new JRDesignFrame();
        leftFrame.setWidth(getReport().getOptions().getPrintableWidth() / NUMBER_2 + DETAIL_MIDDLE_SECTION_WIDTH);

        // Add Title
        String supc = emptyIfNull(productInfo.get("productCode"));
        String description = emptyIfNull(productInfo.get("description"));
        if (!description.isEmpty()) {
            description += "-";
        }
        description += supc;

        int width = leftFrame.getWidth();
        int xPos =0;
        int yPos = 0;
        JRDesignStaticText headerText =
                getJrDesignStaticText(xPos, yPos, description, FONT_HELVETICA_BOLD, 14, false, width);
        int mHeight = getMeasuredHeight(headerText);
        headerText.setHeight(mHeight);
        headerText.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
        leftPadding(headerText, 4);
        leftFrame.addElement(headerText);
        yPos += headerText.getHeight() + 10;


        // Add Image
            JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());

            if (productInfo.get("imagePath") != null) {
                path = "\"" + productInfo.get("imagePath") + "\"";

            }else {
                path =  "\"" + IMG_PATH + "no-image_360.png" + "\"";
            }
            JRDesignExpression imageExp = new JRDesignExpression();

            imageExp.setText(path);
            imageExp.setValueClass(String.class);
            image.setExpression(imageExp);
            image.setWidth(leftFrame.getWidth());
            image.setHeight(200);
            image.setX(0);
            image.setY(yPos);
            image.setScaleImage(ScaleImageEnum.RETAIN_SHAPE);
            image.setHorizontalAlignment(HorizontalAlignEnum.LEFT);
            image.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
            leftFrame.addElement(image);
            yPos += image.getHeight() + 10;

        // Add Description Title
        JRDesignStaticText descText =
                getJrDesignStaticText(0, yPos, TEXT_DESCRIPTION, FONT_HELVETICA_BOLD, 10, true, width);
        mHeight = getMeasuredHeight(descText);
        descText.setHeight(mHeight);
        leftFrame.addElement(descText);
        yPos += descText.getHeight() + 2;

        // Add Description Value
        String descValueText = emptyIfNull(productInfo.get("thirdLineDescription"));
        descValueText = StringUtils.isEmpty(descValueText) ? TEXT_INFORMATION_NOT_AVAILABLE : descValueText;

        JRDesignStaticText descValue =
                getJrDesignStaticText(0, yPos, descValueText, "", 10, false, width);
        mHeight = getMeasuredHeight(descValue);
        descValue.setHeight(mHeight);
        leftFrame.addElement(descValue);
        yPos += descValue.getHeight() + 5;

        // Add Information Title
        JRDesignStaticText infoText =
                getJrDesignStaticText(0, yPos, TEXT_INFORMATION, FONT_HELVETICA_BOLD, 10, true, width);
        mHeight = getMeasuredHeight(infoText);
        infoText.setHeight(mHeight);
        leftFrame.addElement(infoText);
        yPos += infoText.getHeight() + 2;

        // Add Information Value
        StringBuilder infoValueStringBuilder = new StringBuilder();

        infoValueStringBuilder.append("Pack/Size : ").append(emptyIfNull(productInfo.get("pack")))
                .append(" / ").append(emptyIfNull(productInfo.get("size"))).append("\n");
        infoValueStringBuilder.append("Material Description : ").append(emptyIfNull(productInfo.get("description"))).append("\n");
        infoValueStringBuilder.append("Material SUPC : ")
                .append(emptyIfNull(productInfo.get("productCode")));

        JRDesignStaticText infoValue =
                getJrDesignStaticText(0, yPos, infoValueStringBuilder.toString(), "", 10, false, width);
        mHeight = getMeasuredHeight(infoValue);
        infoValue.setHeight(mHeight);
        leftFrame.addElement(infoValue);
        yPos += infoValue.getHeight() + 5;


        // Add Ingredients Title
        JRDesignStaticText ingredientText =
                getJrDesignStaticText(0, yPos, TEXT_INGREDIENTS, FONT_HELVETICA_BOLD, 10, false, width);
        mHeight = getMeasuredHeight(ingredientText);
        ingredientText.setHeight(mHeight);
        ingredientText.setVerticalAlignment(VerticalAlignEnum.BOTTOM);
        leftFrame.addElement(ingredientText);
        yPos += ingredientText.getHeight() + 2;

        // Add Ingredients Value
        String ingredientValueText = emptyIfNull(productInfo.get("ingredients"));
        ingredientValueText = StringUtils.isEmpty(ingredientValueText) ? TEXT_INFORMATION_NOT_AVAILABLE : ingredientValueText;
        JRDesignStaticText ingredientValue =
                getJrDesignStaticText(0, yPos, ingredientValueText, "", 10, false, width);
        mHeight = getMeasuredHeight(ingredientValue);
        ingredientValue.setHeight(mHeight);
        leftFrame.addElement(ingredientValue);
        yPos += ingredientValue.getHeight() + 5;

        // Add Allergens Title
        String allergensValueText = emptyIfNull(productInfo.get("allergens"));
        if (!StringUtils.isEmpty(allergensValueText)) {
            JRDesignStaticText allergensText =
                    getJrDesignStaticText(0, yPos, TEXT_ALLERGENS, FONT_HELVETICA_BOLD, 10, false, width);
            mHeight = getMeasuredHeight(allergensText);
            allergensText.setHeight(mHeight);
            leftFrame.addElement(allergensText);
            yPos += allergensText.getHeight() + 2;

            // Add Allergens Value
            yPos = addAllergenIcons(leftFrame, yPos, allergensValueText) + 5;
        }

        // Add May Allergens Value
        String mayAllergensValueText = emptyIfNull(productInfo.get("mayAllergens"));
        if (!StringUtils.isEmpty(mayAllergensValueText)){
            String mayAllergens = "May Contain Allergens : "+mayAllergensValueText+"\n";
            JRDesignStaticText mayAllergensValue =
                    getJrDesignStaticText(0, yPos, mayAllergens, "", 10, false, width);
            mHeight = getMeasuredHeight(mayAllergensValue);
            mayAllergensValue.setHeight(mHeight);
            leftFrame.addElement(mayAllergensValue);
            yPos += mayAllergensValue.getHeight() + 5;
        }

        leftFrame.setHeight(yPos);
        leftFrame.setX(0);
        leftFrame.setY(0);

        return leftFrame;
    }

    private int addAllergenIcons(JRDesignFrame leftFrame, int nextTop, String allergensValueText) {

        String[] allergenArray = allergensValueText.split(",");
        int xPos = 0;
        int allergenHeight = 0;
        for (int i = 0; i < allergenArray.length; i++) {
            try {
                String res = Allergen.valueOf(allergenArray[i]).getValue();
                if (res != null) {
                    String imagePath = getPath(res);

                    JRDesignImage image = getJrDesignImage(nextTop, xPos, imagePath);
                    int yPos = nextTop + image.getHeight() + 2;
                    JRDesignStaticText imageDesignText = getJrDesignStaticText(xPos, yPos, allergenArray[i], FONT_HELVETICA_BOLD, 8, true, 50);
                    imageDesignText.setHeight(10);
                    xPos += imageDesignText.getWidth() + 2;
                    leftFrame.addElement(image);
                    leftFrame.addElement(imageDesignText);
                    if (allergenHeight < image.getHeight() + imageDesignText.getHeight()) {
                        allergenHeight = image.getHeight() + imageDesignText.getHeight();
                    }

                    // add allergen in next row
                    if (xPos + 50 > 275) {
                        xPos = 0;
                        nextTop += allergenHeight + 5;
                        allergenHeight = 0;
                    }
                }
            } catch (IllegalArgumentException e) {
                log.error("Invalid Allergen", e);
            }
        }
        nextTop += allergenHeight;
        return nextTop;
    }

    private String getPath(String fileName) {
        String imageName = "\"" + IMG_PATH + fileName + "\"";
        return imageName;
    }


    private JRDesignImage getJrDesignImage(int yPos, int xPos, String resPath) {

        JRDesignImage image = new JRDesignImage(new JRDesignStyle().getDefaultStyleProvider());

        JRDesignExpression imageExp = new JRDesignExpression();

        imageExp.setText(resPath);
        imageExp.setValueClass(String.class);
        image.setExpression(imageExp);
        image.setWidth(25);
        image.setHeight(25);
        image.setX(xPos);
        image.setY(yPos);
        image.setScaleImage(ScaleImageEnum.RETAIN_SHAPE);
        return image;
    }

    private JRDesignStaticText getJrDesignStaticText(int xPos, int yPos, String text, String pdfFontName, int fontSize, boolean bold, int width) {
        JRDesignStaticText jrDesignStaticText = new JRDesignStaticText();
        jrDesignStaticText.setText(text);
        if (!StringUtils.isEmpty(pdfFontName))
            jrDesignStaticText.setPdfFontName(pdfFontName);
        jrDesignStaticText.setFontSize(fontSize);
        jrDesignStaticText.setBold(bold);
        jrDesignStaticText.setWidth(width);
        jrDesignStaticText.setX(xPos);
        jrDesignStaticText.setY(yPos);
        if( !StringUtils.isEmpty(text) && !text.equalsIgnoreCase(productInfo.get("calories")) ) {
            jrDesignStaticText.setVerticalAlignment(VerticalAlignEnum.MIDDLE);
        }
        return jrDesignStaticText;
    }

    private JRDesignFrame generateDetailRightSection() {
        JRDesignFrame rightFrame = new JRDesignFrame();
        rightFrame.setWidth((getReport().getOptions().getPrintableWidth() - DETAIL_MIDDLE_SECTION_WIDTH) / NUMBER_2);

        // Add Nutrition grid
        int nextTop = populateNutritionGrid(rightFrame) + 5;
        JRDesignRectangle nutritionRectangle = new JRDesignRectangle();
        nutritionRectangle.getLinePen().setLineWidth(2.0f);
        nutritionRectangle.setWidth(rightFrame.getWidth());
        nutritionRectangle.setHeight(nextTop);
        nutritionRectangle.setX(0);
        nutritionRectangle.setY(0);
        nutritionRectangle.setMode(ModeEnum.TRANSPARENT);

        rightFrame.addElement(0, nutritionRectangle);

        int top = nextTop;

        String PFSFlag = emptyIfNull(productInfo.get("productFormulationStatement"));
        String CNFlag = emptyIfNull(productInfo.get("childNutrition"));

        if ("Yes".equals(PFSFlag) || "Y".equals(PFSFlag) || "Yes".equals(CNFlag) || "Y".equals(CNFlag)) {
            nextTop = populateK12Grid(rightFrame, nextTop + 10) + 5;
            JRDesignRectangle k12EquivalentsRectangle = new JRDesignRectangle();
            k12EquivalentsRectangle.getLinePen().setLineWidth(2.0f);
            k12EquivalentsRectangle.setWidth(rightFrame.getWidth());
            k12EquivalentsRectangle.setHeight(nextTop - top);
            k12EquivalentsRectangle.setX(0);
            k12EquivalentsRectangle.setY(nutritionRectangle.getHeight() + 5);
            k12EquivalentsRectangle.setMode(ModeEnum.TRANSPARENT);

            rightFrame.addElement(0, k12EquivalentsRectangle);
        }

        rightFrame.setHeight(nextTop + 10);
        rightFrame.setX((getReport().getOptions().getPrintableWidth() - DETAIL_MIDDLE_SECTION_WIDTH) / NUMBER_2 + DETAIL_MIDDLE_SECTION_WIDTH);
        rightFrame.setY(0);

        return rightFrame;
    }

    private int populateK12Grid(JRDesignFrame rightFrame, int yPos) {
        // Add K12 Equivalents
        int width = rightFrame.getWidth() - NUTRITION_GRID_PADDING_LEFT - NUTRITION_GRID_PADDING_RIGHT;
        int x = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText k12EquivalentsText =
                getJrDesignStaticText(x, yPos, TEXT_K12_EQUIVALENTS, FONT_HELVETICA_BOLD, 28, true, width);
        int mHeight = getMeasuredHeight(k12EquivalentsText);
        k12EquivalentsText.setHeight(mHeight);
        leftPadding(k12EquivalentsText, 5);
        rightFrame.addElement(k12EquivalentsText);

        int contentWidth = k12EquivalentsText.getWidth();
        yPos += (k12EquivalentsText.getHeight() - 30);
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 6.0f, NUTRITION_GRID_PADDING_LEFT * 2) + 5;

        // Add PFS
        String PFSFlag = emptyIfNull(productInfo.get("productFormulationStatement"));
        JRDesignTextField productFormulationText = new JRDesignTextField();
        JRDesignExpression expression = new JRDesignExpression();
        expression.setValueClass(java.lang.String.class);
        expression.setText("\"" + PRODUCT_FORMULATION_STATEMENT + "\"");
        productFormulationText.setExpression(expression);
        productFormulationText.setPdfFontName(FONT_HELVETICA_BOLD);
        productFormulationText.setFontSize(10);
        productFormulationText.setWidth((contentWidth / NUMBER_2) + 40);
        productFormulationText.setHeight(18);
        productFormulationText.setX(NUTRITION_GRID_PADDING_LEFT);
        productFormulationText.setY(yPos + 3);
        leftPadding(productFormulationText, 5);

        rightFrame.addElement(productFormulationText);

        String productFormulationValue = changeValue(emptyIfNull(PFSFlag), false);
        width = (contentWidth / NUMBER_2) - 40;
        x = NUTRITION_GRID_PADDING_LEFT + productFormulationText.getWidth();
        JRDesignStaticText productFormulationValueText =
                getJrDesignStaticText(x, yPos, productFormulationValue, "", 10, false, width);
        productFormulationValueText.setHeight(18);
        productFormulationValueText.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(productFormulationValueText);
        yPos += productFormulationValueText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        if ("Y".equals(PFSFlag) || "Yes".equals(PFSFlag)) {
            width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            // Add Portion Size
            /*JRDesignStaticText portionSizeText =
                    getJrDesignStaticText(x, yPos, TEXT_PORTION_SIZE, "", 10, false, width);
            portionSizeText.setHeight(18);
            leftPadding(portionSizeText, 10);
            rightFrame.addElement(portionSizeText);

            width = contentWidth / NUMBER_2 - 20;
            x = NUTRITION_GRID_PADDING_LEFT + portionSizeText.getWidth();
            String portionSizeValueStr = emptyIfNull(productInfo.get("portionSize"));
            JRDesignStaticText portionSizeValue =
                    getJrDesignStaticText(x, yPos, portionSizeValueStr, "", 10, false, width);
            portionSizeValue.setHeight(18);
            portionSizeValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(portionSizeValue);
            yPos += portionSizeValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);*/

            // Add Grain Bread
            String grainBreadStr = "Grain/Bread(oz eq)";
            width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText grainBreadText =
                    getJrDesignStaticText(x, yPos, grainBreadStr, "", 10, false, width);
            grainBreadText.setHeight(18);
            leftPadding(grainBreadText, 10);
            rightFrame.addElement(grainBreadText);

            String grainBreadValueStr = productInfo.get("grainBread");
            width = contentWidth / NUMBER_2 - 20;
            x = NUTRITION_GRID_PADDING_LEFT + grainBreadText.getWidth();
            JRDesignStaticText grainBreadValue =
                    getJrDesignStaticText(x, yPos, grainBreadValueStr, "", 10, false, width);
            grainBreadValue.setHeight(18);
            grainBreadValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(grainBreadValue);
            yPos += grainBreadValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

            // Add K12 Fruit
            width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText fruitText =
                    getJrDesignStaticText(x, yPos, FRUIT_CUP, "", 10, false, width);
            fruitText.setHeight(18);
            leftPadding(fruitText, 10);
            rightFrame.addElement(fruitText);

            String fruitValueStr = emptyIfNull(productInfo.get("fruit"));
            width = contentWidth / NUMBER_2 - 20;
            x = NUTRITION_GRID_PADDING_LEFT + fruitText.getWidth();
            JRDesignStaticText fruitValue =
                    getJrDesignStaticText(x, yPos, fruitValueStr, "", 10, false, width);
            fruitValue.setHeight(18);
            fruitValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(fruitValue);
            yPos += fruitValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

            // Add Vegetable Red Orange
            JRDesignTextField vegetableRedOrangeText = new JRDesignTextField();
            expression = new JRDesignExpression();
            expression.setValueClass(java.lang.String.class);
            expression.setText("\"" + VEGETABLE_RED_ORANGE + "\"");
            vegetableRedOrangeText.setExpression(expression);
            vegetableRedOrangeText.setFontSize(10);
            vegetableRedOrangeText.setWidth((contentWidth / 2) + 40);
            vegetableRedOrangeText.setHeight(18);
            vegetableRedOrangeText.setX(NUTRITION_GRID_PADDING_LEFT);
            vegetableRedOrangeText.setY(yPos + 3);
            leftPadding(vegetableRedOrangeText, 10);

            rightFrame.addElement(vegetableRedOrangeText);

            String vegetableRedOrangeValueStr = emptyIfNull(productInfo.get("vegetableRedOrange"));
            width = (contentWidth / NUMBER_2) - 40;
            x = NUTRITION_GRID_PADDING_LEFT + vegetableRedOrangeText.getWidth();
            JRDesignStaticText vegetableRedOrangeValue =
                    getJrDesignStaticText(x, yPos, vegetableRedOrangeValueStr, "", 10, false, width);
            vegetableRedOrangeValue.setHeight(18);
            vegetableRedOrangeValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(vegetableRedOrangeValue);
            yPos += vegetableRedOrangeValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

            // Add Vegetable Dark Green
            JRDesignTextField vegetableDarkGreenText = new JRDesignTextField();
            expression = new JRDesignExpression();
            expression.setValueClass(java.lang.String.class);
            expression.setText("\"" + VEGETABLE_DARK_GREEN + "\"");
            vegetableDarkGreenText.setExpression(expression);
            vegetableDarkGreenText.setFontSize(10);
            vegetableDarkGreenText.setWidth((contentWidth / NUMBER_2) + 40);
            vegetableDarkGreenText.setHeight(18);
            vegetableDarkGreenText.setX(NUTRITION_GRID_PADDING_LEFT);
            vegetableDarkGreenText.setY(yPos + 3);
            leftPadding(vegetableDarkGreenText, 10);

            rightFrame.addElement(vegetableDarkGreenText);


            String vegetableDarkGreenValueStr = emptyIfNull(productInfo.get("vegetableDarkGreen"));
            x = NUTRITION_GRID_PADDING_LEFT + vegetableDarkGreenText.getWidth();
            width = (contentWidth / NUMBER_2) - 40;
            JRDesignStaticText vegetableDarkGreenValue =
                    getJrDesignStaticText(x, yPos, vegetableDarkGreenValueStr, "", 10, false, width);
            vegetableDarkGreenValue.setHeight(18);
            vegetableDarkGreenValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(vegetableDarkGreenValue);
            yPos += vegetableDarkGreenValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

            // Add Vegetable Starchy
            width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText vegetableStarchyText =
                    getJrDesignStaticText(x, yPos, VEGETABLE_STARCHY, "", 10, false, width);
            vegetableStarchyText.setHeight(18);
            leftPadding(vegetableStarchyText, 10);
            rightFrame.addElement(vegetableStarchyText);

            String vegetableStarchyValueStr = emptyIfNull(productInfo.get("vegetableStarchy"));
            x = NUTRITION_GRID_PADDING_LEFT + vegetableStarchyText.getWidth();
            width = contentWidth / NUMBER_2 - 20;
            JRDesignStaticText vegetableStarchyValue =
                    getJrDesignStaticText(x, yPos, vegetableStarchyValueStr, "", 10, false, width);
            vegetableStarchyValue.setHeight(18);
            vegetableStarchyValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(vegetableStarchyValue);
            yPos += vegetableStarchyValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

            // Add Vegetable Beans Peas
            width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText vegetableBeansPeasText =
                    getJrDesignStaticText(x, yPos, VEGETABLE_BEANS_PEAS, "", 10, false, width);
            vegetableBeansPeasText.setHeight(18);
            leftPadding(vegetableBeansPeasText, 10);
            rightFrame.addElement(vegetableBeansPeasText);

            width = contentWidth / NUMBER_2 - 20;
            x = NUTRITION_GRID_PADDING_LEFT + vegetableBeansPeasText.getWidth();
            String vegetableBeansPeasValueStr = emptyIfNull(productInfo.get("vegetableBeansPeas"));
            JRDesignStaticText vegetableBeansPeasValue =
                    getJrDesignStaticText(x, yPos, vegetableBeansPeasValueStr, "", 10, false, width);
            vegetableBeansPeasValue.setHeight(18);
            vegetableBeansPeasValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(vegetableBeansPeasValue);
            yPos += vegetableBeansPeasValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

            // Add Vegetables Other
            width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText vegetableOtherText =
                    getJrDesignStaticText(x, yPos, VEGETABLE_OTHER, "", 10, false, width);
            vegetableOtherText.setHeight(18);
            leftPadding(vegetableOtherText, 10);
            rightFrame.addElement(vegetableOtherText);

            String vegetableOtherValueStr = emptyIfNull(productInfo.get("vegetableOther"));
            width = contentWidth / NUMBER_2 - 20;
            x = NUTRITION_GRID_PADDING_LEFT + vegetableOtherText.getWidth();
            JRDesignStaticText vegetableOtherValue =
                    getJrDesignStaticText(x, yPos, vegetableOtherValueStr, "", 10, false, width);
            vegetableOtherValue.setHeight(18);
            vegetableOtherValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(vegetableOtherValue);
            yPos += vegetableOtherValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        }
        // Add child Nutrition
        String CNFlag = emptyIfNull(productInfo.get("childNutrition"));
        width = contentWidth / NUMBER_2 + 20;
        x = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText childNutritionText =
                getJrDesignStaticText(x, yPos, CHILD_NUTRITION, FONT_HELVETICA_BOLD, 10, false, width);
        childNutritionText.setHeight(18);
        leftPadding(childNutritionText, 5);
        rightFrame.addElement(childNutritionText);

        String childNutritionValueStr = changeValue(emptyIfNull(CNFlag), false);
        width = contentWidth / NUMBER_2 - 20;
        x = NUTRITION_GRID_PADDING_LEFT + childNutritionText.getWidth();
        JRDesignStaticText childNutritionValue =
                getJrDesignStaticText(x, yPos, childNutritionValueStr, "", 10, false, width);
        childNutritionValue.setHeight(18);
        childNutritionValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(childNutritionValue);
        yPos += childNutritionValue.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        if ("Yes".equals(CNFlag) || "Y".equals(CNFlag)) {
            //Add Portion Size
            /*width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText portionSizeText =
                    getJrDesignStaticText(x, yPos, TEXT_PORTION_SIZE, "", 10, false, width);
            portionSizeText.setHeight(18);
            leftPadding(portionSizeText, 10);
            rightFrame.addElement(portionSizeText);

            String portionSizeValueStr = emptyIfNull(productInfo.get("portionSize"));
            width = contentWidth / NUMBER_2 - 20;
            x = NUTRITION_GRID_PADDING_LEFT + portionSizeText.getWidth();
            JRDesignStaticText portionSizeValue =
                    getJrDesignStaticText(x, yPos, portionSizeValueStr, "", 10, false, width);
            portionSizeValue.setHeight(18);
            portionSizeValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(portionSizeValue);
            yPos += portionSizeValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);*/

            // Add Meat Alt
            width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText meatAltText =
                    getJrDesignStaticText(x, yPos, MEAT_MEAT_ALT_OZ_EQ, "", 10, false, width);
            meatAltText.setHeight(18);
            leftPadding(meatAltText, 10);
            rightFrame.addElement(meatAltText);

            String meatAltValueStr = emptyIfNull(productInfo.get("meat")) + OZ_EQ;
            width = contentWidth / NUMBER_2 - 20;
            x = NUTRITION_GRID_PADDING_LEFT + meatAltText.getWidth();
            JRDesignStaticText meatAltValue = getJrDesignStaticText(x, yPos, meatAltValueStr, "", 10, false, width);
            meatAltValue.setHeight(18);
            meatAltValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(meatAltValue);
            yPos += meatAltValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);
        }
        if (!StringUtils.isEmpty(PFSFlag) || !StringUtils.isEmpty(CNFlag)) {
            // Add CN Identification
            width = contentWidth / NUMBER_2 + 20;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText identificationText =
                    getJrDesignStaticText(x, yPos, CN_IDENTIFICATION, "", 10, false, width);
            identificationText.setWidth(contentWidth / NUMBER_2 + 20);
            identificationText.setHeight(18);
            leftPadding(identificationText, 5);
            rightFrame.addElement(identificationText);

            String identificationValueStr = emptyIfNull(productInfo.get("cnIdentification"));
            width = contentWidth / NUMBER_2 - 20;
            x = NUTRITION_GRID_PADDING_LEFT + identificationText.getWidth();
            JRDesignStaticText identificationValue =
                    getJrDesignStaticText(x, yPos, identificationValueStr, "", 10, false, width);
            identificationValue.setHeight(18);
            identificationValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
            rightFrame.addElement(identificationValue);
            yPos += identificationValue.getHeight();
            yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

            // Add Notes
            String notesValue = emptyIfNull(productInfo.get("notes"));
            String notesTextStr = String.join("", NOTES, notesValue);
            width = contentWidth;
            x = NUTRITION_GRID_PADDING_LEFT;
            JRDesignStaticText notesText =
                    getJrDesignStaticText(x, yPos, notesTextStr, "", 9, false, width);
            notesText.setHeight((notesValue.length() + 6) / 55 * 12 + 12);
            leftPadding(notesText, 5);
            Map attributes = new HashMap();
            FontUtil fontUtil = FontUtil.getInstance(jasperReportsContext);
            JRTextMeasurerUtil jrTextMeasurerUtil = JRTextMeasurerUtil.getInstance(jasperReportsContext);
            attributes.putAll(fontUtil.getAttributesWithoutAwtFont(attributes, notesText));
            JRStyledText styledText = JRStyledTextParser.getInstance().getStyledText(attributes, notesText.getText(), false, Locale.US);
            JRMeasuredText measuredText = jrTextMeasurerUtil.createTextMeasurer(notesText).measure(styledText, 0, 1000, false);
            notesText.setHeight((int) Math.ceil(measuredText.getTextHeight()));
            rightFrame.addElement(notesText);

            yPos += notesText.getHeight();
        }
        return yPos;
    }

    private int populateNutritionGrid(JRDesignFrame rightFrame) {
        // Add Nutrition Facts
        int width = rightFrame.getWidth() - NUTRITION_GRID_PADDING_LEFT - NUTRITION_GRID_PADDING_RIGHT;
        int xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText nutritionFactsText =
                getJrDesignStaticText(xPos, 0, TEXT_NUTRITION_FACTS, FONT_HELVETICA_BOLD, 32, true, width);
        nutritionFactsText.setStretchType(StretchTypeEnum.NO_STRETCH);
        int mHeight = getMeasuredHeight(nutritionFactsText);
        nutritionFactsText.setHeight(mHeight);
        leftPadding(nutritionFactsText, 5);

        int contentWidth = nutritionFactsText.getWidth();
        int yPos = (nutritionFactsText.getHeight() - 40);
        rightFrame.addElement(nutritionFactsText);

        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);


        // Add Serving Size Label
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText servingSizeText =
                getJrDesignStaticText(xPos, yPos, TEXT_SERVING_SIZE, FONT_HELVETICA_BOLD, 15, true, width);
        mHeight = getMeasuredHeight(servingSizeText);
        servingSizeText.setHeight(mHeight);
        leftPadding(servingSizeText, 5);
        rightFrame.addElement(servingSizeText);

        // Add Serving size
        width =contentWidth / NUMBER_2;
        String servingSize = emptyIfNull(productInfo.get("servingSize"));
        if (StringUtils.isEmpty(servingSize)) {
            servingSize += " (" + emptyIfNull(productInfo.get("servingSizeWeight")) + "g)";
        }
        xPos = NUTRITION_GRID_PADDING_LEFT + servingSizeText.getWidth();
        JRDesignStaticText servingSizeValue =
                getJrDesignStaticText(xPos, yPos, servingSize, FONT_HELVETICA_BOLD, 15, true, width);
        mHeight = getMeasuredHeight(servingSizeValue);
        servingSizeValue.setHeight(mHeight);
        servingSizeValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(servingSizeValue);
        yPos += servingSizeValue.getHeight() + 3;
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 6.0f, NUTRITION_GRID_PADDING_LEFT * 2);


        // Add  Per Serving
        width = rightFrame.getWidth() - NUTRITION_GRID_PADDING_LEFT - NUTRITION_GRID_PADDING_RIGHT;
        xPos = NUTRITION_GRID_PADDING_LEFT * 2;
        JRDesignStaticText amtPerServingText =
                getJrDesignStaticText(xPos, yPos, TEXT_AMOUNT_PER_SERVING, FONT_HELVETICA_BOLD, 15, true, width);
        amtPerServingText.setFontSize(15);
        amtPerServingText.setHeight(18);
        amtPerServingText.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        rightFrame.addElement(amtPerServingText);
        yPos += amtPerServingText.getHeight();

        // Add Calories
        width = contentWidth/NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText caloriesText =
                getJrDesignStaticText(xPos, yPos, TEXT_CALORIES, FONT_HELVETICA_BOLD, 28, true, width);
        leftPadding(caloriesText, 5);
        mHeight = getMeasuredHeight(caloriesText);
        caloriesText.setHeight(mHeight);
        rightFrame.addElement(caloriesText);

        // Add Calories from Fat
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + caloriesText.getWidth();
        String caloriesValueStr = emptyIfNull(productInfo.get("calories"));
        JRDesignStaticText caloriesValue =
                getJrDesignStaticText(xPos, yPos, caloriesValueStr, FONT_HELVETICA_BOLD, 28, true, width);
        caloriesValue.setHeight(mHeight);
        caloriesValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(caloriesValue);
        yPos += (caloriesText.getHeight() - 30);
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 3.0f, NUTRITION_GRID_PADDING_LEFT * 2) + 3;

        // Add Daily Value
        width =contentWidth;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText dailyValue =
                getJrDesignStaticText(xPos, yPos, TEXT_DAILY_VALUE, FONT_HELVETICA_BOLD, 12, true, width);
        mHeight = getMeasuredHeight(dailyValue);
        dailyValue.setHeight(mHeight);
        dailyValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        dailyValue.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        rightFrame.addElement(dailyValue);
        yPos += dailyValue.getHeight() + 3;
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Total Fat
        width = contentWidth/NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText totalFatText =
                getJrDesignStaticText(xPos, yPos, TEXT_TOTAL_FAT, FONT_HELVETICA_BOLD, 10, true, width);
        totalFatText.setHeight(18);
        leftPadding(totalFatText, 5);
        rightFrame.addElement(totalFatText);

        // Add Total Fat value
        String totalFatValueStr = emptyIfNull(productInfo.get("totalFat")) ;
        totalFatValueStr = String.join("",totalFatValueStr,emptyIfNull(productInfo.get("totalFatUOM")));
        width =50;
        xPos = NUTRITION_GRID_PADDING_LEFT + totalFatText.getWidth() - 50;
        JRDesignStaticText totalFatValue =
                getJrDesignStaticText(xPos, yPos, totalFatValueStr, "", 10, false, width);
        totalFatValue.setHeight(18);
        rightFrame.addElement(totalFatValue);

        // Add Total Fat percent
        String totalFatPercentStr = emptyIfNull(productInfo.get("totalFatDailyValue"));
        totalFatPercentStr = String.join("",totalFatPercentStr,"%");
        width = contentWidth/NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + contentWidth / NUMBER_2;
        JRDesignStaticText totalFatPercent =
                getJrDesignStaticText(xPos, yPos, totalFatPercentStr, FONT_HELVETICA_BOLD, 10, true, width);
        totalFatPercent.setHeight(18);
        totalFatPercent.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(totalFatPercent);
        yPos += totalFatText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Saturated Fat
        String satFatTextStr =emptyIfNull(productInfo.get("saturatedFat")) ;
        satFatTextStr = String.join("",TEXT_SATURATED_FAT,satFatTextStr,productInfo.get("saturatedFatUOM"));
        width = contentWidth/NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText satFatText =
                getJrDesignStaticText(xPos, yPos, satFatTextStr, "", 10, false, width);
        satFatText.setHeight(18);
        leftPadding(satFatText, 10);
        rightFrame.addElement(satFatText);

        // Add Saturated Fat percent
        String satFatPercentStr = emptyIfNull(productInfo.get("saturatedFatDailyValue")) + "%";
        satFatPercentStr = String.join("",satFatPercentStr);
        width = contentWidth/NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + satFatText.getWidth();
        JRDesignStaticText satFatPercent =
                getJrDesignStaticText(xPos, yPos, satFatPercentStr, "", 10, true, width);
        satFatPercent.setHeight(18);
        satFatPercent.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(satFatPercent);
        yPos += satFatText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Trans Fat text
        width = 43;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText transText =
                getJrDesignStaticText(xPos, yPos, TEXT_TRANS, FONT_HELVETICA_OBLIQUE, 10, true, width);
        transText.setHeight(18);
        leftPadding(transText, 10);
        rightFrame.addElement(transText);

        // Add Fat text
        String fatTextStr = emptyIfNull(productInfo.get("transFat"));
        fatTextStr = String.join("",TEXT_FAT, fatTextStr , productInfo.get("transFatUOM"));
        width = (contentWidth/NUMBER_2)-50;
        xPos = transText.getWidth();
        JRDesignStaticText fatText =
                getJrDesignStaticText(xPos, yPos, fatTextStr, "", 10, false, width);
        fatText.setHeight(18);
        rightFrame.addElement(fatText);
        yPos += fatText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Cholesterol
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText cholesterolText =
                getJrDesignStaticText(xPos, yPos, TEXT_CHOLESTEROL, FONT_HELVETICA_BOLD, 10, true, width);
        cholesterolText.setHeight(18);
        leftPadding(cholesterolText, 5);
        rightFrame.addElement(cholesterolText);

        // Add Cholesterol value
        String cholesterolValueStr = emptyIfNull(productInfo.get("cholesterol"));
        cholesterolValueStr = String.join("", cholesterolValueStr, productInfo.get("cholesterolUOM"));
        width = 50;
        xPos = NUTRITION_GRID_PADDING_LEFT + cholesterolText.getWidth() - 50;
        JRDesignStaticText cholesterolValue =
                getJrDesignStaticText(xPos, yPos, cholesterolValueStr, "", 10, false, width);
        cholesterolValue.setHeight(18);
        rightFrame.addElement(cholesterolValue);

        // Add Cholesterol value
        String cholPercentStr = emptyIfNull(productInfo.get("cholesterolDailyValue"));
        cholPercentStr = String.join("", cholPercentStr, "%");
        width = (contentWidth / NUMBER_2) - cholesterolValue.getWidth();
        xPos = NUTRITION_GRID_PADDING_LEFT + cholesterolText.getWidth() + cholesterolValue.getWidth();
        JRDesignStaticText cholPercent =
                getJrDesignStaticText(xPos, yPos, cholPercentStr, FONT_HELVETICA_BOLD, 10, true, width);
        cholPercent.setHeight(18);
        cholPercent.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(cholPercent);
        yPos += cholesterolText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Sodium
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText sodiumText =
                getJrDesignStaticText(xPos, yPos, TEXT_SODIUM, FONT_HELVETICA_BOLD, 10, true, width);
        sodiumText.setHeight(18);
        sodiumText.setY(yPos);
        leftPadding(sodiumText, 5);
        rightFrame.addElement(sodiumText);

        // Add Sodium value
        String sodiumValueStr = productInfo.get("sodium");
        sodiumValueStr = String.join("", sodiumValueStr, productInfo.get("sodiumUOM"));
        width = 50;
        xPos = NUTRITION_GRID_PADDING_LEFT + sodiumText.getWidth() - 50;
        JRDesignStaticText sodiumValue =
                getJrDesignStaticText(xPos, yPos, sodiumValueStr, "", 10, false, width);
        sodiumValue.setHeight(18);
        rightFrame.addElement(sodiumValue);

        // Add Sodium percent
        String sodiumPercentStr = emptyIfNull(productInfo.get("sodiumDailyValue"));
        sodiumPercentStr = String.join("", sodiumPercentStr, "%");
        width = (contentWidth / NUMBER_2) - sodiumValue.getWidth();
        xPos = NUTRITION_GRID_PADDING_LEFT + sodiumText.getWidth() + sodiumValue.getWidth();
        JRDesignStaticText sodiumPercent =
                getJrDesignStaticText(xPos, yPos, sodiumPercentStr, FONT_HELVETICA_BOLD, 10, true, width);
        sodiumPercent.setHeight(18);
        sodiumPercent.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(sodiumPercent);
        yPos += sodiumText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Total Carbohydrate
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText totalCarbText =
                getJrDesignStaticText(xPos, yPos, TEXT_TOTAL_CARBOHYDRATE, FONT_HELVETICA_BOLD, 10, true, width);
        totalCarbText.setHeight(18);
        leftPadding(totalCarbText, 5);
        rightFrame.addElement(totalCarbText);

        // Add Total Carb value
        String totalCarbValueStr = emptyIfNull(productInfo.get("totalCarbohydrate"));
        totalCarbValueStr = String.join("", totalCarbValueStr, productInfo.get("totalCarbohydrateUOM"));
        width = 50;
        xPos = NUTRITION_GRID_PADDING_LEFT + totalCarbText.getWidth();
        JRDesignStaticText totalCarbValue =
                getJrDesignStaticText(xPos, yPos, totalCarbValueStr, "", 10, false, width);
        totalCarbValue.setHeight(18);
        rightFrame.addElement(totalCarbValue);

        // Add Total Carb percent
        String totalCarbPercentStr = emptyIfNull(productInfo.get("totalCarbohydrateDailyValue"));
        totalCarbPercentStr = String.join("", totalCarbPercentStr, "%");
        width = (contentWidth / NUMBER_2) - totalCarbValue.getWidth();
        xPos = NUTRITION_GRID_PADDING_LEFT + totalCarbText.getWidth() + totalCarbValue.getWidth();
        JRDesignStaticText totalCarbPercent =
                getJrDesignStaticText(xPos, yPos, totalCarbPercentStr, FONT_HELVETICA_BOLD, 10, true, width);
        totalCarbPercent.setText(emptyIfNull(productInfo.get("totalCarbohydrateDailyValue")) + "%");
        totalCarbPercent.setHeight(18);
        totalCarbPercent.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(totalCarbPercent);
        yPos += totalCarbText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Dietary Fiber
        String fiberTextStr = emptyIfNull(productInfo.get("dietaryFiber"));
        fiberTextStr = String.join("", TEXT_DIETARY_FIBER, fiberTextStr, productInfo.get("dietaryFiberUOM"));
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText fiberText =
                getJrDesignStaticText(xPos, yPos, fiberTextStr, "", 10, false, width);
        fiberText.setHeight(18);
        leftPadding(fiberText, 10);
        rightFrame.addElement(fiberText);

        // Add Fiber percent
        String fiberPercentStr = emptyIfNull(productInfo.get("dietaryFiberDailyValue"));
        fiberPercentStr = String.join("", fiberPercentStr, "%");
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + fiberText.getWidth();
        JRDesignStaticText fiberPercent =
                getJrDesignStaticText(xPos, yPos, fiberPercentStr, FONT_HELVETICA_BOLD, 10, true, width);
        fiberPercent.setHeight(18);
        fiberPercent.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(fiberPercent);
        yPos += fiberText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Sugars
        String sugarTextStr = emptyIfNull(productInfo.get("sugars"));
        sugarTextStr = String.join("", TEXT_SUGARS, sugarTextStr, productInfo.get("sugarsUOM"));
        width = contentWidth;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText sugarText =
                getJrDesignStaticText(xPos, yPos, sugarTextStr, "", 10, false, width);
        sugarText.setHeight(18);
        leftPadding(sugarText, 10);
        rightFrame.addElement(sugarText);
        yPos += sugarText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 4), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 4);

        //Included Sugars
        String sugarAmount = emptyIfNull(productInfo.get("includedSugars"));
        String sugarUOM = emptyIfNull(productInfo.get("includedSugarsUOM"));
        String addedSugar = "".equals(sugarAmount) ? "NA" : String.join("", sugarAmount, sugarUOM);
        String incSugarContent = MessageFormat.format(TEXT_INCLUDED_SUGARS, addedSugar);
        width = contentWidth;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText includedSugarText =
                getJrDesignStaticText(xPos, yPos, incSugarContent, "", 10, false, width);
        includedSugarText.setHeight(18);
        leftPadding(includedSugarText, 20);
        rightFrame.addElement(includedSugarText);

        // Add IncludedSugar daily value
        String includedSugarsDailyValueStr = emptyIfNull(productInfo.get("includedSugarsDailyValue"));
        includedSugarsDailyValueStr = String.join(includedSugarsDailyValueStr, "%");
        width = 50;
        xPos = includedSugarText.getWidth() - 50;
        JRDesignStaticText includedSugarsDailyValue =
                getJrDesignStaticText(xPos, yPos, includedSugarsDailyValueStr, FONT_HELVETICA_BOLD, 10, true, width);
        includedSugarsDailyValue.setHeight(18);
        includedSugarsDailyValue.setHorizontalAlignment(HorizontalAlignEnum.RIGHT);
        rightFrame.addElement(includedSugarsDailyValue);
        yPos += includedSugarText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Protein
        width = 55;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText proteinText =
                getJrDesignStaticText(xPos, yPos, TEXT_PROTEIN, FONT_HELVETICA_BOLD, 10, false, width);
        proteinText.setHeight(16);
        leftPadding(proteinText, 5);
        rightFrame.addElement(proteinText);

        // Add protein value
        width = contentWidth - 55;
        xPos = NUTRITION_GRID_PADDING_LEFT + proteinText.getWidth();
        String proteinValueStr = emptyIfNull(productInfo.get("protein"));
        proteinValueStr = String.join("", proteinValueStr, productInfo.get("proteinUOM"));
        JRDesignStaticText proteinValue =
                getJrDesignStaticText(xPos, yPos, proteinValueStr, "", 10, false, width);
        proteinValue.setHeight(16);
        rightFrame.addElement(proteinValue);
        yPos += proteinText.getHeight() + 3;
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 6.0f, NUTRITION_GRID_PADDING_LEFT * 2) + 3;

        // Add Vitamin A
        String vitaminAAmount = emptyIfNull(productInfo.get("vitaminA"));
        String vitaminAUOM = emptyIfNull(productInfo.get("vitaminAUOM"));
        String vitmainATextStr = String.join("", TEXT_VITAMIN_A, " ", vitaminAAmount, vitaminAUOM);
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText vitaminAText =
                getJrDesignStaticText(xPos, yPos, vitmainATextStr, "", 10, false, width);
        vitaminAText.setHeight(18);
        vitaminAText.setY(yPos);
        leftPadding(vitaminAText, 5);
        rightFrame.addElement(vitaminAText);

        //Add Vitamin A daily value
        String vitaminADailyValue = emptyIfNull(productInfo.get("vitaminADailyValue"));
        vitaminADailyValue = vitaminADailyValue.join("", vitaminADailyValue, "%");
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + contentWidth / NUMBER_2;
        JRDesignStaticText vitaminADailyValueText =
                getJrDesignStaticText(xPos, yPos, vitaminADailyValue, "", 10, false, width);
        vitaminADailyValueText.setHeight(18);
        vitaminADailyValueText.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        rightFrame.addElement(vitaminADailyValueText);
        yPos += vitaminAText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Vitamin C
        String vitaminCAmount = emptyIfNull(productInfo.get("vitaminC"));
        String vitaminCUOM = emptyIfNull(productInfo.get("vitaminCUOM"));
        String vitaminCTextStr = String.join("", TEXT_VITAMIN_C, " ", vitaminCAmount, vitaminCUOM);
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText vitaminCText =
                getJrDesignStaticText(xPos, yPos, vitaminCTextStr, "", 10, false, width);
        vitaminCText.setHeight(18);
        leftPadding(vitaminCText, 5);
        rightFrame.addElement(vitaminCText);

        // Add  Vitamin C daily value
        String vitaminCDailyValue = emptyIfNull(productInfo.get("vitaminCDailyValue"));
        vitaminCDailyValue = String.join("", vitaminCDailyValue, "%");
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + contentWidth / NUMBER_2;
        JRDesignStaticText vitaminCDailyValueText =
                getJrDesignStaticText(xPos, yPos, vitaminCDailyValue, "", 10, false, width);
        vitaminCDailyValueText.setHeight(18);
        vitaminCDailyValueText.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        rightFrame.addElement(vitaminCDailyValueText);
        yPos += vitaminCDailyValueText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Vitamin D
        String vitaminDAmount = emptyIfNull(productInfo.get("vitaminD"));
        String vitaminDUOM = emptyIfNull(productInfo.get("vitaminDUOM"));
        String vitaminDTextStr = String.join("", TEXT_VITAMIN_D, " ", vitaminDAmount, vitaminDUOM);
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText vitaminDText =
                getJrDesignStaticText(xPos, yPos, vitaminDTextStr, "", 10, false, width);
        vitaminDText.setHeight(18);
        leftPadding(vitaminDText, 5);
        rightFrame.addElement(vitaminDText);

        // Add Vitamin D daily value
        String vitaminDDailyValue = emptyIfNull(productInfo.get("vitaminDDailyValue"));
        vitaminDDailyValue = String.join("", vitaminDDailyValue, "%");
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + contentWidth / NUMBER_2;
        JRDesignStaticText vitaminDDailyValueText =
                getJrDesignStaticText(xPos, yPos, vitaminDDailyValue, "", 10, false, width);
        vitaminDDailyValueText.setHeight(18);
        vitaminDDailyValueText.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        rightFrame.addElement(vitaminDDailyValueText);
        yPos += vitaminDDailyValueText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2);

        // Add Calcium
        String calciumAmount = emptyIfNull(productInfo.get("calcium"));
        String calciumUOM = emptyIfNull(productInfo.get("calciumUOM"));
        String calciumTexStr = String.join("", TEXT_CALCIUM + " " + calciumAmount + calciumUOM);
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText calciumText =
                getJrDesignStaticText(xPos, yPos, calciumTexStr, "", 10, false, width);
        calciumText.setHeight(18);
        leftPadding(calciumText, 5);
        rightFrame.addElement(calciumText);

        //Add calcium daily value
        String calciumDailyValue = emptyIfNull(productInfo.get("calciumDailyValue"));
        calciumDailyValue = calciumDailyValue.join("", calciumDailyValue, "%");
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + contentWidth / NUMBER_2;
        JRDesignStaticText calciumDailyValueText =
                getJrDesignStaticText(xPos, yPos, calciumDailyValue, "", 10, false, width);
        calciumDailyValueText.setHeight(18);
        calciumDailyValueText.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        rightFrame.addElement(calciumDailyValueText);
        yPos += calciumDailyValueText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2) + 1;

        // Add Iron
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        String ironAmount = emptyIfNull(productInfo.get("iron"));
        String ironUOM = emptyIfNull(productInfo.get("ironUOM"));
        String ironTextStr = String.join("", TEXT_IRON, " ", ironAmount, ironUOM);
        JRDesignStaticText ironText =
                getJrDesignStaticText(xPos, yPos, ironTextStr, "", 10, false, width);
        ironText.setHeight(18);
        leftPadding(ironText, 5);
        rightFrame.addElement(ironText);

        // Add iron daily value
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + contentWidth / NUMBER_2;
        String ironDailyValue = emptyIfNull(productInfo.get("ironDailyValue"));
        ironDailyValue = String.join("", ironDailyValue, "%");
        JRDesignStaticText ironDailyValueText =
                getJrDesignStaticText(xPos, yPos, ironDailyValue, "", 10, false, width);
        ironDailyValueText.setHeight(18);
        ironDailyValueText.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        rightFrame.addElement(ironDailyValueText);
        yPos += ironDailyValueText.getHeight();
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 0.5f, NUTRITION_GRID_PADDING_LEFT * 2) + 1;

        // Add Potassium
        String potassiumAmount = emptyIfNull(productInfo.get("potassium"));
        String potassiumUOM = emptyIfNull(productInfo.get("potassiumUOM"));
        String potassiumTextStr = String.join("", TEXT_POTASSIUM, " ", potassiumAmount, potassiumUOM);
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText potassiumText =
                getJrDesignStaticText(xPos, yPos, potassiumTextStr, "", 10, false, width);
        potassiumText.setHeight(18);
        leftPadding(potassiumText, 5);
        rightFrame.addElement(potassiumText);

        // Add potassium daily value
        width = contentWidth / NUMBER_2;
        xPos = NUTRITION_GRID_PADDING_LEFT + contentWidth / NUMBER_2;
        String potassiumDailyValue = emptyIfNull(productInfo.get("potassiumDailyValue"));
        potassiumDailyValue = String.join("", potassiumDailyValue, "%");
        JRDesignStaticText potassiumDailyValueText =
                getJrDesignStaticText(xPos, yPos, potassiumDailyValue, "", 10, false, width);
        potassiumDailyValueText.setHeight(18);
        potassiumDailyValueText.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
        rightFrame.addElement(potassiumDailyValueText);
        yPos += potassiumDailyValueText.getHeight() + 2;
        yPos += populateLine(rightFrame, contentWidth - (NUTRITION_GRID_PADDING_LEFT * 2), yPos, 6.0f, NUTRITION_GRID_PADDING_LEFT * 2) + 3;

        // Add Percent Desc
        width = contentWidth;
        xPos = NUTRITION_GRID_PADDING_LEFT;
        JRDesignStaticText percentDescText =
                getJrDesignStaticText(xPos, yPos, TEXT_PERCENT_DESC, "", 9, false, width);
        percentDescText.setHeight(40);
        leftPadding(percentDescText, 5);
        rightFrame.addElement(percentDescText);
        yPos += percentDescText.getHeight();

        return yPos;
    }

    private int populateLine(JRDesignFrame rightFrame, int lineWidth, int nextHeight, float linePenWidth, int xPos) {
        JRDesignLine line = new JRDesignLine();
        line.getLinePen().setLineWidth(linePenWidth);
        line.setWidth(lineWidth);
        line.setHeight(1);
        line.setX(xPos);
        line.setY(nextHeight);

        rightFrame.addElement(line);

        return 1;
    }

    private JRDesignFrame generateSummarySection(int yPos) {
        JRDesignFrame frame = new JRDesignFrame();
        frame.setWidth(getReport().getOptions().getPrintableWidth());

        // Add Disclaimer
        int width = getReport().getOptions().getPrintableWidth();
        int x = 0;
        JRDesignStaticText disclaimerText =
                getJrDesignStaticText(x, yPos, TEXT_DISCLAIMER, "", 7, false, width);
        int mHeight = getMeasuredHeight(disclaimerText);
        disclaimerText.setHeight(mHeight);
        frame.addElement(disclaimerText);
        frame.setHeight(mHeight + 5);
        return frame;
    }

    private int getMeasuredHeight(JRDesignStaticText jrDesignStaticText) {
        FontUtil fontUtil = FontUtil.getInstance(jasperReportsContext);
        JRTextMeasurerUtil jrTextMeasurerUtil = JRTextMeasurerUtil.getInstance(jasperReportsContext);
        Map attributes = new HashMap();
        attributes.putAll(fontUtil.getAttributesWithoutAwtFont(attributes, jrDesignStaticText));
        JRStyledText styledText = JRStyledTextParser.getInstance().getStyledText(attributes, jrDesignStaticText.getText(), false, Locale.US);
        JRMeasuredText jrMeasuredText = jrTextMeasurerUtil.createTextMeasurer(jrDesignStaticText).measure(styledText, 0, 1000, false);
        int mHeight = (int) Math.ceil(jrMeasuredText.getTextHeight());
        return mHeight;
    }


    protected void leftPadding(JRDesignStaticText text, int padding) {
        text.getLineBox().setLeftPadding(padding);
    }

    protected void leftPadding(JRDesignTextField text, int padding) {
        text.getLineBox().setLeftPadding(padding);
    }

    protected String emptyIfNull(String value) {
        return value == null ? "" : value;
    }

    protected String changeValue(String value, boolean childNutritionFlag) {
        if ("Y".equals(value)) {
            value = "Yes";
        } else if ("N".equals(value) || ("".equals(value) && !childNutritionFlag)) {
            value = "No";
        }
        return value;
    }
}
