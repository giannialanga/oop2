package Views;

import Mountain.MountainPM;
import MountainApp.Controller;
import javafx.scene.control.TableView;


/**
 * Created by Gianni on 30/12/15.
 */
public class Table extends TableView<MountainPM> {


    private final MountainPM model;
    private final Controller controller;
    private TableModel tableModel;


    public Table(MountainPM, Controller controller) {
        this.model = model;
        this.controller = controller;
        this.tableModel = new TableModel(model);
        setModel(tableModel);
        addEvents();
        addObservers();
    }


    private void addEvents() {
        this.getSelectionModel().addListSelectionListener(event -> {
            if (this.getSelectedRow() > -1) {
                Mountain mountain = this.model.getMountainByIdIndex((this.getSelectedRow());
                this.model.setSelectedMovieId(mountain.getId());
                refreshTable(model.getFilteredList().size());
            }
        });
    }

    public void refreshSelectedMountain(int index) {
        tableModel.fireTableRowsUpdated(index, index);
    }

    public void refreshTable(int index) {
        tableModel.fireTableRowsUpdated(0, index);
    }

    public void addObservers() {
        model.addObserver(m -> {
            MountainPM model = (MountainPM) m;

            switch (model.observerAction) {
                case MountainPM.ACTION_INSERT:
                    tableModel.fireTableRowsInserted(model.observerIndex, model.observerIndex);
                    break;

                case MountainPM.ACTION_DELETE:
                    tableModel.fireTableRowsDeleted(model.observerIndex, model.observerIndex);
                    break;

                case MountainPM.ACTION_PRISTINE:
                    refreshTable(model.getFilteredList().size() - 1);
                    break;

                case MountainPM.ACTION_SEARCH:
                    refreshTable(model.getList().size() - 1);
                    break;
            }

            refreshSelectedMountain(model.getIndexById(model.getSelectedMovieId()));
            model.observerAction = model.ACTION_NONE;
            model.observerIndex = model.getIndexById(model.getSelectedMovieId());

        });
    }


    class TableModel extends AbstractTableModel {
        public final static String MARK_EMPTY = "../resources/marks/Mark_Empty.png";
        public final static String MARK_BLUE = "../resources/marks/Mark_Blue.png";
        public final static String MARK_GREEN = "../resources/marks/Mark_Green.png";
        public final static String MARK_ORANGE = "../resources/marks/Mark_Orange.png";
        public final static String MARK_RED = "../resources/marks/Mark_Red.png";
        public final static String MARK_YELLOW = "../resources/marks/Mark_Yellow.png";

        public final static int COL_YEAR = 1;
        public final static int COL_TITLE = 2;
        public final static int COL_DIRECTOR = 3;
        public final static int COL_MAIN_ACTOR = 4;

        private String[] columnNames = {
                "",
                "Jahr",
                "Titel",
                "Regisseur",
                "Hauptdarsteller"
        };
        private final MountainPM model;

        public TableModel(MountainPM model) {
            this.model = model;
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return model.getFilteredList().size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            Object value = model.getValueAt(row, col);

            if (col == 0) {
                String path = TableModel.MARK_EMPTY;
                Mountain mountain = model.getMountainByIdIndex(row);
                if (mountain.getId().equals(model.getSelectedMovieId())) {
                    path = TableModel.MARK_BLUE;
                } else if (!mountain.isValid()) {
                    path = TableModel.MARK_RED;
                }else if (mountain.isHasModified()) {
                    path = TableModel.MARK_ORANGE;
                }
                URL imageURL = TableView.class.getResource(path);
                return new ImageIcon(imageURL);
            }

            return value;
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            return col >= 2;
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            model.setValueAt(value.toString(), row, col);
            fireTableCellUpdated(row, col);
        }

    }

}
