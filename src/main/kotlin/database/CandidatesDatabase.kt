package database

import database.daos.*
import database.tables.*
import database.tables.CandidateTable
import database.tables.InterviewTable
import database.tables.InterviewTypeTable
import database.tables.JobRoleTable
import database.tables.LocationTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class CandidatesDatabase private constructor() {

    val dbVersion
        get() = DB.version

    private val listeners = ArrayList<OnTransactionCommittedListener>()

    companion object {
        private const val DB_URL = "jdbc:postgresql://localhost:5432/db_course"
        private const val DB_DRIVER = "org.postgresql.Driver"
        private const val DB_USER = "grecuhinnikita"

        private val ENTITIES = arrayOf(
            JobRoleTable,
            InterviewTypeTable,
            InterviewerTable,
            CandidateTable,
            InterviewTable,
            LocationTable
        )

        private lateinit var INSTANCE: CandidatesDatabase
        private lateinit var DB: Database

        fun getInstance(): CandidatesDatabase =
            if (::INSTANCE.isInitialized) INSTANCE
            else synchronized(this) {
                INSTANCE = CandidatesDatabase()
                DB = Database.connect(url = DB_URL, driver = DB_DRIVER, user = DB_USER)
                initSchema()
                INSTANCE
            }

        private fun initSchema() {
            transaction(DB) {
                SchemaUtils.drop(*ENTITIES)
                SchemaUtils.apply {
                    createMissingTablesAndColumns(*ENTITIES)

                    InterviewTypeTable.insertDefault()
                    JobRoleEntity.insertDefaults()
                    InterviewerEntity.insertDefaults()
                    LocationEntity.insertDefaults()
                    return@transaction
                }
            }
        }

    }

    fun executeInTransaction(executable: () -> Unit) {
        transaction {
            executable()
        }
        listeners.forEach { it.onDataBaseChanged() }
    }

    fun registerListener(listener: OnTransactionCommittedListener) {
        synchronized(listeners) {
            listeners.add(listener)
            listener.onDataBaseChanged()
        }
    }

    interface OnTransactionCommittedListener {
        fun onDataBaseChanged()
    }

}