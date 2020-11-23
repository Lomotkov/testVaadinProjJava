package ru.lom.d.gui

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import ru.lom.d.data.Contact
import ru.lom.d.services.ContactService
import ru.lom.d.services.ContactServiceImpl


class ContactManageView(val rootView: RootView) : VerticalLayout() {
    var contactService: ContactService = ContactServiceImpl()
    private var newContactFlag = false
    var firstName = TextField("First name")
    var lastName = TextField("Last name")
    var email = TextField("Email")
    var phoneNumber = TextField("Phone number")
    var layout = VerticalLayout()

    init {
        val newContactBtn = Button("Add new contact")
        val saveBtn = Button("Save")
        val closeBtn = Button("Close")
        saveBtn.addClickListener { addNewContact() }
        saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        closeBtn.addClickListener { layout.isVisible = false }
        newContactBtn.addClickListener { showNewContactForm() }
        add(newContactBtn)
        layout.add(HorizontalLayout(firstName, lastName, email, phoneNumber))
        layout.add(saveBtn)
        layout.add(closeBtn)
        layout.isVisible = false
        add(layout)
    }

    private fun showNewContactForm() {
        firstName.value = ""
        lastName.value = ""
        email.value = ""
        phoneNumber.value = ""
        newContactFlag = true
        layout.isVisible = true
    }

    fun showContact(contact: Contact?) {
        if (contact != null) {
            firstName.value = contact.firstName
            lastName.value = contact.lastName
            email.value = contact.email
            phoneNumber.value = contact.phoneNumber
            newContactFlag = false
            layout.isVisible = true
        }
    }

    private fun addNewContact() {
        if (newContactFlag) {
            val contact = Contact(firstName.value,
                    lastName.value,
                    email.value,
                    phoneNumber.value)
            contactService.addContact(contact)
        } else {
            val contact = rootView.tableOfContact.getSelectedContact()!!
            contact.firstName = firstName.value
            contact.lastName = lastName.value
            contact.email = email.value
            contact.phoneNumber = phoneNumber.value
            contactService.updateContact(contact)
        }
        firstName.value = ""
        lastName.value = ""
        email.value = ""
        phoneNumber.value = ""
        layout.isVisible = false
        rootView.tableOfContact.fillGrid()
    }


}
