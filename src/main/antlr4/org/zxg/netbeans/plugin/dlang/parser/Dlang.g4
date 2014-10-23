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

lexer grammar Dlang;

//program:;

fragment
Character : . ;

/*
EndOfFile 
    : EOF
//    | '\u0000'
//    | '\u001A'
    ;
*/

EndOfLine
    : /*'\u000D'
    | '\u000A'
    | '\u000D\u000A'
    | '\u2028'
    | '\u2029' 
    | */'\n'
    | '\r\n'
    | EOF
    ;

WhiteSpace
    : (Space | (Space WhiteSpace)) // -> channel(HIDDEN)
    ;
fragment
Space
    : '\u0020'
    | '\u0009'
    | '\u000B'
    | '\u000C'
    ;

Comment
    : BlockComment
    | LineComment
    | NestingBlockComment
    ;
fragment
BlockComment
    : '/*' Characters '*/'
    ;
fragment
LineComment
    : '//' Characters EndOfLine
    ;
fragment
NestingBlockComment:'/+' NestingBlockCommentCharacters '+/';
fragment
NestingBlockCommentCharacters
    : NestingBlockCommentCharacter
    | NestingBlockCommentCharacter NestingBlockCommentCharacters
    ;
fragment
NestingBlockCommentCharacter
    : Character
    | NestingBlockComment
    ;
fragment
Characters
    : Character
    | Character Characters
    ;

/*
Token
    : Identifier
    | StringLiteral
    | CharacterLiteral
    | IntegerLiteral
    | FloatLiteral
    | Keyword
    | Operator
    ;
*/

Operator
    : '/'
    | '/='
    | '.'
    | '..'
    | '...'
    | '&'
    | '&='
    | '&&'
    | '|'
    | '|='
    | '||'
    | '-'
    | '-='
    | '--'
    | '+'
    | '+='
    | '++'
    | '<'
    | '<='
    | '<<'
    | '<<='
    | '<>'
    | '<>='
    | '>'
    | '>='
    | '>>='
    | '>>>='
    | '>>'
    | '>>>'
    | '!'
    | '!='
    | '!<>'
    | '!<>='
    | '!<'
    | '!<='
    | '!>'
    | '!>='
    | '\'('
    | '\')'
    | '['
    | ']'
    | '{'
    | '}'
    | '?'
    | ','
    | ';'
    | ':'
    | '$'
    | '='
    | '=='
    | '*'
    | '*='
    | '%'
    | '%='
    | '^'
    | '^='
    | '^^'
    | '^^='
    | '~'
    | '~='
    | '@'
    | '=>'
    | '#'
    ;

Identifier
    : IdentifierStart
    | IdentifierStart IdentifierChars
    ;
fragment
IdentifierChars
    : IdentifierChar
    | IdentifierChar IdentifierChars
    ;
fragment
IdentifierStart
    : '_'
    | Letter
//    | UniversalAlpha
    ;
fragment
IdentifierChar
    : IdentifierStart
    | '0'
    | NonZeroDigit
    ;
fragment
Letter
    : 'a'..'z'
    | 'A'..'Z'
    ;

StringLiteral
    : WysiwygString
    | AlternateWysiwygString
    | DoubleQuotedString
    | HexString
    | DelimitedString
//    | TokenString
    ;
fragment
WysiwygString:'r' '"' WysiwygCharacters '"' StringPostfix;
fragment
AlternateWysiwygString:'`' WysiwygCharacters '`' StringPostfix;
fragment
WysiwygCharacters
    : WysiwygCharacter
    | WysiwygCharacter WysiwygCharacters
    ;
fragment
WysiwygCharacter
    : Character
    | EndOfLine
    ;
fragment
DoubleQuotedString
    : '"' DoubleQuotedCharacters '"' StringPostfix
    ;
fragment
DoubleQuotedCharacters
    :DoubleQuotedCharacter
    |DoubleQuotedCharacter DoubleQuotedCharacters
    ;
fragment
DoubleQuotedCharacter
    :Character
    |EscapeSequence
    |EndOfLine
    ;
