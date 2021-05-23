package com.pichincha.backend.test.rest;

import com.pichincha.backend.test.dto.AccountDto;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AccountControllerTest extends AbstractControllerTest {

    @Test
    public void shouldReturnFoundAccount() throws Exception {
        // given
        LocalDateTime creationDate = LocalDateTime.of(2020, 9, 21, 15, 00, 16);
        AccountDto account = new AccountDto("001", "saving", creationDate);

        // when
        when(accountService.getAccount(1L)).thenReturn(account);

        // then
        mockMvc.perform(get("/accounts/1").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.number", is("001")))
                .andExpect(jsonPath("$.type", is("saving")))
                .andExpect(jsonPath("$.creationDate", is(creationDate.toString())));

    }
}
