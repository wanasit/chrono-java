package com.wanasit.chrono.refiner;

import java.util.List;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedResult;

public class ExtractConcordanceRefiner implements Refiner {

    public static final int CONCORDANCE_SIZE = 30;

    public List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOption options) {

	for (ParsedResult result : results) {

	    String preText = text.substring(0, result.index);
	    preText = preText.replaceAll("\\r\\n|\\n|\\r", " ");
	    preText = preText.replaceAll("\\s+", " ");
	    if (preText.length() > CONCORDANCE_SIZE)
		preText = preText.substring(preText.length() - CONCORDANCE_SIZE,
				preText.length());

	    String posText = text.substring(result.index + result.text.length());
	    posText = preText.replaceAll("\\r\\n|\\n|\\r", " ");
	    posText = preText.replaceAll("\\s+", " ");

	    if (posText.length() > CONCORDANCE_SIZE)
		posText = posText.substring(0, CONCORDANCE_SIZE);

	    result.concordance = preText + result.text + posText;
	}

	return results;
    }
}
