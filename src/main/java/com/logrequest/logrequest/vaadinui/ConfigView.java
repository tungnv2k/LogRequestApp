package com.logrequest.logrequest.vaadinui;

import com.logrequest.logrequest.backend.models.LogFormat;
import com.logrequest.logrequest.backend.repositories.LogFormatRepository;
import com.logrequest.logrequest.backend.services.LogFormatService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(ConfigView.ROUTE)
@PageTitle("Config")
public class ConfigView extends VerticalLayout {

    public static final String ROUTE = "config";

    public ConfigView(LogFormatRepository logFormatRepository, LogFormatService logFormatService) {

        HorizontalLayout labelWrapper = new HorizontalLayout(new Html("<h2>Config</h2>"));
        labelWrapper.setWidthFull();
        labelWrapper.setMargin(false);
        add(labelWrapper);

        HorizontalLayout logFmtWrapper = new HorizontalLayout();
        logFmtWrapper.setWidthFull();
        logFmtWrapper.setMargin(false);

        TextField inputLogFmt = new TextField("Log format");
        inputLogFmt.setWidth("100%");

        logFmtWrapper.add(inputLogFmt);

        Button addButton = new Button("Save");
        addButton.addClickShortcut(Key.ENTER);

        logFmtWrapper.add(addButton);

        add(logFmtWrapper);

        HorizontalLayout fmtListWrapper = new HorizontalLayout();
        fmtListWrapper.setWidthFull();
        fmtListWrapper.setMargin(false);

        CallbackDataProvider<LogFormat, Void> logDataProvider = DataProvider.fromCallbacks(
                query -> logFormatService.fetch(query.getOffset(), query.getLimit()).stream(),
                query -> logFormatService.count()
        );

        Grid<LogFormat> fmtList = new Grid<>(20);
        fmtList.setDataProvider(logDataProvider);
        fmtList.addColumn(LogFormat::getFormat);
        fmtListWrapper.add(fmtList);

        add(fmtListWrapper);

        Notification saveSuccess = new Notification("Saved format", 1000, Notification.Position.TOP_END);
        Notification duplicate = new Notification("Format already exists!", 1000, Notification.Position.TOP_END);

        addButton.addClickListener(buttonClickEvent -> {
            if (!inputLogFmt.getValue().isEmpty()) {
                try {
                    logFormatRepository.save(new LogFormat(inputLogFmt.getValue()));
                } catch (Exception ignored) {   // Mongo Duplicate key
                    duplicate.open();
                }
                saveSuccess.open();
                fmtList.getDataProvider().refreshAll();
                fmtList.recalculateColumnWidths();
            }
        });
    }
}
