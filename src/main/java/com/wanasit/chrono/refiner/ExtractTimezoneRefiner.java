package com.wanasit.chrono.refiner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wanasit.chrono.ChronoOption;
import com.wanasit.chrono.ParsedDateComponent.Components;
import com.wanasit.chrono.ParsedResult;

public class ExtractTimezoneRefiner implements Refiner {

    public static final Pattern TIMEZONE_OFFSET_PATTERN = Pattern
            .compile("^\\s*(GMT|UTC)?(\\+|\\-)(\\d{1,2}):?(\\d{2})");

    public static final Pattern TIMEZONE_NAME_PATTERN = Pattern
            .compile("^\\s*\\(?([A-Z]{1,4})\\)?(?=\\W|$)");

    public static final Map<String, Integer> TIMEZONE_ABBR_MAP = new HashMap<String, Integer>();

    public List<ParsedResult> refine(List<ParsedResult> results, String text, ChronoOption options) {


        for (ParsedResult result : results) {

            if (result.start.isCertain(Components.TimezoneOffset))
                continue;

            Matcher timezoneOffsetMatcher = TIMEZONE_OFFSET_PATTERN.matcher(text.substring(result.index + result.text.length()));
            if (timezoneOffsetMatcher.find()) {

                int hourOffset = Integer.parseInt(timezoneOffsetMatcher.group(3));
                int minuteOffset = Integer.parseInt(timezoneOffsetMatcher.group(4));
                int timezoneOffset = hourOffset * 60 + minuteOffset;
                if (timezoneOffsetMatcher.group(2).equals("-"))
                    timezoneOffset = -timezoneOffset;

                if (result.end != null) {
                    result.end.assign(Components.TimezoneOffset, timezoneOffset);
                }

                result.start.assign(Components.TimezoneOffset, timezoneOffset);
                result.text += timezoneOffsetMatcher.group();
                result.tags.add(ExtractTimezoneRefiner.class.getName());
            }

            Matcher timezoneNameMatcher = TIMEZONE_NAME_PATTERN.matcher(text.substring(result.index + result.text.length()));
            if (timezoneNameMatcher.find()) {

                String timezoneAbbr = timezoneNameMatcher.group(1);
                if (TIMEZONE_ABBR_MAP.containsKey(timezoneAbbr)) {

                    int timezoneOffset = TIMEZONE_ABBR_MAP.get(timezoneAbbr);

                    if (!result.start.isCertain(Components.TimezoneOffset)) {
                        result.start.assign(Components.TimezoneOffset, timezoneOffset);
                    }

                    if (result.end != null && !result.start.isCertain(Components.TimezoneOffset)) {
                        result.end.assign(Components.TimezoneOffset, timezoneOffset);
                    }

                    result.text += timezoneNameMatcher.group();
                    result.tags.add(ExtractTimezoneRefiner.class.getName());
                }
            }
        }

        return results;
    }

