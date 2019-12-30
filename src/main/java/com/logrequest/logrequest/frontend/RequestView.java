package com.logrequest.logrequest.frontend;

import com.logrequest.logrequest.backend.models.Param;
import com.logrequest.logrequest.backend.models.Request;
import com.logrequest.logrequest.backend.repositories.RequestRepository;
import com.logrequest.logrequest.backend.services.impl.RequestService;
import com.logrequest.logrequest.backend.services.utils.ParseRequest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Route("request")
@PageTitle("Log Request")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class RequestView extends VerticalLayout {

    public RequestView(RequestRepository requestRepository, RequestService requestService) {
        ParseRequest parse = new ParseRequest(requestRepository);
//        parse.parser("s2s.adflex.vn.access.log-2019121819.gz:10.42.146.153 - - [18/Dec/2019:18:55:17 +0700] \"GET /v2/logs/advertisers/test/postbacks.json?api_key=4tcs8mm7egZmZ9MhAy21cqA&status=1&transaction_id=7833&advertiser_offer_id=1&click_id=5dfa11f6ff76600042497ed0&offer_type=cpa&message=Kh%C3%A1ch+h%C3%A0ng+%C4%91%E1%BB%93ng+%C3%BD+mua+h%C3%A0ng+-+t%E1%BB%91i+18.12+%C4%91%C3%A3+xn&product_quantity=1 HTTP/1.1\" 200 29 \"-\" \"-\" \"-\" \"10.130.40.88\" ");
//        parse.parser("s2s.adflex.vn.access.log-2019121917.gz:10.42.146.153 - - [19/Dec/2019:16:54:45 +0700] \"GET /v2/logs/advertisers/test/postbacks.json?api_key=4tcs8mm7egZm12MhAyUcqA&status=1&transaction_id=7833&advertiser_offer_id=1&click_id=5dfa11f6ff76600042497ed0&offer_type=cpa&message=Kh%C3%A1ch+h%C3%A0ng+%C4%91%E1%BB%93ng+%C3%BD+mua+h%C3%A0ng+-+t%E1%BB%91i+18.12+%C4%91%C3%A3+xn&product_quantity=1 HTTP/1.1\" 200 29 \"-\" \"PostmanRuntime/7.20.1\" \"PostmanRuntime/7.20.1\" \"27.72.146.50\" ");
//        parse.parser("s2s.adflex.vn.access.log-2019121819.gz:10.42.146.153 - - [18/Dec/2019:18:55:17 +0700] \"GET /v2/logs/advertisers/test/postbacks.json?api_key=4tcs8mm7egZmZ9MhAy21cqA&status=1&transaction_id=7833&advertiser_offer_id=1&click_id=5dfa11f6ff76600042497ed0&offer_type=cpa&message=Kh%C3%A1ch+h%C3%A0ng+%C4%91%E1%BB%93ng+%C3%BD+mua+h%C3%A0ng+-+t%E1%BB%91i+18.12+%C4%91%C3%A3+xn&product_quantity=1 HTTP/1.1\" 200 29 \"-\" \"-\" \"-\" \"10.130.40.88\" ");
//        parse.parser("s2s.adflex.vn.access.log-2019121917.gz:10.42.146.153 - - [19/Dec/2019:16:54:45 +0700] \"GET /v2/logs/advertisers/test/postbacks.json?api_key=4tcs8mm7egZm12MhAyUcqA&status=1&transaction_id=7833&advertiser_offer_id=1&click_id=5dfa11f6ff76600042497ed0&offer_type=cpa&message=Kh%C3%A1ch+h%C3%A0ng+%C4%91%E1%BB%93ng+%C3%BD+mua+h%C3%A0ng+-+t%E1%BB%91i+18.12+%C4%91%C3%A3+xn&product_quantity=1 HTTP/1.1\" 200 29 \"-\" \"PostmanRuntime/7.20.1\" \"PostmanRuntime/7.20.1\" \"27.72.146.50\" ");
//        parse.parser("s2s.adflex.vn.access.log-2019121819.gz:10.42.146.153 - - [18/Dec/2019:18:55:17 +0700] \"GET /v2/logs/advertisers/test/postbacks.json?api_key=4tcs8mm7egZmZ9MhAy21cqA&status=1&transaction_id=7833&advertiser_offer_id=1&click_id=5dfa11f6ff76600042497ed0&offer_type=cpa&message=Kh%C3%A1ch+h%C3%A0ng+%C4%91%E1%BB%93ng+%C3%BD+mua+h%C3%A0ng+-+t%E1%BB%91i+18.12+%C4%91%C3%A3+xn&product_quantity=1 HTTP/1.1\" 200 29 \"-\" \"-\" \"-\" \"10.130.40.88\" ");
//        parse.parser("s2s.adflex.vn.access.log-2019121917.gz:10.42.146.153 - - [19/Dec/2019:16:54:45 +0700] \"GET /v2/logs/advertisers/test/postbacks.json?api_key=4tcs8mm7egZm12MhAyUcqA&status=1&transaction_id=7833&advertiser_offer_id=1&click_id=5dfa11f6ff76600042497ed0&offer_type=cpa&message=Kh%C3%A1ch+h%C3%A0ng+%C4%91%E1%BB%93ng+%C3%BD+mua+h%C3%A0ng+-+t%E1%BB%91i+18.12+%C4%91%C3%A3+xn&product_quantity=1 HTTP/1.1\" 200 29 \"-\" \"PostmanRuntime/7.20.1\" \"PostmanRuntime/7.20.1\" \"27.72.146.50\" ");

        add(new Label("Requests"));

        TextField tmp = new TextField();

        ValueProvider<Request, TextField> hostProvider = (ValueProvider<Request, TextField>) request -> {
            TextField textField = new TextField();
            textField.addBlurListener(event -> tmp.setValue(textField.getValue()));
            return textField;
        };

        ValueProvider<Request, Button> requestPMProvider = (ValueProvider<Request, Button>) request -> {
            Button copyButton = new Button("Copy Request");
            Notification noti = new Notification("", 5000, Notification.Position.BOTTOM_STRETCH);
            copyButton.addClickListener(event -> {
                noti.setText(tmp.getValue().isBlank()
                        ? "Please set host for request"
                        : request.getRequest(tmp.getValue()));
                noti.open();
            });
            copyButton.setIcon(new Icon(VaadinIcon.COPY));
            copyButton.setWidth("170px");
            return copyButton;
        };

        ValueProvider<Request, Button> requestCURLProvider = (ValueProvider<Request, Button>) request -> {
            Button copyButton = new Button("Copy cURL");
            Notification noti = new Notification("", 5000, Notification.Position.BOTTOM_STRETCH);
            copyButton.addClickListener(event -> {
                noti.setText(tmp.getValue().isBlank()
                        ? "Please set host for request"
                        : request.getCURL(tmp.getValue()));
                noti.open();
            });
            copyButton.setIcon(new Icon(VaadinIcon.COPY));
            copyButton.setWidth("150px");
            return copyButton;
        };

        CallbackDataProvider<Request, Void> requestDataProvider = DataProvider.fromCallbacks(
                query -> requestService.findAll(query.getOffset(), query.getLimit()).stream(),
                query -> requestService.count()
        );

        Grid<Request> table = new Grid<>(20);
        table.setHeight("750px");
        table.setWidth("1880px");

        table.setDataProvider(requestDataProvider);

        table.addComponentColumn(hostProvider).setHeader("Host").setWidth("11.8%");
        table.addComponentColumn(requestPMProvider).setWidth("10%");
        table.addComponentColumn(requestCURLProvider).setWidth("9%");
        table.addColumn(new LocalDateTimeRenderer<>(Request::getTimestamp, DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT, FormatStyle.MEDIUM)))
                .setHeader("Timestamp").setResizable(true).setWidth("10%");
        table.addColumn(Request::getIpResponse)
                .setHeader("Server").setResizable(true).setSortable(true).setWidth("9%");
        table.addColumn(Request::getIpRequest)
                .setHeader("Client").setResizable(true).setSortable(true).setWidth("9%");
        table.addColumn(Request::getMethod)
                .setHeader("Method").setResizable(true).setSortable(true).setFlexGrow(1).setWidth("6%");
        table.addColumn(Request::getPath)
                .setHeader("Path").setResizable(true).setSortable(true).setFlexGrow(1).setWidth("22%");
        table.addColumn(Request::getQueryStr)
                .setHeader("Query String").setResizable(true).setSortable(true).setFlexGrow(1).setWidth("150%");
        add(table);

        Dialog paramDialog = new Dialog();
        paramDialog.setCloseOnEsc(true);
        paramDialog.setCloseOnOutsideClick(true);
        paramDialog.setWidth("560px");
        paramDialog.setHeight("450px");

        Grid<Param> paramTable = new Grid<>();
        paramTable.addColumn(Param::getName).setHeader("Key").setWidth("10px");
        paramTable.addColumn(Param::getValue).setHeader("Value").setWidth("200px");
        paramTable.setHeightByRows(true);
        paramDialog.add(paramTable);

        table.addItemClickListener(event -> {
            paramTable.setItems(event.getItem().getParams());
            paramDialog.open();
        });
    }
}
