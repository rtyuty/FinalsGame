import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.Color;

public class Game extends Canvas implements Runnable{

	private boolean isRunning = false;
	private Thread thread;

	public Game(){
		new Window (1000,563,"TopDownGame", this);
		start();
	}
	public void start(){
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	public void stop(){
		isRunning = false;
		try {
			thread.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime= now;
			while(delta >=1){
				tick();
				//updates++;
				delta--;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				frames = 0;
				//updates = 0;
			}
		}
		stop();
	}
	public void tick(){

	}
	public void render(){
		BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
        createBufferStrategy(3);
        return;
    }
    Graphics g = bs.getDrawGraphics();

    g.setColor(new Color(0,0,0)); 
    g.fillRect(0, 0, 1000, 563);

    g.dispose();
    bs.show();
	}
	public static void main(String args[]){
		new Game();
	}

}