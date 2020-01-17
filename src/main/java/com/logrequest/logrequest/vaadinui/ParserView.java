package com.logrequest.logrequest.vaadinui;

import com.logrequest.logrequest.backend.repositories.RequestRepository;
import com.logrequest.logrequest.backend.services.impl.RequestServiceImpl;
import com.logrequest.logrequest.backend.services.utils.RequestParse;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.util.ArrayList;
import java.util.List;

@PreserveOnRefresh
@Route(value = ParserView.ROUTE)
@PageTitle("Log 2 Request")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/shared-styles.css")
public class ParserView extends VerticalLayout {
    public static final String ROUTE = "requests/parse";

    public ParserView(RequestRepository requestRepository) {

        setSizeFull();

        add(new Html("<h2>Parse Requests</h2>"));

        HorizontalLayout parserWrapper = new HorizontalLayout();
        parserWrapper.setWidth("100%");
        parserWrapper.setHeight("500%");

        VerticalLayout inputLayout = new VerticalLayout();
        TextArea inputLog = new TextArea("Log");
        inputLog.setWidth("100%");
        inputLog.setHeight("100%");
        inputLayout.add(inputLog);

        TextField tmp = new TextField();

        VerticalLayout hostLayout = new VerticalLayout();
        hostLayout.setWidth("30%");
        TextField hostField = new TextField();
        hostField.setValueChangeMode(ValueChangeMode.LAZY);
        hostField.addBlurListener(textFieldBlurEvent -> tmp.setValue(hostField.getValue()));
        hostField.setWidth("100%");
        Button convert = new Button();
        convert.setIcon(new Icon(VaadinIcon.ARROW_RIGHT));
        convert.setWidth("100%");
        hostLayout.add(hostField, convert);

        VerticalLayout requestLayout = new VerticalLayout();
        TextArea reqBox = new TextArea("Request");
        reqBox.setWidth("100%");
        reqBox.setHeight("100%");
        reqBox.setAutoselect(true);
        reqBox.setAutofocus(true);
        TextArea cURLBox = new TextArea("CURL");
        cURLBox.setWidth("100%");
        cURLBox.setHeight("100%");
        cURLBox.setAutoselect(true);
        cURLBox.setAutofocus(true);
        requestLayout.add(reqBox, cURLBox);

        Notification emptyHost = new Notification("Set host for request", 1000, Notification.Position.TOP_END);

        List<String> reqList = new ArrayList<>();
//        Button copyReq = new Button("Request");
//        copyReq.addClickListener(event -> {
//        });
//        copyReq.setIcon(new Icon(VaadinIcon.COPY));
//        copyReq.setWidth("170px");

        List<String> cURLList = new ArrayList<>();
//        Button copyCURL = new Button("CURL");
//        copyCURL.addClickListener(event -> {
//        });
//        copyCURL.setIcon(new Icon(VaadinIcon.COPY));
//        copyCURL.setWidth("160px");

        convert.addClickListener(buttonClickEvent -> {
            if (!tmp.getValue().isBlank()) {
                reqList.clear();
                cURLList.clear();
                RequestParse.parser(inputLog.getValue()).forEach(request -> {
                    reqList.add(request.getRequest(tmp.getValue()));
                    cURLList.add(request.getCURL(tmp.getValue()));
                });
                reqBox.setValue(toString(reqList));
                cURLBox.setValue(toString(cURLList));
            } else emptyHost.open();
        });

        parserWrapper.setFlexGrow(2, inputLayout);
        parserWrapper.expand(hostLayout);
        parserWrapper.setFlexGrow(2, requestLayout);

        parserWrapper.setVerticalComponentAlignment(Alignment.STRETCH, inputLayout);
        parserWrapper.setVerticalComponentAlignment(Alignment.CENTER, hostLayout);
        parserWrapper.setAlignSelf(Alignment.CENTER, hostLayout);
        parserWrapper.setVerticalComponentAlignment(Alignment.STRETCH, requestLayout);

        parserWrapper.add(inputLayout, hostLayout, requestLayout);

        add(parserWrapper);

//        // Theming components
//        copyReq.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        copyCURL.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    public String toString(List<String> stringList) {
        return stringList.toString()
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll(", ", "\n");
    }
}
