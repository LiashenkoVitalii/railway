package com.liashenko.app.web.controller.commands;

import com.liashenko.app.service.ServiceFactory;
import com.liashenko.app.service.ServiceTestFactoryImpl;
import com.liashenko.app.web.controller.commands.index.ShowIndexViewCommand;
import com.liashenko.app.web.controller.manager.PageManagerConf;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ShowIndexViewCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;
    private ServiceFactory serviceFactory = ServiceTestFactoryImpl.getInstance();
    private ICommand command = new ShowIndexViewCommand(serviceFactory);

    @Test
    public void returnsExpectedPath() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String expectedPath = PageManagerConf.getInstance().getProperty(PageManagerConf.INDEX_PAGE_PATH);
        String actualPath = command.execute(request, response);
        assertEquals(expectedPath, actualPath);
    }
}
