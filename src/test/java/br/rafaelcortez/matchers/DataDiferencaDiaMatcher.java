package br.rafaelcortez.matchers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.rafaelcortez.utils.DataUtils;

public class DataDiferencaDiaMatcher extends TypeSafeMatcher<Date> {
	
	private Integer qtdDias;

	public DataDiferencaDiaMatcher(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}
	
	public void describeTo(Description description) {
		Date dataEsperada = DataUtils.obterDataComDiferencaDias(qtdDias);
		DateFormat format = new SimpleDateFormat("dd/MM/YYYY");
		description.appendText(format.format(dataEsperada));
	}

	@Override
	protected boolean matchesSafely(Date data) {	
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
	}

}
