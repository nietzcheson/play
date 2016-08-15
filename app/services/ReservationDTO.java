package services;

/**
 * Created by Sistemas on 12/08/2016.
 */
public class ReservationDTO {

        private String id;
        private String name;
        private String lastName;
        private String hotel;
        private int status;
        private int bulkbank;
        private String username;
        private String confirmation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getHotel() {
            return hotel;
        }

        public void setHotel(String hotel) {
            this.hotel = hotel;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
        }

        public void setBulkbank(int bulkbank) {
            this.bulkbank = bulkbank;
        }

        public int getBulkbank() {
            return this.bulkbank;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return this.username;
        }

        public void setConfirmation(String confirmation) {
            this.confirmation = confirmation;
        }

        public String getConfirmation() {
            return this.confirmation;
        }
    }
