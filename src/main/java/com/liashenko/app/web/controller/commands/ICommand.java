package com.liashenko.app.web.controller.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ICommand {
    String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;
}
