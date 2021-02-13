package br.com.iteris.caiqueborges.testcontainersdemo.transference.controller;

import br.com.iteris.caiqueborges.testcontainersdemo.base.BaseTestController;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.mapper.TransferenceControllerMapper;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.model.TransferenceRequest;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.fixture.TransferenceTemplateLoader;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.TransferMoneyService;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.model.TransferenceDTO;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("transference")
@WebMvcTest(TransferenceController.class)
class TransferenceControllerTest extends BaseTestController {

    @MockBean
    private TransferMoneyService transferMoneyService;

    @SpyBean
    private TransferenceControllerMapper transferenceControllerMapper;

    @BeforeAll
    static void setupForAll() {
        FixtureFactoryLoader.loadTemplates("br.com.iteris.caiqueborges.testcontainersdemo.transference.fixture");
    }

    @SneakyThrows
    @Test
    void whenTransferMoney_thenReturnStatus200() {

        final ArgumentCaptor<TransferenceDTO> transferenceDTOCaptor = ArgumentCaptor.forClass(TransferenceDTO.class);

        final TransferenceRequest transferenceRequest = Fixture.from(TransferenceRequest.class)
                .gimme(TransferenceTemplateLoader.TRANSFERENCE_REQUEST_VALID);

        willDoNothing()
                .given(transferMoneyService)
                .transferMoney(any(TransferenceDTO.class));

        mockMvc.perform(post("/transference")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferenceRequest)))
                .andExpect(status().isOk());

        verify(transferMoneyService).transferMoney(transferenceDTOCaptor.capture());

        final TransferenceDTO transferenceDTO = transferenceDTOCaptor.getValue();

        assertThat(transferenceDTO.getSenderId()).isEqualTo(transferenceRequest.getSenderId());
        assertThat(transferenceDTO.getValue()).isEqualTo(transferenceRequest.getValue());
        assertThat(transferenceDTO.getReceiverId()).isEqualTo(transferenceRequest.getReceiverId());

    }

}
