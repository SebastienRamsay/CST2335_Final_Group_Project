package com.example.cst2335finalgroupproject;

import java.util.ArrayList;
import java.util.List;

public class EventJSON {
    private _Embedded _embedded;

    public EventJSON(_Embedded _embedded) {
        this._embedded = _embedded;
    }

    public _Embedded get_embedded() {
        return _embedded;
    }



    public class _Embedded {
        ArrayList<Event> events;

        public ArrayList<Event> getEvents() {
            return events;
        }

        public Event getEvent(int index) {
            return events.get(index);
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




            public String getName() {
                return name;
            }

            public String getType() {
                return type;
            }

            public String getId() {
                return id;
            }

            public boolean isTest() {
                return test;
            }

            public String getUrl() {
                return url;
            }

            public String getLocale() {
                return locale;
            }

            public List<Image> getImages() {
                return images;
            }

            public Dates getDates() {
                return dates;
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

                public String getUrl() {
                    return url;
                }

                public int getWidth() {
                    return width;
                }

                public int getHeight() {
                    return height;
                }

                public boolean isFallback() {
                    return fallback;
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
                }
            }
        }
    }
}
