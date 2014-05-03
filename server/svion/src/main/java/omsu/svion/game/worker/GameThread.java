package omsu.svion.game.worker;

import omsu.svion.game.Game;
import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.handler.resolver.GameMessageHandlersResolver;
import omsu.svion.messages.AbstractMessage;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by victor on 11.04.14.
 */
public class GameThread extends Thread {
    private static final Logger logger = Logger.getLogger(GameThread.class);
    private GameMessageHandlersResolver resolver;
    private Game game;
    public  GameThread(GameMessageHandlersResolver resolver,Game game) {
        this.resolver = resolver;
        this.game = game;
    }
    public void addMesssage(AbstractMessage message) {
        try {
            messages.put(message);
        } catch (InterruptedException e) {
            logger.debug("me interrupted");
            return;
        }
    }
    private BlockingQueue<AbstractMessage> messages = new LinkedBlockingQueue<AbstractMessage>();
    public void run() {
        logger.debug("game thread started");
        while (!interrupted()) {
            try {
                AbstractMessage message = messages.take();
                logger.debug("got msg "+message);
                logger.debug("hohol");
               // List<GameMessageHandler> handlers = resolver.getHandler(message,game.getState());
                //GameMessageHandler gameMessageHandler =resolver. getHandler(message,game.getState());
                logger.debug("hohol");
                logger.debug("game  state is" + game.getState());
                logger.debug("message class is "+message.getClass());
                List<GameMessageHandler> handlers = resolver.getHandler(message.getClass(),game.getState());
                logger.debug("handler is" +handlers.size());
               // logger.debug("handlers size is );
                for (GameMessageHandler handler : handlers) {
                    handler.handle(message,game);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearMessages() {
        messages.clear();
    }
    public String test() {
        return null;
    }
}
