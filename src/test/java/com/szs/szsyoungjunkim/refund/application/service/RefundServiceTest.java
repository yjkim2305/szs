package com.szs.szsyoungjunkim.refund.application.service;

import com.szs.szsyoungjunkim.refund.application.repository.RefundRepository;
import com.szs.szsyoungjunkim.refund.application.dto.RefundCreateCommand;
import com.szs.szsyoungjunkim.refund.domain.Refund;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefundServiceTest {
    @Mock
    private RefundRepository refundRepository;

    @InjectMocks
    private RefundService refundService;

    @Test
    @DisplayName("정상적으로 저장 테스트")
    void saveRefund_Success() {
        // Given
        RefundCreateCommand refundCreateCommand = new RefundCreateCommand(2023, 50000000L, "3000000.0", "testUser");
        Refund mockRefund = mock(Refund.class);

        mockStatic(Refund.class);
        when(Refund.from(refundCreateCommand)).thenReturn(mockRefund);

        refundService.saveRefund(refundCreateCommand);

        verify(refundRepository, times(1)).saveRefund(mockRefund);
    }

    @Test
    @DisplayName("종합소득금액과 세액공제가 존재할 경우 true를 반환한다.")
    void existsByUserIdAndTaxYear_test() {
        String userId = "testUser";
        Integer taxYear = 2023;

        when(refundRepository.existsByUserIdAndTaxYear(userId, taxYear)).thenReturn(true);

        boolean exists = refundService.existsByUserIdAndTaxYear(userId, taxYear);

        assertThat(exists).isTrue();
         verify(refundRepository, times(1)).existsByUserIdAndTaxYear(userId, taxYear);
    }

    @Test
    @DisplayName("소득공제가 해당 유저의 데이터가 존재할 경우 데이터를 반환한다.")
    void findByUserId_test() {
        String userId = "testUser";
        Refund expectedRefund = mock(Refund.class);

        when(refundRepository.findByUserId(userId)).thenReturn(expectedRefund);

        Refund result = refundService.findByUserId(userId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(expectedRefund);
        verify(refundRepository, times(1)).findByUserId(userId);
    }
}