package facebook.entity;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeAdapter extends XmlAdapter<String, Date> {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    @Override
    public Date unmarshal(String s) throws Exception {
        return sdf.parse(s);
    }

    @Override
    public String marshal(Date date) throws Exception {
        return sdf.format(date);
    }
}
