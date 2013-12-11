package com.taro.backspring;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class ParserTest {

	@Rule
	public TestName name = new TestName();

	@Test
	public void testParse() throws Exception {
		Assert.assertEquals("testParse", name.getMethodName());

		System.out.println("Class name: " + this.getClass().getName());

		File f = new File("src/test/output.txt");

		Assert.assertTrue(f.exists());

		InputStream is = null;

		try {
			InputStream in = new FileInputStream(f);

			String s = IOUtils.toString(in);

			System.out.println(s);

			byte[] bytes = s.getBytes();

			is = new ByteArrayInputStream(bytes);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document document = db.parse(is);

			XPathFactory factory = XPathFactory.newInstance();

			XPath xpath = factory.newXPath();

			XPathExpression expr = xpath.compile("//row/field/text()");

			Object result = expr.evaluate(document, XPathConstants.NODESET);

			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println(nodes.item(i).getNodeValue());
			}

		} finally {
			IOUtils.closeQuietly(is);

		}
	}

}
