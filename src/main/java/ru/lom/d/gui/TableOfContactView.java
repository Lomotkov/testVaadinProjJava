package ru.lom.d.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.lom.d.data.Contact;
import ru.lom.d.services.ContactService;
import ru.lom.d.services.ContactServiceImpl;

import java.util.Set;

public class TableOfContactView extends VerticalLayout {

    Grid<Contact> grid = new Grid<>(Contact.class);
    ContactService contactService = new ContactServiceImpl();
    TextField searchString = new TextField("Search");


    public TableOfContactView(RootView rootView) {
        Button removeBtn = new Button("Remove");
        Button searchBtn = new Button("Search");
        Button refreshBtn = new Button("Refresh");

        searchString.setWidth("100%");

        refreshBtn.addClickListener(e -> fillGrid());

        searchBtn.setWidth("10%");
        refreshBtn.setWidth("10%");
        searchBtn.addClickListener(e -> searchContacts());
        HorizontalLayout horizontalLayout = new HorizontalLayout(searchString, searchBtn, refreshBtn);
        horizontalLayout.setWidthFull();
        horizontalLayout.setDefaultVerticalComponentAlignment(Alignment.END);


        removeBtn.addClickListener(e -> remove());

        fillGrid();
        grid.setWidthFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(Contact::getFirstName).setHeader("first name");
        grid.addColumn(Contact::getLastName).setHeader("Last name");
        grid.addColumn(Contact::getEmail).setHeader("email");
        grid.addColumn(Contact::getPhoneNumber).setHeader("phone number");
        grid.addSelectionListener(e -> rootView.getContactManage().showContact(getSelectedContact()));

        add(horizontalLayout);
        add(grid);
        add(removeBtn);
    }

    public Contact getSelectedContact() {
        Set<Contact> selectedContact = grid.getSelectedItems();
        if (!selectedContact.isEmpty()) {
            return selectedContact.iterator().next();
        }
        return null;
    }

    private void searchContacts() {
        grid.setItems(contactService.getContactsByRegex(searchString.getValue()));
        grid.getDataProvider().refreshAll();
    }

    public void fillGrid() {
        grid.setItems(contactService.getAllContact());
        grid.getDataProvider().refreshAll();
    }

    public void remove() {
        Contact selectedContact = getSelectedContact();
        if (selectedContact != null) {
            contactService.removeContact(selectedContact);
            fillGrid();
        }
    }


}
