package br.com.iteris.caiqueborges.testcontainersdemo.transference.service;

import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.model.TransferenceDTO;

import javax.validation.Valid;

public interface TransferMoneyService {

    void transferMoney(@Valid TransferenceDTO transferenceDTO);

}
