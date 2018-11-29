# spring-boot-rest


## 常用注解



@Controller

@RestController

@RequestMapping

@PathVariable

@RequestParam


## 自描述消息

```java
@Import(DelegatingWebMvcConfiguration.class)
public @interface EnableWebMvc {
}
```

```java
@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {}
```

```java
WebMvcConfigurationSupport#getDefaultMediaTypes(系统自动选择自描述消息)
```

## 返回XML格式的自描述消息

```xml
	<dependency>
		<groupId>com.fasterxml.jackson.dataformat</groupId>
		<artifactId>jackson-dataformat-xml</artifactId>
		<version>2.9.7</version>
	</dependency>
```


## WebMvcConfigurationSupport#getDefaultMediaTypes

```java
protected Map<String, MediaType> getDefaultMediaTypes() {
	Map<String, MediaType> map = new HashMap<>(4);
       ...
	if (jaxb2Present || jackson2XmlPresent) {
		map.put("xml", MediaType.APPLICATION_XML);
	}
	if (jackson2Present || gsonPresent || jsonbPresent) {
		map.put("json", MediaType.APPLICATION_JSON);
	}
        ...
	return map;
}
```

### 为什么加了xml的依赖，返回数据格式就成为了xml格式

1. 与请求的accept有关

```http
Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,/;q=0.8
```

​	如果请求的时候指定了服务器能都消费的媒体类型,在WebMvcConfigurationSupport#getDefaultMediaTypes中会根据存在的class设置能够处理媒体类型。

​	如果没有指定或者指定的类型，在WebMvcConfigurationSupport#getDefaultMediaTypes中没有找到能够处理指定类型的class文件，会默认返回json格式数据