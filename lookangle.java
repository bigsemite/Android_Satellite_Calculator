import javax.microedition.midlet.MIDlet;
//import java.lang.Math;
import java.lang.Math;
import java.lang.String;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import javax.wireless.messaging.*;

public class lookangle extends MIDlet implements CommandListener{
	//declaring variables
	private TextBox t1 = new TextBox("Bigsemite","",5000,0);
	private TextBox abt = new TextBox("About Us","This package is designed by: Semiu Amao of Engineering dept., Vodacom Business Africa, Nigeria.\n Contact us: www.vodacom.com\nEmail us: semiu.amao@vodacom.com",5000,TextField.UNEDITABLE);
	private List l =new List("Satellite Calculator ",List.IMPLICIT);
	private Command cm = new Command("Exit",Command.EXIT,0);
	private Command chos = new Command("Select",Command.OK,1);
	private Display d;
//cab fields	
	private Form f = new Form("CAB Calculator");
	private TextField dataRate = new TextField("Data Rate(Mb/s)", "",10, TextField.DECIMAL);
	private TextField modIndex = new TextField("Mod. Index", "",10, TextField.DECIMAL);
	private TextField fecRate =new TextField("FEC Rate", "",10, TextField.DECIMAL);
	private TextField rollI = new TextField("Roll-Off", "",10, TextField.DECIMAL);
	private Command solveCab = new Command("Solve", Command.OK,1);
	private Command bak = new Command("Back",Command.BACK,0);
//look angele fields
	private Form fl = new Form("Look Angle Calc");
	private TextField sLon = new TextField("Sat-Long(o E)", "",10,TextField.DECIMAL);
	private TextField lat = new TextField("Site-Lat(o N)","",10,TextField.DECIMAL);
	private TextField lon = new TextField("Site_Long(o E)","",10,TextField.DECIMAL);
	private Command solveLook = new Command("Solve",Command.OK,1);

	// Watt calculator fields
	private TextField watt = new TextField("Enter value in Watt","",10, TextField.DECIMAL);
	Form fw = new Form("Watt-dBm converter");
	Command solvewatt = new Command("Solve",Command.OK,1);
	
	// text messaging
	MessageConnection conn;
	TextField addr = new TextField("Phone Number","",20,TextField.PHONENUMBER);
	Command sendit = new Command("Send",Command.SCREEN,1);
	Form rep = new Form("Report");
	Command callReport = new Command("Send Report", Command.SCREEN,1);
	
	//Watt to dBm calculator
	
	public lookangle() {
		// TODO Auto-generated constructor stub
//Home screen
	l.addCommand(cm);
	l.append("Look Angle", null);
	l.append("Allocated Bandwidth", null);
	l.append("Watt-dBm Calculator", null);
	l.append("AboutUs",null);
	l.setSelectCommand(chos);
	l.setCommandListener(this);
	d = Display.getDisplay(this);
	
	//Setting the Form for CAB
	f.append(dataRate);
	f.append(fecRate);
	f.append(modIndex);
	f.append(rollI);
	f.addCommand(solveCab);
	f.addCommand(bak);
	f.setCommandListener(this);
	
	//setting the form for Look Angle
	fl.append(sLon);
	fl.append(lat);
	fl.append(lon);
	fl.addCommand(solveLook);
	fl.addCommand(bak);
	fl.setCommandListener(this);
	
	//watt-dBm form
	fw.append(watt);
	fw.addCommand(solvewatt);
	fw.addCommand(bak);
	fw.setCommandListener(this);
	
	//Setting the result file
	t1.addCommand(callReport);
	t1.addCommand(bak);
	t1.setCommandListener(this);
	
	
	//About Us
	abt.addCommand(bak);
	
	abt.setCommandListener(this);

	//Report
	rep.append(addr);
	rep.addCommand(sendit);
	rep.addCommand(bak);
	rep.setCommandListener(this);
	
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
d.setCurrent(l);
	}

