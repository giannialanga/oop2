package Mountain;

import MountainApp.Controller;
import Views.Table;
import Views.Toolbar;
import com.sun.org.apache.xpath.internal.operations.String;
import javafx.animation.KeyFrame;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Created by Gianni on 04/01/16.
 */
public class MountainView extends Stage {

        private ToolBar toolbar;
        private SplitPane splitPane;
        private final MountainPM model;
        private final Controller controller;

        public MountainView(MountainPM model, Controller controller) {
            super("Mountain App");
            this.model = model;
            this.controller = controller;
        }

        public void createAndShow() {
            initializeComponents();
            Pane contents = layoutComponents();
            addEvents();

            setPreferredSize(new Pane(1400, 800));

            // Add Header Toolbar
            contents.add(toolbar, Border.PAGE_START);
            contents.add(splitPane, Border.CENTER);
            add(contents);

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void initializeComponents() {
            toolbar = new Toolbar(this.model, this.controller);
            Pane detailPanel = new Pane(this.model, this.controller);
            Table table = new TableView(this.model, this.controller);

            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(0).setMaxWidth(25);
            table.getColumnModel().getColumn(1).setMaxWidth(50);
            ScrollPane tableScrollPane = new ScrollPane(table);
            splitPane = new SplitPane(SplitPane.HORIZONTAL_SPLIT, true, tableScrollPane, detailPanel);
            splitPane.setDividerLocation(700);

            //set minimum Size of the Components
            Pane minimumSize = new Pane(500, 500);
            tableScrollPane.setMinimumSize(minimumSize);
            detailPanel.setMinimumSize(minimumSize);
        }

        private Pane layoutComponents() {
            Pane pane = new Pane();
            pane.setLayout(new Border());
            pane.setBackground(Color.GRAY);
            return panel;
        }

        private void addEvents() {
            setDefaultCloseOperation(KeyFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new Window() {
                @Override
                public void windowClosing(Window e) {
                    int answer = DialogPane.showConfirmDialog(
                            MountainView.this,
                            "Programm wirklich beenden?",
                            "Confirm",
                            DialogPane.YES_NO_OPTION,
                            DialogPane.PLAIN_MESSAGE
                    );
                    if (answer == DialogPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });

        }

    }
