package com.sirra.appcore.util;

import java.util.*;

import com.sirra.server.json.*;

public class Timezones {
	
	protected static SortedMap<String, Data> sortedMapNegatives;
	protected static SortedMap<String, Data> sortedMap;
	protected static List<Data> list;
	
	public static List<Data> get() {
		if(list == null) {
			sortedMap = new TreeMap();
			sortedMapNegatives = new TreeMap(Collections.reverseOrder());
			list = new ArrayList();
			addAll();
			
			list = new ArrayList(sortedMapNegatives.values());
			list.addAll(sortedMap.values());
		}
		
		return list;
	}
	
	public static String getDefault() {
		return "America/Los_Angeles";
	}
	
	protected static void addAll() {
		add("Afghanistan Standard Time", "Asia/Kabul", "(GMT +04:30) Kabul");
        add("Alaskan Standard Time", "America/Anchorage", "(GMT -09:00) Alaska");
        add("Arab Standard Time", "Asia/Riyadh", "(GMT +03:00) Kuwait, Riyadh");
        add("Arabian Standard Time", "Asia/Dubai", "(GMT +04:00) Abu Dhabi, Muscat");
        add("Arabic Standard Time", "Asia/Baghdad", "(GMT +03:00) Baghdad");
        add("Argentina Standard Time", "America/Buenos_Aires", "(GMT -03:00) Buenos Aires");
        add("Atlantic Standard Time", "America/Halifax", "(GMT -04:00) Atlantic Time (Canada)");
        add("AUS Central Standard Time", "Australia/Darwin", "(GMT +09:30) Darwin");
        add("AUS Eastern Standard Time", "Australia/Sydney", "(GMT +10:00) Canberra, Melbourne, Sydney");
        add("Azerbaijan Standard Time", "Asia/Baku", "(GMT +04:00) Baku");
        add("Azores Standard Time", "Atlantic/Azores", "(GMT -01:00) Azores");
        add("Bangladesh Standard Time", "Asia/Dhaka", "(GMT +06:00) Dhaka");
        add("Canada Central Standard Time", "America/Regina", "(GMT -06:00) Saskatchewan");
        add("Cape Verde Standard Time", "Atlantic/Cape_Verde", "(GMT -01:00) Cape Verde Is.");
        add("Caucasus Standard Time", "Asia/Yerevan", "(GMT +04:00) Yerevan");
        add("Cen. Australia Standard Time", "Australia/Adelaide", "(GMT +09:30) Adelaide");
        add("Central America Standard Time", "America/Guatemala", "(GMT -06:00) Central America");
        add("Central Asia Standard Time", "Asia/Almaty", "(GMT +06:00) Astana");
        add("Central Brazilian Standard Time", "America/Cuiaba", "(GMT -04:00) Cuiaba");
        add("Central Europe Standard Time", "Europe/Budapest", "(GMT +01:00) Belgrade, Bratislava, Budapest, Ljubljana, Prague");
        add("Central European Standard Time", "Europe/Warsaw", "(GMT +01:00) Sarajevo, Skopje, Warsaw, Zagreb");
        add("Central Pacific Standard Time", "Pacific/Guadalcanal", "(GMT +11:00) Solomon Is., New Caledonia");
        add("Central Standard Time (Mexico)", "America/Mexico_City", "(GMT -06:00) Guadalajara, Mexico City, Monterrey");
        add("Central Standard Time", "America/Chicago", "(GMT -06:00) Central Time (US & Canada)");
        add("China Standard Time", "Asia/Shanghai", "(GMT +08:00) Beijing, Chongqing, Hong Kong, Urumqi");
        add("Dateline Standard Time", "Etc/GMT+12", "(GMT -12:00) International Date Line West");
        add("E. Africa Standard Time", "Africa/Nairobi", "(GMT +03:00) Nairobi");
        add("E. Australia Standard Time", "Australia/Brisbane", "(GMT +10:00) Brisbane");
        add("E. Europe Standard Time", "Europe/Minsk", "(GMT +02:00) Minsk");
        add("E. South America Standard Time", "America/Sao_Paulo", "(GMT -03:00) Brasilia");
        add("Eastern Standard Time", "America/New_York", "(GMT -05:00) Eastern Time (US & Canada)");
        add("Egypt Standard Time", "Africa/Cairo", "(GMT +02:00) Cairo");
        add("Ekaterinburg Standard Time", "Asia/Yekaterinburg", "(GMT +05:00) Ekaterinburg");
        add("Fiji Standard Time", "Pacific/Fiji", "(GMT +12:00) Fiji, Marshall Is.");
        add("FLE Standard Time", "Europe/Kiev", "(GMT +02:00) Helsinki, Kyiv, Riga, Sofia, Tallinn, Vilnius");
        add("Georgian Standard Time", "Asia/Tbilisi", "(GMT +04:00) Tbilisi");
        add("GMT Standard Time", "Europe/London", "(GMT) Dublin, Edinburgh, Lisbon, London");
        add("Greenland Standard Time", "America/Godthab", "(GMT -03:00) Greenland");
        add("Greenwich Standard Time", "Atlantic/Reykjavik", "(GMT) Monrovia, Reykjavik");
        add("GTB Standard Time", "Europe/Istanbul", "(GMT +02:00) Athens, Bucharest, Istanbul");
        add("Hawaiian Standard Time", "Pacific/Honolulu", "(GMT -10:00) Hawaii");
        add("India Standard Time", "Asia/Calcutta", "(GMT +05:30) Chennai, Kolkata, Mumbai, New Delhi");
        add("Iran Standard Time", "Asia/Tehran", "(GMT +03:30) Tehran");
        add("Israel Standard Time", "Asia/Jerusalem", "(GMT +02:00) Jerusalem");
        add("Jordan Standard Time", "Asia/Amman", "(GMT +02:00) Amman");
        add("Kamchatka Standard Time", "Asia/Kamchatka", "(GMT +12:00) Petropavlovsk-Kamchatsky - Old");
        add("Korea Standard Time", "Asia/Seoul", "(GMT +09:00) Seoul");
        add("Magadan Standard Time", "Asia/Magadan", "(GMT +11:00) Magadan");
        add("Mauritius Standard Time", "Indian/Mauritius", "(GMT +04:00) Port Louis");
        add("Mid-Atlantic Standard Time", "Etc/GMT+2", "(GMT -02:00) Mid-Atlantic");
        add("Middle East Standard Time", "Asia/Beirut", "(GMT +02:00) Beirut");
        add("Montevideo Standard Time", "America/Montevideo", "(GMT -03:00) Montevideo");
        add("Morocco Standard Time", "Africa/Casablanca", "(GMT) Casablanca");
        add("Mountain Standard Time (Mexico)", "America/Chihuahua", "(GMT -07:00) Chihuahua, La Paz, Mazatlan");
        add("Mountain Standard Time", "America/Denver", "(GMT -07:00) Mountain Time (US & Canada)");
        add("Myanmar Standard Time", "Asia/Rangoon", "(GMT +06:30) Yangon (Rangoon)");
        add("N. Central Asia Standard Time", "Asia/Novosibirsk", "(GMT +06:00) Novosibirsk");
        add("Namibia Standard Time", "Africa/Windhoek", "(GMT +02:00) Windhoek");
        add("Nepal Standard Time", "Asia/Katmandu", "(GMT +05:45) Kathmandu");
        add("New Zealand Standard Time", "Pacific/Auckland", "(GMT +12:00) Auckland, Wellington");
        add("Newfoundland Standard Time", "America/St_Johns", "(GMT -03:30) Newfoundland");
        add("North Asia East Standard Time", "Asia/Irkutsk", "(GMT +08:00) Irkutsk");
        add("North Asia Standard Time", "Asia/Krasnoyarsk", "(GMT +07:00) Krasnoyarsk");
        add("Pacific SA Standard Time", "America/Santiago", "(GMT -04:00) Santiago");
        add("Pacific Standard Time (Mexico)", "America/Tijuana", "(GMT -08:00) Baja California");
        add("Pacific Standard Time", "America/Los_Angeles", "(GMT -08:00) Pacific Time (US & Canada)");
        add("Pakistan Standard Time", "Asia/Karachi", "(GMT +05:00) Islamabad, Karachi");
        add("Paraguay Standard Time", "America/Asuncion", "(GMT -04:00) Asuncion");
        add("Romance Standard Time", "Europe/Paris", "(GMT +01:00) Brussels, Copenhagen, Madrid, Paris");
        add("Russian Standard Time", "Europe/Moscow", "(GMT +03:00) Moscow, St. Petersburg, Volgograd");
        add("SA Eastern Standard Time", "America/Cayenne", "(GMT -03:00) Cayenne, Fortaleza");
        add("SA Pacific Standard Time", "America/Bogota", "(GMT -05:00) Bogota, Lima, Quito");
        add("SA Western Standard Time", "America/La_Paz", "(GMT -04:00) Georgetown, La Paz, Manaus, San Juan");
        add("Samoa Standard Time", "Pacific/Apia", "(GMT -11:00) Samoa");
        add("SE Asia Standard Time", "Asia/Bangkok", "(GMT +07:00) Bangkok, Hanoi, Jakarta");
        add("Singapore Standard Time", "Asia/Singapore", "(GMT +08:00) Kuala Lumpur, Singapore");
        add("South Africa Standard Time", "Africa/Johannesburg", "(GMT +02:00) Harare, Pretoria");
        add("Sri Lanka Standard Time", "Asia/Colombo", "(GMT +05:30) Sri Jayawardenepura");
        add("Syria Standard Time", "Asia/Damascus", "(GMT +02:00) Damascus");
        add("Taipei Standard Time", "Asia/Taipei", "(GMT +08:00) Taipei");
        add("Tasmania Standard Time", "Australia/Hobart", "(GMT +10:00) Hobart");
        add("Tokyo Standard Time", "Asia/Tokyo", "(GMT +09:00) Osaka, Sapporo, Tokyo");
        add("Tonga Standard Time", "Pacific/Tongatapu", "(GMT +13:00) Nuku'alofa");
        add("Ulaanbaatar Standard Time", "Asia/Ulaanbaatar", "(GMT +08:00) Ulaanbaatar");
        add("US Eastern Standard Time", "America/Indianapolis", "(GMT -05:00) Indiana (East)");
        add("US Mountain Standard Time", "America/Phoenix", "(GMT -07:00) Arizona");
        add("GMT ", "Etc/GMT", "(GMT) Coordinated Universal Time");
        add("GMT +12", "Etc/GMT-12", "(GMT +12:00) Coordinated Universal Time+12");
        add("GMT -02", "Etc/GMT+2", "(GMT -02:00) Coordinated Universal Time-02");
        add("GMT -11", "Etc/GMT+11", "(GMT -11:00) Coordinated Universal Time-11");
        add("Venezuela Standard Time", "America/Caracas", "(GMT -04:30) Caracas");
        add("Vladivostok Standard Time", "Asia/Vladivostok", "(GMT +10:00) Vladivostok");
        add("W. Australia Standard Time", "Australia/Perth", "(GMT +08:00) Perth");
        add("W. Central Africa Standard Time", "Africa/Lagos", "(GMT +01:00) West Central Africa");
        add("W. Europe Standard Time", "Europe/Berlin", "(GMT +01:00) Amsterdam, Berlin, Bern, Rome, Stockholm, Vienna");
        add("West Asia Standard Time", "Asia/Tashkent", "(GMT +05:00) Tashkent");
        add("West Pacific Standard Time", "Pacific/Port_Moresby", "(GMT +10:00) Guam, Port Moresby");
        add("Yakutsk Standard Time", "Asia/Yakutsk", "(GMT +09:00) Yakutsk");
	}
	
	protected static void add(String windowsStandardName, String olsonName, String windowsDisplayName) {
		Data data = new Data();
		
		data.put("id", olsonName);
		data.put("name", windowsDisplayName);
		
		String sortKey = windowsDisplayName;
		
		if(sortKey.indexOf("(GMT -") >= 0) {
			sortedMapNegatives.put(sortKey,  data);
		} else if(sortKey.indexOf("(GMT)") >= 0) {
			sortedMap.put("0" + sortKey, data);
		} else {
			sortedMap.put("1" + sortKey, data);
		}
		
		
	}
}
