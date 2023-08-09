对于不同领域层使用不同JavaBean对象传输数据，避免相互影响，因此基于数据库实体对象User衍生出比如UserDto、UserVo等对象，于是在不同层之间进行数据传输时，不可避免地需要将这些对象进行互相转换操作。

1、VO、DTO、DO、PO 概念
--
* VO(View Object)：视图对象，用于展示层，它的作用是把某个指定页面(或组件)的所有数 据封装起来。
* DTO(Data Transfer Object)：数据传输对象，这个概念来源于J2EE的设计模式，在这里，我泛指用于展示层与服务层之间的数据传输对象。
* DO(Domain Object)：领域对象，就是从现实世界中抽象出来的有形或无形的业务实体。
* PO(Persistent Object)：持久化对象，它跟持久层(通常是关系型数据库)的数据结构形成一一对应的映射关系，如果持久层是关系型数据库，那么，数据表中的每个字段(或若干个)就对 应PO的一个(或若干个)属性。


2、常见的转换方式
--
* 调用getter/setter方法进行属性赋值
* 调用BeanUtil.copyPropertie进行反射属性赋值

第一种方式不必说，属性多了就需要写一大坨getter/setter代码。
第二种方式比第一种方式要简便很多，但是坑巨多，比如sources与target写反，难以定位某个字段在哪里进行的赋值，同时因为用到反射，导致性能也不佳。

鉴于此，第三种对象转换方式，使用 MapStruct 工具进行转换，MapStruct 原理也很简单，就是`在代码编译阶段生成对应的赋值代码，底层原理还是调用getter/setter方法`，但是这是由工具替我们完成，MapStruct在不影响性能的情况下，解决了前面两种方式弊端。


3、两种配置方式：依赖配置、插件配置
--

> 依赖配置（推荐使用）
```xml
<!--包含了必要注解，例如@Mapping-->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-jdk8</artifactId>
    <version>1.3.1.Final</version>
</dependency>
<!--注解处理器，根据注解自动生成mapper的实现-->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct-processor</artifactId>
    <version>1.3.1.Final</version>
</dependency>
```

> 插件配置

4、入门
--
下面的代码演示了如何使用mapstruct实现Java Bean之间的映射。

> 假设我们有一个表示汽车的类Car，并且还有一个数据传输对象CarDto。两个类中表示作为数量的属性名称是不同的并且，在Car对象中，表示汽车类型的字段是一个枚举，而在CarDTO中，直接使用字符串表示。

Car类
```java
public class Car {

    private String make;
    private int numberOfSeats;
    private CarType type;
    // constructor，getters，setters
    
    enum CarType{
        SEDAN
    }
}
```

CarDto类
```java
public class CarDto {

    private String make;
    private int seatCount;
    private String type;

    // constructor，getters，setters
}
```

5、创建Mapper接口
--
要生成一个CarDto与Car对象相互转换的映射器，需要定义一个mapper接口。

```java
@Mapper
public interface BaseMapper {
    
    @Mapping(source = "numberOfSeats",target = "seatCount")
    CarDto carToCarDto(Car car);
}
```

* `@Mapper`注解标记这个接口作为一个映射接口，并且是编译时MapStruct处理器的入口。
* 真正实现映射的方法需要`源对象作为参数`，`并返回目标对象`。映射方法的名字是随意的。对于在源对象和目标对象中，属性名字不同的情况，可以通过`@Mapping`注解来配置这些名字。我们也可以将源类型与目标类型中类型不同的参数进行转换，在这里就是通过type属性将枚举类型转换为一个字符串。当然在一个接口里可以定义多个映射方法。MapStruct都会为其生成一个实现。
* 自动生成的接口的实现可以通过Mapper的class对象获取。

6、获取Mapper的方式
--

> 默认配置：采用`Mappers`通过动态工厂内部反射机制完成Mapper实现类的获取。

```
// Mapper接口内部定义
public static CarInfoConverter MAPPER = Mappers.getMapper(CarInfoConverter);

// 外部调用
CarInfoConverter.MAPPER.carToCarDto(car);
```

> Spring配置方式：需要在`@Mapper注解内添加componentModel属性值`，配置后在外部可以采用@Autowired方式注入Mapper实现类完成映射方法调用。

```
//注解配置
@Mapper(componentModel = "spring")

//注入Mapper实现类
@Autowired
private CarInfoConverter carInfoConverter;

//调用
carInfoConverter.carToCarDto(car);
```

