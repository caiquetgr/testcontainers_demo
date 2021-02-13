package br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.mapper;

import br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.model.TransferenceRequest;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.model.TransferenceDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class TransferenceControllerMapperTest {

    private TransferenceControllerMapper mapper = new TransferenceControllerMapper();

    @Test
    void whenMapTransferenceRequestToDto_shouldReturnTransferenceDTO() {

        final long senderId = 1L;
        final BigDecimal value = new BigDecimal("100.99");
        final long receiverId = 2L;

        final TransferenceRequest transferenceRequest = TransferenceRequest.builder()
                .senderId(senderId)
                .value(value)
                .receiverId(receiverId)
                .build();

        final TransferenceDTO transferenceDTO = mapper.mapTransferenceRequestToDto(transferenceRequest);

        assertThat(transferenceDTO).isNotNull();
        assertThat(transferenceDTO.getSenderId()).isEqualTo(senderId);
        assertThat(transferenceDTO.getValue()).isEqualTo(value);
        assertThat(transferenceDTO.getReceiverId()).isEqualTo(receiverId);

    }

}
