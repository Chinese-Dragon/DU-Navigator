package edu.drury.mcs.Dnav.JavaClass;

/**
 * Created by mark93 on 2/28/2016.
 */

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class XMLController {

    private String xmlInputFile;
    private Document xml;
    private OutputStream xmlOutputFile;
    private Context context;

    //path for the schema file that goes with the schedule xml
    private static final String SCHEMA_PATH ="/schedules.xsd";

    public XMLController(Context context)
    {
        this.context = context;


    }

    /**
     Method used to get a whole list of every Schedule in the file.
     **/
    public List<Schedule> getSchedules()
    {
        ArrayList<Schedule> result = new ArrayList<Schedule>();

        retrieveXML();

        NodeList list = xml.getElementsByTagName("schedule");

        for(int i=0; i<list.getLength(); i++)
        {
            String id = list.item(i).getAttributes().getNamedItem("scheduleId").getNodeValue();
            System.out.println(id);
            Node schedName = getChildByName(list.item(i), "name");
            Node nameNode = schedName.getFirstChild();
            String name = "";
            if(nameNode != null)
                name = nameNode.getNodeValue();
            System.out.println(schedName.getNodeName());
            System.out.println(name);

            Schedule sched =  new Schedule(id, name);

            NodeList courseList = getChildByName(list.item(i), "courseList").getChildNodes();

            for(int n=0; n<courseList.getLength(); n++)
            {
                if(courseList.item(n).getNodeName().equals("course"))
                {
                    Node course = courseList.item(n);

                    String courseID = course.getAttributes().getNamedItem("courseId").getNodeValue();

                    String courseName = "";
                    String courseCode = "";
                    String location = "";
                    String startTime = "";
                    String endTime = "";
                    String days = "";
                    String roomNum = "";
                    String prof = "";

                    Node courseNameNode = getChildByName(course, "name");
                    Node courseCodeNode = getChildByName(course, "courseCode");
                    Node locationNode = getChildByName(course, "location");
                    Node startTimeNode = getChildByName(course, "startTime");
                    Node endTimeNode = getChildByName(course, "endTime");
                    Node daysNode = getChildByName(course, "days");
                    Node roomNumNode = getChildByName(course, "roomNum");
                    Node profNode = getChildByName(course, "prof");

                    if(courseNameNode.getFirstChild() != null)
                        courseName = courseNameNode.getFirstChild().getNodeValue();
                    if(courseCodeNode.getFirstChild() != null)
                        courseCode = courseCodeNode.getFirstChild().getNodeValue();
                    if(locationNode.getFirstChild() != null)
                        location = locationNode.getFirstChild().getNodeValue();
                    if(startTimeNode.getFirstChild() != null)
                        startTime = startTimeNode.getFirstChild().getNodeValue();
                    if(endTimeNode.getFirstChild() != null)
                        endTime = endTimeNode.getFirstChild().getNodeValue();
                    if(daysNode.getFirstChild() != null)
                        days = daysNode.getFirstChild().getNodeValue();
                    if(roomNumNode.getFirstChild() != null)
                        roomNum = roomNumNode.getFirstChild().getNodeValue();
                    if(profNode.getFirstChild() != null)
                        prof = profNode.getFirstChild().getNodeValue();

                    System.out.println(courseID);
                    System.out.println(courseName);
                    System.out.println(courseCode);
                    System.out.println(location);
                    System.out.println(startTime);
                    System.out.println(endTime);
                    System.out.println(days);
                    System.out.println(roomNum);
                    System.out.println(prof);

                    Course c = new Course(courseID, courseName, courseCode, location, days, startTime, endTime, roomNum, prof);

                    sched.addCourse(c);
                }

            }

            result.add(sched);

        }

        return result;
    }

    /**
     Method used to add a Schedule to the xml file
     **/
    public void addSchedule(Schedule sched)
    {
        retrieveXML();

        Element newSched = xml.createElement("schedule");
        Element schedName = xml.createElement("name");
        Element courseListTag = xml.createElement("courseList");


        newSched.setAttribute("scheduleId", sched.getID());
        schedName.setTextContent(sched.getName());
        newSched.appendChild(schedName);

        List<Course> courseList = sched.getCourses();

        for(int i = 0; i<courseList.size(); i++)
        {
            Element course = xml.createElement("course");
            course.setAttribute("courseId", courseList.get(i).getID());

            Element courseName = xml.createElement("name");
            Element courseCode = xml.createElement("courseCode");
            Element startTime = xml.createElement("startTime");
            Element endTime = xml.createElement("endTime");
            Element location = xml.createElement("location");
            Element roomNum = xml.createElement("roomNum");
            Element days = xml.createElement("days");
            Element prof = xml.createElement("prof");

            courseName.setTextContent(courseList.get(i).getName());
            courseCode.setTextContent(courseList.get(i).getCode());
            startTime.setTextContent(courseList.get(i).getBeginTime());
            endTime.setTextContent(courseList.get(i).getEndTime());
            location.setTextContent(courseList.get(i).getLocation());
            roomNum.setTextContent(courseList.get(i).getRoomNum());
            days.setTextContent(courseList.get(i).getDays());
            prof.setTextContent(courseList.get(i).getProf());

            course.appendChild(courseName);
            course.appendChild(courseCode);
            course.appendChild(startTime);
            course.appendChild(endTime);
            course.appendChild(location);
            course.appendChild(roomNum);
            course.appendChild(days);
            course.appendChild(prof);

            courseListTag.appendChild(course);

        }

        newSched.appendChild(courseListTag);

        xml.getDocumentElement().appendChild(newSched);

        updateXML();


    }

    /**
     Method used to remove a Schedule from the xml file
     **/
    public void deleteSchedule(Schedule sched)
    {
        String id = sched.getID();

        retrieveXML();

        NodeList list = xml.getElementsByTagName("schedule");
        int target = -1;
        for(int i = 0; i<list.getLength(); i++)
        {
            String attrValue = list.item(i).getAttributes().getNamedItem("scheduleId").getNodeValue();
            if(attrValue.equals(id))
            {
                target=i;
            }
        }

        if(target!=-1)
        {
            list.item(target).getParentNode().removeChild(list.item(target));
        }

        updateXML();

    }

    /**
     Method used to update a Schedule from the xml file with new information
     **/
    public void editSchedule(Schedule sched)
    {
        String targetID = sched.getID();

        retrieveXML();

        NodeList list = xml.getElementsByTagName("schedule");
        int target = -1;
        for(int i = 0; i<list.getLength(); i++)
        {
            String attrValue = list.item(i).getAttributes().getNamedItem("scheduleId").getNodeValue();
            if(attrValue.equals(targetID))
            {
                target=i;
            }
        }

        Element newSched = xml.createElement("schedule");
        Element schedName = xml.createElement("name");
        Element courseListTag = xml.createElement("courseList");

        newSched.setAttribute("scheduleId", sched.getID());
        schedName.setTextContent(sched.getName());
        newSched.appendChild(schedName);

        List<Course> courseList = sched.getCourses();

        for(int i = 0; i<courseList.size(); i++)
        {
            Element course = xml.createElement("course");
            course.setAttribute("courseId", courseList.get(i).getID());

            Element courseName = xml.createElement("name");
            Element courseCode = xml.createElement("courseCode");
            Element startTime = xml.createElement("startTime");
            Element endTime = xml.createElement("endTime");
            Element location = xml.createElement("location");
            Element roomNum = xml.createElement("roomNum");
            Element days = xml.createElement("days");
            Element prof = xml.createElement("prof");

            courseName.setTextContent(courseList.get(i).getName());
            courseCode.setTextContent(courseList.get(i).getCode());
            startTime.setTextContent(courseList.get(i).getBeginTime());
            endTime.setTextContent(courseList.get(i).getEndTime());
            location.setTextContent(courseList.get(i).getLocation());
            roomNum.setTextContent(courseList.get(i).getRoomNum());
            days.setTextContent(courseList.get(i).getDays());
            prof.setTextContent(courseList.get(i).getProf());

            course.appendChild(courseName);
            course.appendChild(courseCode);
            course.appendChild(startTime);
            course.appendChild(endTime);
            course.appendChild(location);
            course.appendChild(roomNum);
            course.appendChild(days);
            course.appendChild(prof);

            courseListTag.appendChild(course);

        }

        newSched.appendChild(courseListTag);

        xml.getDocumentElement().replaceChild(newSched, list.item(target));

        updateXML();
    }

    /**
     Method used only internally. Initializes the internal representation of the xml file.
     **/
    private void retrieveXML()
    {
            try{
                xmlInputFile = context.getFilesDir() + "/schedules.xml";
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                //dbFactory.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File(context.getFilesDir()+SCHEMA_PATH)));
                dbFactory.setIgnoringElementContentWhitespace(true);
                DocumentBuilder db = dbFactory.newDocumentBuilder();
                File xmlFile = new File(xmlInputFile);
                if(xmlFile.exists()) {
                    this.xml = db.parse(xmlFile);
                }
                else
                {
                    xmlFile.createNewFile();
                    PrintWriter pw = new PrintWriter(xmlFile);
                    pw.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<schedules xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"></schedules>");
                    pw.close();
                    this.xml = db.parse(xmlFile);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }

    /**
     Method used only internally. Updates the actual xml file by overwriting it with
     the current iteration of the internal DOM representation of the xml file
     **/
    private void updateXML()
    {
        try{
            xmlOutputFile =context.openFileOutput("schedules.xml", Context.MODE_PRIVATE);
            Transformer tran = TransformerFactory.newInstance().newTransformer();
            tran.setOutputProperty(OutputKeys.INDENT, "yes");
            tran.setOutputProperty(OutputKeys.METHOD, "xml");
            tran.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tran.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            tran.transform(new DOMSource(xml), new StreamResult(xmlOutputFile));

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private Node getChildByName(Node parent, String name)
    {
        NodeList list = parent.getChildNodes();

        Node target = null;

        for(int i=0; i<list.getLength(); i++)
        {
            if(list.item(i).getNodeName().equals(name))
                target = list.item(i);
        }

        return target;
    }

}
