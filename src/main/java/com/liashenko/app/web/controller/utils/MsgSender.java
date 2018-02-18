package com.liashenko.app.web.controller.utils;


import com.google.gson.Gson;
import com.liashenko.app.service.dto.AutocompleteDto;
import com.liashenko.app.service.dto.ResponseMsgDto;
import com.liashenko.app.web.controller.utils.exceptions.SendMsgException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public abstract class MsgSender {

    private static final Logger classLogger = LogManager.getLogger(MsgSender.class);
    private static final Gson GSON = new Gson();


    public static void sendJsonMsg(HttpServletResponse resp, List<AutocompleteDto> advices) {
        resp.setHeader("Content-Type", "application/octet-stream;charset=UTF-8");
        try {
            PrintWriter out = resp.getWriter();
            out.println(jsonResponse(advices));
            out.close();
        } catch (IOException ex) {
            classLogger.error(ex);
            throw new SendMsgException(ex.getMessage());
        }
    }

    public static void sendJsonMsg(HttpServletResponse resp, String jsonText) {
        resp.setHeader("Content-Type", "application/octet-stream;charset=UTF-8");
        try {
            PrintWriter out = resp.getWriter();
            out.println(jsonText);
            out.close();
        } catch (IOException ex) {
            classLogger.error(ex);
            throw new SendMsgException(ex.getMessage());
        }
    }


//    public static void sendJsonMsg(HttpServletResponse resp, Rights<AutocompleteDto> advices) {
//        resp.setHeader("Content-Type", "application/json;charset=UTF-8");
//        try {
//            PrintWriter out = resp.getWriter();
//            out.println(jsonResponse(advices));
//            out.close();
//        } catch (IOException ex) {
//            classLogger.error(ex);
//            throw new SendMsgException(ex.getMessage());
//        }
//    }

    public static void sendJsonMsg(HttpServletResponse resp, String message, boolean resultFlag) {
        resp.setHeader("Content-Type", "application/octet-stream;");
        try {
            PrintWriter out = resp.getWriter();
            out.println(jsonResponse(message, resultFlag));
            out.close();
        } catch (IOException ex) {
            classLogger.error(ex);
            throw new SendMsgException(ex.getMessage());
        }
    }

    public static void sendJsonMsg(HttpServletResponse resp, String message, String message2, boolean resultFlag) {
        resp.setHeader("Content-Type", "application/octet-stream;");
        try {
            PrintWriter out = resp.getWriter();
            out.println(jsonResponse(message, message2, resultFlag));
            out.close();
        } catch (IOException ex) {
            classLogger.error(ex);
            throw new SendMsgException(ex.getMessage());
        }
    }


    private static String jsonResponse(List<AutocompleteDto> advices) {
        return GSON.toJson(advices);
    }

    private static String jsonResponse(String printMessage1, boolean resultFlag) {
        ResponseMsgDto responseMsgDto = new ResponseMsgDto(printMessage1, resultFlag);
        return GSON.toJson(responseMsgDto);
    }

    private static String jsonResponse(String printMessage1, String printMessage2, boolean resultFlag) {
        ResponseMsgDto responseMsgDto = new ResponseMsgDto(printMessage1, printMessage2, resultFlag);
        return GSON.toJson(responseMsgDto);
    }
}
