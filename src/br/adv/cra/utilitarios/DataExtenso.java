package br.adv.cra.utilitarios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataExtenso {

	public DataExtenso() {
	}

	public String DateExtenso(Date data) {
		DateFormat dfmt = new SimpleDateFormat("EEEE, d MMMM yyyy");
		data = Calendar.getInstance(Locale.getDefault()).getTime();
		System.out.println(dfmt.format(data));
		return dfmt.format(data);
	}

	public static void main(String[] args) {
		DataExtenso dt = new DataExtenso();
		Date d = new Date();
		dt.DateExtenso(d);
	}
}
