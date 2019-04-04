package nameInverter;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class nameInverterTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void givenNull_returns_EmptyString() throws Exception {
		assertInverted(null, "");
	}

	@Test
	public void givenEmptyString_returnEmptyString() throws Exception {
		assertInverted("", "");
	}

	@Test
	public void givenSimpleName_returnSimpleName() throws Exception {
		assertInverted("Name", "Name");
	}

	@Test
	public void givenSimpleNameWithSpaces_returnSimpleNameWithoutSpaces() throws Exception {
		assertInverted(" Name ", "Name");
	}

	@Test
	public void givenFirstLast_returnLastFirst() throws Exception {
		assertInverted("First Last", "Last, First");
	}

	@Test
	public void givenFirstLastwithExtraSpaces_returnLastFirst() throws Exception {
		assertInverted("  First  Last   ", "Last, First");
	}

	@Test
	public void ignoreHonorific() throws Exception {
		assertInverted("Mr. First Last", "Last, First");
		assertInverted("Mrs. First Last", "Last, First");
	}

	@Test
	public void postNominals_stayAtEnd() throws Exception {
		assertInverted("First Last Sr.", "Last, First Sr.");
		assertInverted("First Last BS. Phd.", "Last, First BS. Phd.");
	}

	@Test
	public void integration() throws Exception {
		assertInverted("   Robert     Martin    III   esq.     ", "Martin, Robert III esq.");
	}

	private String invertName(String name) {
		// TODO Auto-generated method stub
		if (name == null || name.length() <= 0)
			return "";
		else
			return formatName(removeHonorifics(splitNames(name)));
	}

	private String formatName(List<String> names) {
		if (names.size() == 1) 
			return names.get(0);
		else 
			return formatMultiElementName(names);
		
	}

	private String formatMultiElementName(List<String> names) {
		String postNominal = getPostNominals(names);
		String firstName = names.get(0);
		String lastName = names.get(1);
		String formattedName = String.format("%s, %s %s", lastName, firstName, postNominal).trim();
		return formattedName;
	}

	private String getPostNominals(List<String> names) {
		String postNominalString = "";
		if (names.size() > 2) {
			List<String> postNominals = names.subList(2, names.size());
			for (String pn : postNominals)
				postNominalString += pn + " ";
		}
		return postNominalString;
	}

	private List<String> removeHonorifics(List<String> names) {
		if (names.size() > 1 && isHonorific(names.get(0)))
			names.remove(0);
		return names;
	}
	
	private void assertInverted(String originalName, String invertedName) {
		assertEquals(invertedName, invertName(originalName));
	}

	private boolean isHonorific(String word) {
		return word.matches("Mr\\.|Mrs\\.");
	}

	private ArrayList<String> splitNames(String name) {
		return new ArrayList<String>(Arrays.asList(name.trim().split("\\s+")));
	}

}
