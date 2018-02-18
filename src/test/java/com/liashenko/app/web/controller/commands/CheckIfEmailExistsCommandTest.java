package com.liashenko.app.web.controller.commands;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.ServiceTestFactoryImpl;
import com.liashenko.app.web.controller.commands.registration.CheckIfEmailExistsCommand;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import com.liashenko.app.web.controller.utils.SessionAttrInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckIfEmailExistsCommandTest {
    private ServiceFactory serviceFactory = ServiceTestFactoryImpl.getInstance();
    private ICommand command = new CheckIfEmailExistsCommand(serviceFactory);

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Test
    public void someTest() throws ServletException, IOException {
        HttpSession session = mock(HttpSession.class);
        when(request.getSession(Boolean.TRUE)).thenReturn(session);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(SessionAttrInitializer.USER_LOCALE)).thenReturn("uk_UA");
        when(request.getParameter("email")).thenReturn("user@mail.com");
        PrintWriter out = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(out);
        String expectedValue = PageManagerConf.getInstance().getProperty(PageManagerConf.EMPTY_RESULT);
        String actualValue = command.execute(request, response);
        assertEquals(expectedValue, actualValue);
    }
}
