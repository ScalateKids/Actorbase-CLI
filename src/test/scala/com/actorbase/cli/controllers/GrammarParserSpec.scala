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

import com.actorbase.cli.CLISpecs.CLIUnitSpec

import com.actorbase.cli.views.ResultView
import com.actorbase.cli.models.CommandInvoker
import com.actorbase.cli.models.CommandReceiver
import com.actorbase.driver.ActorbaseDriver
import scala.util.{ Failure, Success }

/**
  * Insert description here
  *
  * @param
  * @return
  * @throws
  */
class GrammarParserSpec extends CLIUnitSpec {

  DriverConnection.getDriver("localhost", 9994, "admin", "Actorb4se") match {

    case Success(d) =>

      "GrammarParser.parseInput" should "parse 'listCollections' command" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("listCollections") === true)
      }


      it should "parse 'addContributor user to collection read'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("addContributor testUser to testCollection read") === true)
      }

      it should "parse 'addContributor user to collection readwrite'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("addContributor testUser to testCollection readwrite") === true)
      }

      it should "parse 'removeContributor user from collection'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("removeContributor testUser from testCollection") === true)
      }

      it should "parse 'createCollection collection'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("createCollection testCollection") === true)
      }

      it should "parse 'insert (key -> value ) to collection'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("insert (key -> value ) to testCollection") === true)
      }

      it should "parse 'remove key from collection'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("remove key from testCollection") === true)
      }

      it should "parse 'find key from collection'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("find key from testCollection") === true)
      }

      it should "parse 'find key'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("find key from") === true)
      }

      it should "parse 'find from collection'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("find from testCollection") === true)
      }

      it should "parse 'find'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("find") === true)
      }

      it should "parse a single key as first argument in export command e.g. 'export key to path'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("export key to path") === true)
      }

      it should "parse list as first argument in export command e.g. 'export key1,key2,key3 to path'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("export key1,key2,key3 to path") === true)
      }

      it should "parse 'help'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("help") === true)
      }

      it should "parse a single command in the help command 'help command'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("help test") === true)
      }

      it should "parse 'addUser user'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("addUser testUser") === true)
      }

      it should "parse 'removeUser user'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("removeUser testUser") === true)
      }

      it should "parse 'resetPassword user'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("resetPassword testUser") === true)
      }
      
      it should "parse 'logout' command" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("logout") === false)
      }

      it should "parse 'exit'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("exit") === false)
      }

      it should "parse 'quit'" in {
        val grammarParser = new GrammarParser(new CommandInvoker, new ResultView, d)
        assert(grammarParser.parseInput("quit") === false)
      }

    /**
      * Error case handling tests
      */

      /*it should "return an error message while trying to login with wrong credentials" in {
        val params = Map[String,Any]("username" -> "wrongUsername", "password" -> "wrongPassword")
        val gr: CommandReceiver = new CommandReceiver(params, d)
        val response = gr.login
        println(response+"\n\n")
        response should be("UndefinedCollection")
    }*/

    /*  it should "return an error message while trying to login with wrong credentials" in {
        val params = Map[String,Any]("username" -> "wrongUsername", "password" -> "wrongPassword")
        val gr: CommandReceiver = new CommandReceiver(params, d)
        val response = gr.login
        println(response+"\n\n")
        response should be("UndefinedCollection")
      }
*/

    case Failure(e) => println(e.getMessage)

  }
}
