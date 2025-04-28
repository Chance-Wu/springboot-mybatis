package com.chance.designpartterms;

import com.chance.designpatterns.abstractdocument.cms.Article;
import com.chance.designpatterns.abstractdocument.cms.ImageContent;
import com.chance.designpatterns.abstractdocument.cms.TextContent;
import com.chance.designpatterns.abstractdocument.cms.VideoContent;
import com.chance.designpatterns.abstractdocument.domain.Car;
import com.chance.designpatterns.abstractdocument.domain.enums.CarProperty;
import com.chance.designpatterns.abstractdocument.ec.Digital;
import com.chance.designpatterns.abstractdocument.ec.HasBrand;
import com.chance.designpatterns.abstractdocument.ec.HasCategory;
import com.chance.designpatterns.abstractdocument.ec.HasPrice;
import com.chance.designpatterns.abstractdocument.ec.HasType;
import com.chance.designpatterns.abstractdocument.ec.HasWeight;
import com.chance.designpatterns.abstractdocument.excel.FormulaCell;
import com.chance.designpatterns.abstractdocument.excel.NumberCell;
import com.chance.designpatterns.abstractdocument.excel.Spreadsheet;
import com.chance.designpatterns.abstractdocument.excel.TextCell;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chance
 * @date 2024/11/29 13:37
 * @since 1.0
 */
@Slf4j
public class AbstractDocumentTest {

    @Test
    public void goods() {
        //创建电视属性
        Map<String, Object> televisionProperties = new HashMap<>();
        televisionProperties.put(HasType.TYPE_PROPERTIES, "电视");
        televisionProperties.put(HasPrice.PRICE_PROPERTIES, 2000);
        televisionProperties.put(HasBrand.BRAND_PROPERTIES, "创维");
        televisionProperties.put(HasWeight.WEIGHT_PROPERTIES, 50);

        //创建手机属性
        Map<String, Object> phoneProperties = new HashMap<>();
        phoneProperties.put(HasType.TYPE_PROPERTIES, "手机");
        phoneProperties.put(HasPrice.PRICE_PROPERTIES, 1900);
        phoneProperties.put(HasBrand.BRAND_PROPERTIES, "小米");
        phoneProperties.put(HasWeight.WEIGHT_PROPERTIES, 0.5);

        //创建平板属性
        Map<String, Object> padProperties = new HashMap<>();
        padProperties.put(HasType.TYPE_PROPERTIES, "平板");
        padProperties.put(HasPrice.PRICE_PROPERTIES, 5000);
        padProperties.put(HasBrand.BRAND_PROPERTIES, "苹果");
        padProperties.put(HasWeight.WEIGHT_PROPERTIES, 0.5);

        //创建数码产品属性
        Map<String, Object> digitalProperties = new HashMap<>();
        digitalProperties.put(HasCategory.CATEGORY_PROPERTIES, Arrays.asList(televisionProperties, phoneProperties, padProperties));
        Digital digital = new Digital(digitalProperties);

        log.info(digital.toString());
    }

    @Test
    public void spreadCell() {
        log.info("构造表格");
        Spreadsheet spreadsheet = new Spreadsheet(1, 3);
        log.info("设置单元格");
        spreadsheet.setCell(0, 0, new TextCell("Hello"));
        spreadsheet.setCell(0, 1, new NumberCell(3));
        spreadsheet.setCell(0, 2, new FormulaCell("1+2", 3));
        log.info("表格渲染结果：{}", spreadsheet.getDisplay(0, 0) + spreadsheet.getDisplay(0, 1) + spreadsheet.getDisplay(0, 2));
    }

    @Test
    public void cms() {
        log.info("构造文章");
        Article article = new Article(Arrays.asList(
                new TextContent("Hello World"),
                new ImageContent("https://www.baidu.com"),
                new VideoContent("https://www.baidu.com")
        ));
        log.info("文章渲染结果：{}", article.render());
    }

    @Test
    public void abstractDocument() {
        log.info("构造部件和汽车");
        Map<String, Object> wheelProperties = new HashMap<>();
        wheelProperties.put(CarProperty.TYPE.toString(), "wheel");
        wheelProperties.put(CarProperty.MODEL.toString(), "15C");
        wheelProperties.put(CarProperty.PRICE.toString(), 100L);

        Map<String, Object> doorProperties = new HashMap<>();
        doorProperties.put(CarProperty.TYPE.toString(), "door");
        doorProperties.put(CarProperty.MODEL.toString(), "Lambo");
        doorProperties.put(CarProperty.PRICE.toString(), 300L);

        Map<String, Object> carProperties = new HashMap<>();
        carProperties.put(CarProperty.MODEL.toString(), "300SL");
        carProperties.put(CarProperty.PRICE.toString(), 10000L);
        carProperties.put(CarProperty.PARTS.toString(), Arrays.asList(wheelProperties, doorProperties));

        Car car = new Car(carProperties);
        log.info("Here is our car:");
        log.info("-> model: {}", car.getModel().orElseThrow(() -> new IllegalStateException("Model not found")));
        log.info("-> price: {}", car.getPrice().orElseThrow(() -> new IllegalStateException("Price not found")));
        log.info("-> parts: ");

        car.getParts().forEach(part ->
                log.info("\t{}/{}/{}",
                        part.getType().orElse(null),
                        part.getModel().orElse(null),
                        part.getPrice().orElse(null))
        );
    }
}
