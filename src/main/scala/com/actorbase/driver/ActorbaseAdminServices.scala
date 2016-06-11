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

/**
  * Insert description here
  *
  * @param
  * @return
  * @throws
  */
trait ActorbaseAdminServices extends Connector {

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def addUser(username: String): Boolean = true

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def removeUser(username: String): Boolean = ???

  /**
    * Insert description here
    *
    * @param
    * @return
    * @throws
    */
  def resetPassword(username: String): Boolean = ???

}
