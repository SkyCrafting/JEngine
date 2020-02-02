/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine.jclient.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import ru.skycrafting.jengine.jclient.packets.*;

/**
 *
 * @author SkyCrafting_
 */
public class NetworkRegister {

    public void register(EndPoint ep) {
        Kryo k = ep.getKryo();
        k.register(PlayerMP.class);
        k.register(PacketAuth.class);
        k.register(PacketPos.class);
        k.register(PacketChat.class);
        k.register(PacketDisconnect.class);
        k.register(PacketGet.class);
        k.register(PacketFile.class);
        k.register(PacketWorldState.class);
    }
}
