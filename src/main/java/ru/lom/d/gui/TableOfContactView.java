package ru.lom.d.gui;

import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import ru.lom.d.data.Contact;
import ru.lom.d.services.ContactServiceImpl;

import java.util.Set;

public class TableOfContactView extends VerticalLayout {

    private final RootView rootView;

    private Grid<Contact> grid = new Grid();
    private TextField searchString = new TextField("Search");
    private Button removeBtn = new Button("Remove");
    private Button searchBtn = new Button("Search");
    private Button refreshBtn = new Button("Refresh");
    private ContactServiceImpl contactService = new ContactServiceImpl();

    public TableOfContactView(RootView rootView) {
        this.rootView = rootView;

        searchString.setWidth("100%");

        refreshBtn.addClickListener(e -> fillGrid());

        searchBtn.setWidth("10%");
        searchBtn.addClickListener(e -> searchContacts());
        HorizontalLayout horizontalLayout = new HorizontalLayout(searchString, searchBtn, refreshBtn);
        horizontalLayout.setSizeFull();
        horizontalLayout.setComponentAlignment(searchBtn, Alignment.BOTTOM_LEFT);
        horizontalLayout.setComponentAlignment(refreshBtn, Alignment.BOTTOM_RIGHT);


        removeBtn.addClickListener(e -> remove());

        fillGrid();
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(Contact::getFirstName).setCaption("first name");
        grid.addColumn(Contact::getLastName).setCaption("Last name");
        grid.addColumn(Contact::getEmail).setCaption("email");
        grid.addColumn(Contact::getPhoneNumber).setCaption("phone number");
        grid.addSelectionListener(e -> rootView.getContactManage().showContact(getSelectedContact()));

        addComponent(horizontalLayout);
        addComponent(grid);
        addComponent(removeBtn);
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
