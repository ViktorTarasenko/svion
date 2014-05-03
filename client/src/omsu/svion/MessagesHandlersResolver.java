package omsu.svion;

import omsu.svion.game.handler.MessageFromServerHandler;
import omsu.svion.game.states.State;
import omsu.svion.messages.MessageFromServer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by victor on 03.05.14.
 */
public  class MessagesHandlersResolver {
    private static Map<Class<? extends MessageFromServer>,MessageFromServerHandler[]> handlers = new ConcurrentHashMap<Class<? extends MessageFromServer>, MessageFromServerHandler[]>();

    public static Map<Class<? extends MessageFromServer>, MessageFromServerHandler[]> getHandlers() {
        return handlers;
    }
}
