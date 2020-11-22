package ru.lom.d.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import ru.lom.d.data.Contact;
import ru.lom.d.services.ContactService;
import ru.lom.d.services.ContactServiceImpl;

public class ContactManageView extends VerticalLayout {

    ContactService contactService = new ContactServiceImpl();

    private final RootView rootView;

    private boolean newContactFlag = false;

    private Button newContactBtn = new Button("Add new contact");
    private VerticalLayout layout = new VerticalLayout();
    private Button saveBtn = new Button("Save");
    private Button closeBtn = new Button("Close");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private TextField phoneNumber = new TextField("Phone number");


    public ContactManageView(RootView rootView) {
        this.rootView = rootView;

        saveBtn.addClickListener(e -> addNewContact());
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        closeBtn.addClickListener(e -> layout.setVisible(false));

        newContactBtn.addClickListener(e -> showNewContactForm());
        add(newContactBtn);

        layout.add(new HorizontalLayout(firstName, lastName, email, phoneNumber));
        layout.add(saveBtn);
        layout.add(closeBtn);
        layout.setVisible(false);
        add(layout);
    }

    private void showNewContactForm() {
        firstName.setValue("");
        lastName.setValue("");
        email.setValue("");
        phoneNumber.setValue("");

        newContactFlag = true;

        layout.setVisible(true);
    }

    public void showContact(Contact contact) {
        if(contact != null) {
            firstName.setValue(contact.getFirstName());
            lastName.setValue(contact.getLastName());
            email.setValue(contact.getEmail());
            phoneNumber.setValue(contact.getPhoneNumber());

            newContactFlag = false;

            layout.setVisible(true);
        }
    }

    private void addNewContact() {
        if(newContactFlag) {
            Contact contact = new Contact(firstName.getValue(),
                    lastName.getValue(),
                    email.getValue(),
                    phoneNumber.getValue());
            contactService.addContact(contact);
        } else {
            Contact contact = rootView.getTableOfContact().getSelectedContact();
            contact.setFirstName(firstName.getValue());
            contact.setLastName(lastName.getValue());
            contact.setEmail(email.getValue());
            contact.setPhoneNumber(phoneNumber.getValue());
            contactService.updateContact(contact);
        }

        firstName.setValue("");
        lastName.setValue("");
        email.setValue("");
        phoneNumber.setValue("");

        layout.setVisible(false);
        rootView.getTableOfContact().fillGrid();
    }


}
