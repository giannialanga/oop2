package Views;

import Mountain.MountainPM;
import MountainApp.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyEvent;

/**
 * Created by Gianni on 04/01/16.
 */
public class Toolbar extends ToolBar{

    public class ToolbarView extends ToolBar{

        static final private String ICON_PATH = "wappen/wappen_32px/";
        static final private String PNG_EXTENSION = ".png";

        private final MountainPM model;
        private final Controller controller;

        private Button saveButton;
        private Button addButton;
        private Button removeButton;
        private Button undoButton;
        private Button redoButton;
        private ToolBar buttonsToolbar;
        private TextField searchTextField;

        public ToolbarView(MountainPM model, Controller controller) {
            this.model = model;
            this.controller = controller;
            this.createAndShow();
        }

        public void createAndShow() {
            initializeComponents();
            addEvents();

            // Main toolbar
            this.setLayout(new BorderLayout());
            this.setFloatable(false);
            this.setRollover(true);
            this.setBackground(Color.darkGray);
            this.setVisible(true);

            // Buttons
            buttonsToolbar.setFloatable(false);
            buttonsToolbar.setRollover(true);
            buttonsToolbar.setBackground(Color.darkGray);
            buttonsToolbar.setVisible(true);

            buttonsToolbar.add(saveButton);
            buttonsToolbar.addSeparator();
            buttonsToolbar.add(addButton);
            buttonsToolbar.add(removeButton);
            buttonsToolbar.addSeparator();
            buttonsToolbar.add(undoButton);
            buttonsToolbar.add(redoButton);
            this.add(buttonsToolbar, BorderLayout.WEST);

            // Search field
            this.add(searchTextField, BorderLayout.EAST);

        }


        private void initializeComponents() {
            saveButton = makeNavigationButton("Save", "save", "Save changes", "Save");
            addButton = makeNavigationButton("Plus", "add", "Add changes", "Add");
            removeButton = makeNavigationButton("Minus", "remove", "Remove changes", "Remove");
            undoButton = makeNavigationButton("Undo", "undo", "Undo changes", "Undo");
            redoButton = makeNavigationButton("Redo", "redo", "Redo changes", "Redo");
            buttonsToolbar = new ToolBar();
            searchTextField = new TextField(30);

            saveButton.setEnabled(false);
            redoButton.setEnabled(false);
            undoButton.setEnabled(false);
        }

        private void addEvents() {
            addButton.addActionListener(e -> controller.addNewMovie());
            saveButton.addActionListener(e -> controller.save());
            removeButton.addActionListener(e -> controller.removeMovie());

            searchTextField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {
                    controller.onChangeSearch(searchTextField.getText());
                }
            });

            model.addObserver(m -> {
                AcademyModel academyModel = (AcademyModel) m;

                saveButton.setEnabled(
                        academyModel.hasModelBeenChanged()
                                && academyModel.editorIsValid()
                                && academyModel.areAllMoviesValid()
                );

            });
        }


        protected Button makeNavigationButton(String imageName,
                                              String actionCommand,
                                              String toolTipText,
                                              String altText) {
            //Look for the image.
            String imgLocation = ICON_PATH
                    + imageName
                    + PNG_EXTENSION;

            String imgPressedLocation = ICON_PATH
                    + imageName
                    + "_Pressed" + PNG_EXTENSION;

            URL imageURL = AppStarter.class.getResource(imgLocation);
            URL imagePressedURL = AppStarter.class.getResource(imgPressedLocation);

            //Create and initialize the button.
            Button button = new Button();
            button.setActionCommand(actionCommand);
            button.setToolTipText(toolTipText);

            if (imageURL != null) {                      //image found
                button.setIcon(new ImageIcon(imageURL, altText));
                button.setPressedIcon(new ImageIcon(imagePressedURL, altText));
            } else {                                     //no image found
                button.setText(altText);
                System.err.println("Resource not found: "
                        + imgLocation);
            }

            button.setOpaque(false);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);

            return button;
        }
    }
}

}
