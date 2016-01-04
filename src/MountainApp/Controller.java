package MountainApp;

import Mountain.Mountain;
import Mountain.MountainView;


public class Controller {

        private final Mountain model;
        private final MountainView view;

        public Controller(MountainPM model) {
            this.model = model;
            this.view = new MountainView(model, this);
        }

        public void setValueAtSelectedRow(String value, int col) {
            model.setValueAt(value, model.getSelectedMountainId(), col);
        }

        public void addNewMovie() {
            Mountain mountain = new Mountain();
            model.add(mountain);
        }


        //TODO
        public void removeMountain() {
            model.removeById(model.getSelectedMountainId());
        }

        public void onChangeCountry(String value) {
            model.setSelectedMovieCountry(value);
        }

        public void onChangeFsk(int value) {
            model.setSelectedMovieFsk(value);
        }

        public void onChangeMainActor(String value) {
            model.setSelectedMovieMainActor(value);
        }

        public void onChangeDirector(String value) {
            model.setSelectedMovieDirector(value);
        }

        public void onChangeDuration(int value) {
            model.setSelectedMovieDuration(value);
        }

        public void onChangeGenre(String value) {
            model.setSelectedMovieGenre(value);
        }

        public void onChangeNumberOfOscars(int value) {
            model.setSelectedMovieNumberOfOscars(value);
        }

        public void onChangeStartDate(String value) {
            model.setSelectedMovieStartDate(value);
        }

        public void onChangeTitle(String value) {
            model.setSelectedMovieTitle(value);
        }

        public void onChangeTitleEnglish(String value) {
            model.setSelectedMovieTitleEnglish(value);
        }

        public void onChangeYearOfAward(String value) {
            model.setSelectedMovieYearOfAward(value);
        }

        public void onChangeYearOfProduction(String value) {
            model.setSelectedMovieYearOfProduction(value);
        }

        public void save() {
            this.model.exportListToCsv();
        }

        public void onChangeSearch(String value) {
            this.model.setSearchValue(value);
        }
    }

}
