package com.egt.demo.demo.aspects;

import com.egt.demo.demo.controller.api.json.contract.ClientRequest;
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
 * The aspect will reject the second incoming request.
 */
@Component
@Aspect
public class JsonRequestTrackerAspect {
    private String jsonServiceName = "EXT_SERVICE_JSON";

    @Autowired
    private RequestHistoryDAO requestHistoryDAO;

    @Around("execution(* com.egt.demo.demo.controller.api.json.CurrencyRest.*(..))")
    public Object registerRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] signatureArgs = joinPoint.getArgs();
        ClientRequest clientRequest = (ClientRequest) signatureArgs[0];

        //It was not clear what will be the format of  the requiestId, 123-4441-233 is valid id from the document

        RequestHistory requestHistory = RequestHistory.builder()
                .uniqueRequestId(clientRequest.getUniqueRequestId())
                .clientId(clientRequest.getClientId())
                .serviceName(jsonServiceName)
                .date(DateTransformator.transform(Long.parseLong(clientRequest.getTimestamp())))
                .build();

        RequestHistory existingRequest = requestHistoryDAO.findByUniqueRequestId(clientRequest.getUniqueRequestId());
        if (existingRequest != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        requestHistoryDAO.save(requestHistory);
        return joinPoint.proceed();
    }
}
