package br.com.iteris.caiqueborges.testcontainersdemo.transference.controller;

import br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.mapper.TransferenceControllerMapper;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.controller.model.TransferenceRequest;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.TransferMoneyService;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.model.TransferenceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TransferenceController {

    private final TransferenceControllerMapper transferenceControllerMapper;
    private final TransferMoneyService transferMoneyService;

    @PostMapping("/transference")
    public ResponseEntity<Void> transferMoney(@RequestBody TransferenceRequest transferenceRequest) {

        final TransferenceDTO transferenceDTO = transferenceControllerMapper
                .mapTransferenceRequestToDto(transferenceRequest);

        transferMoneyService.transferMoney(transferenceDTO);

        return ResponseEntity.ok().build();

    }

}
