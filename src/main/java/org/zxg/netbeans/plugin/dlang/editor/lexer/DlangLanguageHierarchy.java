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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.zxg.netbeans.plugin.dlang.parser.Dlang;

/**
 *
 * @author Xianguang Zhou <xianguang.zhou@outlook.com>
 */
public class DlangLanguageHierarchy extends LanguageHierarchy<DlangTokenId> {

    private static List<DlangTokenId> tokens;
    private static Map<Integer, DlangTokenId> idToToken;

    private static void init() {
//        TokenType[] tokenTypes = TokenType.values();
//        tokens = new ArrayList<DlangTokenId>();
//        for (TokenType tokenType : tokenTypes) {
//            tokens.add(new DlangTokenId(tokenType.toString(), tokenType.category, tokenType.id));
//        }
        tokens = Arrays.asList(new DlangTokenId[]{
            new DlangTokenId("Operator", "operator", 4),
            new DlangTokenId("EOF", "whitespace", Dlang.EOF),
            new DlangTokenId("Comment", "comment", 3),
            new DlangTokenId("Identifier", "identifier", 5),
            new DlangTokenId("EndOfLine", "whitespace", 1),
            new DlangTokenId("StringLiteral", "string", 6),
            new DlangTokenId("Keyword", "keyword", 10),
            new DlangTokenId("FloatLiteral", "number", 9),
            new DlangTokenId("WhiteSpace", "whitespace", 2),
            new DlangTokenId("IntegerLiteral", "number", 8),
            new DlangTokenId("CharacterLiteral", "string", 7),
        });
        idToToken = new HashMap<Integer, DlangTokenId>();
        for (DlangTokenId token : tokens) {
            idToToken.put(token.ordinal(), token);
        }
    }

    static synchronized DlangTokenId getToken(int id) {
        if (idToToken == null) {
            init();
        }
        return idToToken.get(id);
    }

    @Override
    protected synchronized Collection<DlangTokenId> createTokenIds() {
        if (tokens == null) {
            init();
        }
        return tokens;
    }

    @Override
    protected synchronized Lexer<DlangTokenId> createLexer(LexerRestartInfo<DlangTokenId> info) {
        return new DlangLexer(info);
    }

    @Override
    protected String mimeType() {
        return "text/x-dsrc";
    }
}
