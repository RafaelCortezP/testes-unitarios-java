package br.rafaelcortez.matchers;

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
		
	}

	@Override
	protected boolean matchesSafely(Date data) {	
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(qtdDias));
	}

}
