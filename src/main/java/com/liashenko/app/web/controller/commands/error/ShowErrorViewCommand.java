package com.liashenko.app.web.controller.commands.error;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.web.controller.commands.ICommand;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Shows error page if something wrong occured during request handling
public class ShowErrorViewCommand implements ICommand {
    private static final Logger classLogger = LogManager.getLogger(ShowErrorViewCommand.class);

    private ServiceFactory serviceFactory;

    public ShowErrorViewCommand(ServiceFactory serviceFactory) {
//        this.serviceFactory = serviceFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return PageManagerConf.getInstance().getProperty(PageManagerConf.ERROR_PAGE_PATH);
    }
}
