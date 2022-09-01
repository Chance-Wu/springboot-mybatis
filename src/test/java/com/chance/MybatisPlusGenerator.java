//package com.chance;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.core.toolkit.StringPool;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
//import com.baomidou.mybatisplus.generator.config.po.TableFill;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * <p>
// *
// * <p>
// *
// * @author chance
// * @since 2020-08-22
// */
//public class MybatisPlusGenerator {
//
//    public static void main(String[] args) {
//        // 即生成的代码在哪个包下
//        String packageModuleName = "";
//
//        String author = "chance";
//        String projectPath = System.getProperty("user.dir");
//        String outputDir = projectPath+"/src/main/java";
//        String controllerName = "Controller";
//        String packagePathParent = "com.chance";
//        String packageControllerName = "controller"; //这里是控制器包名，默认web
//
//        String[] tablePrefix = new String[]{"sys_", "svc_", "pay_", "spc_"}; //表前缀
//
//        //自定义需要填充的字段
//        ArrayList<TableFill> tableFillList = new ArrayList<>();
//        String controllerTemplatePath = "/templates/controller.java";
//        String entityTemplatePath = "/templates/entity.java";
//        String mapperTemplatePath = "/templates/mapper.java";
//        String serviceTemplatePath = "/templates/service.java";
//        String serviceImplTemplatePath = "/templates/serviceImpl.java";
//
//
//        AutoGenerator mpg = new AutoGenerator()
//                .setTemplateEngine(new FreemarkerTemplateEngine())
//                .setDataSource(getDataSourceConfig()) //数据源配置
//                .setGlobalConfig(getGlobalConfig(author, outputDir, controllerName)) //全局配置
//                .setStrategy(getStrategyConfig(tableFillList)) //策略配置
//                .setPackageInfo(getPackageConfig(packagePathParent, packageModuleName, packageControllerName))
//                .setCfg(getInjectionConfig(projectPath))
//                .setTemplate(getTemplateConfig(controllerTemplatePath, entityTemplatePath,
//                        mapperTemplatePath, serviceTemplatePath, serviceImplTemplatePath));
//
//        //执行生成
//        mpg.execute();
//    }
//
//    /**
//     * 数据源配置
//     */
//    private static DataSourceConfig getDataSourceConfig() {
//        return new DataSourceConfig().setDbType(DbType.MYSQL) //数据库类型
//                .setUrl("jdbc:mysql://localhost:3306/test?autoReconnect=true&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&useSSL=false")
//                .setDriverName("com.mysql.jdbc.Driver")
//                .setUsername("root")
//                .setPassword("539976")
//                .setTypeConvert(new MySqlTypeConvert()); //自定义数据类型转换
//    }
//
//    /**
//     * 全局配置
//     */
//    public static GlobalConfig getGlobalConfig(String author, String outputDir, String controllerName) {
//        return new GlobalConfig()
//                .setAuthor(author)
//                .setOutputDir(outputDir) //输出目录
//                .setFileOverride(true) //是否覆盖文件
//                .setActiveRecord(true) //开启 activeRecord模式
//                .setEnableCache(false) //XML 二级缓存
//                .setBaseResultMap(true) //XML ResultMap
//                .setBaseColumnList(true) //XML columnList
////                .setKotlin(true)
////                自定义文件命名，注意%s会自动填充表实体属性！
////                .setMapperName("%Dao")
////                .setXmlName("%Dao")
////                .setServiceName("MP%sService")
////                .setServiceImplName("MP%sServiceDiy")
//                .setControllerName("%s" + controllerName);
//    }
//
//    /**
//     * 策略配置
//     */
//    public static StrategyConfig getStrategyConfig(List<TableFill> tableFillList) {
//        return new StrategyConfig()
////                .setCapitalMode(true) //全局大写命名
////                .setTablePrefix(tablePrefix) //此处可以修改为你的表的前缀
//                .setNaming(NamingStrategy.underline_to_camel) //表名生成策略
//                .setTableFillList(tableFillList)
////                .setInclude(new String[]{"swagger_json"}) //需要生成的表
////                .setExclude(new String[]{"test"}) //排除生成的表
////                .setSuperEntityClass("com.baomidou.demo.TestEntity") //自定义实体父类
////                .setSuperEntityColumns(new String[]{"test_id"}) //自定义实体，公共字段
////                .setSuperMapperClass("com.baomidou.demo.TestMapper") //自定义mapper父类
////                .setSuperServiceClass("com.baomidou.demo.TestService") //自定义service父类
////                .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl") //自定义service实现类父类
////                .setSuperControllerClass("com.baomidou.demo.TestController") //自定义controller父类
////                .setEntityColumnConstant(true) //【实体】是否生成字段常量，默认false
////                .setEntityBuilderModel(true) //【实体】是否为构建者模型，默认false
//                .setEntityLombokModel(true) //【实体】是否为lombok模型，默认false
////                .setControllerMappingHyphenStyle(true)
//                .setEntityBooleanColumnRemoveIsPrefix(true) //Boolean类型字段是否移除is前缀处理
//                .setRestControllerStyle(true);
//    }
//
//    /**
//     * 包配置
//     */
//    public static PackageConfig getPackageConfig(String packagePathParent, String moduleName, String controllerName) {
//        return new PackageConfig()
//                .setParent(packagePathParent) //自定义包路径
////                .setModuleName(moduleName)
//                .setController(controllerName); //控制器包名，默认web
//    }
//
//    /**
//     * 自定义配置
//     */
//    public static InjectionConfig getInjectionConfig(String projectPath) {
//        return new InjectionConfig() { //注入自定义配置，可以在VM中使用cfg.abc设置的值
//            @Override
//            public void initMap() {
//                HashMap<String, Object> map = new HashMap<>();
////                map.put("abc",this.getConfig().getGlobalConfig().getAuthor()+"-mp");
//                this.setMap(map);
//            }
//        }.setFileOutConfigList(Collections.singletonList(new FileOutConfig("/templates/mapper.xml.ftl") {
//            @Override
//            public String outputFile(TableInfo tableInfo) { //自定义输出文件目录
//                return projectPath + "src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//            }
//        }));
//
//        //打印注入设置，这里演示模板里面怎么获取注入内容
////        System.err.println(mpg.getCfg().getMap().get("abc"));
//    }
//
//    /**
//     * 模板配置
//     */
//    public static TemplateConfig getTemplateConfig(String controllerTemplatePath, String entityTemplatePath,
//                                                   String mapperTemplatePath, String serviceTemplatePath, String serviceImplTemplatePath) {
//        //关闭默认xml生成，调整生成至根目录
//        //自定义模板配置，模板可以参考源码/mybatis-plus/src/main/resources/template 使用copy
//        //至您项目 src/main/resources/template目录下，模板名称也可自定义如下配置：
//        return new MyTemplateConfig()
//                .setController(controllerTemplatePath)
//                .setEntity(entityTemplatePath)
//                .setMapper(mapperTemplatePath)
//                .setService(serviceTemplatePath)
//                .setServiceImpl(serviceImplTemplatePath)
//                .setXml(null);
//    }
//}
