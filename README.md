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
public class DelegatingWebMvcConfiguration 
        extends WebMvcConfigurationSupport {}
        
WebMvcConfigurationSupport#getDefaultMediaTypes(系统自动选择自描述消息)
