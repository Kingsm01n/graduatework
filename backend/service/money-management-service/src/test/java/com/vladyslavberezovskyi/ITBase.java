package com.vladyslavberezovskyi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WithMockUser(username = "user")
@SpringBootTest(classes = {MoneyManagementService.class})
@AutoConfigureMockMvc(webDriverEnabled = false, webClientEnabled = false)
@ActiveProfiles("integration-test")
public class ITBase {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

}
