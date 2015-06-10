
    package com.example.praba1110.weather;

    import org.xmlpull.v1.XmlPullParser;
    import org.xmlpull.v1.XmlPullParserFactory;

    import java.io.InputStream;
    import java.net.HttpURLConnection;
    import java.net.URL;

    /**
     * Created by praba1110 on 10/6/15.
     */
    class XML {
        private String temperature = "temperature";
        private String urlString = null;
        private XmlPullParserFactory xmlFactoryObject;
        public volatile boolean parsingComplete = true;

        public XML(String url){
            this.urlString = url;
        }


        public String getTemperature(){
            return temperature;
        }


        public void parseXMLAndStoreIt(XmlPullParser myParser) {
            int event;


            try {
                event = myParser.getEventType();

                while (event != XmlPullParser.END_DOCUMENT) {
                    String name=myParser.getName();

                    switch (event){
                        case XmlPullParser.START_TAG:
                            break;



                        case XmlPullParser.END_TAG:

                            if(name.equals("temperature")){
                                temperature = myParser.getAttributeValue(null,"value");
                            }

                            break;
                    }
                    event = myParser.next();
                }
                parsingComplete = false;
            }

            catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void fetchXML(){
            Thread thread = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        URL url = new URL(urlString);
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                        conn.setReadTimeout(10000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream stream = conn.getInputStream();
                        xmlFactoryObject = XmlPullParserFactory.newInstance();
                        XmlPullParser myparser = xmlFactoryObject.newPullParser();

                        myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        myparser.setInput(stream, null);

                        parseXMLAndStoreIt(myparser);
                        stream.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }

