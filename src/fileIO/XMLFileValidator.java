package fileIO;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XMLFileValidator implements FileValidator {

	/**
	 * @param file:
	 *            File to be checked for XML validation.
	 * @effects If the file fits the schema provided in "cezmi.xsd", it returns
	 *          "valid" string. Otherwise it returns an error as a string.
	 **/
	public String isValid(File file) {
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File("xmls/hadicezmi.xsd"));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(file));
		} catch (IOException e) {
			return "Exception: " + e.getMessage();
		} catch (SAXException e1) {
			return "SAX Exception: " + e1.getMessage();

		}

		return "valid";
	}

}
