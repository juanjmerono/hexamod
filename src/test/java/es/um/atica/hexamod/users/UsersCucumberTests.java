package es.um.atica.hexamod.users;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.test.context.TestPropertySource;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("users")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "es.um.atica.hexamod.users.cucumber")
@TestPropertySource("classpath:test.properties")
public class UsersCucumberTests {
    
}
