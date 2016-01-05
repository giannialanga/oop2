package Mountain;

import MountainApp.Controller;
import Views.Table;
import Views.Toolbar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Created by Gianni on 04/01/16.
 */
public class MountainView extends  Stage {

        private ToolBar toolbar;
        private SplitPane splitPane;
        private final MountainPM model;
        private final Controller controller;

        public MountainView(MountainPM, Mountain model, Controller controller) {
            super("Mountain App");
            this.model = this.model;
            this.controller = controller;
        }

        public void createAndShow() {
            initializeComponents();
            Pane contents = layoutComponents();
            addEvents();

            setPreferredSize(new Dimension(1400, 800));

            // Add Header Toolbar
            contents.add(toolbar, BorderLayout.PAGE_START);
            contents.add(splitPane, BorderLayout.CENTER);
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
            JScrollPane tableScrollPane = new JScrollPane(table);
            splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, tableScrollPane, detailPanel);
            splitPane.setDividerLocation(700);

            //set minimum Size of the Components
            Dimension minimumSize = new Dimension(500, 500);
            tableScrollPane.setMinimumSize(minimumSize);
            detailPanel.setMinimumSize(minimumSize);
        }

        private Pane layoutComponents() {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBackground(Color.gray);
            return panel;
        }

        private void addEvents() {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int answer = JOptionPane.showConfirmDialog(
                            MountainView.this,
                            "Programm wirklich beenden?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.PLAIN_MESSAGE
                    );
                    if (answer == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });

        }

    }
