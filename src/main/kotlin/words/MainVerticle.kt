package words

import io.vertx.reactivex.core.AbstractVerticle
import words.model.Definition
import words.model.Word
import words.repository.WordsRepository

class MainVerticle : AbstractVerticle() {
    override fun start() {
        val db = Database.setup()
        val wordsRepository = WordsRepository(db)

        val server = vertx.createHttpServer()
        server.requestStream().toFlowable().subscribe { req ->
            wordsRepository.insert(Word(
                english = Definition(word = "table", description = "")
            ))

            req.response().end("Hello Vert.x!")
        }
        server.listen(8080)
    }
}