package com.egt.demo.demo.util;

import com.egt.demo.demo.controller.api.json.contract.ClientRequest;
import com.egt.demo.demo.controller.api.xml.contract.GetCurrentExchangeRateRequest;
import com.egt.demo.demo.controller.api.xml.contract.GetHistoryExchangeRateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Performs dummy validation based on the document that was send.
 */
public class DummyValidator {
    private static final Logger logger = LoggerFactory.getLogger(DummyValidator.class);

    /**
     * Performing some dummy validation
     *
     * @param obj
     * @return
     */
    public static boolean validateJsonRequest(ClientRequest obj) {
        logger.info("Validating request {}", obj);
        if (obj == null) {
            return false;
        }
        if (obj.getClientId() == null) {
            return false;
        }
        if (obj.getTimestamp() == null) {
            return false;
        }
        if (obj.getUniqueRequestId() == null) {
            return false;
        }
        if (obj.getCurrency() == null) {
            return false;
        }

        logger.info("Validated successfully {}", obj);
        return true;
    }

    public static boolean validateXMLCurrentRequest(GetCurrentExchangeRateRequest request) {
        logger.info("Validating request {}", request);
        if (request == null) {
            return false;
        }
        if (request.getCommand() == null) {
            return false;
        }

        if (request.getCommand().getCurrency() == null) {
            return false;
        }

        if (request.getCommand().getCustomerId() == null) {
            return false;
        }

        logger.info("Validated successfully {}", request);
        return true;
    }

    public static boolean validateXMLHistoryRequest(GetHistoryExchangeRateRequest request) {
        logger.info("Validating request {}", request);
        if (request == null) {
            return false;
        }
        if (request.getHistoryTag() == null) {
            return false;
        }

        if (request.getHistoryTag().getPeriod() < 0) {
            return false;
        }

        if (request.getHistoryTag().getCurrency() == null) {
            return false;
        }

        logger.info("Validated successfully {}", request);
        return true;
    }
}
