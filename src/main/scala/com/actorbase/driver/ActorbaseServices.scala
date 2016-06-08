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

import com.actorbase.driver.client.Connector
import com.actorbase.driver.client.api.RestMethods._
import com.actorbase.driver.client.api.RestMethods.Status._
import com.actorbase.driver.data.{ ActorbaseCollection, ActorbaseCollectionMap, ActorbaseObject }
import com.actorbase.driver.exceptions.{ MalformedFileExc, WrongCredentialsExc }
import scala.io.Source

import scala.util.parsing.json._
import scala.collection.immutable.TreeMap

object ActorbaseServices {

  // def apply(): ActorbaseServices = new ActorbaseServices("127.0.0.1", 9999) with Connector

  // def apply(address: String): ActorbaseServices = new ActorbaseServices(address, 9999) with Connector

  // def apply(address: String, port: Int): ActorbaseServices = new ActorbaseServices(address, port) with Connector

  case class Connection(username: String, password: String, address: String, port: Int)

}

/**
  * Insert description here
  *
  * @param
  * @return
  * @throws
  */
class ActorbaseServices (address: String = "127.0.0.1", port: Int = 9999) (implicit val scheme: String = "http://") {

  this: Connector =>

  val uri: String = scheme + address + ":" + port

  implicit val connection = ActorbaseServices.Connection("admin", "Actorb4se", address, port)

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def insertTo(collection: String, update: Boolean, kv: (String, Any)*): Unit = {
    kv.foreach {
      case (k, v) =>
        if (!update)
          requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collection + "/" + k withBody serialize2byteArray(v) withMethod POST send()
        else
          requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collection + "/" + k withBody serialize2byteArray(v) withMethod PUT send()
    }
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def insertTo[A >: Any](collection: String, update: Boolean, kv: ActorbaseObject[A]): Unit = this.insertTo(collection, update, kv.toSeq:_*)

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def removeFrom(collection: String, keys: String*): Boolean = {
    keys.foreach { key =>
      requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collection + "/" + key withMethod DELETE send()
    }
    true // must be checked / exceptions
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def find[A >: Any](key: String, collections: String*): ActorbaseObject[A] = {
    var buffer: Map[String, Any] = Map[String, Any]().empty
    collections.foreach { collectionName =>
      val response = requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collectionName + "/" + key withMethod GET send()
      if (response.statusCode == OK)
        response.body map { content =>
          buffer ++= Map(JSON.parseFull(content).get.asInstanceOf[Map[String, List[Double]]].transform((k, v) => deserializeFromByteArray(v.map(_.toByte).toArray)).toArray:_*)
        } getOrElse (Map[String, Any]().empty)
    }
    ActorbaseObject(buffer)
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def changePassword(newpassword: String): Boolean = ???

  /**
    * Return a list of collection name stored remotely on the server
    *
    * @param
    * @return a List[String] contained the collection names
    * @throws
    */
  def listCollections : List[String] = {
    val response =
      requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/listcollection" withMethod GET send()
    if(response.statusCode == OK)
      JSON.parseFull(response.body.get).getOrElse(List()).asInstanceOf[List[String]]
    else List()
  }

  /**
    * Return a list of collections, consider an object ActorbaseCollectionMap
    *
    * @param
    * @return an ActorbaseCollectionMap containing a map of collections
    * @throws
    */
  def getCollections: ActorbaseCollectionMap = ???

  /**
    * Retrieves an entire collection from server given the name. A collection is
    * composed by an owner, a collection name and a list of key/value pairs.
    * Keys are represented as String, value can be anything, from primitive
    * types to custom objects. This tuples are stored inside the server as array
    * of bytes; sending a getCollection request call for a marshaller on server
    * side that convert to JSON string all contents of the collection requested,
    * then, the value as Array[Byte] stream is deserialized to the original
    * object stored inside the database.
    *
    * @param collectionName a String representing the collection to fetch
    * @return an object of type ActorbaseCollection, traversable with foreach,
    * containing a list of ActorbaseObject, representing key/value type object
    * of Actorbase
    * @throws
    */
  def getCollection(collectionName: String): ActorbaseCollection = {
    var buffer: TreeMap[String, Any] = TreeMap[String, Any]().empty
    val response = requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collectionName + "/" withMethod GET send()
    if (response.statusCode == OK) {
      val mapObject = JSON.parseFull(response.body.get).get.asInstanceOf[Map[String, Any]]
      buffer = TreeMap(mapObject.get("map").get.asInstanceOf[Map[String, List[Double]]].transform((k, v) => deserializeFromByteArray(v.map(_.toByte).toArray)).toArray:_*)
    }
    ActorbaseCollection("anonymous", collectionName, buffer)(connection, scheme)
  }

  /**
    * Add a collection on server side of Actorbase
    *
    * @param collectionName a String representing the collection name to be retrieved from the server
    * @return an ActorbaseCollection representing a collection stored on Actorbase
    * @throws
    */
  def addCollection(collectionName: String): ActorbaseCollection = {
    val response = requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collectionName withMethod POST send() // control response
    ActorbaseCollection("admin", collectionName)(connection, scheme) // stub owner
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def addCollection(collection: ActorbaseCollection): ActorbaseCollection = {
    val response =
      requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collection.collectionName withMethod POST send() // control response and add payload to post
    collection
  }

  /**
    * Wipe out the entire database by dropping every collection inside the
    * system
    *
    * @param
    * @return
    * @throws
    */
  def dropCollections: Boolean = {
    val response = requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections" withMethod DELETE send()
    if(response.statusCode != OK)
      false
    else true
  }

  /**
    * Drop one or more specified collections from the database, silently fail in
    * case of no match of the specified collections
    *
    * @param collections a vararg of String, represents a sequence of collections to be removed from the system
    * @return Unit, no return value
    * @throws WrongCredentialsExc in case of unathorized section reply from the system
    */
  @throws(classOf[WrongCredentialsExc])
  def dropCollections(collections: String*): Boolean = {
    collections.foreach { collectionName =>
      val response = requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collectionName withMethod DELETE send()
      response.statusCode match {
        case 401 | 403 => throw WrongCredentialsExc("Attempted a request without providing valid credentials")
        case _ => // all ok
      }
    }
    true // to be checked / exc
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  @throws(classOf[MalformedFileExc])
  def importFromFile(path: String): Boolean = {
    try {
      val json = Source.fromFile(path).getLines.mkString
      val mapObject = JSON.parseFull(json).get.asInstanceOf[Map[String, Any]]
      val collectionName = mapObject.get("collection").getOrElse("NoName")
      val buffer = mapObject.get("map").get.asInstanceOf[Map[String, Any]]
      buffer map { x =>
        val response = requestBuilder withCredentials("admin", "Actorb4se") withUrl uri + "/collections/" + collectionName + "/" + x._1 withBody serialize2byteArray(x._2) withMethod POST send()
        response.statusCode match {
          case 401 | 403 => throw WrongCredentialsExc("Attempted a request without providing valid credentials")
          case _ => // all ok
        }
      } //getOrElse throw MalformedFileExc("Malformed json file")
    } catch {
      case nse: NoSuchElementException => throw MalformedFileExc("Malformed json file")
      case wce: WrongCredentialsExc => throw wce
      case mfe: MalformedFileExc => throw mfe
    }
    true
  }

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def exportToFile(path: String): Boolean = {
    listCollections map (getCollection(_).export(path))
    true
  }

  /**
    * Shutdown the connection with the server
    */
  def logout() : Unit = println("logout")

}
