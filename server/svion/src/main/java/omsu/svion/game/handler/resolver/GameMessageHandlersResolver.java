package omsu.svion.game.handler.resolver;

import omsu.svion.game.handler.GameMessageHandler;
import omsu.svion.game.states.State;
import omsu.svion.messages.AbstractMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by victor on 11.04.14.
 */

@Service
public class GameMessageHandlersResolver {
    private static final Logger logger = Logger.getLogger(GameMessageHandlersResolver.class);
    private Map<AbstractMap.SimpleEntry<Class<? extends AbstractMessage>,Class<? extends State>>, GameMessageHandler> handlers = new HashMap<AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>, GameMessageHandler>();
    public List<GameMessageHandler> getHandler(Class<? extends AbstractMessage> messageClass, Class<? extends State> state){
       List<GameMessageHandler> result = new ArrayList<GameMessageHandler>(1);
        GameMessageHandler gameMessageHandler = handlers.get(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(messageClass,state));
        logger.debug("got handler "+gameMessageHandler);
        if (gameMessageHandler!= null) {
            result.add(gameMessageHandler);
        }
        Class<? extends State> s =state;
        while(State.class.isAssignableFrom(s.getSuperclass())) {
            s = (Class<? extends State>) s.getSuperclass();
            logger.debug("class is "+s);
            gameMessageHandler = handlers.get(new AbstractMap.SimpleEntry<Class<? extends AbstractMessage>, Class<? extends State>>(messageClass,s));
            logger.debug("got handler "+gameMessageHandler);
            if (gameMessageHandler != null) {
                result.add(gameMessageHandler);
            }
        }
       return result;
    }
    public Map<AbstractMap.SimpleEntry<Class<? extends AbstractMessage>,Class<? extends State>>, GameMessageHandler> getHandlers() {
        return handlers;
    }
    }


