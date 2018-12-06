# spring-boot-rest


## 常用注解


```java
@Controller

@RestController

@RequestMapping

@PathVariable

@RequestParam
```

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

## WebMvcConfigurationSupport#addDefaultHttpMessageConverters

```java
protected final void addDefaultHttpMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
  		...
  		if (jackson2XmlPresent) {
  			Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.xml();
  			if (this.applicationContext != null) {
  				builder.applicationContext(this.applicationContext);
  			}
  			messageConverters.add(new MappingJackson2XmlHttpMessageConverter(builder.build()));
  		}
  		...
  		
		if (jackson2Present) {
			Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder.json();
			if (this.applicationContext != null) {
				builder.applicationContext(this.applicationContext);
			}
			messageConverters.add(new MappingJackson2HttpMessageConverter(builder.build()));
		}
}
```


## WebMvcConfigurationSupport#getMessageConverters

```java
protected final List<HttpMessageConverter<?>> getMessageConverters() {
    if (this.messageConverters == null) {
        this.messageConverters = new ArrayList<>();
        configureMessageConverters(this.messageConverters);
        if (this.messageConverters.isEmpty()) {
            addDefaultHttpMessageConverters(this.messageConverters);
        }
        extendMessageConverters(this.messageConverters);
    }
    return this.messageConverters;
}
```

## WebMvcConfigurationSupport#requestMappingHandlerAdapter

```java
public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
    RequestMappingHandlerAdapter adapter = createRequestMappingHandlerAdapter();
    adapter.setContentNegotiationManager(mvcContentNegotiationManager());
    adapter.setMessageConverters(getMessageConverters());
    adapter.setWebBindingInitializer(getConfigurableWebBindingInitializer());
    adapter.setCustomArgumentResolvers(getArgumentResolvers());
    adapter.setCustomReturnValueHandlers(getReturnValueHandlers());
    ｝

```


```html
所有的HTTP自描述消息处理器都保存在messageConverters中，这个集合会传递到requestMappingHandlerAdapter中
并且控制写出
```

```html
Spring Boot 默认使用 Jackson2 序列化方式,处理类 MappingJackson2HttpMessageConverter
```

## MappingJackson2HttpMessageConverter
```html
MappingJackson2HttpMessageConverter，提供两类方法：
读read* ：通过 HTTP 请求内容转化成对应的 Bean
写write*：通过 Bean 序列化成对应文本内容作为响应内容
```
```java
@Override
public boolean canRead(Class<?> clazz, @Nullable MediaType mediaType) {
   ...
}
	
@Override
public boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType) {
   ...
}
```
```html
canWrite()为切入点
AbstractMessageConverterMethodProcessor#writeWithMessageConverters 处理匹配的类型
 AbstractMessageConverterMethodProcessor#getProducibleMediaTypes  产生的类型
```


### 为什么加了xml的依赖，返回数据格式就成为了xml格式

1. 与请求的accept有

```http
Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,/;q=0.8
```

### 修改自描述消息的排序

```java
@Configuration
public class WebWvcConfig implements WebMvcConfigurer{
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.set(0,new MappingJackson2XmlHttpMessageConverter());
    }
}

```


## 自定义自描述消息

输入JSON输出properties

输入properties输出JSON

User

JSON(application/json)
```json
{
    "name": "zhangsan",
    "address": "china"
}
```

XML(application/XML)
```xml
<User>
    <name>zhangsan</name>
    <address>china</address>
</User>
```

properties (application/properties+user)
扩展
```properties
user.name = zhangsan
user.address = china
```

