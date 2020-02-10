package com.logrequest.logrequest.vaadinui;

import com.logrequest.logrequest.backend.models.LogFormat;
import com.logrequest.logrequest.backend.repositories.LogFormatRepository;
import com.logrequest.logrequest.backend.services.utils.RequestParse;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
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

@Route(value = ParserView.ROUTE)
@PageTitle("Log 2 Request")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PreserveOnRefresh
@CssImport("./styles/shared-styles.css")
public class ParserView extends VerticalLayout {
    public static final String ROUTE = "parse";

    private static Page page = UI.getCurrent().getPage();

    public ParserView(LogFormatRepository logFormatRepository) {

        setSizeFull();

        HorizontalLayout labelWrapper = new HorizontalLayout(new Html("<h2>Parse Requests</h2>"));
        labelWrapper.setWidthFull();
        labelWrapper.setMargin(false);
        add(labelWrapper);

        HorizontalLayout logFmtWrapper = new HorizontalLayout();
        logFmtWrapper.setWidthFull();
        logFmtWrapper.setMargin(false);

        ComboBox<LogFormat> logFmtSelector = new ComboBox<>();
        logFmtSelector.setLabel("Log format");
        logFmtSelector.setWidthFull();
        logFmtSelector.setItems(logFormatRepository.findAll());
        logFmtSelector.setItemLabelGenerator(LogFormat::getFormat);

        logFmtWrapper.add(logFmtSelector);
        add(logFmtWrapper);

        HorizontalLayout parserWrapper = new HorizontalLayout();
        parserWrapper.setWidthFull();
        parserWrapper.setMargin(false);
        parserWrapper.setHeight("79%");

        VerticalLayout inputLayout = new VerticalLayout();
        inputLayout.setMargin(false);
        TextArea inputLog = new TextArea("Log");
        inputLog.setWidthFull();
        inputLog.setHeight("93%");
        inputLayout.add(inputLog);

        TextField tmp = new TextField();

        VerticalLayout hostLayout = new VerticalLayout();
        hostLayout.setMargin(false);
        hostLayout.setWidth("30%");
        TextField hostField = new TextField();
        hostField.setValueChangeMode(ValueChangeMode.LAZY);
        hostField.setWidthFull();
        Button convert = new Button();
        convert.setIcon(new Icon(VaadinIcon.ARROW_RIGHT));
        convert.setWidthFull();
        convert.addClickShortcut(Key.ENTER);
        hostLayout.add(hostField, convert);

        VerticalLayout requestLayout = new VerticalLayout();
        requestLayout.setMargin(false);
        TextArea cURLBox = new TextArea("CURL");
        cURLBox.setWidthFull();
        cURLBox.setHeight("44%");
        cURLBox.setAutofocus(true);
        TextArea reqBox = new TextArea("Request");
        reqBox.setWidthFull();
        reqBox.setHeight("44%");
        reqBox.setAutofocus(true);
        requestLayout.add(cURLBox, reqBox);

        Notification emptyHost = new Notification("Set host for request", 1000, Notification.Position.TOP_END);

        List<String> reqList = new ArrayList<>();
        List<String> cURLList = new ArrayList<>();

        hostField.addValueChangeListener(valueChangeEvent -> tmp.setValue(hostField.getValue()));

        Notification wrongFormat = new Notification("", 10000, Notification.Position.BOTTOM_STRETCH);

        convert.addClickListener(buttonClickEvent -> {
            if (!tmp.getValue().isBlank()) {
                reqList.clear();
                cURLList.clear();
                try {
                    RequestParse.parser(inputLog.getValue(), logFmtSelector.getValue().getVariables()).forEach(request -> {
                        reqList.add(request.getRequest(tmp.getValue()));
                        cURLList.add(request.getCURL(tmp.getValue()));
                    });
                } catch (IllegalArgumentException e) {
                    wrongFormat.setText("Wrong format # " + e.getMessage());
                    wrongFormat.open();
                }
                reqBox.setValue(toString(reqList));
                cURLBox.setValue(toString(cURLList));
            } else emptyHost.open();
        });

        parserWrapper.setVerticalComponentAlignment(Alignment.STRETCH, inputLayout);
        parserWrapper.setVerticalComponentAlignment(Alignment.CENTER, hostLayout);
        parserWrapper.setAlignSelf(Alignment.CENTER, hostLayout);
        parserWrapper.setVerticalComponentAlignment(Alignment.STRETCH, requestLayout);

        parserWrapper.add(inputLayout, hostLayout, requestLayout);

        add(parserWrapper);
    }

    public String toString(List<String> stringList) {
        return stringList.toString()
                .substring(1)
                .replace("]", "")
                .replaceAll(", ", "\n");
    }
}
