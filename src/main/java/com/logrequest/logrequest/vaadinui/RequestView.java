package com.logrequest.logrequest.vaadinui;

import com.logrequest.logrequest.backend.models.Param;
import com.logrequest.logrequest.backend.models.Request;
import com.logrequest.logrequest.backend.repositories.RequestRepository;
import com.logrequest.logrequest.backend.services.impl.RequestServiceImpl;
import com.logrequest.logrequest.backend.services.utils.RequestParse;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Route(value = RequestView.ROUTE)
@PageTitle("Requests")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/shared-styles.css")
public class RequestView extends VerticalLayout {
    public static final String ROUTE = "requests";

    public RequestView(RequestRepository requestRepository, RequestServiceImpl requestService) {

        setSizeFull();

        add(new Html("<h2>Requests</h2>"));

        Button refresh = new Button("Refresh");
        refresh.setIcon(new Icon(VaadinIcon.REFRESH));

        Button inputButton = new Button("Insert Log");
        inputButton.setIcon(new Icon(VaadinIcon.INSERT));
        Dialog inputDialog = new Dialog();
        inputDialog.setWidth("1400px");
        inputDialog.setHeight("700px");
        inputButton.addClickListener(buttonClickEvent -> inputDialog.open());
        TextArea inputLog = new TextArea("Log");
        inputLog.setWidth("1360px");
        inputLog.setHeight("630px");
        Button addButton = new Button("Add");
        addButton.setWidth("200px");
        addButton.addClickListener(buttonClickEvent -> {
//            requestRepository.saveAll(RequestParse.parser(inputLog.getValue()));
            inputDialog.close();
            inputLog.clear();
            refresh.click();
        });

        FlexLayout inputLogWrapper = new FlexLayout(inputLog);
        inputLogWrapper.setJustifyContentMode(JustifyContentMode.CENTER);

        FlexLayout addButtonWrapper = new FlexLayout(addButton);
        addButtonWrapper.setJustifyContentMode(JustifyContentMode.END);

        inputDialog.add(inputLogWrapper, addButtonWrapper);
        add(inputButton);

        TextField tmp = new TextField();

        HorizontalLayout copyLayout = new HorizontalLayout();
        TextField hostField = new TextField();
        hostField.setValueChangeMode(ValueChangeMode.LAZY);
        hostField.addBlurListener(textFieldBlurEvent -> tmp.setValue(hostField.getValue()));
        hostField.setWidth("200px");

        TextArea copyBox = new TextArea("Request");
        copyBox.setWidth("1400px");
        copyBox.setHeight("650px");
        copyBox.setAutoselect(true);
        copyBox.setAutofocus(true);

        Dialog copyDialog = new Dialog();
        copyDialog.setCloseOnOutsideClick(true);
        copyDialog.setCloseOnEsc(true);
        copyDialog.setWidth("1400px");
        copyDialog.setHeight("700px");
        copyDialog.add(copyBox);

        Notification emptyHost = new Notification("Set host for request", 1000, Notification.Position.TOP_END);

        List<String> reqList = new ArrayList<>();
        Button copyRequest = new Button("Copy Request");
        copyRequest.addClickListener(event -> {
            if (tmp.getValue().isBlank()) {
                emptyHost.open();
            } else {
                reqList.clear();
                requestRepository.findAll().forEach(request -> reqList.add(request.getRequest(tmp.getValue())));
                copyBox.setValue(toString(reqList));
                copyDialog.open();
            }
        });
        copyRequest.setIcon(new Icon(VaadinIcon.COPY));
        copyRequest.setWidth("170px");

        List<String> cURLList = new ArrayList<>();
        Button copyCURL = new Button("Copy cURL");
        copyCURL.addClickListener(event -> {
            if (tmp.getValue().isBlank()) {
                emptyHost.open();
            } else {
                cURLList.clear();
                requestRepository.findAll().forEach(request -> cURLList.add(request.getCURL(tmp.getValue())));
                copyBox.setValue(toString(cURLList));
                copyDialog.open();
            }
        });
        copyCURL.setIcon(new Icon(VaadinIcon.COPY));
        copyCURL.setWidth("160px");

//        copyLayout.add(hostField, copyRequest, copyCURL);
        add(hostField);
        add(copyRequest);
        add(copyCURL);

        CallbackDataProvider<Request, Void> requestDataProvider = DataProvider.fromCallbacks(
                query -> requestService.fetch(query.getOffset(), query.getLimit()).stream(),
                query -> requestService.count()
        );

        Grid<Request> table = new Grid<>(20);
        table.setHeightByRows(false);
        table.setWidthFull();

        table.setDataProvider(requestDataProvider);

        refresh.addClickListener(event -> {
            table.recalculateColumnWidths();
            table.getDataProvider().refreshAll();
        });
        add(refresh);

        table.addColumn(new LocalDateTimeRenderer<>(Request::getTimestamp, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM)))
                .setHeader("Timestamp").setResizable(true).setSortable(true).setAutoWidth(true);
        table.addColumn(Request::getIpRemote)
                .setHeader("Server").setResizable(true).setSortable(true).setAutoWidth(true);
        table.addColumn(Request::getMethod)
                .setHeader("Method").setResizable(true).setSortable(true).setAutoWidth(true);
        table.addColumn(Request::getPath)
                .setHeader("Path").setResizable(true).setSortable(true).setAutoWidth(true);
        table.addColumn(Request::getQueryStr)
                .setHeader("Query String").setResizable(true).setSortable(true).setAutoWidth(true);
        add(table);

        Dialog paramDialog = new Dialog();
        paramDialog.setCloseOnEsc(true);
        paramDialog.setCloseOnOutsideClick(true);
        paramDialog.setWidth("560px");
        paramDialog.setHeight("450px");

        Grid<Param> paramTable = new Grid<>();
        paramTable.addColumn(Param::getName).setHeader("Key").setAutoWidth(true);
        paramTable.addColumn(Param::getValue).setHeader("Value").setAutoWidth(true);
        paramTable.setHeightByRows(true);
        paramDialog.add(paramTable);

        table.addItemClickListener(event -> {
            paramTable.recalculateColumnWidths();
            paramTable.setItems(event.getItem().getParams());
            paramDialog.open();
        });

        // Theming components
        inputButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        copyRequest.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        copyCURL.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        refresh.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    public String toString(List<String> stringList) {
        return stringList.toString()
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll(", ", "\n");
    }
}