fragment
EscapeSequence
    :'\\\''
    |'\\"'
    |'\\?'
    |'\\\\'
    |'\\0'
    |'\\a'
    |'\\b'
    |'\\f'
    |'\\n'
    |'\\r'
    |'\\t'
    |'\\v'
    |'\\x' HexDigit HexDigit
    |'\\' OctalDigit
    |'\\' OctalDigit OctalDigit
    |'\\' OctalDigit OctalDigit OctalDigit
    |'\\u' HexDigit HexDigit HexDigit HexDigit
    |'\\U' HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit HexDigit
    |'\\' NamedCharacterEntity
    ;
fragment
HexString
    :'x"' HexStringChars '"' StringPostfix
    ;
fragment
HexStringChars
    :HexStringChar
    |HexStringChar HexStringChars
    ;
fragment
HexStringChar
    :HexDigit
    |WhiteSpace
    |EndOfLine
    ;
fragment
StringPostfix
    :'c'
    |'w'
    |'d'
    ;
       
fragment
DelimitedString
    :'q"' Delimiter WysiwygCharacters MatchingDelimiter '"'
    ;
    
/*
TokenString
    :'q{' Tokens? '}'
    ;
*/

//HexDigit:('0'..'9'|'a'..'f'|'A'..'F');
//OctalDigit:('0'..'7');
fragment
NamedCharacterEntity:'&' Identifier ';';
fragment
Delimiter:'['|'('|'<'|'{';
fragment
MatchingDelimiter:']'|')'|'>'|'}';
//Tokens:Token+;

CharacterLiteral
    : '\'' SingleQuotedCharacter '\''
    ;
fragment
SingleQuotedCharacter
    : Character
    | EscapeSequence
    ;

IntegerLiteral
    :Integer
    |Integer IntegerSuffix
    ;
fragment
Integer
    :DecimalInteger
    |BinaryInteger
    |HexadecimalInteger
    ;
fragment
IntegerSuffix
    :'L'
    |'u'
    |'U'
    |'Lu'
    |'LU'
    |'uL'
    |'UL'
    ;
fragment
DecimalInteger
    :'0'
    |NonZeroDigit
    |NonZeroDigit DecimalDigitsUS
    ;
fragment
BinaryInteger
    :BinPrefix BinaryDigitsUS
    ;
fragment
BinPrefix
    :'0b'
    |'0B'
    ;
fragment
HexadecimalInteger
    :HexPrefix HexDigitsNoSingleUS
    ;
fragment
NonZeroDigit
    :'1'..'9'
    ;
fragment
DecimalDigits
    :DecimalDigit
    |DecimalDigit DecimalDigits
    ;
fragment
DecimalDigitsUS
    :DecimalDigitUS
    |DecimalDigitUS DecimalDigitsUS
    ;
fragment
DecimalDigitsNoSingleUS
    :DecimalDigit
    |DecimalDigit DecimalDigitsUS
    |DecimalDigitsUS DecimalDigit
    ;
fragment
DecimalDigitsNoStartingUS
    :DecimalDigit
    |DecimalDigit DecimalDigitsUS
    ;
fragment
DecimalDigit
    :'0'
    |NonZeroDigit
    ;
fragment
DecimalDigitUS
    :DecimalDigit
    |'_'
    ;
fragment
BinaryDigitsUS
    :BinaryDigitUS
    |BinaryDigitUS BinaryDigitsUS
    ;
fragment
BinaryDigit
    :'0'
    |'1'
    ;
fragment
BinaryDigitUS
    :BinaryDigit
    |'_'
    ;
fragment
OctalDigits
    :OctalDigit
    |OctalDigit OctalDigits
    ;
fragment
OctalDigitsUS
    :OctalDigitUS
    |OctalDigitUS OctalDigitsUS
    ;
fragment
OctalDigit:'0'..'7';
fragment
OctalDigitUS
    :OctalDigit
    |'_'
    ;
fragment
HexDigits
    :HexDigit
    |HexDigit HexDigits
    ;
fragment
HexDigitsUS
    :HexDigitUS
    |HexDigitUS HexDigitsUS
    ;
fragment
HexDigitUS
    :HexDigit
    |'_'
    ;
fragment
HexDigitsNoSingleUS
    :HexDigit
    |HexDigit HexDigitsUS
    |HexDigitsUS HexDigit
    ;
fragment
HexDigitsNoStartingUS
    :HexDigit
    |HexDigit HexDigitsUS
    ;
