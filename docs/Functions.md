#### 医院挂号系统

##### 一、医院设置接口模块

- 不同的医院连接到本系统时，需要提供医院自己的信息，如名称、编号、api基础路径和签名密钥等，这些信息也称为**医院设置**

###### 1、整合Swagger2

- 新建Swagger的config配置类
- 因为Swagger在common模块下，与Service不同，因此需要在引用的同时在主启动类上使用注解`@ComponentScan(basePackages = {"com.edu"})`来确保可以扫描到swagger的配置类
- 在Controller上使用`@Api`注解，来添加对该类的中文描述。在方法上使用注解`@ApiOperation`，来添加对该方法的中文描述

###### 2、功能开发

- 都是基本的MP常规操作，没意思
- 查询医院设置表中的所有信息、逻辑删除医院的设置信息、分页、条件查询医院设置、新增医院设置、根据id查询对应的医院设置、修改医院设置的信息、批量删除医院设置、修改医院的可用状态、向管理系统发送密钥

###### 3、自定义异常类

- 新建实体类继承RuntimeException，该类作为自定义异常实体类可以自己抛出（感觉没卵用，但是要知道怎么自定义异常类）

###### 4、自定义全局异常处理器

- 在类上使用`@ControllerAdvice`注解，进行组件的注册
- 本质是AOP，提供了多种指定Advice的规则拦截，默认什么也不写，则拦截所有的Controller，因此每个Controller发生异常时，该处理器都会进行检测并拦截，从而达到自定义处理异常的功能

###### 5、统一日志处理

- 新建xml配置文件，可以将指定的日志级别输出到指定的文件夹中（没配置，需要的话再看，8难）



##### 二、数据字典模块

- 即通用基础数据，如省市区层级联动数据、学历、民族等

###### 1、显示基础数据的层级关系

- 根据传入的id查询其子节点
- 在实体类中新增字段hasChild，但并不存在于数据库表中，用于向前端返回值时，前端判断是否有子节点而现实展开、折叠的图标。前端并才用懒加载模式，只有当点开时，才会发送请求去查询该节点下的子节点。

###### 2、导入导出数据

- 使用第三方技术EasyExcel操作Excel

- 引入依赖

    ```xml
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>easyexcel</artifactId>
        <version>2.1.1</version>
    </dependency>
    ```

- 写操作（将数据写入到Excel中）

    - 创建实体类，并在字段上添加`@ExcelProperty("表头名称")`注解，将Excel中的表头信息与实体类字段一一对应起来

    ```java
    //设置生成的Excel文件存放的位置
    String fileName = "F:\\Excel\\foo.xlsx";
    //传入文件路径及对应的实体类，并设置sheet信息，最后将List写入
    EasyExcel.write(fileName, UserData.class).sheet("我是sheet信息").doWrite(list.of(1, 2, 3));
    ```

- 读操作（将Excel中的数据读取出来）

    - 在对应的实体类的对应字段上添加`@ExcelProperty(value = "表头名称", index = 0)`注解，表示该字段在Excel中是第几列

    - 创建一个监听器用来读取Excel，新建一个类继承`AnalysisEventListener<对应的实体类>`

        - 默认实现两个方法：其中`Invoke`表示一行一行的读取Excel中的内容，且从第二行开始（第一行是标头）。
        - `doAfterAllAnalysed`表示全部读取完之后要执行的操作
        - 非默认实现的方法：`invokeHeadMap`用来返回表头的索引和表头的kv值，如：`{0=表头,1=表头啊}`

        ```java
        //读取Excel的文件路径
        String fileName = "F:\\Excel\\bar.xlsx";
        //传入文件路径及对应的实体类，并将监听器(foobar类继承了AnalysisEventListener)放入，sheet不设置时默认读取第一个sheet
        EasyExcel.read(fileName, UserData.class, new foobar()).sheet().doRead();
        //该方法执行时，会调用监听器中的Invoke方法
        ```

- 导出Excel

    - 导出时是下载操作，因此要使用`HttpServletResponse`设置下载信息
    - 导出的实体跟查询出来的实体不相同，因此采用对象的拷贝，又因为拷贝的是集合，`beanutils.copyproperties`不支持拷贝集合，如果遍历拷贝效率较低，因此采用AlibabaFastJson将List集合转成Json，再将Json转为对应的List集合List`<DictEeVo> dictEeVos = JSON.parseArray(JSON.toJSONString(list), DictEeVo.class);`

- Excel导入数据

    - 使用`MultipartFile`获取用户上传的文件
    - 监听器中是一行一行读的，因此可以使用`beanutils.copyproperties`进行对象的拷贝，并插入到数据库中

###### 3、添加缓存

- 数据字典经常查询，且不经常改变，因此适合放入缓存中。
- 配置RedisConfig，自定义key生成规则、自定义Redis序列化器和TTL时间，并使用注解`@EnableCaching`开启缓存功能
- 在实现类的查询方法上添加注解`@Cacheable(value = "dict", keyGenerator = "keyGenerator")`，表明该方法的返回值写到redis中，在更新数据的方法上添加`@CacheEvict(value = "dict", allEntries = true)`，表示有数据更新时就从缓存中移除。其中value表示操作的缓存key是哪一个（实际中key=value+keyGenerator ），而`@Cacheable`和`@CacheEvict`中的value一致时，可以表示同名的可缓存操作，一个添加缓存，一个删除缓存。因此value只是idea层面的唯一标识。

###### 4、使用Nginx做请求转发

- hosp服务在8201端口，cmn在8202端口，使用Nginx做统一的端口转发
- 先试用简单的Win版完成功能，后面会换成SpringCloudGateway做服务网关