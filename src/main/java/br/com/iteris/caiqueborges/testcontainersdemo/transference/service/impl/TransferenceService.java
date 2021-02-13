package br.com.iteris.caiqueborges.testcontainersdemo.transference.service.impl;

import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.CalculateFeeService;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.TransferMoneyService;
import br.com.iteris.caiqueborges.testcontainersdemo.transference.service.model.TransferenceDTO;
import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;
import br.com.iteris.caiqueborges.testcontainersdemo.user.service.UserReadService;
import br.com.iteris.caiqueborges.testcontainersdemo.user.service.UserUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;

@Validated
@RequiredArgsConstructor
@Service
class TransferenceService implements TransferMoneyService {

    private final UserReadService userReadService;
    private final UserUpdateService userUpdateService;
    private final CalculateFeeService calculateFeeService;

    @Override
    @Transactional
    public void transferMoney(@Valid TransferenceDTO transferenceDTO) {

        final User senderUser = findUserById(transferenceDTO.getSenderId());
        final User receiverUser = findUserById(transferenceDTO.getReceiverId());

        final BigDecimal transferValue = calculateFeeService
                .discountFeeFromValue(transferenceDTO.getValue(), senderUser, receiverUser);

        calculateAndSetNewAmountForUsers(transferValue, senderUser, receiverUser);
        updateUser(senderUser);
        updateUser(receiverUser);

    }

    private User findUserById(Long userId) {
        return userReadService.findById(userId);
    }

    private void updateUser(User user) {
        userUpdateService.updateUser(user);
    }

    private void calculateAndSetNewAmountForUsers(BigDecimal transferValue, User senderUser, User receiverUser) {

        senderUser.setBalance(senderUser.getBalance().subtract(transferValue));
        receiverUser.setBalance(receiverUser.getBalance().add(transferValue));

    }

}
