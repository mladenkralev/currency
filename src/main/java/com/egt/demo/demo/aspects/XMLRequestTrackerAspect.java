package com.egt.demo.demo.aspects;

import com.egt.demo.demo.controller.api.xml.contract.GetCurrentExchangeRateRequest;
import com.egt.demo.demo.controller.api.xml.contract.GetHistoryExchangeRateRequest;
import com.egt.demo.demo.dao.RequestHistoryDAO;
import com.egt.demo.demo.model.RequestHistory;
import com.egt.demo.demo.util.DateTransformator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * In case of duplicate requests based on requestId
 * The aspect will reject the second incoming request
 */
@Component
@Aspect
public class XMLRequestTrackerAspect {
    private String xmlServiceName = "EXT_SERVICE_XML";

    @Autowired
    private RequestHistoryDAO requestHistoryDAO;

    @Around("execution(* com.egt.demo.demo.controller.api.xml.CurrencyXML.getCurrentExchangeRate(..))")
    public Object registerCurrentRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] signatureArgs = joinPoint.getArgs();
        GetCurrentExchangeRateRequest clientRequest = (GetCurrentExchangeRateRequest) signatureArgs[0];

        //It was not clear what will be the format of  the requiestId, 123-4441-233 is valid id from the document
        String requestId = Long.toString(clientRequest.getId());
        Long customerId = clientRequest.getGet().getCustomerId();

        return validateIfFirstRequest(joinPoint, requestId, customerId);
    }

    @Around("execution(* com.egt.demo.demo.controller.api.xml.CurrencyXML.getHistoryExchangeRate(..))")
    public Object registerHistoryRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] signatureArgs = joinPoint.getArgs();
        GetHistoryExchangeRateRequest clientRequest = (GetHistoryExchangeRateRequest) signatureArgs[0];

        //It was not clear what will be the format of  the requiestId, 123-4441-233 is valid id from the document
        String requestId = Long.toString(clientRequest.getId());
        Long customerId = clientRequest.getHistoryTag().getCustomerId();

        return validateIfFirstRequest(joinPoint, requestId, customerId);
    }

    private Object validateIfFirstRequest(ProceedingJoinPoint joinPoint, String requestId, Long customerId) throws Throwable {
        RequestHistory requestHistory = RequestHistory.builder()
                .uniqueRequestId(requestId)
                .clientId(customerId)
                .serviceName(xmlServiceName)
                .date(DateTransformator.transform(System.currentTimeMillis()))
                .build();

        RequestHistory existingRequest = requestHistoryDAO.findByUniqueRequestId(requestId);
        if (existingRequest != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        requestHistoryDAO.save(requestHistory);
        return joinPoint.proceed();
    }
}
