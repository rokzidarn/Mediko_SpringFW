package si.fri.t15.base.helpers;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public abstract class Utils {
	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(date);
	}

	public static String format(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
		return sdf.format(timestamp);
	}
}
