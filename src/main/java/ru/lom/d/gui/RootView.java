package ru.lom.d.gui;


import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;


@Route(value = "hello")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@PageTitle("Hello World")
public class RootView extends HorizontalLayout {
    private final TableOfContactView tableOfContact = new TableOfContactView(this);
    private final ContactManageView contactManage = new ContactManageView(this);


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
