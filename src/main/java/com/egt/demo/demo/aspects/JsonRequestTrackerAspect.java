package com.egt.demo.demo.aspects;

import com.egt.demo.demo.controller.api.json.contract.ClientRequest;
import com.egt.demo.demo.dao.RequestHistoryDAO;
import com.egt.demo.demo.model.RequestHistory;
import com.egt.demo.demo.util.DateTransformator;
import com.egt.demo.demo.util.DummyValidator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


/**
 * In case of duplicate requests based on requestId
 * the aspect will reject the second incoming request.
 *
 * Note: Performs dummy validation see {@link DummyValidator}
 */
@Component
@Aspect
public class JsonRequestTrackerAspect {
    private final Logger logger = LoggerFactory.getLogger(JsonRequestTrackerAspect.class);

    private String jsonServiceName = "EXT_SERVICE_JSON";

    @Autowired
    private RequestHistoryDAO requestHistoryDAO;

    @Around("execution(* com.egt.demo.demo.controller.api.json.CurrencyRest.*(..))")
    public Object registerRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] signatureArgs = joinPoint.getArgs();
        ClientRequest clientRequest = (ClientRequest) signatureArgs[0];
        logger.info("Registering request {}", clientRequest);

        if (!DummyValidator.validateJsonRequest(clientRequest)) {
            logger.info("Validation NOT passed {}", clientRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        //It was not clear what will be the format of  the requiestId, 123-4441-233 is valid id from the document
        RequestHistory requestHistory = RequestHistory.builder()
                .uniqueRequestId(clientRequest.getUniqueRequestId())
                .clientId(clientRequest.getClientId())
                .serviceName(jsonServiceName)
                .date(DateTransformator.transform(Long.parseLong(clientRequest.getTimestamp())))
                .build();

        RequestHistory existingRequest = requestHistoryDAO.findByUniqueRequestId(clientRequest.getUniqueRequestId());
        if (existingRequest != null) {
            logger.info("Request with {} is already present in the Database", clientRequest.getUniqueRequestId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        logger.info("Request with {} is registered in the Database", clientRequest.getUniqueRequestId());
        requestHistoryDAO.save(requestHistory);

        logger.info("Proceeding to controller...");
        return joinPoint.proceed();
    }


}
