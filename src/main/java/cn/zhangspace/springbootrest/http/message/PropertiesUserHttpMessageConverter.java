package cn.zhangspace.springbootrest.http.message;

import cn.zhangspace.springbootrest.domain.User;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesUserHttpMessageConverter extends AbstractHttpMessageConverter<User> {

    public PropertiesUserHttpMessageConverter(){
        super(MediaType.valueOf("application/properties+user"));
        setDefaultCharset(Charset.forName("UTF-8"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(User.class);
    }

    /**
     * 将properties转为user对象输出
     * @param clazz
     * @param inputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected User readInternal(Class<? extends User> clazz,
                                HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        InputStream inputStream =  inputMessage.getBody();

        Properties properties = new Properties();

        properties.load(new InputStreamReader(inputStream,getDefaultCharset()));

        User  user = new User();

        user.setName(properties.getProperty("user.name"));

        user.setAddress(properties.getProperty("user.address"));

        return user;
    }

    @Override
    protected void writeInternal(User user, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        OutputStream outputStream = outputMessage.getBody();

        Properties properties = new Properties();

        properties.setProperty("user.name",user.getName());

        properties.setProperty("user.address",user.getAddress());

        properties.store(new OutputStreamWriter(outputStream,getDefaultCharset()),"Write by application");
    }
}
