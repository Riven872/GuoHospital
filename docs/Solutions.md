##### 后端

###### 1、MybatisPlus中，使用LambdaQueryWrapper抛异常：`org.apache.ibatis.reflection.ReflectionException: Error parsing property name 'lambda$findPageHospSet$7dae9b70$1'.  Didn't start with 'is', 'get' or 'set'.`

- 异常代码

    ```java
    LambdaQueryWrapper<HospitalSet> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(!StringUtils.isEmpty(queryVo.getHosname()), e -> e.getHosname(), queryVo.getHosname())
                    .like(!StringUtils.isEmpty(queryVo.getHoscode()), e -> e.getHoscode(), queryVo.getHoscode());
    ```

- 异常原因：

    - MyBatisPlus的条件构造器不会真的去调用SFunction这个函数式接口而是只解析实际的方法名。如果解析的是Lambda表达式，那么**方法名跟数据库的列名匹配不上**就会报错。如果是方法引用那么方法名通过is/get/set规则就能找到相应的字段名然后在根据规则转换成数据库表的列名。

- 更改后的代码

    ```java
    LambdaQueryWrapper<HospitalSet> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(!StringUtils.isEmpty(queryVo.getHosname()), HospitalSet::getHosname, queryVo.getHosname())
                    .like(!StringUtils.isEmpty(queryVo.getHoscode()), HospitalSet::getHoscode, queryVo.getHoscode());
    ```




##### 前端

###### 1、跨域问题报错：`Access to XMLHttpRequest at 'http://localhost:8201/admin/hosp/hospitalSet/findPageHospSet/1/3' from origin 'http://localhost:9528' has been blocked by CORS policy: Response to preflight request doesn't pass access control check: No 'Access-Control-Allow-Origin' header is present on the requested resource.`

- 三者任何一个不同则会产生跨域问题：
    - 访问协议不同：如Http访问Https
    - 访问地址不同：如192.168.31.100访问172.11.1.1
    - 端口号不同：如本项目的前端9528， 后端接口8201
- 解决方案：
    - 在Controller类上添加`@CrossOrigin`注解，允许跨域访问

- 延伸知识：
    - 一次请求，在Network中可能出现两次同样的请求，第一次是“预检”，也就是询问服务器是否请求地址在许可名单中，如果在则进行下一次正儿八经的XHR请求（跨域中见的）