package ru.lom.d.gui;


import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;


import javax.servlet.annotation.WebServlet;

@Route(value = "hello")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@PageTitle("Hello World")
public class RootView extends HorizontalLayout {
    private TableOfContactView tableOfContact = new TableOfContactView(this);
    private ContactManageView contactManage = new ContactManageView(this);


    public RootView() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.add(tableOfContact, contactManage);
        add(mainLayout);
    }

    public ContactManageView getContactManage() {
        return contactManage;
    }

    public TableOfContactView getTableOfContact() {
        return tableOfContact;
    }
}
