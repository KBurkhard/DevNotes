package org.broadinstitute.mpg.dcc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mduby on 1/24/18.
 */
public class DccXmlParser extends DefaultHandler {

    private String temp = null;
    private List<String> metaboliteList = new ArrayList<String>();
    private MetaboliteBean metaboliteBean;
    private List<MetaboliteBean> metaboliteBeanList = new ArrayList<MetaboliteBean>();

    // xml tags
    private String METABOLITE = "metabolite";
    private String ACCESSION = "accession";
    private String KEGG_ID = "kegg_id";
    private String DIRECT_PARENT = "direct_parent";
    private String DESCRIPTION = "description";
    private String SUPER_CLASS = "super_class";
    private String CLASS = "class";
    private String MOLECULAR_FRAMEWORK = "molecular_framework";


    /** The main method sets things up for parsing */
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        //Create a "parser factory" for creating SAX parsers
        SAXParserFactory spfac = SAXParserFactory.newInstance();

        //Now use the parser factory to create a SAXParser object
        SAXParser sp = spfac.newSAXParser();

        //Create an instance of this class; it defines all the handler methods
        DccXmlParser handler = new DccXmlParser();

        //Finally, tell the parser to parse the input and notify the handler
        sp.parse(new File("/Users/mduby/Google Drive/Broad/Documents/Work/Broad/Projects/20180109metabolitesXml/Data/hmdb_metabolites.xml"),handler);
//        sp.parse(new File("/Users/mduby/Google Drive/Broad/Documents/Work/Broad/Projects/20180109metabolitesXml/Data/testMetabolite.xml"),handler);

        handler.readList();

    }

    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }


    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase(this.METABOLITE)) {
            this.metaboliteBean = new MetaboliteBean();
        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase(this.KEGG_ID)) {
            // add it to the list
            metaboliteList.add(temp);
            this.metaboliteBean.setKeggId(temp);

        } else if (qName.equalsIgnoreCase(this.METABOLITE)) {
            this.metaboliteBeanList.add(this.metaboliteBean);

        } else if (qName.equalsIgnoreCase(this.DESCRIPTION)) {
            this.metaboliteBean.setDescription(temp);

        } else if (qName.equalsIgnoreCase(this.ACCESSION)) {
            this.metaboliteBean.getAccesionList().add(temp);

        } else if (qName.equalsIgnoreCase(this.DIRECT_PARENT)) {
            this.metaboliteBean.setDirectParent(temp);

        } else if (qName.equalsIgnoreCase(this.SUPER_CLASS)) {
            this.metaboliteBean.setSuperClass(temp);

        } else if (qName.equalsIgnoreCase(this.CLASS)) {
            this.metaboliteBean.setClasses(temp);

        } else if (qName.equalsIgnoreCase(this.MOLECULAR_FRAMEWORK)) {
            this.metaboliteBean.setMolecularFramework(temp);
        }

    }

    private void readList() {
        System.out.println("No of  the metabolites in bank '" + metaboliteList.size()  + "'.");
//        Iterator<String> it = metaboliteList.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next().toString());
//        }

        // create the CSV file
        Date now = new Date();
        String fileOutPath = "/Users/mduby/Google Drive/Broad/Documents/Work/Broad/Projects/20180109metabolitesXml/Data/Out/" + now.getTime() + "metabolite.txt";
        String header = "accension\tdescription\tdirect_parent\tsuper_class\tclass\tmolecular_framework\tkegg_id\n";

        // create the print writer
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(fileOutPath));

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }

        // loop through and write the file
        pw.write(header);
        for (MetaboliteBean bean : metaboliteBeanList) {
            List<String> stringList = this.createCsvLines(bean);
            for (String line : stringList) {
                pw.write(line);
            }
        }
        pw.close();

        // indicate when donw
        System.out.println("Done!!!!");
    }

    private List<String> createCsvLines(MetaboliteBean metaboliteBean) {
        List<String> stringList = new ArrayList<String>();
        StringBuffer stringBuffer = null;

        for (String accesion : metaboliteBean.getAccesionList()) {
            stringBuffer = new StringBuffer();
            stringBuffer.append(accesion);
            stringBuffer.append("\t");
            stringBuffer.append(metaboliteBean.getDescription());
            stringBuffer.append("\t");
            stringBuffer.append(metaboliteBean.getDirectParent());
            stringBuffer.append("\t");
            stringBuffer.append(metaboliteBean.getSuperClass());
            stringBuffer.append("\t");
            stringBuffer.append(metaboliteBean.getClasses());
            stringBuffer.append("\t");
            stringBuffer.append(metaboliteBean.getMolecularFramework());
            stringBuffer.append("\t");
            stringBuffer.append(metaboliteBean.getKeggId());
            stringBuffer.append("\n");

            // add the string
            stringList.add(stringBuffer.toString());
        }

        // return
        return stringList;
    }

}

class MetaboliteBean {
    private List<String> accesionList = new ArrayList<>();
    private String keggId;
    private String superClass;
    private String classes;
    private String molecularFramework;
    private String description;
    private String directParent;

    public List<String> getAccesionList() {
        return accesionList;
    }

    public void setAccesionList(List<String> accesionList) {
        this.accesionList = accesionList;
    }

    public String getKeggId() {
        return keggId;
    }

    public void setKeggId(String keggId) {
        this.keggId = keggId;
    }

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getMolecularFramework() {
        return molecularFramework;
    }

    public void setMolecularFramework(String molecularFramework) {
        this.molecularFramework = molecularFramework;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDirectParent() {
        return directParent;
    }

    public void setDirectParent(String directParent) {
        this.directParent = directParent;
    }
}