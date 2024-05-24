package org.server;

public class Main {
    public static void main(String[] args) {
        /*GameServer server = new GameServer(5000);
        server.startServer();*/

        //bonus
        ScheduleGenerator scheduleGenerator = new ScheduleGenerator();

        int n=5;    //number of players
        int p=2;    //number of games per day for a player
        int d=3;    //max number of days

        System.out.println("The schedule for a game with "+n+" players, "+p+" games per day and "+d+" days is:");
        scheduleGenerator.generateSchedule(n,p,d);
        System.out.println();
        scheduleGenerator.findSequence(scheduleGenerator.simulateGames(5),5);
    }
}