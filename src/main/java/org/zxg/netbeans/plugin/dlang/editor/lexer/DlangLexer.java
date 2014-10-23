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

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 */
public class DlangLexer implements Lexer<DlangTokenId> {

    private LexerRestartInfo<DlangTokenId> info;
    private org.zxg.netbeans.plugin.dlang.parser.Dlang dlangLexer;

    DlangLexer(LexerRestartInfo<DlangTokenId> info) {
        this.info = info;

//        AntlrCharStream charStream = new AntlrCharStream(info.input(), "DlangEditor");
        ANTLRInputStream charStream = new ANTLRInputStream(info.input().readText().toString());
        dlangLexer = new org.zxg.netbeans.plugin.dlang.parser.Dlang(charStream);
    }

    @Override
    public org.netbeans.api.lexer.Token<DlangTokenId> nextToken() {
        Token token = dlangLexer.nextToken();
        if (info.input().readLength() < 1) {
            return null;
        }
        DlangTokenId tokenId = DlangLanguageHierarchy.getToken(token.getType());
//        if(tokenId==null){
//            return null;
//        }
        return info.tokenFactory().createToken(tokenId);
//        if (token == null) {
//            System.out.println("token is null");
//            return null;
//        }
//        int tokenType=token.getType();
//        if (tokenType!=Dlang.EOF) {
//            DlangTokenId tokenId = DlangLanguageHierarchy.getToken(token.getType());
//            if (tokenId == null) {
//                System.out.println("tokenId is null");
//                return null;
//            }
//            org.netbeans.api.lexer.Token<DlangTokenId> nextToken=info.tokenFactory().createToken(tokenId);
//            if (nextToken == null) {
//                System.out.println("nextToken is null");
//                return null;
//            }
//            return nextToken;
//        }
//        return null;
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }

}
