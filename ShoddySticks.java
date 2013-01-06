/**
 * ShoddySticks: An IRC bot that implements a variant of the game of Nim.
 * Copyright (C) 2009  Cathy Fitzpatrick <cathy@cathyjf.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/

import java.util.Random;
import org.jibble.pircbot.*;

/**
 *
 * @author Cathy
 */
public class ShoddySticks {

    // The default server and channel to join if none is specified on the
    // command line.
    static final String DEFAULT_HOST = "irc.synirc.net";
    static final String DEFAULT_CHANNEL = "#example";

    static class SticksBot extends PircBot {
        private static final Random m_random = new Random();

        private String[] m_players;
        private boolean m_playing = false;
        private int m_count = 0;
        private int m_turn = 0;
        private boolean[][] m_board;

        public SticksBot() {
            setName("ShoddySticks");
        }
        private void beginMatch() {
            m_turn = m_random.nextBoolean() ? 0 : 1;
            m_board = new boolean[][] {
                { true },
                { true, true },
                { true, true, true },
                { true, true, true, true },
                { true, true, true, true, true }
            };
        }
        private void outputBoard(String channel) {
            for (int i = 0; i < m_board.length; ++i) {
                boolean empty = true;
                boolean[] row = m_board[i];
                String line = "" + (i + 1) + ": ";
                for (int j = 0; j < row.length; ++j) {
                    if (row[j]) {
                        line += "| ";
                        empty = false;
                    } else {
                        line += "_ ";
                    }
                }
                if (!empty) {
                    sendMessage(channel, line);
                }
            }
        }
        private boolean inBounds(boolean[] arr, int idx) {
            if (idx < 0)
                return false;
            if (idx >= arr.length)
                return false;
            return true;
        }
        private boolean isGameOver() {
            for (int i = 0; i < m_board.length; ++i) {
                boolean[] row = m_board[i];
                for (int j = 0; j < row.length; ++j) {
                    if (row[j])
                        return false;
                }
            }
            return true;
        }
        private void executeMove(String channel, int rowId, int low, int high) {
            boolean[] row = m_board[rowId];
            if (low > high)
                return;
            if (!inBounds(row, low) || !inBounds(row, high))
                return;
            // verify that the move is legal
            for (int i = low; i <= high; ++i) {
                if (!row[i])
                    return;
            }
            // execute the move
            for (int i = low; i <= high; ++i) {
                row[i] = false;
            }
            // change turns
            m_turn = 1 - m_turn;
            // check if the game is over
            if (isGameOver()) {
                sendMessage(channel, "Game over! "
                        + m_players[m_turn] + " wins!");
                m_playing = false;
            } else {
                outputBoard(channel);
                informTurn(channel);
            }
        }
        private void informTurn(String channel) {
            sendMessage(channel, "It's " + m_players[m_turn] + "'s turn!");
        }
        public int getPlayerId(String player) {
            if ((m_count < 2) || !m_playing)
                return -1;
            for (int i = 0; i < m_players.length; ++i) {
                if (player.equals(m_players[i]))
                    return i;
            }
            return -1;
        }
        public void onMessage(String channel, String sender,
                String login, String hostname, String message) {
            if (!message.startsWith("!sticks "))
                return;
            message = message.substring(8).trim();
            String[] parts = message.split(" ", 2);
            String command = parts[0];
            String argument = (parts.length > 1) ? parts[1] : "";
            if (command.equalsIgnoreCase("begin")) {
                if (!m_playing) {
                    m_playing = true;
                    m_count = 0;
                    m_players = new String[2];
                    sendMessage(channel, "Type !sticks join to join the game.");
                }
            } else if (command.equalsIgnoreCase("join")) {
                if (m_playing && (m_count < 2)) {
                    if (m_count == 1) {
                        if (m_players[0].equalsIgnoreCase(sender)) {
                            sendMessage(channel, sender
                                    + ", you're already in the game!");
                            return;
                        }
                    }
                    m_players[m_count] = sender;
                    ++m_count;
                    sendMessage(channel, sender + " has joined the game.");
                    if (m_count == 1) {
                        sendMessage(channel, "Another player is needed! "
                                + "Type !sticks join to join the game.");
                    } else {
                        sendMessage(channel, m_players[0] + " v. "
                                + m_players[1] + " begins!");
                        beginMatch();
                        outputBoard(channel);
                        informTurn(channel);
                    }
                }
            } else if (command.equalsIgnoreCase("take")) {
                if (getPlayerId(sender) != m_turn) {
                    return;
                }

                String[] args = argument.split(":");
                if (args.length == 2) {
                    try {
                        int row = Integer.valueOf(args[0]);
                        if ((row > 0) && (row < 6)) {
                            String[] range = args[1].split("-");
                            int low = Integer.valueOf(range[0]);
                            int high = low;
                            if (range.length == 2) {
                                high = Integer.valueOf(range[1]);
                            }
                            executeMove(channel, row - 1, low - 1, high - 1);
                        }
                    } catch (NumberFormatException e) {

                    }
                }
            }
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        SticksBot bot = new SticksBot();
        String host = DEFAULT_HOST;
        String channel = DEFAULT_CHANNEL;
        if (args.length > 0) {
            host = args[0];
        }
        if (args.length > 1) {
            channel = args[1];
        }
        bot.connect(host);
        bot.joinChannel(channel);
    }

}
