/*
 * Если вы пидр, который хочет спиздить сорсы, то нам такие не нужны!
 */
package ru.skycrafting.jengine;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 *
 * @author SkyCrafting_
 */
public class InputHandler extends JComponent {

    private boolean map[];

    public InputHandler() {
        map = new boolean[256];

        for (int i = 0; i < map.length; i++) {
            final int keycode = i;
            

            /*Enable*/
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i * 2);
            getActionMap().put(i * 2, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    map[keycode] = true;
                }
            });

            /*Disable*/
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i * 2 + 1);
            getActionMap().put(i * 2 + 1, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    map[keycode] = false;
                }
            });
        }
    }

    public boolean[] getmap() {
        return Arrays.copyOf(map, map.length);
    }

    public boolean getKey(int keycode) {
        return map[keycode];
    }

}
