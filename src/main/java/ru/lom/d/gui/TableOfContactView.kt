package ru.lom.d.gui

import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import ru.lom.d.data.Contact
import ru.lom.d.services.ContactService
import ru.lom.d.services.ContactServiceImpl


class TableOfContactView(rootView: RootView) : VerticalLayout() {
    var grid = Grid(Contact::class.java)
    var contactService: ContactService = ContactServiceImpl()
    var searchString = TextField("Search")


    init {
        val removeBtn = Button("Remove")
        val searchBtn = Button("Search")
        val refreshBtn = Button("Refresh")
        searchString.width = "100%"
        refreshBtn.addClickListener { fillGrid() }
        searchBtn.width = "10%"
        refreshBtn.width = "10%"
        searchBtn.addClickListener { searchContacts() }
        val horizontalLayout = HorizontalLayout(searchString, searchBtn, refreshBtn)
        horizontalLayout.setWidthFull()
        horizontalLayout.defaultVerticalComponentAlignment = FlexComponent.Alignment.END
        removeBtn.addClickListener { remove() }
        fillGrid()
        grid.setWidthFull()
        grid.setSelectionMode(Grid.SelectionMode.SINGLE)
        grid.removeColumnByKey("id")
        grid.addSelectionListener { rootView.contactManage.showContact(getSelectedContact()) }
        add(horizontalLayout)
        add(grid)
        add(removeBtn)
    }

    fun getSelectedContact(): Contact? {
        return if (grid.selectedItems.isNotEmpty()) {
            grid.selectedItems.iterator().next()
        } else null
    }

    private fun searchContacts() {
        grid.setItems(contactService.getContactsByRegex(searchString.value))
        grid.dataProvider.refreshAll()
    }

    fun fillGrid() {
        grid.setItems(contactService.allContact)
        grid.dataProvider.refreshAll()
    }

    private fun remove() {
        val selectedContact = getSelectedContact()
        if (selectedContact != null) {
            contactService.removeContact(selectedContact)
            fillGrid()
        }
    }


}