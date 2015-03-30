package ua.goit.alg.xmlparser.statemashines;

import ua.goit.alg.xmlparser.parser.ParserData;

public enum TagStates {
  INIT {
    @Override
    public TagStates next(char c, ParserData parserData) {
      TagStates result = INVALID;
      if (c == ' ') {
        result = INIT;
      }
      if (c == '<') {
        result = OPENTAG;
      }
      return result;
    }
  },
  OPENTAG{
    @Override
    public TagStates next(char c, ParserData parserData) {
      TagStates result = INVALID;
      if (c == '?') {
        result = START;
      }
      if (c == '/') {
        result = CLOSETAG;
      }
      return result;
    }
  },
  CLOSETAG{
    @Override
    public TagStates next(char c, ParserData parserData) {
      TagStates result = INVALID;
      if (c == '?') {
        result = START;
      }
      if (c == '>') {
        result = CLOSETAG;
      }
      return result;
    }
  },
  START {
    @Override
    public TagStates next(char c, ParserData parserData) {
      TagStates result = INVALID;
      if (c == '?') {
        result = INIT;
      }
      if (c == '<') {
        result = START;
      }
      return result;
    }
  },
  ELEMENT {
    @Override
    public TagStates next(char c, ParserData parserData) {
      TagStates result = INVALID;
      if (c == '?') {
        result = INIT;
      }
      if (c == '<') {
        result = START;
      }
      return result;
    }
  },
  NODE {
    @Override
    public TagStates next(char c, ParserData parserData) {
      TagStates result = INVALID;
      if (c == '<') {
        result = OPENTAG;
      } else {
        parserData.setText(parserData.getText() + c);
      }
      return result;
    }
  },
  INVALID {
    @Override
    public TagStates next(char c, ParserData parserData) {
      TagStates result = INVALID;
      return result;
    }
  };

  public abstract TagStates next(char c,  ParserData parserData);
}