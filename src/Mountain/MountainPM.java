package Mountain;

import Mountain.*;

import Search.FilterModule;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by Gianni on 04/01/16.
 */
public class MountainPM {


        private static final String FILE_PATH = "./com.company/data/mountains.csv";
        private static final String STRING_BOL_TRUE = "X";
        private static final String STRING_BOL_FALSE = "";
        public static final String ACTION_INSERT = "insert";
        public static final String ACTION_UPDATE = "update";
        public static final String ACTION_DELETE = "delete";
        public static final String ACTION_PRISTINE = "pristine";
        public static final String ACTION_SEARCH = "search";
        public static final String ACTION_NONE = "";
        private static String csvFileHeader;
        private static boolean aMovieHasBeenRemoved = false;

        private final Set<Observer> observers = new HashSet<>();
        private boolean undoAvailable = false;
        private boolean redoAvailable = false;
        private int selectedMountainId;
        public int observerIndex;
        public String observerAction = "";
        private List<Object> list = new ArrayList<>();
        private List<Object> filterdList = new ArrayList<>();
        private String searchValue = "";

        public MountainPM() throws IOException, URISyntaxException {
            filterdList = list = readCSVfile(MountainPM.class.getResource(FILE_PATH).toURI());
            selectedMountainId = list.get(0).hashCode();
        }

        public List<?> getList(){
            return this.list;
        }

        public List<?> getFilteredList(){
            return filterdList;
        }


        public String getValueAt(Integer index, int col) {
            Mountain mountain = filterdList.get(index);
            switch (col) {
                case 0:
                    return mountain.isHasModified() ? STRING_BOL_TRUE : STRING_BOL_FALSE;
                case 1:
                    return mountain.getKmBis();
                case 2:
                    return mountain.getBildunters();
                case 3:
                    return mountain.getGebiet();
                case 4:
                    return mountain.getKanton();
                case 5:
                    return mountain.getRegion();
                case 6:
                    return mountain.getTyp();
                default:
                    return mountain.getBergName();
            }
        }

        public void setValueAt(String value, int index, int col) {
            Mountain mountain = filterdList.get(index);
            switch (col) {
                case 0:
                    mountain.setHasModified((value.equals(STRING_BOL_TRUE)));
                    break;
                case 1:
                    mountain.setKmBis(value);
                    break;
                case 2:
                    mountain.setBildunters(value);
                    break;
                case 3:
                    mountain.setGebiet(value);
                    break;
                case 4:
                    mountain.setKanton(value);
                    break;
                case 5:
                    mountain.setRegion(value);
                    break;
                case 6:
                    mountain.setTyp(value);
                default:
                    mountain.setBergName(value);
            }
            filterdList.set(index, mountain);
            notifyObservers();
        }

        private static List<Object> readCSVfile(URI csvFileName) throws IOException {
            String line;
            BufferedReader stream = null;
            List<Object> csvData = new ArrayList<>();

            try {
                stream = new BufferedReader(new FileReader(new File(csvFileName)));
                csvFileHeader = stream.readLine();
                while ((line = stream.readLine()) != null) {
                    csvData.add(new Mountain(line));
                }

            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if (stream != null)
                    stream.close();
            }

            return csvData;

        }


