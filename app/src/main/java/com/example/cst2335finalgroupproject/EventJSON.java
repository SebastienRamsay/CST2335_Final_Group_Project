package com.example.cst2335finalgroupproject;

import java.util.ArrayList;
import java.util.List;

/**
 * class to store information from the api in java class format
 */
public class EventJSON {
    private _Embedded _embedded;

    public _Embedded get_embedded() {
        return _embedded;
    }

    public void set_embedded(_Embedded _embedded) {
        this._embedded = _embedded;
    }

    public static class _Embedded {
        ArrayList<Event> events;

        public ArrayList<Event> getEvents() {
            return events;
        }

        public Event getEvent(int index) {
            return events.get(index);
        }

        public void setEvents(ArrayList<Event> events) {
            this.events = events;
        }

        public int getNumberOfEvents(){
            return events.size();
        }

        public class Event {
            String name;
            String type;
            String id;
            boolean test;
            String url;
            String locale;
            List<Image> images;
            Dates dates;
            List<PriceRange> priceRanges;


            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isTest() {
                return test;
            }

            public void setTest(boolean test) {
                this.test = test;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getLocale() {
                return locale;
            }

            public void setLocale(String locale) {
                this.locale = locale;
            }

            public List<Image> getImages() {
                return images;
            }

            public void setImages(List<Image> images) {
                this.images = images;
            }

            public void setImageURL(String URL){
                Image image = new Image();
                image.setUrl(URL);
                this.images.set(0, image);
            }

            public Dates getDates() {
                return dates;
            }

            public void setDates(Dates dates) {
                this.dates = dates;
            }

            public List<PriceRange> getPriceRanges() {
                return priceRanges;
            }

            public void setPriceRanges(List<PriceRange> priceRanges) {
                this.priceRanges = priceRanges;
            }

            public class Image {
                String ratio;
                String url;
                int width;
                int height;
                boolean fallback;



                public String getRatio() {
                    return ratio;
                }

                public void setRatio(String ratio) {
                    this.ratio = ratio;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getWidth() {
                    return width;
                }

                public void setWidth(int width) {
                    this.width = width;
                }

                public int getHeight() {
                    return height;
                }

                public void setHeight(int height) {
                    this.height = height;
                }

                public boolean isFallback() {
                    return fallback;
                }

                public void setFallback(boolean fallback) {
                    this.fallback = fallback;
                }
            }

            public class Dates {
                Start start;

                public Start getStart() {
                    return start;
                }

                public class Start {
                    String localDate;
                    String localTime;
                    String dateTime;

                    public String getLocalDate() {
                        return localDate;
                    }

                    public String getLocalTime() {
                        return localTime;
                    }

                    public String getDateTime() {
                        return dateTime;
                    }

                    public void setLocalDate(String localDate) {
                        this.localDate = localDate;
                    }

                    public void setLocalTime(String localTime) {
                        this.localTime = localTime;
                    }

                    public void setDateTime(String dateTime) {
                        this.dateTime = dateTime;
                    }
                }
            }

            public class PriceRange {
                String min;
                String max;

                public String getMin() {
                    return min;
                }

                public void setMin(String min) {
                    this.min = min;
                }

                public String getMax() {
                    return max;
                }

                public void setMax(String max) {
                    this.max = max;
                }
            }
        }
    }
}
