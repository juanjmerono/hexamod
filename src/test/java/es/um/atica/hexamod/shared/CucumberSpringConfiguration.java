package es.um.atica.hexamod.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
@ContextConfiguration
public class CucumberSpringConfiguration {
    
    private static final String VALID_SCOPE = "SCOPE_test";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private RequestPostProcessor jwt;
    private String apiPath;

    public void setup() {
        objectMapper.registerModule(new org.springframework.hateoas.mediatype.hal.Jackson2HalModule());
    }

    protected void setAPIPath(String path) { this.apiPath = path; }
    protected String getAPIPath() { return this.apiPath; }

    protected void setJWT() { setJWT(null); }

    protected void setJWT(String authUser) {
        if (authUser!=null) {
            jwt = SecurityMockMvcRequestPostProcessors.jwt()
                    .jwt(u->u.subject(authUser))
                    .authorities(new SimpleGrantedAuthority(VALID_SCOPE));
        } else {
            jwt = new RequestPostProcessor(){
                @Override
                public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                    // Nothing to to
                    return request;
                }  
            };                
        }
    }

    protected RequestPostProcessor getJWT() { return this.jwt; }

    protected MockMvc getMVC() { return this.mvc; }
    protected ObjectMapper getObjectMapper() { return this.objectMapper; }
}