        @Override
        public void addObserver(Observer observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(Observer observer) {
            observers.remove(observer);
        }

        public void notifyObservers() {
            observers.forEach(observer -> observer.update(this));
        }

        public int getSelectedMountainId() {
            return selectedMountainId;
        }

        public void setSelectedMountainId(int selectedMovieId) {
            this.selectedMountainId = selectedMovieId;
            notifyObservers();
        }

        public boolean hasModelBeenChanged() {
            long counter = list.stream()
                    .map(Mountain::isHasModified)
                    .filter(a -> a)
                    .count();
            return counter > 0 || aMountainHasBeenRemoved;
        }

        public void add(Mountain mountain) {
            final Comparator<Object> comp = (p1, p2) -> Integer.compare(p1.getId(), p2.getId());
            int maxId = this.list.stream().max(comp).get().getId();
            mountain.setId(maxId + 1);
            this.list.add(Mountain);
            this.selectedMountainId = mountain.getId();
            this.observerIndex = getIndexByMountain(mountain);
            this.observerAction = ACTION_INSERT;
            notifyObservers();
        }

        public void removeById(int id) {
            Integer index = getIndexById(id);
            if (index.equals(this.list.size() - 1)) {
                this.selectedMountainId = getMountainByIdIndex(index - 1).getId();
            } else {
                this.selectedMountainId = getMountainByIdIndex(index + 1).getId();
            }
            this.list.remove((int) index);
            this.observerIndex = index;
            this.observerAction = ACTION_DELETE;
            aMovieHasBeenRemoved = true;
            notifyObservers();
        }

        public int getIndexById(int id) {
            int counter = 0;
            for (Object m : list) {
                if (m.getId() == id) {
                    return counter;
                }
                ++counter;
            }
            return 0;
        }

        public int getIndexByMountain(Mountain movie) {
            return getIndexById(movie.getId());
        }

        public Object getMountainById(int id) {
            int index = getIndexById(id);
            return list.get(index);
        }

        public Object getMountainByIdIndex(int index) {

            return filterdList.get(index);
        }

        public void setSelectedMountainCountry(String value) {
            this.getMountainById(this.getSelectedMountainId()).setCountry(value);
            this.notifyObservers();
        }

        public void setSelectedMovieFsk(int value) {
            this.getMountainById(this.getSelectedMountainId()).setFsk(value);
            this.notifyObservers();
        }

        public void setSelectedMovieMainActor(String value) {
            this.getMountainById(this.getSelectedMountainId()).setMainActor(value);
            this.notifyObservers();
        }

        public void setSelectedMovieDirector(String value) {
            this.getMountainById(this.getSelectedMountainId()).setDirector(value);
            this.notifyObservers();
        }

        public void setSelectedMovieDuration(int value) {
            this.getMountainById(this.getSelectedMountainId()).setDuration(value);
            this.notifyObservers();
        }

        public void setSelectedMovieGenre(String value) {
            this.getMountainById(this.getSelectedMountainId()).setGenre(value);
            this.notifyObservers();
        }

        public void setSelectedMovieNumberOfOscars(int value) {
            this.getMountainById(this.getSelectedMountainId()).setNumberOfOscars(value);
            this.notifyObservers();
        }

        public void setSelectedMovieStartDate(String value) {
            this.getMountainById(this.getSelectedMountainId()).setStartDate(value);
            this.notifyObservers();
        }

        public void setSelectedMovieTitle(String value) {
            this.getMountainById(this.getSelectedMountainId()).setTitle(value);
            this.notifyObservers();
        }

        public void setSelectedMovieTitleEnglish(String value) {
            this.getMountainById(this.getSelectedMountainId()).setTitleEnglish(value);
            this.notifyObservers();
        }

        public void setSelectedMovieYearOfAward(String value) {
            this.getMountainById(this.getSelectedMountainId()).setYearOfAward(value);
            this.notifyObservers();
        }

        public void setSelectedMovieYearOfProduction(String value) {
            this.getMountainById(this.getSelectedMountainId()).setYearOfProduction(value);
            this.notifyObservers();
        }

        private void pristineList() {
            this.list.stream().forEach(movie -> movie.setHasModified(false));
            this.observerAction = ACTION_PRISTINE;
            aMovieHasBeenRemoved = false;
            this.notifyObservers();
        }

        public void exportListToCsv() {
            BufferedWriter br;
            try {
                try {
                    br = new BufferedWriter(
                            new FileWriter(
                                    new File(
                                            MountainPM.class.getResource(FILE_PATH).toURI()
                                    )
                            )
                    );

                    // Add header line
                    br.write(csvFileHeader);
                    br.newLine();
                    // Add body
                    for (Object mountain : this.list) {
                        try {
                            br.write(mountain.toString());
                            br.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    br.close();
                    this.pristineList();

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public String getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(String searchValue) {
            if (searchValue.length() > 2) {
                this.searchValue = searchValue;
                FilterModule searchFilterModule = new FilterModule(this);
                this.filterdList = searchFilterModule.filter(searchValue);
                this.setSelectedMountainId(this.filterdList.get(0).getId());
            } else {
                this.searchValue = "";
                this.filterdList = this.list;
            }

            this.observerAction = ACTION_SEARCH;
            notifyObservers();

        }

        public boolean areAllMountainValid(){
            long counter = this.list.stream()
                    .map(Mountain::isValid)
                    .filter(b -> !b)
                    .count();
            return counter == 0;
        }

        public boolean editorIsValid() {
            Object mountain= getMountainById(getSelectedMountainId());

            mountain.setIsValid(false);

            if (!MountainValidator.isValidYear(mountain.getYearOfAward())) {
                return false;
            }

            if (!MountainValidator.isRequired(mountain.getTitle())) {
                return false;
            }

            if (!MountainValidator.isRequired(mountain.getDirector())) {
                return false;
            }

            if (!MountainValidator.isRequired(mountain.getMainActor())) {
                return false;
            }

            if (!MountainValidator.isDate(mountain.getStartDate())) {
                return false;
            }

            if (!MountainValidator.isFlag(mountain.getCountry())) {
                return false;
            }

            if (!MountainValidator.isNumber(mountain.getFsk().toString())) {
                return false;
            }

            if (!MountainValidator.isNumber(mountain.getDuration().toString())) {
                return false;
            }

            if (!MountainValidator.isNumber(mountain.getNumberOfOscars().toString()) || mountain.getNumberOfOscars() < 1) {
                return false;
            }

            mountain.setIsValid(true);
            return true;
        }

    }

}
