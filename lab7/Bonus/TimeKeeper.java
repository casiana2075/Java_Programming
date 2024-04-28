package org.example;

public class TimeKeeper implements Runnable {
    private final Game game;
    private final long startTime;
    private final long timeLimit;

    public TimeKeeper(Game game, long timeLimit) {
        this.game = game;
        this.startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
    }

    @Override
    public void run() {
        while (true) {
            long runningTime = System.currentTimeMillis() - startTime;
            if (runningTime > timeLimit) {
                game.stopGame();
                System.out.println("Time limit exceeded! Stopping the game...");
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Game has been running for " + runningTime / 1000 + " seconds.");
        }
    }
}
