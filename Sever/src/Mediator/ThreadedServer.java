package Mediator;

import Model.*;
import com.google.gson.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadedServer implements Runnable, PropertyChangeListener {
  private Thread s1;
  private ServerSocket welcomeSocket;
  private Socket currentSocket;
  private PrintWriter out;
  private BufferedReader in;
  private ServerMaster serverMaster;
  private boolean working;
  private Gson gson;
  private User thisUser;
  private EventListModel model;

  public ThreadedServer(ServerSocket welcomeSocket, ServerMaster serverMaster,
      EventListModel model) {
    this.model = model;
    this.welcomeSocket = welcomeSocket;
    this.serverMaster = serverMaster;
    working = true;
    currentSocket = null;
    out = null;
    in = null;
    gson = new Gson();
  }

  @Override public void run() {
    try {
      System.out.println(
          "→Server ip: " + InetAddress.getLocalHost().getHostAddress());
      System.out.println("→Waiting for a client...");
      currentSocket = welcomeSocket.accept();
      s1 = new Thread(new ThreadedServer(welcomeSocket, serverMaster, model));
      s1.start();
      System.out.println(
          "→Found a client... connecting to " + currentSocket.getInetAddress()
              .getHostAddress());
      int currentThreadNum = Thread.activeCount() - 3;
      System.out.println(
          "\t\tStarting a listening thread number " + (currentThreadNum + 1)
              + "...");
      in = new BufferedReader(
          new InputStreamReader(currentSocket.getInputStream()));
      out = new PrintWriter(currentSocket.getOutputStream(), true);
      out.println("->->->Connected with " + currentSocket.getInetAddress()
          .getHostAddress());
      while (working) {
        handleRequest(in.readLine().split(";"));
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void handleRequest(String[] reqSplit) throws IOException,
      SQLException {
    switch (reqSplit[0].toUpperCase()) {
      case "INITCALL" -> {
        serverMaster.privateAnswer(this, "initCallReply", "Server");
      }
      case "GETEVENTLIST" -> {
        EventListPackage eventListPackage = new EventListPackage();
        eventListPackage.addEnMasse(model.getAllEvents());
        serverMaster.privateAnswer(this,
            gson.toJson(eventListPackage),
            "AllEvents");
      }
      case "GETEVENT" -> {
        if (model.getEvent(reqSplit[1])!=null){
          serverMaster.privateAnswer(this, gson.toJson(new EventInformationPackage(model.getEvent(reqSplit[1]))), "GetEvent");
        }
        else {
          serverMaster.privateAnswer(this, "Error, no such event exists_;_true", "Notification");
        }
      }
      case "SIGNUPTOEVENT" -> {
        ArrayList<Object> ans = model.addParticipant(reqSplit[1], thisUser);
        serverMaster.privateAnswer(this, ans.get(0) + "_;_" + ans.get(1), "Notification");
      }
      case "LOG" -> {
        if (UserListSingleton.getInstance().getUserList().getUserByUsername(reqSplit[1])!=null){
          User potentialUser = UserListSingleton.getInstance().getUserList().getUserByUsername(reqSplit[1]);
          if (reqSplit[2].equals(potentialUser.getPassword())){
            thisUser = potentialUser;
            serverMaster.privateAnswer(this, "pog;"+thisUser.getClass()+";"+gson.toJson(thisUser), "Login");
            System.out.println(thisUser.getClass());
          }
          else {
            serverMaster.privateAnswer(this, "Incorrect password.", "Login");
          }
        }
        else {
          serverMaster.privateAnswer(this, "No such user exists.", "Login");
        }
        serverMaster.addListener(thisUser, this);
      }
      case "REGISTER" -> {
        if (UserListSingleton.getInstance().getUserList().getUserByUsername(reqSplit[1])==null){
          User tempUser = new User(reqSplit[1], reqSplit[2], reqSplit[3]);
          UserListSingleton.getInstance().getUserList().addUser(tempUser);
          model.registerUser(tempUser);
          serverMaster.privateAnswer(this, "pog", "Register");
          serverMaster.privateAnswer(this, "Account successfully created!_;_false", "Notification");
        }
        else {
          serverMaster.privateAnswer(this, "User already exists.", "Register");
        }
      }
      case "CONFIRMPARTICIPATION" -> {model.checkIn(reqSplit[1], gson.fromJson(reqSplit[2], User.class));}
      case "ADDOPPONENT" -> {
        switch (reqSplit[1].toUpperCase()){
          case "LOWER" -> model.addOpponent(thisUser, 1000, 0);
          case "SAME" -> model.addOpponent(thisUser, 500, 500);
          case "HIGHER" -> model.addOpponent(thisUser, 0, 1000);
          case "ANY" -> model.addOpponent(thisUser, 9999, 9999);
        }
      }
//      case "ADDEVENT" -> {model.addEvent(gson.fromJson(reqSplit[1], EventInformationPackage.class).convertToEvent());}
//      case "STARTVOTING" -> {model.checkIn(reqSplit[1], gson.fromJson(reqSplit[2], User.class));}
      case "^Q" -> {
        working = false;
        currentSocket.close();
      }
      case "PUSHNOTIFICATION" -> {serverMaster.broadcast(reqSplit[1] + "_;_" + reqSplit[2], "Notification");}
      default -> {
        serverMaster.privateAnswer(this, "Error01: unknown server request",
            "->System");
      }
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    out.println(evt.getOldValue() + ": " + evt.getNewValue());
  }
}
