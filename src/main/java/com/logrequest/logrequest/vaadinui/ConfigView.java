package com.logrequest.logrequest.vaadinui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(ConfigView.ROUTE)
@PageTitle("Config")
public class ConfigView extends VerticalLayout {

    public static final String ROUTE = "config";

    private HorizontalLayout layout = new HorizontalLayout();

    public ConfigView() {

        TextField configField = new TextField("Add Log format");

        add(configField);

        Button addButton = new Button("Add");
        addButton.addClickListener(buttonClickEvent -> {
            System.out.println("ok");
        });

        layout.add(addButton);
    }
}
