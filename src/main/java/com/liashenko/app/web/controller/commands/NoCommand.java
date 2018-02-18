package com.liashenko.app.web.controller.commands;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.web.controller.manager.PageManagerConf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Used to redirect user to the index.jsp if there is not requested command
public class NoCommand implements ICommand {

    private ServiceFactory serviceFactory;

    public NoCommand(ServiceFactory serviceFactory) {
//        this.serviceFactory = serviceFactory;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        return PageManagerConf.getInstance().getProperty(PageManagerConf.INDEX_PAGE_PATH);
    }
}