fragment
HexDigit
    :DecimalDigit
    |HexLetter
    ;
fragment
HexLetter
    :'a'..'f'
    |'A'..'F'
    |'_'
    ;

FloatLiteral
    :Float
    |Float Suffix
    |Integer ImaginarySuffix
    |Integer FloatSuffix ImaginarySuffix
    |Integer RealSuffix ImaginarySuffix
    ;
fragment
Float
    :DecimalFloat
    |HexFloat
    ;
fragment
DecimalFloat
    :LeadingDecimal '.'
    |LeadingDecimal '.' DecimalDigits
    |DecimalDigits '.' DecimalDigitsNoStartingUS DecimalExponent
    |'.' DecimalInteger
    |'.' DecimalInteger DecimalExponent
    |LeadingDecimal DecimalExponent
    ;
fragment
DecimalExponent
    :DecimalExponentStart DecimalDigitsNoSingleUS
    ;
fragment
DecimalExponentStart
    :'e'
    |'E'
    |'e+'
    |'E+'
    |'e-'
    |'E-'
    ;
fragment
HexFloat
    :HexPrefix HexDigitsNoSingleUS '.' HexDigitsNoStartingUS HexExponent
    |HexPrefix '.' HexDigitsNoStartingUS HexExponent
    |HexPrefix HexDigitsNoSingleUS HexExponent
    ;
fragment
HexPrefix
    :'0x'
    |'0X'
    ;
fragment
HexExponent
    :HexExponentStart DecimalDigitsNoSingleUS
    ;
fragment
HexExponentStart
    :'p'
    |'P'
    |'p+'
    |'P+'
    |'p-'
    |'P-'
    ;
fragment
Suffix
    :FloatSuffix
    |RealSuffix
    |ImaginarySuffix
    |FloatSuffix ImaginarySuffix
    |RealSuffix ImaginarySuffix
    ;
fragment
FloatSuffix
    :'f'
    |'F'
    ;
fragment
RealSuffix
    :'L'
    ;
fragment
ImaginarySuffix
    :'i'
    ;
fragment
LeadingDecimal
    :DecimalInteger
    |'0' DecimalDigitsNoSingleUS
    ;

Keyword
    :'abstract'
    |'alias'
    |'align'
    |'asm'
    |'assert'
    |'auto'

    |'body'
    |'bool'
    |'break'
    |'byte'

    |'case'
    |'cast'
    |'catch'
    |'cdouble'
    |'cent'
    |'cfloat'
    |'char'
    |'class'
    |'const'
    |'continue'
    |'creal'

    |'dchar'
    |'debug'
    |'default'
    |'delegate'
    |'delete'
    |'deprecated'
    |'do'
    |'double'

    |'else'
    |'enum'
    |'export'
    |'extern'

    |'false '
    |'final'
    |'finally'
    |'float'
    |'for'
    |'foreach'
    |'foreach_reverse'
    |'function'

    |'goto'

    |'idouble'
    |'if'
    |'ifloat'
    |'immutable'
    |'import'
    |'in'
    |'inout'
    |'int'
    |'interface'
    |'invariant'
    |'ireal'
    |'is'

    |'lazy'
    |'long'

    |'macro'
    |'mixin'
    |'module'

    |'new'
    |'nothrow'
    |'null'

    |'out'
    |'override'

    |'package'
    |'pragma'
    |'private'
    |'protected'
    |'public'
    |'pure'

    |'real'
    |'ref'
    |'return'

    |'scope'
    |'shared'
    |'short'
    |'static'
    |'struct'
    |'super'
    |'switch'
    |'synchronized'

    |'template'
    |'this'
    |'throw'
    |'true '
    |'try'
    |'typedef'
    |'typeid'
    |'typeof'

    |'ubyte'
    |'ucent'
    |'uint'
    |'ulong'
    |'union'
    |'unittest'
    |'ushort'

    |'version'
    |'void'
    |'volatile'

    |'wchar'
    |'while'
    |'with'

    |'__FILE__'
    |'__MODULE__'
    |'__LINE__'
    |'__FUNCTION__'
    |'__PRETTY_FUNCTION__'

    |'__gshared'
    |'__traits'
    |'__vector'
    |'__parameters'
    ;
