package com.logrequest.logrequest.vaadinui;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(RegisterView.ROUTE)
@PageTitle("Register")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RegisterView extends HorizontalLayout {

    public static final String ROUTE = "register";

    public RegisterView () {

        add(new Html("<h2>Create new password</h2>"));

        PasswordField newPass = new PasswordField("New password");
        newPass.setWidth("100%");
        PasswordField retypePass = new PasswordField("Retype password");
        retypePass.setWidth("100%");

        Button submit = new Button("Log in");
        submit.setWidth("100%");
        submit.setDisableOnClick(true);
        submit.addClickListener(buttonClickEvent -> {

        });
    }
}
