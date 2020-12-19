package com.example.myapplication1;

public class Firebase {

        private double latitude;
        private double longnitude;
        private String cropname;

        Firebase()
        {

        }

        public double getLatitude()
        {
            return latitude;
        }

        public void setLatitude()
        {
            this.latitude=latitude;
        }

        public double getLongnitude()
        {
            return longnitude;
        }

        public void setLongnitudeitude()
        {
            this.longnitude=longnitude;
        }

        public String getCropname()
        {
            return cropname;
        }

        public void setCropname()
        {
            this.cropname=cropname;
        }

        public String toString()
        {
            return latitude+":"+longnitude+":"+cropname ;

        }

        public Firebase(double latitude,double longnitude,String cropname)
        {
            this.latitude=latitude;
            this.longnitude=longnitude;
            this.cropname=cropname;
        }

    }
