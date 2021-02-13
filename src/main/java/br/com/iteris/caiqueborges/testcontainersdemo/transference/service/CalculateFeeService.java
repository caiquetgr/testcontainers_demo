package br.com.iteris.caiqueborges.testcontainersdemo.transference.service;

import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;

import java.math.BigDecimal;

public interface CalculateFeeService {

    BigDecimal discountFeeFromValue(BigDecimal transferValue, User senderUser, User receiverUser);

}
