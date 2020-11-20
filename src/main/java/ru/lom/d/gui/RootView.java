package ru.lom.d.gui;

import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Title("testVaadin")
public class RootView extends UI {
    private TableOfContactView tableOfContact = new TableOfContactView(this);
    private ContactManageView contactManage = new ContactManageView(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.addComponents(tableOfContact, contactManage);
        setContent(mainLayout);
    }

    public ContactManageView getContactManage() {
        return contactManage;
    }

    public TableOfContactView getTableOfContact() {
        return tableOfContact;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RootView.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
