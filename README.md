# spring-boot-rest


##常用注解



@Controller

@RestController

@RequestMapping

@PathVariable

@RequestParam


##自描述消息

@Import(DelegatingWebMvcConfiguration.class)
public @interface EnableWebMvc {
}



@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {}



WebMvcConfigurationSupport#getDefaultMediaTypes(系统自动选择自描述消息)

##返回XML格式的自描述消息
		<dependency>
			<groupId>com.fasterxml.jackson.dataformat</groupId>
			<artifactId>jackson-dataformat-xml</artifactId>
			<version>2.9.7</version>
		</dependency>
		

##WebMvcConfigurationSupport#getDefaultMediaTypes

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
	
	
	
