package br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.mapper;

import br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.model.TransferenceRequest;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.model.TransferenceDTO;
import org.springframework.stereotype.Component;

@Component
public class TransferenceControllerMapper {

    public TransferenceDTO mapTransferenceRequestToDto(TransferenceRequest transferenceRequest) {

        return TransferenceDTO.builder()
                .senderId(transferenceRequest.getSenderId())
                .value(transferenceRequest.getValue())
                .receiverId(transferenceRequest.getReceiverId())
                .build();

    }

}
