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

package com.actorbase.driver.data

import scala.language.implicitConversions
import scala.pickling.Defaults._

case object ActorbaseObject {
  /** Implicit conversion to Array[Byte], using BinaryPickle.PickleType object */
  implicit def ActorbaseObject2Binary(o: ActorbaseObject): Array[Byte] = {
    import scala.pickling.binary._
    o.pickle.value
  }
}

/**
  * Insert description here
  *
  * @param
  * @return
  * @throws
  */
case class ActorbaseObject(elems: Tuple2[String, Any]) {

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def getKey: String = elems._1

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def getValue: Any = elems._2

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  override def toString: String = {
    import scala.pickling.json._
    this.pickle.value
  }

}
