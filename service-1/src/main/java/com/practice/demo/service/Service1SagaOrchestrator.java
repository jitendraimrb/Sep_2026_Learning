package com.practice.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.practice.demo.dto.PaymentSagaRequest;
import com.practice.demo.exception.BusinessException;

@Service
public class Service1SagaOrchestrator {

    private final Logger log = LoggerFactory.getLogger(Service1SagaOrchestrator.class);

    private final Service1LocalService service1LocalService;
    private final Service2WebClient service2WebClient;

    public Service1SagaOrchestrator(Service1LocalService service1LocalService,
                                    Service2WebClient service2WebClient) {
        this.service1LocalService = service1LocalService;
        this.service2WebClient = service2WebClient;
    }

//    @Transactional
    public void startPaymentSaga(PaymentSagaRequest request, String correlationId) {

        log.info("SAGA STARTED in Service‑1, txId={}, correlationId={}",
                request.getTransactionId(), correlationId);

        // ---------------------------------------------------------
        // STEP 1: Local transaction in Service‑1 (DEBIT)
        // ---------------------------------------------------------
        service1LocalService.debitCustomerAccount(request, correlationId);

        try {
            // ---------------------------------------------------------
            // STEP 2: Downstream call to Service‑2 (RESERVE LIMIT)
            // ---------------------------------------------------------
            service2WebClient.reserveLimit(request, correlationId);

            log.info("SAGA COMPLETED successfully, txId={}, correlationId={}",
                    request.getTransactionId(), correlationId);

        } catch (BusinessException  ex) {

            log.error("SAGA FAILED, triggering compensation in Service‑1, txId={}, correlationId={}",
                    request.getTransactionId(), correlationId, ex);

            // ---------------------------------------------------------
            // STEP 3: Compensation (REVERSE DEBIT)
            // ---------------------------------------------------------
            service1LocalService.reverseDebit(request.getTransactionId(), correlationId);

            throw ex;
        }
    }
}
