Thymeleaf 3和spring boot整合例子。
在js中访问model:
<script th:inline="javascript">
    var single = [[${singlePerson}]];
    console.log(single.name + "/" + single.age);
    function getName(name) {
        console.log(name);
    }
</script>
通过th:inline="javascript"添加到script标签,js代码就可以访问model中的属性
通过[[${singlePerson}]]获取实际的属性值

可以参考官网https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-web-thymeleaf3