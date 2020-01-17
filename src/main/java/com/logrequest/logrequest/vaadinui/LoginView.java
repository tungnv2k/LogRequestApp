package com.logrequest.logrequest.vaadinui;

import com.logrequest.logrequest.backend.models.User;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value = LoginView.ROUTE)
@PageTitle("Log In")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/shared-styles.css")
public class LoginView extends HorizontalLayout {
    public static final String ROUTE = "login";

    public LoginView() {

        VerticalLayout vertLayout = new VerticalLayout();
        vertLayout.add(new Html("<h1>Log in</h1>"));

        User user = new User();

        TextField username = new TextField("Username");
        username.setWidth("100%");
        PasswordField password = new PasswordField("Password");
        password.setWidth("100%");
        Button submit = new Button("Log in");
        submit.setWidth("100%");
        submit.setDisableOnClick(true);
        submit.addClickListener(buttonClickEvent -> {
            user.setUsername(username.getValue());
            user.setPassword(password.getValue());
            System.out.println(user.getUsername() + " " + user.getPassword());
            submit.getUI().ifPresent(ui -> ui.navigate("register"));
        });

        vertLayout.add(username, password, submit);

        vertLayout.add(new Text("__________or__________"));

        Button loginGoogle = new Button("Login with Google");
        loginGoogle.setWidth("100%");
        loginGoogle.setIcon(new Icon(VaadinIcon.GOOGLE_PLUS_SQUARE));
        Anchor googleLink = new Anchor("/login/google");
        googleLink.add(loginGoogle);
        vertLayout.add(googleLink);

        FlexLayout loginWrapper = new FlexLayout(vertLayout);
        loginWrapper.setJustifyContentMode(JustifyContentMode.CENTER);

        add(loginWrapper);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }
}