	public void commandAction(Command arg0, Displayable arg1) {
		// TODO Auto-generated method stub
		if (arg0==cm){
			try
			{
				destroyApp(true);
			}catch(MIDletStateChangeException e)
			{
				e.printStackTrace();
				
			}
			finally{
				this.notifyDestroyed();
			}
		}
		else if(arg0==chos)
		{
			if (l.isSelected(3))
			{
				about();
				return;
				
			}
			if (l.isSelected(2))
			{
				wattC();
				return;
				
			}
			if(l.isSelected(1))
				{
				cab();
				return;
				}
			if(l.isSelected(0))
			{
				lookAngle();
				return;
			}
			
			
		}
		else if(arg0==solveCab)
		{
			
			if ((dataRate.getString().length()==0) || (fecRate.getString().length()==0) || (rollI.getString().length()==0)||(modIndex.getString().length()==0))
			{
				Alert al =null;
				al= new Alert("Missing Value");
				al.setString("Enter Values in the fields");
				al.setType(AlertType.ALARM);
				d.setCurrent(al);
				/*
				t1.setString(t1.getString() + "\n Data Rate = " + dataRate.getString()+"Mb/s");
				t1.setString(t1.getString() + "\n FEC Rate= " +fecRate.getString());
				t1.setString(t1.getString()+ "\n Roll-Off Index = "+rollI.getString());
				t1.setString(t1.getString() + "\n Modulation Index = " + modIndex.getString());
				t1.setString(t1.getString() + "\n CAB cannot be calculated with 0 value");
				
				d.setCurrent(t1);
				*/
				return;
			}
			double dat = Double.parseDouble(dataRate.getString());
			double fec = Double.parseDouble(fecRate.getString());
			double ri = Double.parseDouble(rollI.getString());
			double mi = Double.parseDouble(modIndex.getString());
			double cabAnswer = (dat * ri)/(mi*fec);
			
			String s =String.valueOf(cabAnswer);
			s= (s.length()>6? s.substring(0,6):s);
			//output
			t1.setString(t1.getString() +"\nC. A. B.\n*********");
			t1.setString(t1.getString() + "\n Data Rate = " + dataRate.getString()+"Mb/s");
			t1.setString(t1.getString() + "\n FEC Rate= " +fecRate.getString());
			t1.setString(t1.getString()+ "\n Roll-Off Index = "+rollI.getString());
			t1.setString(t1.getString() + "\n Modulation Index = " + modIndex.getString());
			t1.setString(t1.getString()+ "\n CAB = " +  s+"Mb/s");
			
			d.setCurrent(t1);
				
		}
		else if (arg0==bak)
		{
			d.setCurrent(l);
		}
		
		else if(arg0==solveLook)
		{
			if((sLon.getString().length()==0)||(lat.getString().length()==0)||(lon.getString().length()==0))
			{
				Alert al =null; 
				al= new Alert("Missing Value");
				al.setString("Enter Values in the fields");
				al.setType(AlertType.ALARM);
				d.setCurrent(al);
				/*
				t1.setString(t1.getString() + "\n Sat. Longitude = " + sLon.getString()+"E");
				t1.setString(t1.getString() +"\n Site Latitude =" + lat.getString()+"N");
				t1.setString(t1.getString() + "\n Site Longitude = " + lon.getString()+"E");
				d.setCurrent(t1);
				*/
				return;
			}
			double satLong = Double.parseDouble(sLon.getString());
			double siteLat = Math.toRadians(Double.parseDouble(lat.getString()));
			double siteLon = Double.parseDouble(lon.getString());
			
			//Calculating Elevation angle
			double G = Math.toRadians(satLong - siteLon);
			double temp1 = (Math.cos(G)* Math.cos(siteLat)) -0.1512;
			double temp2 = (Math.cos(G)*Math.cos(siteLat))*(Math.cos(G)*Math.cos(siteLat));
			double temp = temp1/Math.sqrt(1-temp2);
			double Elans =Math.toDegrees(mMath.atan(temp));
			
			//Calculating Azimuth angle
			temp1 = Math.tan(G)/Math.sin(siteLat);
			temp = mMath.atan(temp1*(-1));
			double az = Math.toDegrees(temp) +180;
			
			String azimuth = String.valueOf(az);
			azimuth = (azimuth.length()>5?azimuth.substring(0,5):azimuth);
			String Elevation = String.valueOf(Elans);
			Elevation = (Elevation.length()>5?Elevation.substring(0,5): Elevation);
			//Output
			t1.setString(t1.getString() + "\nLook Angle\n***********");
			t1.setString(t1.getString() + "\n Sat. Longitude = " + sLon.getString()+"E");
			t1.setString(t1.getString() +"\n Site Latitude =" + lat.getString()+"N");
			t1.setString(t1.getString() + "\n Site Longitude = " + lon.getString()+"E");
			t1.setString(t1.getString() +" \n Elevation = " + Elevation+"deg");
			t1.setString(t1.getString() +" \n Azimuth = " + azimuth+"deg");
			d.setCurrent(t1);
			
		}
		//watt converter
		else if(arg0==solvewatt){
			if (watt.getString().length()==0){
				Alert al =null; 
				al= new Alert("Missing Value");
				al.setString("Enter Values in the fields");
				al.setType(AlertType.ALARM);
				d.setCurrent(al);
				return;
			}
			double wt = Double.parseDouble(watt.getString());
			//double dbw = 10 * Math.log10(wt);
			
			
			t1.setString(t1.getString() + "\n" + "Power meter");
			t1.setString(t1.getString() + "\n" + "***********");
			t1.setString(t1.getString() + "\n" + wt +"W = ??");
			d.setCurrent(t1);
		}
		//sending it
		else if(arg0==callReport){
			d.setCurrent(rep);
			
		}
		else if (arg0==sendit){
			if(addr.getString().length()>0){
				String dest = "sms://" + addr.getString();
				String wording = t1.getString();
				Alert al = null;
				al = new Alert("Info");
				al.setString(wording +" to: " + dest);
				al.setType(AlertType.ALARM);
				report r = new report(dest, wording);
				d.setCurrent(t1);
				
			}
			else{
				Alert al = null;
				al = new Alert("Info");
				al.setString("Enter phone number");
				al.setType(AlertType.ALARM);
			}
		}
	}
	
	public void cab(){
		
		//f.setItemStateListener()
		d.setCurrent(f);
		
	}
	public void lookAngle()
	{
		d.setCurrent(fl);
	}
	public void about()
	{
		d.setCurrent(abt);
	}
	public void wattC(){
		d.setCurrent(fw);
	}

}