7、`@Mappings` & `@Mapping`
--

在Mapper接口定义方法上面声明了一系列的注解映射@Mapping以及@Mappings，那么这两个注解是用来干什么工作的呢？

@Mapping注解我们用到了两个属性，分别是source、target
* source代表的是映射接口方法内的参数名称，如果是基本类型的参数，参数名可以直接作为source的内容，如果是实体类型，则可以采用实体参数名.字段名的方式作为source的内容，配置如上面GoodInfoMapper内容所示。
* target代表的是映射到方法方法值内的字段名称，配置如上面CarInfoConverter所示。

> 查看Mapper实现：编译过后到target/generated-sources/annotations目录下查看对应Mapper实现类：
```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-17T10:25:29+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
@Component
public class CarInfoConverterImpl implements CarInfoConverter {

    @Override
    public CarDto carToCarDto(Car car) {
        if ( car == null ) {
            return null;
        }

        CarDto carDto = new CarDto();

        carDto.setName( car.getMake() );
        carDto.setSeatCount( car.getNumberOfSeats() );
        if ( car.getType() != null ) {
            carDto.setType( car.getType().name() );
        }

        return carDto;
    }
}
```

```text
MapStruct根据我们配置的`@Mapping`注解自动将`source`实体内的字段进行了调用`target`实体内字段的setXXX方法赋值，并且做出了一些参数验证。

采用`spring方式`获取`Mapper`，在自动生成的实现类上MapStruct为我们自动添加了`@Component`Spring声明式注入注解配置。
```

8、抽取出转换基类接口
--
因为项目中的对象转换操作基本都一样，因此抽取出一个转换基类，不同对象如果只是简单转换可以直接继承该基类，而无需覆写基类任何方法，即只需要一个空类即可。
如果子类覆写了基类的方法，则基类上的 @Mapping 会失效。

```java
@MapperConfig
public interface BaseConverter<S, T> {

    /**
     * 映射同名属性
     */
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    T sourceToTarget(S source);

    /**
     * 反向，映射同名属性
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    S targetToSource(T var1);

    /**
     * 映射同名属性，集合形式
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<T> sourceToTarget(List<S> var1);

    /**
     * 反向，映射同名属性，集合形式
     */
    @InheritConfiguration(name = "targetToSource")
    List<S> targetToSource(List<T> var1);

    /**
     * 映射同名属性，集合流形式
     */
    List<T> sourceToTarget(Stream<S> stream);

    /**
     * 反向，映射同名属性，集合流形式
     */
    List<S> targetToSource(Stream<T> stream);
}
```

继承该转换基类接口即可。

* @Mapping用于配置对象的映射关系，示例中 User 对象性别属性名为 sex，而UserVo对象性别属性名为gender，因此需要配置 target 与 source 属性。
* password 字段不应该返回到前台，可以采取两种方式不进行转换，第一种就是在vo对象中不出现password字段，第二种就是在@Mapping中设置该字段 ignore = true。
* MapStruct 提供了时间格式化的属性 dataFormat，支持Date、LocalDate、LocalDateTime等时间类型与String的转换。示例中birthday 属性为 LocalDate 类型，可以无需指定dataFormat自动完成转换，而LocalDateTime类型默认使用的是ISO格式时间，在国内往往不符合需求，因此需要手动指定一下 dataFormat。

9、自定义属性类型转换方法
--
JDK8支持接口中的默认方法，可以直接在转换器中添加自定义类型转换方法。

示例中User对象的config属性是一个JSON字符串，UserVo对象中是List类型的，这需要实现JSON字符串与对象的互转。
```
default List<UserConfig> strConfigToListUserConfig(String config) {
  return JSON.parseArray(config, UserConfig.class);
}

default String listUserConfigToStrConfig(List<UserConfig> list) {
  return JSON.toJSONString(list);
}
```

10、常见问题
--
* 当两个对象属性不一致时，比如User对象中某个字段不存在于UserVo当中时，在编译时会有警告提示，可以在@Mapping中配置 ignore = true，当字段较多时，可以直接在@Mapper中设置unmappedTargetPolicy属性或者unmappedSourcePolicy属性为 ReportingPolicy.IGNORE即可。
* 如果项目中也同时使用到了 Lombok，一定要注意 Lombok的版本要等于或者高于1.18.10，否则会有编译不通过的情况发生，笔者掉进这个坑很久才爬了出来，希望各位不要重复踩坑。

