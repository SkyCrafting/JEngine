/*
 * ���� �� ����, ������� ����� �������� �����, �� ��� ����� �� �����!
 */
package ru.skycrafting.jengine.jclient.network;

/**
 *
 * @author SkyCrafting_
 */
public enum ConnectionState {
    CONNECTED,
    DISCONNECTED,
    AUTH_EXPECT,
    AUTH_DONE,
    AUTH_ADDPLAYER;
}
