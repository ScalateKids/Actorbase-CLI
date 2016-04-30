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

package com.actorbase.driver

import com.actorbase.driver.client.{ActorbaseClient, SSLClient}
import com.actorbase.driver.client.api.RestMethods._
import com.actorbase.driver.client.api.RequestBuilder

/**
  * Insert description here
  *
  * @param
  * @return
  * @throws
  */
class ActorbaseDriver(address: String, port: Int = 9999) {

  /**
    * ActorbaseClient instance, with stacked trait for SSL
    * support
    */
  val client = new ActorbaseClient() with SSLClient

  val requestBuilder = RequestBuilder()

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def listCollections : Response = client.send(
    requestBuilder withUrl "https://" + address + ":" + port + "/collectionlist" withMethod GET)

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def find : Response = {
    client.send(
      requestBuilder
        .withUrl("https://" + address + ":" + port + "/collections")
        .withMethod(GET))
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def find(key: String, collection: String = "") : Response = {
    val path =
      if(!collection.isEmpty) "/" + collection + "/" + key
      else "/" + key
    client.send(
      requestBuilder
        .withUrl("https://" + address + ":" + port + "/collections/dummy" + path)
        .withMethod(GET))
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def insert(key: String, collection: String = "", json: String = "") : Response = {
    val path =
      if(!collection.isEmpty) "/" + collection + "/" + key
      else "/" + key
    client.send(
      requestBuilder
        .withUrl("https://" + address + ":" + port + "/collections" + path)
        .withBody(json)
        .withMethod(POST)
    )
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def delete(key: String, collection: String = ""): Response = {
    val path =
      if(!collection.isEmpty) "/" + collection + "/" + key
      else "/" + key
    client.send(
      requestBuilder
        .withUrl("https://" + address + ":" + port + "/collections" + path)
        .withMethod(DELETE))
  }

  /**
    * Shutdown the connection with the server
    */
  def logout() : Unit = println("logout")

}