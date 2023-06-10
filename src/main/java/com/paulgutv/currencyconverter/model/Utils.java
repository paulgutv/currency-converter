package com.paulgutv.currencyconverter.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static String addSymbol(String currency) {
        return switch (currency) {
            case "USD" -> "$";
            case "EUR" -> "€";
            case "JPY" -> "¥";
            case "GBP" -> "£";
            case "AUD" -> "A$";
            case "CAD" -> "C$";
            case "CHF" -> "Fr";
            case "CNY" -> "¥";
            case "NZD" -> "NZ$";
            case "MXN" -> "$";
            case "BRL" -> "R$";
            case "RUB" -> "₽";
            case "INR" -> "₹";
            case "KRW" -> "₩";
            case "TRY" -> "₺";
            case "ZAR" -> "R";
            case "SGD" -> "S$";
            case "HKD" -> "HK$";
            case "ILS" -> "₪";
            case "PHP" -> "₱";
            case "SEK" -> "kr";
            case "NOK" -> "kr";
            case "TWD" -> "NT$";
            case "SAR" -> "﷼";
            case "AED" -> "د.إ";
            case "JMD" -> "J$";
            case "BSD" -> "B$";
            case "HUF" -> "Ft";
            case "ARS" -> "$";
            case "KWD" -> "KD";
            case "GTQ" -> "Q";
            case "HNL" -> "L";
            case "NIO" -> "C$";
            case "PAB" -> "B/.";
            case "GYD" -> "G$";
            case "PEN" -> "S/.";
            case "PYG" -> "₲";
            case "UYU" -> "$U";
            case "VES" -> "Bs.";
            default -> "Desconocido";
        };
    }

    static String obtenerCodigoPais(String currency) {
        Map<String, String> currencyCountryMap = new HashMap<>();
        currencyCountryMap.put("ARS", "argentina");
        currencyCountryMap.put("AUD", "australia");
        currencyCountryMap.put("BSD", "bahamas");
        currencyCountryMap.put("BRL", "brazil");
        currencyCountryMap.put("CAD", "canada");
        currencyCountryMap.put("CNY", "china");
        currencyCountryMap.put("EUR", "europe");
        currencyCountryMap.put("GTQ", "guatemala");
        currencyCountryMap.put("GYD", "guyana");
        currencyCountryMap.put("HNL", "honduras");
        currencyCountryMap.put("HKD", "hong");
        currencyCountryMap.put("HUF", "hungary");
        currencyCountryMap.put("INR", "india");
        currencyCountryMap.put("ILS", "israel");
        currencyCountryMap.put("JMD", "jamaica");
        currencyCountryMap.put("JPY", "japan");
        currencyCountryMap.put("KWD", "kuwait");
        currencyCountryMap.put("MXN", "mexico");
        currencyCountryMap.put("NZD", "newzealand");
        currencyCountryMap.put("NIO", "nicaragua");
        currencyCountryMap.put("NOK", "norway");
        currencyCountryMap.put("PAB", "panama");
        currencyCountryMap.put("PYG", "paraguay");
        currencyCountryMap.put("PEN", "peru");
        currencyCountryMap.put("PHP", "philippines");
        currencyCountryMap.put("RUB", "russia");
        currencyCountryMap.put("SAR", "saudiarabia");
        currencyCountryMap.put("SGD", "singapore");
        currencyCountryMap.put("ZAR", "southafrica");
        currencyCountryMap.put("KRW", "southkorea");
        currencyCountryMap.put("SEK", "sweden");
        currencyCountryMap.put("CHF", "switzerland");
        currencyCountryMap.put("TWD", "taiwan");
        currencyCountryMap.put("TRY", "turkey");
        currencyCountryMap.put("AED", "unitedarabemirates");
        currencyCountryMap.put("GBP", "unitedkingdom");
        currencyCountryMap.put("USD", "unitedstates");
        currencyCountryMap.put("UYU", "uruguay");
        currencyCountryMap.put("VES", "venezuela");
        return currencyCountryMap.get(currency);
    }

    public static List<String> addCurrencies() {
        return Arrays.asList("AED", "ARS", "AUD", "BRL", "BSD", "CAD", "CHF", "CNY", "EUR", "GBP",
                "GTQ", "GYD", "HKD", "HNL", "HUF", "ILS", "INR", "JPY", "JMD", "KRW",
                "KWD", "MXN", "NIO", "NOK", "NZD", "PAB", "PEN", "PHP", "PYG", "RUB",
                "SAR", "SEK", "SGD", "TRY", "TWD", "USD", "UYU", "VES", "ZAR");
    }
}
