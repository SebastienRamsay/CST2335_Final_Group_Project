package com.example.cst2335finalgroupproject;

import android.app.ListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores the information from the api in java class format.
 */
public class ListJSON {

        private _Embedded _embedded;

        public ListJSON(_Embedded _embedded) {
            this._embedded = _embedded;
        }

        public _Embedded get_embedded() {
            return _embedded;
        }

        public class _Embedded {
            ArrayList<Title> titles;

            public ArrayList<Title> getTitles() {
                return titles;
            }

            public Title getTitles(int index) {
                return titles.get(index);
            }

            public int getNumberOftitles(){
                return titles.size();
            }

            public class Title {
                String title;
                String embed;
                String url;
                List<Image> thumbnail;
                Dates dates;

                public String getTitle() {
                    return title;
                }

                public String getEmbed() {
                    return embed;
                }


                public String getUrl() {
                    return url;
                }

                public List<Image> getThumbnail() {
                    return thumbnail;
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
