/*
 * Copyright (C) 2014 Xianguang Zhou <xianguang.zhou@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.zxg.netbeans.plugin.dlang.editor.lexer;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.misc.Interval;
import org.netbeans.spi.lexer.LexerInput;

/**
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 */
public class AntlrCharStream implements CharStream {

    private class CharStreamState {
        int index;
        int line;
        int charPositionInLine;
    }

    private int line = 1;
    private int charPositionInLine = 0;
    private LexerInput input;
    private String name;
    private int index = 0;
    private List<CharStreamState> markers;
    private int markDepth = 0;
    private int lastMarker;

    public AntlrCharStream(LexerInput input, String name) {
        this.input = input;
        this.name = name;
    }

//    public String substring(int start, int stop) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    @Override
    public String getText(Interval interval) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int LT(int i) {
        return LA(i);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setCharPositionInLine(int pos) {
        this.charPositionInLine = pos;
    }

    public int getCharPositionInLine() {
        return charPositionInLine;
    }

    @Override
    public void consume() {
        int c = input.read();
        index++;
        charPositionInLine++;

        if (c == '\n') {
            line++;
            charPositionInLine = 0;
        }
    }

    @Override
    public int LA(int i) {
        if (i == 0) {
            return 0; // undefined
        }

        int c = 0;
        for (int j = 0; j < i; j++) {
            c = read();
        }
        backup(i);
        return c;
    }

    @Override
    public int mark() {
        if (markers == null) {
            markers = new ArrayList<CharStreamState>();
            markers.add(null); // depth 0 means no backtracking, leave blank
        }
        markDepth++;
        CharStreamState state;
        if (markDepth >= markers.size()) {
            state = new CharStreamState();
            markers.add(state);
        } else {
            state = (CharStreamState) markers.get(markDepth);
        }
        state.index = index;
        state.line = line;
        state.charPositionInLine = charPositionInLine;
        lastMarker = markDepth;

        return markDepth;
    }

    public void rewind() {
        rewind(lastMarker);
    }

    public void rewind(int marker) {
        CharStreamState state = (CharStreamState) markers.get(marker);
        // restore stream state
        seek(state.index);
        line = state.line;
        charPositionInLine = state.charPositionInLine;
        release(marker);
    }

    @Override
    public void release(int marker) {
        // unwind any other markers made after m and release m
        markDepth = marker;
        // release this marker
        markDepth--;
    }

    @Override
    public void seek(int index) {
        if (index < this.index) {
            backup(this.index - index);
            this.index = index; // just jump; don't update stream state (line, ...)
            return;
        }

        // seek forward, consume until p hits index
        while (this.index < index) {
            consume();
        }
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public int size() {
        return -1; //unknown...
    }

    @Override
    public String getSourceName() {
        return name;
    }

    private int read() {
        int result = input.read();
        if (result == LexerInput.EOF) {
            result = CharStream.EOF;
        }

        return result;
    }

    private void backup(int count) {
        input.backup(count);
    }
}
