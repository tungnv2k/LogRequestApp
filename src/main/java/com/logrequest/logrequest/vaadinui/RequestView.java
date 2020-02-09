package com.logrequest.logrequest.vaadinui;

import com.logrequest.logrequest.backend.models.Param;
import com.logrequest.logrequest.backend.models.Request;
import com.logrequest.logrequest.backend.services.impl.RequestServiceImpl;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Route(value = RequestView.ROUTE)
@PageTitle("Requests")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@CssImport("./styles/shared-styles.css")
public class RequestView extends VerticalLayout {
    public static final String ROUTE = "requests";

    public RequestView(RequestServiceImpl requestService) {

        setSizeFull();

        add(new Html("<h2>Requests</h2>"));

        Button refresh = new Button("Refresh");
        refresh.setIcon(new Icon(VaadinIcon.REFRESH));

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
        refresh.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    public String toString(List<String> stringList) {
        return stringList.toString()
                .replace("[", "")
                .replace("]", "")
                .replaceAll(", ", "\n");
    }
}
