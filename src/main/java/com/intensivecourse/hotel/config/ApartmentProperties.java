package com.intensivecourse.hotel.config;

import com.intensivecourse.hotel.models.Currency;
import com.intensivecourse.hotel.models.ReservationStatus;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "apartment")
public class ApartmentProperties {

    private Base base = new Base();
    private Status status = new Status();

    public Base getBase(){
        return base;
    }

    public void setBase(Base base){
        this.base = base;
    }

    public Status getStatus(){
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }


    public static class Base {
        private int price;
        private Currency currency;
        private ReservationStatus status;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public Currency getCurrency() {
            return currency;
        }

        public void setCurrency(Currency currency) {
            this.currency = currency;
        }

        public ReservationStatus getStatus() {
            return status;
        }

        public void setStatus(ReservationStatus status) {
            this.status = status;
        }
    }


    public static class Status {
        private boolean changeable;

        public void setChangeable(boolean changeable) {
            this.changeable = changeable;
        }

        public boolean isChangeable() {
            return changeable;
        }
    }

}


