/*
 * ���� �� ����, ������� ����� �������� �����, �� ��� ����� �� �����!
 */
package ru.skycrafting.jengine.world.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import ru.skycrafting.jengine.TheEngine;

/**
 *
 * @author SkyCrafting_
 */
public class PlayerChat {

    private String chatMsg = "";
    private int newMsg = 1;

    private final ArrayList<String> chatList = new ArrayList<>();
    private boolean renderChat = false;
    private final TheEngine instance;

    public PlayerChat(TheEngine instance) {
        this.instance = instance;
    }

    private int dlina = 1;
    private String max = "";

    public void render(int height, int width, Graphics g) {
        /* <-- Render chat --> */
        if (dlina <= 300) {
            dlina = 300;
        }

        g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.73f));
        g.fillRect(5, height - 230, dlina + 13, 160);
        if (renderChat) {
            g.fillRect(5, height - 64, width - 25, 20);
            g.setColor(Color.red);
            g.drawRect(5, height - 64, width - 25, 20);
            g.setColor(Color.YELLOW);
            g.drawString(chatMsg, 10, height - 50);
            g.drawString("|", g.getFontMetrics().stringWidth(chatMsg) + 12, height - 50);
        }

        dlina = g.getFontMetrics().stringWidth(max) + 12;

        for (int i = 0; i < chatList.size(); i++) {
            int visota = ((height - 60) + i * 15) - (chatList.size() * 16) + newMsg;
            g.setColor(Color.green);
            if (visota >= 301) {
                g.drawString(chatList.get(i), 10, visota);
            }
        }

    }

    public void addChatHistoryMessage() {
        chatList.add(chatMsg);
        newMsg++;
        max = Collections.max(chatList, Comparator.comparing(s -> s.length()));
    }

    public String getChatMsg() {
        return chatMsg;
    }

    public void setChatMsg(String chatMsg) {
        this.chatMsg = chatMsg;
    }

    public boolean isRenderChat() {
        return renderChat;
    }

    private void setRenderChat(boolean renderChat) {
        this.renderChat = renderChat;
    }
    private boolean inChat = false;

    public void keysDown(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_SLASH || e.getKeyChar() == '.') && !inChat) {
            inChat = true;
            setChatMsg("");
            setRenderChat(true);
            instance.getThePlayer().getPlayerHandler().setBlockedInput(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && inChat) {
            inChat = false;
            setRenderChat(false);
            addChatHistoryMessage();
            instance.getThePlayer().getPlayerHandler().setBlockedInput(false);
        }

        if (inChat) {
            if (e.getKeyCode() != KeyEvent.VK_ENTER
                    && e.getKeyCode() != KeyEvent.VK_BACK_SPACE
                    && e.getKeyCode() != KeyEvent.CTRL_MASK
                    && e.getKeyCode() != KeyEvent.VK_CONTROL
                    && e.getKeyCode() != KeyEvent.VK_SHIFT
                    && e.getKeyCode() != KeyEvent.VK_ALT
                    && e.getKeyCode() != KeyEvent.VK_CAPS_LOCK) {
                setChatMsg(getChatMsg() + e.getKeyChar());
            }
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (getChatMsg().length() >= 1) {
                    setChatMsg(handler(getChatMsg()));
                }
            }
        }
    }

    private String handler(String str) {
        return str.substring(0, str.length() - 1);
    }

}
