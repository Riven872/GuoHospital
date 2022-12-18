#### 医院挂号系统

##### 一、医院设置接口

- 不同的医院连接到本系统时，需要提供医院自己的信息，如名称、编号、api基础路径和签名密钥等，这些信息也称为**医院设置**

###### 1、整合Swagger2

- 新建Swagger的config配置类
- 因为Swagger在common模块下，与Service不同，因此需要在引用的同时在主启动类上使用注解`@ComponentScan(basePackages = {"com.edu"})`来确保可以扫描到swagger的配置类
- 在Controller上使用`@Api`注解，来添加对该类的中文描述。在方法上使用注解`@ApiOperation`，来添加对该方法的中文描述