/**
  * The MIT License (MIT)
  * <p/>
  * Copyright (c) 2016 ScalateKids
  * <p/>
  * Permission is hereby granted, free of charge, to any person obtaining a copy
  * of this software and associated documentation files (the "Software"), to deal
  * in the Software without restriction, including without limitation the rights
  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  * copies of the Software, and to permit persons to whom the Software is
  * furnished to do so, subject to the following conditions:
  * <p/>
  * The above copyright notice and this permission notice shall be included in all
  * copies or substantial portions of the Software.
  * <p/>
  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  * SOFTWARE.
  * <p/>
  * @author Scalatekids TODO DA CAMBIARE
  * @version 1.0
  * @since 1.0
  */

package com.actorbase.cli.controllers

import org.scalatest.FlatSpec
import com.actorbase.cli.controllers
import com.actorbase.cli.views.ResultView
import com.actorbase.cli.models.CommandInvoker

class GrammarParserSpec extends FlatSpec {

  "GrammarParser.parseInput" should "parse 'listCollections' command" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("listCollections") === true)
  }

  it should "parse 'logout' command" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("logout") === true)
  }

  it should "parse a single key as first argument in export command e.g. 'export key to path'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("export key to path") === true)
  }

  it should "parse list as first argument in export command e.g. 'export key1,key2,key3 to path'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("export key1,key2,key3 to path") === true)
  }

  it should "parse 'createCollection customer'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("createCollection customer") === true)
  }

  it should "parse 'help customer'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("help") === true)
  }

  it should "parse 'addUser Michelino'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("addUser Michelino") === true)
  }

  it should "parse 'removeUser Michelino'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("removeUser Michelino") === true)
  }

  it should "parse 'resetPassword Michelino'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("resetPassword Michelino") === true)
  }

  it should "parse 'exit'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("exit") === false)
  }

  it should "parse 'quit'" in {
    val grammarParser = new GrammarParser(new CommandInvoker, new ResultView)
    assert(grammarParser.parseInput("quit") === false)
  }

}
