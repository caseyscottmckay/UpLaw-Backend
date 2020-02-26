package us.uplaw.util.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import com.google.common.base.CaseFormat;

public class StringUtils {

	public static String toTitleCase(String inputString){
		String titleCase = (new ArrayList<>(Arrays.asList(inputString.toLowerCase().split(" "))))
				.stream()
				.map(word -> Character.toTitleCase(word.charAt(0)) + word.substring(1))
				.collect(Collectors.joining(" "));
		titleCase = titleCase
						.replaceAll("V\\.","v.")
						.replaceAll("vs\\.","v.")
						.replaceAll("vs\\.","v.")
						.replaceAll(" A "," a ")
						.replaceAll(" An "," an ")
						.replaceAll(" And "," and ")
						.replaceAll(" Of "," of ")
						.replaceAll(" Is "," is ")
						.replaceAll(" Or "," or ")
						.replaceAll(" On "," on ")
						.replaceAll(" To "," to ")
						.replaceAll(" For "," for ")
						.replaceAll(" From "," from ")
						.replaceAll(" Nor "," nor ")
						.replaceAll(" But "," but ")
						.replaceAll(" Yet "," yet ")
						.replaceAll(" Are "," are ")
						.replaceAll(" This "," this ")
						.replaceAll(" With "," with ")
						.replaceAll(" Without "," without ")
						.replaceAll(" The "," the ");
				String firstChar = titleCase.substring(0,1);
				firstChar=firstChar.toUpperCase();
				titleCase = firstChar+titleCase.substring(1,titleCase.length());

		return titleCase;
	}

	public static String toSnakeCase(String str){
		str = str.toLowerCase().trim().replaceAll("\\s[2,]"," ").trim();
		str = str.replaceAll("[^a-z0-9-]","_");
		str = str.replaceAll("__+","_");
		str = str.replaceAll("^_","").replaceAll("_+$","");
		str = str.trim();
		return str;
	}

	public static String toCamelCase(String inputString){
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, inputString);
	}
}
