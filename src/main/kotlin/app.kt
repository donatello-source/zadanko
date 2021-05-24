import com.google.gson.Gson
import spark.Request
import spark.Response
import java.sql.DriverManager
import spark.Spark.*

fun main() {
    port(5000)
    staticFiles.location("/public")
    get("/dane/1") {req, res -> dane1(req, res)}
    get("/dane/2") {req, res -> dane2(req, res)}
    get("/dane/3") {req, res -> dane3(req, res)}
    post("/add") {req, res -> dodaj(req, res)}

}
fun getHerokuPort(): Int {
    val processBuilder = ProcessBuilder()
    return if (processBuilder.environment()["PORT"] != null) {
        processBuilder.environment()["PORT"]!!.toInt()
    } else 5000
}
fun dane1(req: Request, res:Response): String{
    val conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")

    val stmt = conn.createStatement()
    val rs = stmt.executeQuery("SELECT * FROM TABELA01 WHERE \"select\"='tabela1' ")
    //
    var query = mutableListOf<Tab>()
    println(rs)
    while (rs.next()) {
        var tab:Tab = Tab(rs.getString("id").toInt(), rs.getString("radio"), rs.getString("select"))
        query.add(tab)
    }
    conn.close()
    return Gson().toJson(query)
}
fun dane2(req: Request, res:Response): String{
    val conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")

    val stmt = conn.createStatement()
    val rs = stmt.executeQuery("SELECT * FROM TABELA01 WHERE \"select\"='tabela2' ")
    //
    var query = mutableListOf<Tab>()
    println(rs)
    while (rs.next()) {
        var tab:Tab = Tab(rs.getString("id").toInt(), rs.getString("radio"), rs.getString("select"))
        query.add(tab)
    }
    conn.close()
    return Gson().toJson(query)
}
fun dane3(req: Request, res:Response): String{
    val conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")

    val stmt = conn.createStatement()
    val rs = stmt.executeQuery("SELECT * FROM TABELA01 WHERE \"select\"='tabela3' ")
    //
    var query = mutableListOf<Tab>()
    println(rs)
    while (rs.next()) {
        var tab:Tab = Tab(rs.getString("id").toInt(), rs.getString("radio"), rs.getString("select"))
        query.add(tab)
    }
    conn.close()
    return Gson().toJson(query)
}

fun dodaj(req: Request, res:Response):String {
    var a = req.body().split("&")
    var b = a[0].split("=")
    var jeden = b[1]
    b = a[1].split("=")
    var dwa = b[1]

    var conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")

    var stmt = conn.createStatement()
    var rs = stmt.executeQuery("SELECT ID FROM TABELA01 ORDER BY ID DESC LIMIT 1")
    rs.next()
    var idd = rs.getString("id").toInt() + 1

    stmt = conn.createStatement()
    var ss = stmt.executeUpdate("INSERT INTO TABELA01 VALUES(${idd},'${jeden}', '${dwa}')")
    conn.close()

    return "dodałem dane"
}
