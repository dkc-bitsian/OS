package batchprocessor;
/** CS 5338.001 Operating Systems Concepts
 *  Krishna Chaitanya Reddy Dodda
*/

import java.io.File;
import java.io.FileInputStream;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *This class is used to build the batch
 *The batch is built by calling the object of the batch
 *The batch is built by setting its working directory and list of commands to be executed
 *The batchparser class is responsible for this function
 */
public class BatchParser 
{
	//method to build the batch
	public Batch buildBatch(File batchFile) 
	{
		
		Batch batch = new Batch();
		try {
			System.out.println("Opening " + batchFile);
			FileInputStream fis = new FileInputStream(batchFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fis);
			Element pnode = doc.getDocumentElement();
			NodeList nodes = pnode.getChildNodes();
			for (int idx = 0; idx < nodes.getLength(); idx++) {
				Node node = nodes.item(idx);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					Command command = buildCommand(elem);
					//if command.id is wd, then set batch.wordir(path)
					if("wd".equalsIgnoreCase(elem.getNodeName()))
					{
						batch.setWorkingDir(command.comm_path);
					}
					batch.addCommand(command);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Parsing is complete and the batch is built");
		System.out.println("##########################################");

		return batch;
	}
	
	// method to parse the input xml and return the  command to be added into the batch
	private Command buildCommand(Element elem) throws DOMException, ProcessException
	{
		String cmdName = elem.getNodeName();
		Command cmd = null;
		if (cmdName == null) 
		{
			throw new ProcessException("unable to parse command from "+ elem.getTextContent());
		} 
		else if ("wd".equalsIgnoreCase(cmdName)) 
		{
			System.out.println("#####################");
			System.out.println("Parsing wd");
			 cmd = new WDCommand();
			 cmd.parse(elem);
			
		} 
		else if ("file".equalsIgnoreCase(cmdName)) 
		{
			System.out.println("#####################");
			System.out.println("Parsing file");
			cmd = new FileCommand();
			cmd.parse(elem);
		} 
		else if ("cmd".equalsIgnoreCase(cmdName)) 
		{
			System.out.println("#####################");
			System.out.println("Parsing cmd");
			cmd = new CmdCommand();
			cmd.parse(elem);
		} 
		else if ("pipe".equalsIgnoreCase(cmdName)) 
		{
			System.out.println("#####################");
			System.out.println("Parsing pipe");
			cmd = new PipeCommand();
			cmd.parse(elem);
		} 
		else 
		{
			throw new ProcessException("Unknown command " + cmdName + " from: "	+ elem.getBaseURI());
		}
		
		return cmd;
		
	}
	
}
