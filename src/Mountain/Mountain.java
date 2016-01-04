package Mountain;

import javafx.scene.image.Image;
import java.util.Comparator;

/**
 * @Author: Gianni on 04/01/16.
 */
public class Mountain implements Comparator<Mountain>, Comparable<Mountain> {


        /**
         * @param: Declaring Variable of Mountain
         */
        private static final String DELIMITER_NEXT_DATA = ";";

        private boolean hasModified = false;
        private boolean isValid = true;



        private Integer id;
        private Double hoehe;

        private String bergName;
        private double dominanz;
        private double scharten;
        private String kmBis;
        private String mBis;
        private String typ;
        private String region;
        private String kanton;
        private String gebiet;
        private String bildunters;
        private Image preview;

        /**
         * @param: Generating Constructors
         */

        public Mountain(){
            this.hasModified = true;
            this.isValid = false;
        }

        public Mountain(Integer id, Double hoehe){
            this.id = id;
            this.hoehe = hoehe;
            this.dominanz = dominanz;
            this.hasModified = true;
            this.isValid = false;
        }

        public Mountain(String csvInfo){
            String[] split = csvInfo.split(DELIMITER_NEXT_DATA);
            this.isValid = true;
            this.id = Integer.parseInt(split[0]);
            this.bergName = split[1];
            this.typ = split[2];
            this.region = split[3];
            this.gebiet = split[4];
            this.dominanz = Integer.parseInt(split[5]);
            this.kmBis = split[6];
            this.scharten = Integer.parseInt(split[7]);
            this.mBis = split[8];
            this.bildunters = split[9];
        }


        /**
         * @param: Application Program Interface (API)
         * @return: Values
         */
        public boolean isHasModified() {
            return hasModified;
        }

        public void setHasModified(boolean hasModified) {
            this.hasModified = hasModified;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        private boolean confirmChange(Object newValue, Object oldValue){
            return oldValue.equals(newValue);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getHoehe() {
            return hoehe;
        }

        public void setHoehe(Double hoehe) {
            this.hoehe = hoehe;
        }

        public String getBergName() {
            return bergName;
        }

        public void setBergName(String bergName) {
            this.bergName = bergName;
        }

        public double getDominanz() {
            return dominanz;
        }

        public void setDominanz(double dominanz) {
            this.dominanz = dominanz;
        }

        public double getScharten() {
            return scharten;
        }

        public void setScharten(double scharten) {
            this.scharten = scharten;
        }

        public String getKmBis() {
            return kmBis;
        }

        public void setKmBis(String kmBis) {
            this.kmBis = kmBis;
        }

        public String getmBis() {
            return mBis;
        }

        public void setmBis(String mBis) {
            this.mBis = mBis;
        }

        public String getTyp(String value) {
            return typ;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getKanton() {
            return kanton;
        }

        public void setKanton(String kanton) {
            this.kanton = kanton;
        }

        public String getGebiet() {
            return gebiet;
        }

        public void setGebiet(String gebiet) {
            this.gebiet = gebiet;
        }

        public String getBildunters() {
            return bildunters;
        }

        public void setBildunters(String bildunters) {
            this.bildunters = bildunters;
        }

        public Image getPreview() {
            return preview;
        }

        public void setPreview(Image preview) {
            this.preview = preview;
        }

        @Override
        public int compareTo(Mountain o) {
            return this.compare(this, compareMountain);
        }

        @Override
        public int compare(Mountain o1, Mountain o2) {
            int y1 = Double.parseDouble((o1.getHoehe() == null) ? "0" : o2.getHoehe());
            int y2 = Double.parseDouble((o1.getHoehe() == null) ? "0" : o2.getHoehe());
            return y2 - y1;
        }
    }

}