    static {
        TIMEZONE_ABBR_MAP.put("A", 60);
        TIMEZONE_ABBR_MAP.put("ACDT", 630);
        TIMEZONE_ABBR_MAP.put("ACST", 570);
        TIMEZONE_ABBR_MAP.put("ADT", -180);
        TIMEZONE_ABBR_MAP.put("AEDT", 660);
        TIMEZONE_ABBR_MAP.put("AEST", 600);
        TIMEZONE_ABBR_MAP.put("AFT", 270);
        TIMEZONE_ABBR_MAP.put("AKDT", -480);
        TIMEZONE_ABBR_MAP.put("AKST", -540);
        TIMEZONE_ABBR_MAP.put("ALMT", 360);
        TIMEZONE_ABBR_MAP.put("AMST", -180);
        TIMEZONE_ABBR_MAP.put("AMT", -240);
        TIMEZONE_ABBR_MAP.put("ANAST", 720);
        TIMEZONE_ABBR_MAP.put("ANAT", 720);
        TIMEZONE_ABBR_MAP.put("AQTT", 300);
        TIMEZONE_ABBR_MAP.put("ART", -180);
        TIMEZONE_ABBR_MAP.put("AST", -240);
        TIMEZONE_ABBR_MAP.put("AWDT", 540);
        TIMEZONE_ABBR_MAP.put("AWST", 480);
        TIMEZONE_ABBR_MAP.put("AZOST", 0);
        TIMEZONE_ABBR_MAP.put("AZOT", -60);
        TIMEZONE_ABBR_MAP.put("AZST", 300);
        TIMEZONE_ABBR_MAP.put("AZT", 240);
        TIMEZONE_ABBR_MAP.put("B", 120);
        TIMEZONE_ABBR_MAP.put("BNT", 480);
        TIMEZONE_ABBR_MAP.put("BOT", -240);
        TIMEZONE_ABBR_MAP.put("BRST", -120);
        TIMEZONE_ABBR_MAP.put("BRT", -180);
        TIMEZONE_ABBR_MAP.put("BST", 60);
        TIMEZONE_ABBR_MAP.put("BTT", 360);
        TIMEZONE_ABBR_MAP.put("C", 180);
        TIMEZONE_ABBR_MAP.put("CAST", 480);
        TIMEZONE_ABBR_MAP.put("CAT", 120);
        TIMEZONE_ABBR_MAP.put("CCT", 390);
        TIMEZONE_ABBR_MAP.put("CDT", -300);
        TIMEZONE_ABBR_MAP.put("CEST", 120);
        TIMEZONE_ABBR_MAP.put("CET", 60);
        TIMEZONE_ABBR_MAP.put("CHADT", 825);
        TIMEZONE_ABBR_MAP.put("CHAST", 765);
        TIMEZONE_ABBR_MAP.put("CKT", -600);
        TIMEZONE_ABBR_MAP.put("CLST", -180);
        TIMEZONE_ABBR_MAP.put("CLT", -240);
        TIMEZONE_ABBR_MAP.put("COT", -300);
        TIMEZONE_ABBR_MAP.put("CST", -360);
        TIMEZONE_ABBR_MAP.put("CVT", -60);
        TIMEZONE_ABBR_MAP.put("CXT", 420);
        TIMEZONE_ABBR_MAP.put("ChST", 600);
        TIMEZONE_ABBR_MAP.put("D", 240);
        TIMEZONE_ABBR_MAP.put("DAVT", 420);
        TIMEZONE_ABBR_MAP.put("E", 300);
        TIMEZONE_ABBR_MAP.put("EASST", -300);
        TIMEZONE_ABBR_MAP.put("EAST", -360);
        TIMEZONE_ABBR_MAP.put("EAT", 180);
        TIMEZONE_ABBR_MAP.put("ECT", -300);
        TIMEZONE_ABBR_MAP.put("EDT", -240);
        TIMEZONE_ABBR_MAP.put("EEST", 180);
        TIMEZONE_ABBR_MAP.put("EET", 120);
        TIMEZONE_ABBR_MAP.put("EGST", 0);
        TIMEZONE_ABBR_MAP.put("EGT", -60);
        TIMEZONE_ABBR_MAP.put("EST", -300);
        TIMEZONE_ABBR_MAP.put("ET", -300);
        TIMEZONE_ABBR_MAP.put("F", 360);
        TIMEZONE_ABBR_MAP.put("FJST", 780);
        TIMEZONE_ABBR_MAP.put("FJT", 720);
        TIMEZONE_ABBR_MAP.put("FKST", -180);
        TIMEZONE_ABBR_MAP.put("FKT", -240);
        TIMEZONE_ABBR_MAP.put("FNT", -120);
        TIMEZONE_ABBR_MAP.put("G", 420);
        TIMEZONE_ABBR_MAP.put("GALT", -360);
        TIMEZONE_ABBR_MAP.put("GAMT", -540);
        TIMEZONE_ABBR_MAP.put("GET", 240);
        TIMEZONE_ABBR_MAP.put("GFT", -180);
        TIMEZONE_ABBR_MAP.put("GILT", 720);
        TIMEZONE_ABBR_MAP.put("GMT", 0);
        TIMEZONE_ABBR_MAP.put("GST", 240);
        TIMEZONE_ABBR_MAP.put("GYT", -240);
        TIMEZONE_ABBR_MAP.put("H", 480);
        TIMEZONE_ABBR_MAP.put("HAA", -180);
        TIMEZONE_ABBR_MAP.put("HAC", -300);
        TIMEZONE_ABBR_MAP.put("HADT", -540);
        TIMEZONE_ABBR_MAP.put("HAE", -240);
        TIMEZONE_ABBR_MAP.put("HAP", -420);
        TIMEZONE_ABBR_MAP.put("HAR", -360);
        TIMEZONE_ABBR_MAP.put("HAST", -600);
        TIMEZONE_ABBR_MAP.put("HAT", -90);
        TIMEZONE_ABBR_MAP.put("HAY", -480);
        TIMEZONE_ABBR_MAP.put("HKT", 480);
        TIMEZONE_ABBR_MAP.put("HLV", -210);
        TIMEZONE_ABBR_MAP.put("HNA", -240);
        TIMEZONE_ABBR_MAP.put("HNC", -360);
        TIMEZONE_ABBR_MAP.put("HNE", -300);
        TIMEZONE_ABBR_MAP.put("HNP", -480);
        TIMEZONE_ABBR_MAP.put("HNR", -420);
        TIMEZONE_ABBR_MAP.put("HNT", -150);
        TIMEZONE_ABBR_MAP.put("HNY", -540);
        TIMEZONE_ABBR_MAP.put("HOVT", 420);
        TIMEZONE_ABBR_MAP.put("I", 540);
        TIMEZONE_ABBR_MAP.put("ICT", 420);
        TIMEZONE_ABBR_MAP.put("IDT", 180);
        TIMEZONE_ABBR_MAP.put("IOT", 360);
        TIMEZONE_ABBR_MAP.put("IRDT", 270);
        TIMEZONE_ABBR_MAP.put("IRKST", 540);
        TIMEZONE_ABBR_MAP.put("IRKT", 540);
        TIMEZONE_ABBR_MAP.put("IRST", 210);
        TIMEZONE_ABBR_MAP.put("IST", 60);
        TIMEZONE_ABBR_MAP.put("JST", 540);
        TIMEZONE_ABBR_MAP.put("K", 600);
        TIMEZONE_ABBR_MAP.put("KGT", 360);
        TIMEZONE_ABBR_MAP.put("KRAST", 480);
        TIMEZONE_ABBR_MAP.put("KRAT", 480);
        TIMEZONE_ABBR_MAP.put("KST", 540);
        TIMEZONE_ABBR_MAP.put("KUYT", 240);
        TIMEZONE_ABBR_MAP.put("L", 660);
        TIMEZONE_ABBR_MAP.put("LHDT", 660);
        TIMEZONE_ABBR_MAP.put("LHST", 630);
        TIMEZONE_ABBR_MAP.put("LINT", 840);
        TIMEZONE_ABBR_MAP.put("M", 720);
        TIMEZONE_ABBR_MAP.put("MAGST", 720);
        TIMEZONE_ABBR_MAP.put("MAGT", 720);
        TIMEZONE_ABBR_MAP.put("MART", -510);
        TIMEZONE_ABBR_MAP.put("MAWT", 300);
        TIMEZONE_ABBR_MAP.put("MDT", -360);
        TIMEZONE_ABBR_MAP.put("MESZ", 120);
        TIMEZONE_ABBR_MAP.put("MEZ", 60);
        TIMEZONE_ABBR_MAP.put("MHT", 720);
        TIMEZONE_ABBR_MAP.put("MMT", 390);
        TIMEZONE_ABBR_MAP.put("MSD", 240);
        TIMEZONE_ABBR_MAP.put("MSK", 240);
        TIMEZONE_ABBR_MAP.put("MST", -420);
        TIMEZONE_ABBR_MAP.put("MUT", 240);
        TIMEZONE_ABBR_MAP.put("MVT", 300);
        TIMEZONE_ABBR_MAP.put("MYT", 480);
        TIMEZONE_ABBR_MAP.put("N", -60);
        TIMEZONE_ABBR_MAP.put("NCT", 660);
        TIMEZONE_ABBR_MAP.put("NDT", -90);
        TIMEZONE_ABBR_MAP.put("NFT", 690);
        TIMEZONE_ABBR_MAP.put("NOVST", 420);
        TIMEZONE_ABBR_MAP.put("NOVT", 360);
        TIMEZONE_ABBR_MAP.put("NPT", 345);
        TIMEZONE_ABBR_MAP.put("NST", -150);
        TIMEZONE_ABBR_MAP.put("NUT", -660);
        TIMEZONE_ABBR_MAP.put("NZDT", 780);
        TIMEZONE_ABBR_MAP.put("NZST", 720);
        TIMEZONE_ABBR_MAP.put("O", -120);
        TIMEZONE_ABBR_MAP.put("OMSST", 420);
        TIMEZONE_ABBR_MAP.put("OMST", 420);
        TIMEZONE_ABBR_MAP.put("P", -180);
        TIMEZONE_ABBR_MAP.put("PDT", -420);
        TIMEZONE_ABBR_MAP.put("PET", -300);
        TIMEZONE_ABBR_MAP.put("PETST", 720);
        TIMEZONE_ABBR_MAP.put("PETT", 720);
        TIMEZONE_ABBR_MAP.put("PGT", 600);
        TIMEZONE_ABBR_MAP.put("PHOT", 780);
        TIMEZONE_ABBR_MAP.put("PHT", 480);
        TIMEZONE_ABBR_MAP.put("PKT", 300);
        TIMEZONE_ABBR_MAP.put("PMDT", -120);
        TIMEZONE_ABBR_MAP.put("PMST", -180);
        TIMEZONE_ABBR_MAP.put("PONT", 660);
        TIMEZONE_ABBR_MAP.put("PST", -480);
        TIMEZONE_ABBR_MAP.put("PT", -480);
        TIMEZONE_ABBR_MAP.put("PWT", 540);
        TIMEZONE_ABBR_MAP.put("PYST", -180);
        TIMEZONE_ABBR_MAP.put("PYT", -240);
        TIMEZONE_ABBR_MAP.put("Q", -240);
        TIMEZONE_ABBR_MAP.put("R", -300);
        TIMEZONE_ABBR_MAP.put("RET", 240);
        TIMEZONE_ABBR_MAP.put("S", -360);
        TIMEZONE_ABBR_MAP.put("SAMT", 240);
        TIMEZONE_ABBR_MAP.put("SAST", 120);
        TIMEZONE_ABBR_MAP.put("SBT", 660);
        TIMEZONE_ABBR_MAP.put("SCT", 240);
        TIMEZONE_ABBR_MAP.put("SGT", 480);
        TIMEZONE_ABBR_MAP.put("SRT", -180);
        TIMEZONE_ABBR_MAP.put("SST", -660);
        TIMEZONE_ABBR_MAP.put("T", -420);
        TIMEZONE_ABBR_MAP.put("TAHT", -600);
        TIMEZONE_ABBR_MAP.put("TFT", 300);
        TIMEZONE_ABBR_MAP.put("TJT", 300);
        TIMEZONE_ABBR_MAP.put("TKT", 780);
        TIMEZONE_ABBR_MAP.put("TLT", 540);
        TIMEZONE_ABBR_MAP.put("TMT", 300);
        TIMEZONE_ABBR_MAP.put("TVT", 720);
        TIMEZONE_ABBR_MAP.put("U", -480);
        TIMEZONE_ABBR_MAP.put("ULAT", 480);
        TIMEZONE_ABBR_MAP.put("UTC", 0);
        TIMEZONE_ABBR_MAP.put("UYST", -120);
        TIMEZONE_ABBR_MAP.put("UYT", -180);
        TIMEZONE_ABBR_MAP.put("UZT", 300);
        TIMEZONE_ABBR_MAP.put("V", -540);
        TIMEZONE_ABBR_MAP.put("VET", -210);
        TIMEZONE_ABBR_MAP.put("VLAST", 660);
        TIMEZONE_ABBR_MAP.put("VLAT", 660);
        TIMEZONE_ABBR_MAP.put("VUT", 660);
        TIMEZONE_ABBR_MAP.put("W", -600);
        TIMEZONE_ABBR_MAP.put("WAST", 120);
        TIMEZONE_ABBR_MAP.put("WAT", 60);
        TIMEZONE_ABBR_MAP.put("WEST", 60);
        TIMEZONE_ABBR_MAP.put("WESZ", 60);
        TIMEZONE_ABBR_MAP.put("WET", 0);
        TIMEZONE_ABBR_MAP.put("WEZ", 0);
        TIMEZONE_ABBR_MAP.put("WFT", 720);
        TIMEZONE_ABBR_MAP.put("WGST", -120);
        TIMEZONE_ABBR_MAP.put("WGT", -180);
        TIMEZONE_ABBR_MAP.put("WIB", 420);
        TIMEZONE_ABBR_MAP.put("WIT", 540);
        TIMEZONE_ABBR_MAP.put("WITA", 480);
        TIMEZONE_ABBR_MAP.put("WST", 780);
        TIMEZONE_ABBR_MAP.put("WT", 0);
        TIMEZONE_ABBR_MAP.put("X", -660);
        TIMEZONE_ABBR_MAP.put("Y", -720);
        TIMEZONE_ABBR_MAP.put("YAKST", 600);
        TIMEZONE_ABBR_MAP.put("YAKT", 600);
        TIMEZONE_ABBR_MAP.put("YAPT", 600);
        TIMEZONE_ABBR_MAP.put("YEKST", 360);
        TIMEZONE_ABBR_MAP.put("YEKT", 360);
        TIMEZONE_ABBR_MAP.put("Z", 0);
    }
}
