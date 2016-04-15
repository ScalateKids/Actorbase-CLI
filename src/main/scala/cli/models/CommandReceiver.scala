package cli.models

class CommandReceiver(params: Map[Any, Any]) {
  def insert() : String = {
    var result : String = "[INSERT]\n"
    for ((k, v) <- params) {
      result += s"$k -> $v\n"
    }
    result
  }
  def export() : String = {
    var result : String = "[EXPORT]\n"
    for ((k, v) <- params) {
      result += s"$k -> $v\n"
    }
    result
  }
  def login() : String = {
    var result : String ="[LOGIN]\n"
    for((k,v) <- params) {
      result += s"$k -> $v\n"
    }
    result
  }
}