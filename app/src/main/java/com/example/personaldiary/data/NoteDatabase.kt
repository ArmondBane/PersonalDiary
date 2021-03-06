package com.example.personaldiary.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.personaldiary.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoField
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Note::class, Tag::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao

    class  Callback @Inject constructor(
        private val database: Provider<NoteDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val noteDao = database.get().noteDao()
            val tagDao = database.get().tagDao()

            applicationScope.launch {
                noteDao.insert(Note(
                        title = "Начало",
                        text = "Установил ежедневник. Решил, что буду пользоваться системой B-Alert.\n" +
                                "\n" +
                                "4-ый день работе, утомился, в интернете почти ничего не делал, но полон энтузиазма.\n" +
                                "Постараюсь вести дневник каждый день.\n" +
                                "Решил писать на блог каждый день, так как посещаемость блога упала в 3 раза.\n" +
                                "Начал закупаться вечными ссылками, временные поснимал:)\n" +
                                "671 место в рейтинге автономных блогов.\n" +
                                "Возникла идея организовать крупный конкурс на блоге.\n" +
                                "Также думаю, может этим дневником поделиться на блоге? Через месяц, например.",
                        lastDate = LocalDateTime.of(2012, 5, 20, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                noteDao.insert(Note(
                        title = "Еще один день",
                        text = "Смотрю онлайн бизнес семинар. Пока не вдохновил, но 14500 онлайн участников – это что-то… Мировой рекорд Гинеса.\n" +
                                "День прошел по системе B-Alert по книге “Бесцельная жизнь”. Сделал много работы и не устал. Помогает. Кстати, из-за этого я начал ввести личный дневник.\n" +
                                "Пил пиво с другом, ему через месяц в армию, возможно, как и мне. Удивительно, но когда тебе плевать до девушек, они сами лезут к тебе. Как-то договорились даже к им домой поехать, вернее они не против были, их 4ро, нас двое. Были бы немного красивее, я, наверное, не задумываясь поехал бы, но а так лежу в своей кровати. Один. 4.14 утра.\n" +
                                "Хм, может тактику полного игнорирования и к красивым девушкам применить? Может количество завтраков у красивых малознакомых девушек возрастет?:)\n" +
                                "Серьезную девушку однозначно не хочу. Армия. Не хочу, чтобы меня кто-то ждал. Вернее хочу, но не хочу думать в армии про то, с кем сейчас моя девочка и где она.\n" +
                                "5:30 – ушел спать.",
                        lastDate = LocalDateTime.of(2012, 5, 21, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                noteDao.insert(Note(
                        title = "Жара",
                        text = "Проснулся в 14.00, хотя планировал в 13.00. Заснул накануне только в 6.30 утра. Жара.\n" +
                                "\n" +
                                "Много что не успел, так как сейчас 17.20 снова иду на улицу гулять с друзьями. Все-таки почти всем нам через месяц в армию.\n" +
                                "Но за 3 часа успел сделать довольно-таки много чего, книги по тайм-менеджменту читаю однозначно не зря.\n" +
                                "Не хочу в армию в основном только потому, что жалко свои блоги и разные идеи, которые не смогу реализовать. Еще долбанное зрение. В ВДВ точно не возьмут, а так хотелось с парашюта попрыгать и нормальную физическую подготовку получить.\n" +
                                "Через 2 недели ГОС экзамены. Страшно.\n" +
                                "Хочу арбуз, жаль сейчас не август.\n" +
                                "5:10. Только пришел домой. Хочется гулять и почему-то продраться. Апатия. Наверное, снова из-за армии. Сильно хочу продраться. Если ты не выживешь в городе, то в армии точно тебе не место. Не хочу спать хочу ехать на машине под песню “Я свободен” из второго бумера. Просто ехать. Вперед.\n" +
                                "Хочу. Хочу много что. Например, девочку. Не хочу звонить к старым знакомым, с которыми можно было бы провести ночь. Даже не могу. Стер все номера таких девушек. Со злости.\n" +
                                "Пишу все это и думаю: наверное, не буду все это выкладывать на блог, а с другой стороны это плюс к моему рейтингу за открытость, некий пиар.\n" +
                                "Я еще подумаю… Но, по-моему, я уже пишу что-то лишнее. Ну и пусть. Я не стесняюсь соих желаний.\n" +
                                "6:51 – ухожу спать.",
                        lastDate = LocalDateTime.of(2012, 5, 22, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                noteDao.insert(Note(
                        title = "Отключение воды",
                        text = "Проснулся в 15.00. Отключили горячую воду, суки. Опять мыться в тазике под ковшиком. Снова как будто день уже пролетел. Нужно завязывать с этими гулянками и писать диплом…\n" +
                                "Домой пришел в 8 утра. С другом познакомились с 2мя симпатичными девушками, ночевали у него дома. В общем, день результативный. Хочу спать, завтра на работу…",
                        lastDate = LocalDateTime.of(2012, 5, 23, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()))
                tagDao.insert(Tag(
                        name = "неудачник",
                        note_id = 4))
                tagDao.insert(Tag(
                        name = "девушки",
                        note_id = 4))
            }
        }
    }
}