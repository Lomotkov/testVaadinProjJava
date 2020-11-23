package ru.lom.d.gui

import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.PWA


@Route(value = "hello")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@PageTitle("Hello World")
class RootView : HorizontalLayout() {
    val tableOfContact = TableOfContactView(this)
    val contactManage = ContactManageView(this)

    init {
        val mainLayout = VerticalLayout()
        mainLayout.add(tableOfContact, contactManage)
        add(mainLayout)
    }
}